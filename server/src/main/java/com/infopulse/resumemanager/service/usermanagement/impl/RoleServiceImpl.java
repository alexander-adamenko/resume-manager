package com.infopulse.resumemanager.service.usermanagement.impl;

import com.infopulse.resumemanager.dto.RoleDto;
import com.infopulse.resumemanager.mapper.ObjectMapper;
import com.infopulse.resumemanager.repository.RoleRepository;
import com.infopulse.resumemanager.repository.UserRepository;
import com.infopulse.resumemanager.repository.entity.User;
import com.infopulse.resumemanager.service.usermanagement.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository,
                           ObjectMapper objectMapper) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }
    public List<RoleDto> getRoles(){
        return roleRepository
                .findAll()
                .stream()
                .map(objectMapper::roleToRoleDto)
                .collect(Collectors.toList());
    }

    public void addRoleToUser(String userName, RoleDto roleDto) {
        User user = userRepository.findByUsername(userName);
        user.getRoles().add(roleRepository.findByName(roleDto.name()));
        userRepository.save(user);
    }

    public void remove(String userName, RoleDto roleDto) {
        User user = userRepository.findByUsername(userName);
        user.getRoles().remove(roleRepository.findByName(roleDto.name()));
        userRepository.save(user);
    }


}
