package com.infopulse.resumemanager.service;

import com.infopulse.resumemanager.dto.UserDto;
import com.infopulse.resumemanager.exception.UserAlreadyExistsException;
import com.infopulse.resumemanager.mapper.ObjectMapper;
import com.infopulse.resumemanager.repository.CandidateRepository;
import com.infopulse.resumemanager.repository.RoleRepository;
import com.infopulse.resumemanager.repository.UserRepository;
import com.infopulse.resumemanager.repository.entity.Role;
import com.infopulse.resumemanager.repository.entity.User;
import com.infopulse.resumemanager.util.JwtTokenComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class JwtUserWebService implements UserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenComponent jwtTokenComponent;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CandidateRepository candidateRepository;
    private final ObjectMapper objectMapper;
    @Autowired
    public JwtUserWebService(PasswordEncoder passwordEncoder, JwtTokenComponent jwtTokenComponent, UserRepository userRepository, RoleRepository roleRepository, CandidateRepository candidateRepository, ObjectMapper objectMapper) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenComponent = jwtTokenComponent;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.candidateRepository = candidateRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = getUser(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.roles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name())));
        return new org.springframework.security.core.userdetails.User(
                user.username(), user.password(), authorities);
    }

    public UserDto getUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found.");
        }
        return objectMapper.userToUserDto(user);
    }

    public List<UserDto> getUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(objectMapper::userToUserDto).toList();
    }

    //todo: need refactoring, all this written because
    // I don't want to create new rows in "roles" table writing basic "userRepository.save(user);"
    @Transactional
    public UserDto saveUser(UserDto userDto) throws UserAlreadyExistsException {
        User user = userRepository.findByUsername(userDto.username());
        if (user != null){
            throw new UserAlreadyExistsException(userDto.username());
        }
        user = objectMapper.userDtoToUser(userDto);
        Set<Role> roles = user.getRoles();
        user.setRoles(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        user.setId(userRepository.findByUsername(user.getUsername()).getId());
        User finalUser = user;
        roles.forEach(role -> {
            Long roleId = roleRepository.findByName(role.getName()).getId();
            if (roleId == null)
                try {
                    throw new UserAlreadyExistsException("322");//todo: should be another exception
                } catch (UserAlreadyExistsException ignored) {}
            entityManager.createNativeQuery("INSERT INTO user_roles (user_id, role_id) VALUES (?,?)")
                    .setParameter(1, finalUser.getId())
                    .setParameter(2, roleRepository.findByName(role.getName()).getId())
                    .executeUpdate();
        });
        return userDto;
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String jwtToken = jwtTokenComponent.getJwtFromRequestHeader(request);
        String username = jwtTokenComponent.extractUsername(jwtToken);
        if (username != null && jwtTokenComponent.validateToken(jwtToken, username, JwtTokenComponent.TokenType.REFRESH)) {
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User) loadUserByUsername(username);
            jwtTokenComponent.generateTokensForUser(user, response);
        }
        else {
            //todo: refactor
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public UserDto addRoleToUser(String username, String roleName) {
        User user = getCheckedUser(username);
        user.getRoles().add(getCheckedRole(roleName));
        return objectMapper.userToUserDto(user);
    }

    @Transactional
    public UserDto removeRoleFromUser(String username, String roleName) {
        User user = getCheckedUser(username);
        user.getRoles().remove(getCheckedRole(roleName));
        return objectMapper.userToUserDto(user);
    }

    private User getCheckedUser(String username){
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found.123");
        }
        return user;
    }

    private Role getCheckedRole(String roleName){
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new UsernameNotFoundException("User " + roleName + " not found.12");//todo:fix to role not found or smth else
        }
        return role;
    }
}
