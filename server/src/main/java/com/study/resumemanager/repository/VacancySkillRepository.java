package com.study.resumemanager.repository;

import com.study.resumemanager.repository.entity.VacancySkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancySkillRepository extends JpaRepository<VacancySkill, Long> {
}
