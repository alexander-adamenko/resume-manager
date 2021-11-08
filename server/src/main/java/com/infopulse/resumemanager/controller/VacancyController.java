package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.UserDto;
import com.infopulse.resumemanager.dto.VacancyDto;
import com.infopulse.resumemanager.exception.UserAlreadyExistsException;
import com.infopulse.resumemanager.service.vacancy.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
}
