package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.service.resumeParsingService.ParserService;
import com.infopulse.resumemanager.service.storingResume.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {
    private final CandidateService candidateService;
    private final ParserService parserService;

    @Autowired
    public CandidateController(CandidateService candidateService, ParserService parserService) {
        this.candidateService = candidateService;
        this.parserService = parserService;
    }


    @PostMapping("/upload")
    public CandidateDto uploadResume(@RequestParam MultipartFile resume) {
        candidateService.saveCandidateResume(resume);
        return parserService.parseResume(resume.getOriginalFilename());

    }

    @GetMapping()
    public List<CandidateDto> getAllCandidates(){
        return candidateService.getAllCandidates();
    }

    @GetMapping("/fileNames")
    public List<String> getNamesUploadedFiles(){
        return candidateService.getNamesUploadedFiles();
    }

    @GetMapping("/parse")
    public CandidateDto parseResume(@RequestParam String fileName){
        return parserService.parseResume(fileName);
    }

    @PostMapping()
    public CandidateDto saveCandidateAfterParsing(@RequestBody CandidateDto candidate) {
        return candidateService.saveCandidateWithSkills(candidate);
    }
}
