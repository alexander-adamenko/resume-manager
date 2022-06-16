package com.study.resumemanager.repository;

import com.study.resumemanager.repository.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Skill findByName(String name);
}
