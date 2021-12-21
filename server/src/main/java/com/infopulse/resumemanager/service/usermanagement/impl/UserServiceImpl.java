package com.infopulse.resumemanager.service.usermanagement.impl;

import com.infopulse.resumemanager.dto.UserDto;
import com.infopulse.resumemanager.dto.UserSecurDto;
import com.infopulse.resumemanager.mapper.ObjectMapper;
import com.infopulse.resumemanager.repository.RoleRepository;
import com.infopulse.resumemanager.repository.UserRepository;
import com.infopulse.resumemanager.repository.entity.User;
import com.infopulse.resumemanager.service.usermanagement.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;
    private final RoleRepository roleRepository;

    @Override
    public boolean currentUserHasRole(String roleName) {
        return getCurrentUser()
                .roles()
                .stream()
                .anyMatch(roleDto -> roleDto.name().equals(roleName));
    }

    @Override
    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        return objectMapper.userToUserDto(user);
    }

    @Override
    public UserSecurDto getCurrentUserSecurDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new UserSecurDto(authentication.getName(),
                authentication
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList());
    }

    @Override
    @Transactional
    public List<Long> updateUserRoles(Long userId, List<Long> rolesIds) {
        User user = userRepository.getById(userId);
        user.setRoles(new HashSet<>(roleRepository.findAllById(rolesIds)));
        return rolesIds;
    }

    @Override
    @Transactional
    public UserDto updateUser(Long userId, String username, String firstname, String lastname, String password) {
        User user = userRepository.getById(userId);
        if (userRepository.findByUsername(username) == null){
            user.setUsername(username);
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setPassword(passwordEncoder.encode(password));
        }
        return objectMapper.userToUserDto(user);
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = objectMapper.userDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return userDto;
    }

    @Override
    @Transactional
    public void addRoleToUser(Long userId, Long roleId) {
        User user = userRepository.getById(userId);
        user.getRoles().add(roleRepository.getById(roleId));
    }

    @Override
    @Transactional
    public void removeRoleFromUser(Long userId, Long roleId) {
        User user = userRepository.getById(userId);
        user.getRoles().remove(roleRepository.getById(roleId));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(objectMapper::userToUserDto).toList();
    }
}
