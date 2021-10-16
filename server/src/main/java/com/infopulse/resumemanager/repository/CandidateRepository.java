package com.infopulse.resumemanager.repository;

import com.infopulse.resumemanager.repository.entity.Candidate;
import com.infopulse.resumemanager.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Candidate findByAuthor_Username(String username);
}