package com.study.resumemanager.service.vacancy;

import com.study.resumemanager.dto.VacancyDto;

import java.util.List;

public interface VacancyService {
    VacancyDto createVacancy(VacancyDto vacancyDto);
    List<VacancyDto> getAllVacancies();
    VacancyDto getVacancy(Long id);
    VacancyDto updateVacancy(VacancyDto vacancyDto);
}
