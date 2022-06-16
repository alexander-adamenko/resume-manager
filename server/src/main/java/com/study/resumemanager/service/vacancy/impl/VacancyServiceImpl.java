package com.study.resumemanager.service.vacancy.impl;

import com.study.resumemanager.dto.VacancyDto;
import com.study.resumemanager.mapper.ObjectMapper;
import com.study.resumemanager.repository.SkillRepository;
import com.study.resumemanager.repository.VacancyRepository;
import com.study.resumemanager.repository.entity.Skill;
import com.study.resumemanager.repository.entity.Vacancy;
import com.study.resumemanager.repository.entity.VacancySkill;
import com.study.resumemanager.service.usermanagement.UserService;
import com.study.resumemanager.service.vacancy.VacancyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    @PersistenceContext
    private EntityManager entityManager;
    private final VacancyRepository vacancyRepository;
    private final SkillRepository skillRepository;
    private final ObjectMapper objectMapper;
    private final UserService userService;


    @Override
    @Transactional
    public VacancyDto createVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = objectMapper.vacancyDtoToVacancy(vacancyDto);
        vacancy.setAuthor(userService.getCurrentUser());
        ListIterator<Skill> listIterator = skillRepository
                .findAllById(
                    vacancy
                            .getVacancySkills()
                            .stream()
                            .map(vacancySkill -> vacancySkill.getSkill().getId())
                            .toList())
                .listIterator();
        vacancy.getVacancySkills().forEach(vacancySkill -> {
            vacancySkill.setSkill(listIterator.next());
            vacancySkill.setVacancy(vacancy);
        });
        vacancyRepository.save(vacancy);
        return vacancyDto;
    }

    @Override
    public List<VacancyDto> getAllVacancies() {
        return vacancyRepository.findAllByAuthorUsername(userService.getCurrentUserSecurDto().username())
                .stream()
                .map(objectMapper::vacancyToVacancyDto)
                .collect(Collectors.toList());

    }

    @Override
    public VacancyDto getVacancy(Long id) {
        return objectMapper.vacancyToVacancyDto(vacancyRepository.findByIdAndAuthorUsername(id,
                userService.getCurrentUserSecurDto().username()));
    }

    @Override
    @Transactional
    public VacancyDto updateVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = objectMapper.vacancyDtoToVacancy(vacancyDto);
        vacancy.setAuthor(userService.getCurrentUser());
        Set<VacancySkill> vacancySkills = vacancy.getVacancySkills();
        vacancy.setVacancySkills(null);
        vacancyRepository.save(vacancy);

        entityManager.createNativeQuery("DELETE FROM vacancy_skill WHERE vacancy_id=?")
                .setParameter(1, vacancy.getId())
                .executeUpdate();

        if(vacancySkills.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder("INSERT INTO vacancy_skill (vacancy_id, skill_id, level) VALUES ");
            vacancySkills.forEach(vacancySkill -> {
                stringBuilder.append("(")
                        .append(vacancy.getId())
                        .append(",")
                        .append(vacancySkill.getSkill().getId())
                        .append(",'")
                        .append(vacancySkill.getLevel().name())
                        .append("'), ");
            });
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            entityManager.createNativeQuery(stringBuilder.toString()).executeUpdate();
        }
        return vacancyDto;
    }
}
