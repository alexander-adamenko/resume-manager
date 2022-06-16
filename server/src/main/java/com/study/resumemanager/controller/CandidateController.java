package com.study.resumemanager.controller;

import com.study.resumemanager.dto.CandidateDto;
import com.study.resumemanager.dto.parsed.ResponseWrapper;
import com.study.resumemanager.exception.FileExistsException;
import com.study.resumemanager.exception.UnsupportedFileException;
import com.study.resumemanager.service.resumeparsing.ParserService;
import com.study.resumemanager.service.storingresume.CandidateService;
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
    public ResponseWrapper uploadResume(@RequestParam MultipartFile resume) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        try {
            candidateService.saveCandidateResume(resume);
            responseWrapper.setData(parserService.parseResume(resume.getOriginalFilename()));
            responseWrapper.setStatus(200);
            responseWrapper.setMessage("Successfully parsed Resume!");
        } catch (UnsupportedFileException | FileExistsException ex) {
            responseWrapper.setMessage(ex.getMessage());
            responseWrapper.setStatus(500);
        }
        return responseWrapper;

    }

    @GetMapping()
    public List<CandidateDto> getAllCandidates(){
        return candidateService.getAllCandidates();
    }

    @GetMapping("/{id}")
    public CandidateDto getCandidateById(@PathVariable Long id){
        CandidateDto candidateById = candidateService.getCandidateById(id);
        System.out.println(candidateById);
        return candidateById;
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

    @DeleteMapping()
    public void deleteCandidateWithSuchResume(@RequestParam String fileName){
        candidateService.deleteCandidate(fileName);
    }

}
