package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.UserDto;
import com.infopulse.resumemanager.exception.UserAlreadyExistsException;
import com.infopulse.resumemanager.service.JwtUserWebService;
import com.infopulse.resumemanager.service.usermanagement.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final JwtUserWebService jwtUserWebService;
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok().body(jwtUserWebService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid UserDto userDto) throws UserAlreadyExistsException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/user/save").toUriString());
        return ResponseEntity.created(uri).body(jwtUserWebService.saveUser(userDto));
    }

    @PostMapping("/user/add-role")
    public ResponseEntity<UserDto> addRoleToUser(@RequestParam String username, @RequestParam String roleName) {
        return ResponseEntity.ok().body(jwtUserWebService.addRoleToUser(username, roleName));
    }
    @PostMapping("/user/delete-role/{username}/{roleName}")
    public ResponseEntity<UserDto> deleteRoleFromUser(@PathVariable String username, @PathVariable String roleName) {
        return ResponseEntity.ok().body(jwtUserWebService.removeRoleFromUser(username, roleName));
    }

    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        jwtUserWebService.refreshToken(request, response);
    }

    @GetMapping("/is-authenticated")
    public Map<String, String> isAuthenticated() {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("username", "username");
        return jsonMap;
    }

    @GetMapping("/current-user")
    public UserDto curUser() {
        return userService.getCurrentUser();
    }

    @PutMapping("/{oldUserName}")
    public StringBuilder update(@PathVariable String oldUserName, @RequestParam String lastname,
                          @RequestParam String firstname, @RequestParam String username,
                          @RequestParam String oldPassword, @RequestParam String newPassword){
        return userService.update(oldUserName, lastname, firstname, username, oldPassword, newPassword);
    }

}
