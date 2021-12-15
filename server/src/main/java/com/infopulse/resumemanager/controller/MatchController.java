package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.service.matcher.CandidatesMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MatchController {
    private final CandidatesMatcher candidatesMatcher;

    @GetMapping("/match")
    @ResponseStatus(HttpStatus.OK)
    public List<CandidateDto> matchCandidates(@RequestParam Long id) {
        return candidatesMatcher.matchCandidates(id);
    }

}
