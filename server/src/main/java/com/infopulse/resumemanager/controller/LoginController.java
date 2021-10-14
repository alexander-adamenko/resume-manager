package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.record.JwtResponse;
import com.infopulse.resumemanager.record.UserDto;
import com.infopulse.resumemanager.service.JwtUserWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {
    @Autowired
    private JwtUserWebService jwtUserWebService;

    @GetMapping
    public String hello() {
        return "lalala";
    }

    @PostMapping
    public JwtResponse createAuthenticationToken(@Valid @RequestBody UserDto authenticationRequest) {
        String token = jwtUserWebService.createAuthenticationToken(authenticationRequest);
        return new JwtResponse(token);
    }
}
