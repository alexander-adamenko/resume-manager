package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.JwtResponse;
import com.infopulse.resumemanager.dto.UserDto;
import com.infopulse.resumemanager.dto.UserFullDto;
import com.infopulse.resumemanager.service.JwtUserWebService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    private final JwtUserWebService jwtUserWebService;

    public LoginController(JwtUserWebService jwtUserWebService) {
        this.jwtUserWebService = jwtUserWebService;
    }

    @GetMapping
    public UserFullDto hello() {
        UserFullDto userFullDto = jwtUserWebService.getFullUser("alex");
        System.out.println(userFullDto.candidates());
        System.out.println(228);
        return userFullDto;
    }

    @PostMapping
    public JwtResponse createAuthenticationToken(@RequestBody @Valid UserDto authenticationRequest) {
        String token = jwtUserWebService.createAuthenticationToken(authenticationRequest);
        return new JwtResponse(token);
    }
}
