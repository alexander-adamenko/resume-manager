package com.infopulse.resumemanager.service.usermanagement;

import com.infopulse.resumemanager.dto.UserDto;
import com.infopulse.resumemanager.dto.UserSecurDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    void addRoleToUser(Long userId, Long roleId);
    void removeRoleFromUser(Long userId, Long roleId);
    List<Long> updateUserRoles(Long userId, List<Long> rolesIds);
    UserDto updateUser(Long userId, String username, String firstname, String lastname, String password);
    UserDto getCurrentUser();
    UserSecurDto getCurrentUserSecurDto();
    boolean currentUserHasRole(String roleName);
    List<UserDto> getAllUsers();
}
