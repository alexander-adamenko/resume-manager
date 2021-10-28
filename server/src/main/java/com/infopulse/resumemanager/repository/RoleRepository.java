package com.infopulse.resumemanager.repository;

import com.infopulse.resumemanager.repository.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleName);

}
