package com.infopulse.resumemanager.service;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.dto.UserDto;
import com.infopulse.resumemanager.dto.UserFullDto;
import com.infopulse.resumemanager.exception.UserAlreadyExistsException;
import com.infopulse.resumemanager.mapper.ObjectMapper;
import com.infopulse.resumemanager.repository.CandidateRepository;
import com.infopulse.resumemanager.repository.UserRepository;
import com.infopulse.resumemanager.repository.entity.Candidate;
import com.infopulse.resumemanager.repository.entity.Role;
import com.infopulse.resumemanager.repository.entity.User;
import com.infopulse.resumemanager.util.JwtTokenComponent;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JwtUserWebService implements UserDetailsService {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private final JwtTokenComponent jwtTokenComponent;
    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;
    private final ObjectMapper objectMapper;
    @Autowired
    public JwtUserWebService(JwtTokenComponent jwtTokenComponent, UserRepository userRepository, CandidateRepository candidateRepository) {
        this.jwtTokenComponent = jwtTokenComponent;
        this.userRepository = userRepository;
        this.candidateRepository = candidateRepository;
        this.objectMapper = Mappers.getMapper(ObjectMapper.class);
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public String createAuthenticationToken(UserDto authenticationRequest) {
        authenticate(authenticationRequest.username(), authenticationRequest.password());
        UserDetails userDetails = loadUserByUsername(
                authenticationRequest.username());
        return jwtTokenComponent.generateToken(userDetails);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = getUser(username);

        class MyRole implements GrantedAuthority{
            String authority;

            public MyRole(String authority) {
                this.authority = authority;
            }

            @Override
            public String getAuthority() {
                return authority;
            }
        }

        List<MyRole> authorities = new ArrayList<>();
        authorities.add(new MyRole(userDto.role().name()));
        return new org.springframework.security.core.userdetails.User(
                userDto.username(), userDto.password(), authorities);
    }

    public UserDto getUser(String username) throws UsernameNotFoundException {
        return objectMapper.userToUserDto(findUser(username));
    }
    private User findUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found.");
        }
        return user;
    }

    public UserFullDto getFullUser(String username) {
        UserDto userDto = getUser(username);
        Candidate candidate = candidateRepository.findByAuthor_Username(username);
        ObjectMapper objectMapper = Mappers.getMapper(ObjectMapper.class);
        CandidateDto candidateDto = objectMapper.candidateToCandidateDto(candidate);
        Set<CandidateDto> candidates = new HashSet<>();
        candidates.add(candidateDto);
        UserFullDto userFullDto = objectMapper.userDtoToUserFullDto(userDto, candidates);
        System.out.println(userDto);
        System.out.println(candidateDto);
        System.out.println(userFullDto);
        System.out.println(objectMapper.userToUserFullDto(userRepository.findByUsername(username)));
        return  userFullDto;
    }

    public UserDto registerUser(UserDto userDto) throws UserAlreadyExistsException {
        User userWhoMakeRegistration = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(userWhoMakeRegistration.getRole() != Role.ADMIN){
            throw new AccessDeniedException("Your role is too low for this operation.");
        }
        User user = userRepository.findByUsername(userDto.username());
        if (user != null){
            throw new UserAlreadyExistsException(userDto.username());
        }
        return objectMapper.userToUserDto(userRepository.save(objectMapper.userDtoToUser(userDto)));
    }
}
