package com.infopulse.resumemanager.repository;

import com.infopulse.resumemanager.repository.entity.CandidateSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Long> {
    @Modifying
    @Query("delete from CandidateSkill cs where cs.candidate.id=:candidateId")
    void deleteAllSkillsOfCandidate(@Param("candidateId") Long candidateId);

}
