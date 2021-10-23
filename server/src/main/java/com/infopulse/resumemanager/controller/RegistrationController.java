package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.UserDto;
import com.infopulse.resumemanager.exception.UserAlreadyExistsException;
import com.infopulse.resumemanager.service.JwtUserWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    private final JwtUserWebService jwtUserWebService;

    @Autowired
    public RegistrationController(JwtUserWebService jwtUserWebService) {
        this.jwtUserWebService = jwtUserWebService;
    }

    @PostMapping
    public UserDto registerUser(@Valid @RequestBody UserDto user) throws UserAlreadyExistsException {
        return jwtUserWebService.registerUser(user);
    }

}
