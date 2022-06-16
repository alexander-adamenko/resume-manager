package com.study.resumemanager.service.storingresume;

import com.study.resumemanager.dto.CandidateSkillDto;
import com.study.resumemanager.repository.entity.CandidateSkill;

import java.util.Set;

public interface CandidateSkillService {
    Set<CandidateSkillDto> getSkillsByCandidateId(Long candidateId);
    CandidateSkillDto saveCandidateSkill(CandidateSkillDto candidateSkilldto, long candidateId);
    void deleteCandidateSkill(CandidateSkill candidateSkill);
    void deleteAllSkillOfCandidate(Long candidateId);
}
