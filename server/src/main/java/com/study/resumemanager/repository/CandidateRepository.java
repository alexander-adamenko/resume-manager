package com.study.resumemanager.repository;

import com.study.resumemanager.repository.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Candidate findByAuthor_Username(String username);
    Candidate findCandidateByFilePath(String filepath);
}
