package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.service.resumeParsingService.impl.ParserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")

public class ParserController {

    private final ParserServiceImpl parserService;

    @Autowired
    public ParserController(ParserServiceImpl parserServiceImpl) {
        this.parserService = parserServiceImpl;
    }

    @PostMapping("/upload")
    public CandidateDto parseResume(@RequestParam MultipartFile resume) {
        CandidateDto candidateDto = null;
        try {
            candidateDto = parserService.parseResume(resume);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return candidateDto;
    }


}
