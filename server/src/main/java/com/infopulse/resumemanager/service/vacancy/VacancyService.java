package com.infopulse.resumemanager.service.vacancy;

import com.infopulse.resumemanager.dto.VacancyDto;
import com.infopulse.resumemanager.repository.entity.Vacancy;

import java.util.List;

public interface VacancyService {
    VacancyDto createNewVacancy(VacancyDto vacancyDto);
    List<VacancyDto> getAllVacancies();
}
