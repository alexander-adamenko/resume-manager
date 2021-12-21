package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.UserDto;
import com.infopulse.resumemanager.exception.UserAlreadyExistsException;
import com.infopulse.resumemanager.service.usermanagement.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Valid UserDto userDto) throws UserAlreadyExistsException {
        return userService.createUser(userDto);
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
        return userService.getCurrentUserDto();
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestParam String username,
                                    @RequestParam String firstName, @RequestParam String lastName,
                                    @RequestParam String password){
        return userService.updateUser(userId, username, firstName, lastName, password);
    }

}
