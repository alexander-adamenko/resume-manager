package com.infopulse.resumemanager.service.storingResume;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.exception.FileExistsException;
import com.infopulse.resumemanager.exception.UnsupportedFileException;
import com.infopulse.resumemanager.repository.entity.Candidate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CandidateService {
    CandidateDto saveCandidateResume(MultipartFile file) throws UnsupportedFileException, FileExistsException;
    List<CandidateDto> getAllCandidates();
    CandidateDto getCandidateById(Long id);
    List<String> getNamesUploadedFiles();
    CandidateDto saveCandidateWithSkills(CandidateDto candidateDto);
    void deleteCandidate(String fileName);

}
