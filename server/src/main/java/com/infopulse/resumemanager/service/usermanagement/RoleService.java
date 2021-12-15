package com.infopulse.resumemanager.service.usermanagement;

import com.infopulse.resumemanager.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> getRoles();
    void addRoleToUser(String userName, RoleDto roleDto);
    void remove(String userName, RoleDto roleDto);
}
