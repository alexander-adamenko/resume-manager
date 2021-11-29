package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.RoleDto;
import com.infopulse.resumemanager.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDto>> getUsers() {
        return ResponseEntity.ok().body(roleService.getRoles());
    }

}
