package com.infopulse.resumemanager.service.usermanagement;

import com.infopulse.resumemanager.dto.RoleDto;
import com.infopulse.resumemanager.dto.UserDto;

import java.util.Set;

public interface UserService {
    UserDto getCurrentUser();
    StringBuilder update(String oldUserName, String lastname, String firstname,
                          String username, String oldPassword, String newPassword);
    void updateUserRoles(String userName, Set<RoleDto> roles);
    void addRoleToUser(String userName, RoleDto roleDto);
}
