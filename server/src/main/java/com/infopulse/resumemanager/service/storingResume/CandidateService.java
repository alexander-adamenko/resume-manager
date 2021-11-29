package com.infopulse.resumemanager.service.storingResume;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.repository.entity.Candidate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidateService {
    CandidateDto saveCandidateResume(MultipartFile file);
    List<CandidateDto> getAllCandidates();
    List<String> getNamesUploadedFiles();
    CandidateDto saveCandidateWithSkills(CandidateDto candidateDto);

}
