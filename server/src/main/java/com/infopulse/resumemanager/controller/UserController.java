package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.UserDto;
import com.infopulse.resumemanager.exception.UserAlreadyExistsException;
import com.infopulse.resumemanager.service.usermanagement.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public List<UserDto> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) throws UserAlreadyExistsException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/users").toUriString());
        return ResponseEntity.created(uri).body(userService.createUser(userDto));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{userId}/roles/{roleId}")
    public void addRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        userService.addRoleToUser(userId, roleId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}/roles/{roleId}")
    public void deleteRolesFromUser(@PathVariable Long userId, @PathVariable Long roleId) {
        userService.removeRoleFromUser(userId, roleId);
    }

    @GetMapping("/current-user")
    public UserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestParam String username,
                                    @RequestParam String firstname, @RequestParam String lastname,
                                    @RequestParam String password){
        return userService.updateUser(userId, username, firstname, lastname, password);
    }

}
