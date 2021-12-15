package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.dto.VacancyDto;
import com.infopulse.resumemanager.service.matcher.CandidatesMatcher;
import com.infopulse.resumemanager.service.vacancy.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public VacancyDto saveVacancy(@RequestBody @Valid VacancyDto vacancyDto) {
        return vacancyService.createNewVacancy(vacancyDto);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<VacancyDto> getAllVacancies() {
        return vacancyService.getAllVacancies();
    }

    @PutMapping()
    public VacancyDto update(@RequestBody @Valid VacancyDto vacancyDto){
        return vacancyService.updateVacancy(vacancyDto);
    }
}
