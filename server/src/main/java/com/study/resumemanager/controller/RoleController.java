package com.study.resumemanager.controller;

import com.study.resumemanager.dto.RoleDto;
import com.study.resumemanager.service.usermanagement.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/roles")
    public List<RoleDto> getUsers() {
        return roleService.getRoles();
    }

}
