package com.study.resumemanager.service.usermanagement.impl;

import com.study.resumemanager.dto.RoleDto;
import com.study.resumemanager.mapper.ObjectMapper;
import com.study.resumemanager.repository.RoleRepository;
import com.study.resumemanager.service.usermanagement.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper;

    public List<RoleDto> getRoles(){
        return roleRepository
                .findAll()
                .stream()
                .map(objectMapper::roleToRoleDto)
                .collect(Collectors.toList());
    }
}
