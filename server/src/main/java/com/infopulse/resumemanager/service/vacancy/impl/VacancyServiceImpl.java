package com.infopulse.resumemanager.service.vacancy.impl;

import com.infopulse.resumemanager.dto.VacancyDto;
import com.infopulse.resumemanager.mapper.ObjectMapper;
import com.infopulse.resumemanager.repository.SkillRepository;
import com.infopulse.resumemanager.repository.UserRepository;
import com.infopulse.resumemanager.repository.VacancyRepository;
import com.infopulse.resumemanager.repository.VacancySkillRepository;
import com.infopulse.resumemanager.repository.entity.User;
import com.infopulse.resumemanager.repository.entity.Vacancy;
import com.infopulse.resumemanager.service.vacancy.VacancyService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    @PersistenceContext
    private EntityManager entityManager;
    private final VacancyRepository vacancyRepository;
    private final VacancySkillRepository vacancySkillRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final ObjectMapper objectMapper;


    @Override
    @Transactional
    public VacancyDto createNewVacancy(VacancyDto vacancyDto) {
        if (vacancyDto == null) throw new IllegalArgumentException("Vacancy can`t be null");
        User userWhoAddVacancy = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Vacancy vacancy = objectMapper.vacancyDtoToVacancy(vacancyDto);

        vacancy.setAuthor(userWhoAddVacancy);
        vacancyRepository.save(vacancy);
        long vacId = vacancyRepository.findByDescription(vacancy.getDescription()).getId();
        vacancy.getVacancySkills().forEach(vacancySkill -> {
            entityManager.createNativeQuery("INSERT INTO vacancy_skill (vacancy_id, skill_id, level) VALUES (?,?,?)")
                    .setParameter(1, vacId)
                    .setParameter(2, skillRepository.findByName(vacancySkill.getSkill().getName()).getId())
                    .setParameter(3, vacancySkill.getLevel().name())
                    .executeUpdate();
        });
        return objectMapper.vacancyToVacancyDto(vacancyRepository.findByPositionTitle(vacancyDto.positionTitle()));
//        return vacancyDto;
    }

    @Override
    public List<VacancyDto> getAllVacancies() {
        String usernameWhoRequesting = SecurityContextHolder.getContext().getAuthentication().getName();
        return vacancyRepository.findAll().stream()
                .filter(vacancy -> vacancy.getAuthor().getUsername().equals(usernameWhoRequesting))
                .map(objectMapper::vacancyToVacancyDto)
                .collect(Collectors.toList());
    }

    @Override
    public VacancyDto getVacancy(Long id) {
        String usernameWhoRequesting = SecurityContextHolder.getContext().getAuthentication().getName();
        return objectMapper.vacancyToVacancyDto(vacancyRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public VacancyDto updateVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = vacancyRepository.findById(vacancyDto.id()).orElseThrow();
        Vacancy newVacancy = objectMapper.vacancyDtoToVacancy(vacancyDto);
        newVacancy.setAuthor(vacancy.getAuthor());
        vacancyRepository.save(newVacancy);
//        DELETE FROM Customers WHERE CustomerName='Alfreds Futterkiste';
        entityManager.createNativeQuery("DELETE FROM vacancy_skill WHERE vacancy_id=?")
                .setParameter(1, newVacancy.getId())
                .executeUpdate();
        newVacancy.getVacancySkills().forEach(vacancySkill -> {
            entityManager.createNativeQuery("INSERT INTO vacancy_skill (vacancy_id, skill_id, level) VALUES (?,?,?)")
                    .setParameter(1, newVacancy.getId())
                    .setParameter(2, skillRepository.findByName(vacancySkill.getSkill().getName()).getId())
                    .setParameter(3, vacancySkill.getLevel().name())
                    .executeUpdate();
        });
        return vacancyDto;
    }
}
