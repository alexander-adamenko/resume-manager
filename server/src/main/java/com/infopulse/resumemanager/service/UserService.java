package com.infopulse.resumemanager.service;

import com.infopulse.resumemanager.dto.UserDto;
import com.infopulse.resumemanager.mapper.ObjectMapper;
import com.infopulse.resumemanager.repository.UserRepository;
import com.infopulse.resumemanager.repository.entity.User;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    public UserService(UserRepository userRepository, ObjectMapper objectMapper,
                       PasswordEncoder passwordEncoder, Validator validator) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    public UserDto getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return objectMapper.userToUserDto(userRepository.findByUsername(authentication.getName()));
    }

    public StringBuilder update(String oldUserName, String lastname, String firstname,
                           String username, String oldPassword, String newPassword){
        StringBuilder result = new StringBuilder();

        User user = userRepository.findByUsername(oldUserName);
        if(passwordEncoder.matches(oldPassword, user.getPassword())) {

            UserDto userDto;
            if (newPassword.matches(".+"))
                userDto = new UserDto(username, newPassword, firstname, lastname, null);
            else
                userDto = new UserDto(username, null, firstname, lastname, null);

            Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        for (ConstraintViolation<UserDto> c:violations){
            result.append(c.getMessage());
            result.append("\n");
        }
            System.out.println(newPassword.matches(".+"));
        if (violations.size()==0 && newPassword.matches(".+")){
            objectMapper.updateUserFromDto(userDto, user);
            user.setPassword(passwordEncoder.encode(newPassword));
            System.out.println(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
        }
        else return result.append("Invalid old password");
        System.out.println(result);
        return result;
    }
}
