package com.study.resumemanager.repository;

import com.study.resumemanager.repository.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleName);

}
