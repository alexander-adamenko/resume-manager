package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.service.JwtUserWebService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TokenController {
    private final JwtUserWebService jwtUserWebService;

    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        jwtUserWebService.refreshToken(request, response);
    }
}
