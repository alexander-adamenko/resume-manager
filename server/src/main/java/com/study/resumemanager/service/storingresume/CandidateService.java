package com.study.resumemanager.service.storingresume;

import com.study.resumemanager.dto.CandidateDto;
import com.study.resumemanager.exception.FileExistsException;
import com.study.resumemanager.exception.UnsupportedFileException;
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
