package com.infopulse.resumemanager.service;

import com.infopulse.resumemanager.dto.UserDto;
import com.infopulse.resumemanager.mapper.ObjectMapper;
import com.infopulse.resumemanager.repository.UserRepository;
import com.infopulse.resumemanager.repository.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, ObjectMapper objectMapper,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return objectMapper.userToUserDto(userRepository.findByUsername(authentication.getName()));
    }

    public boolean update(String oldUserName, String lastname, String firstname,
                           String username, String oldPassword, String newPassword){
        User user = userRepository.findByUsername(oldUserName);
        if(passwordEncoder.matches(oldPassword, user.getPassword())) {
            objectMapper.updateUserFromDto(new UserDto(username, user.getPassword(), firstname, lastname,
                    null), user);
            if(!newPassword.equals("")) user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        else return false;
    }
}
