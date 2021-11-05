package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.service.resumeParsingService.impl.ParserServiceImpl;
import com.infopulse.resumemanager.record.parsed.CandidateExpand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class ParserController {

    private final ParserServiceImpl parserService;

    @Autowired
    public ParserController(ParserServiceImpl parserServiceImpl) {
        this.parserService = parserServiceImpl;
    }

    @PostMapping("/upload")
    public CandidateExpand parseResume(@RequestParam MultipartFile resume) {
        CandidateExpand candidateExpand = null;
        try {
            candidateExpand = parserService.parseResume(resume);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return candidateExpand;
    }


}
