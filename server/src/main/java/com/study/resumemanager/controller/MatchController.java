package com.study.resumemanager.controller;

import com.study.resumemanager.dto.CandidateDto;
import com.study.resumemanager.service.matcher.CandidatesMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MatchController {
    private final CandidatesMatcher candidatesMatcher;

    @GetMapping("/match/{vacancyId}")
    public List<CandidateDto> matchCandidates(@PathVariable Long vacancyId) {
        return candidatesMatcher.matchCandidates(vacancyId);
    }

}
