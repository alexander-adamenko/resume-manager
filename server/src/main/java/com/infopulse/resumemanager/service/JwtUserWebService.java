package com.infopulse.resumemanager.service;

import com.infopulse.resumemanager.mapper.UserMapper;
import com.infopulse.resumemanager.record.UserDto;
import com.infopulse.resumemanager.repository.entity.User;
import com.infopulse.resumemanager.repository.UserRepository;
import com.infopulse.resumemanager.util.JwtTokenComponent;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserWebService implements UserDetailsService {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private final JwtTokenComponent jwtTokenComponent;
    private final UserRepository userRepository;

    @Autowired
    public JwtUserWebService(JwtTokenComponent jwtTokenComponent, UserRepository userRepository) {
        this.jwtTokenComponent = jwtTokenComponent;
        this.userRepository = userRepository;
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
        return new org.springframework.security.core.userdetails.User(
                userDto.username(), userDto.password(), new ArrayList<>());
    }

    public UserDto getUser(String username) throws UsernameNotFoundException {
        return toDto(findUser(username));
    }
    private User findUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username '" + username + "' not found.");
        }
        return user;
    }

    private UserDto toDto(User user) {
        UserMapper userMapper = Mappers.getMapper(UserMapper.class);
        return userMapper.userToUserDto(user);
    }
}
