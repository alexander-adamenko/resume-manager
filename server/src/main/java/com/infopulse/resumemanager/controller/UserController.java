package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.UserDto;
import com.infopulse.resumemanager.exception.UserAlreadyExistsException;
import com.infopulse.resumemanager.service.JwtUserWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final JwtUserWebService jwtUserWebService;

    //todo: add paginating/sorting if it will be needed
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok().body(jwtUserWebService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserDto userDto) throws UserAlreadyExistsException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/user/save").toUriString());
        return ResponseEntity.created(uri).body(jwtUserWebService.saveUser(userDto));
    }

//    http://localhost:8080/api/v1/user/add-role?username=zxczxc1&roleName=ADMIN
    @PostMapping("/user/add-role")
    public ResponseEntity<UserDto> addRoleToUser(@RequestParam String username, @RequestParam String roleName) {
        return ResponseEntity.ok().body(jwtUserWebService.addRoleToUser(username, roleName));
    }

    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        jwtUserWebService.refreshToken(request, response);
    }

    @GetMapping("/is-authenticated")
    @ResponseStatus(HttpStatus.OK)
    public String isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
