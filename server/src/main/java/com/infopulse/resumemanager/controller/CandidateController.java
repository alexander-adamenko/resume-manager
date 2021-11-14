package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.repository.entity.Candidate;
import com.infopulse.resumemanager.service.storingResume.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {
    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }


    @PostMapping("/upload")
    public CandidateDto parseResume(@RequestParam MultipartFile resume) {
        return candidateService.saveCandidateResume(resume);
    }

    @GetMapping()
    public List<String> getNamesUploadedFiles(){
        return candidateService.getNamesUploadedFiles();
    }
}
