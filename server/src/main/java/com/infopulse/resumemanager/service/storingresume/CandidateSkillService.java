package com.infopulse.resumemanager.service.storingresume;

import com.infopulse.resumemanager.dto.CandidateSkillDto;
import com.infopulse.resumemanager.repository.entity.CandidateSkill;

import java.util.Set;

public interface CandidateSkillService {
    Set<CandidateSkillDto> getSkillsByCandidateId(Long candidateId);
    CandidateSkillDto saveCandidateSkill(CandidateSkillDto candidateSkilldto, long candidateId);
    void deleteCandidateSkill(CandidateSkill candidateSkill);
    void deleteAllSkillOfCandidate(Long candidateId);
}
