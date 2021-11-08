package com.infopulse.resumemanager.service.vacancy.impl;

import com.infopulse.resumemanager.dto.VacancyDto;
import com.infopulse.resumemanager.mapper.ObjectMapper;
import com.infopulse.resumemanager.repository.UserRepository;
import com.infopulse.resumemanager.repository.VacancyRepository;
import com.infopulse.resumemanager.repository.entity.User;
import com.infopulse.resumemanager.repository.entity.Vacancy;
import com.infopulse.resumemanager.service.vacancy.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public VacancyServiceImpl(VacancyRepository vacancyRepository, UserRepository userRepository, ObjectMapper objectMapper) {
        this.vacancyRepository = vacancyRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public VacancyDto createNewVacancy(VacancyDto vacancyDto) {
        if (vacancyDto == null) throw new IllegalArgumentException("Vacancy can`t be null");
        User userWhoAddVacancy = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Vacancy vacancy = objectMapper.vacancyDtoToVacancy(vacancyDto);
        vacancy.setAuthor(userWhoAddVacancy);
        return objectMapper.vacancyToVacancyDto(vacancyRepository.save(vacancy));
    }

    @Override
    public List<VacancyDto> getAllVacancies() {
        String usernameWhoRequesting = SecurityContextHolder.getContext().getAuthentication().getName();
        return vacancyRepository.findAll().stream()
                .filter(vacancy -> vacancy.getAuthor().getUsername().equals(usernameWhoRequesting))
                .map(objectMapper::vacancyToVacancyDto)
                .collect(Collectors.toList());
    }
}
