package com.study.resumemanager.controller;

import com.study.resumemanager.dto.VacancyDto;
import com.study.resumemanager.service.vacancy.VacancyService;
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
    public VacancyDto createVacancy(@RequestBody @Valid VacancyDto vacancyDto) {
        return vacancyService.createVacancy(vacancyDto);
    }

    @GetMapping()
    public List<VacancyDto> getVacancies() {
        return vacancyService.getAllVacancies();
    }

    @PatchMapping("/{vacancyId}")
    public VacancyDto updateVacancy(@PathVariable Long vacancyId, @RequestBody @Valid VacancyDto vacancyDto){
        return vacancyService.updateVacancy(vacancyDto);
    }
}
