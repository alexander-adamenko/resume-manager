package com.infopulse.resumemanager.service.storingResume;

import com.infopulse.resumemanager.dto.CandidateSkillDto;

public interface CandidateSkillService {
    CandidateSkillDto saveCandidateSkill(CandidateSkillDto candidateSkilldto, long candidateId);
}
