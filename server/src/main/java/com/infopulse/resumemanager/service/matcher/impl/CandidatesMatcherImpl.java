package com.infopulse.resumemanager.service.matcher.impl;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.dto.VacancyDto;
import com.infopulse.resumemanager.mapper.ObjectMapper;
import com.infopulse.resumemanager.repository.CandidateRepository;
import com.infopulse.resumemanager.repository.VacancyRepository;
import com.infopulse.resumemanager.repository.entity.Candidate;
import com.infopulse.resumemanager.service.matcher.CandidatesMatcher;
import com.infopulse.resumemanager.service.matcher.impl.comparators.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class CandidatesMatcherImpl implements CandidatesMatcher {
    private final CandidateRepository candidateRepository;
    private final VacancyRepository vacancyRepository;
    private final ObjectMapper objectMapper;

    private List<CandidateDto> doMatching(VacancyDto vacancy) {
        return candidateRepository
                .findAll()
                .stream()
                .filter(hasEnoughDegree(vacancy)
                        .and(hasEnoughYearsOfExperience(vacancy))
                        .and(liveInRightLocation(vacancy))
                        .and(hasEnoughEnglishLevel(vacancy))
                        .and(hasEnoughSkillsLevel(vacancy)))
                .map(objectMapper::candidateToCandidateDto)
                .collect(Collectors.toList());
    }

    private Predicate<Candidate> hasEnoughDegree(VacancyDto vacancy) {
        return candidate -> candidate.getDegree().ordinal() >= vacancy.degree().ordinal();
    }

    private Predicate<Candidate> liveInRightLocation(VacancyDto vacancy) {
        return candidate -> candidate.getLocation().equals(vacancy.location());
    }

    private Predicate<Candidate> hasEnoughYearsOfExperience(VacancyDto vacancy) {
        return candidate -> candidate.getYearsOfExperience() >= vacancy.minimumYearsOfExperience();
    }

    private Predicate<Candidate> hasEnoughEnglishLevel(VacancyDto vacancy) {
        return candidate -> candidate.getEnglishLevel().ordinal() >= vacancy.englishLevel().ordinal();
    }

    private Predicate<Candidate> hasEnoughSkillsLevel(VacancyDto vacancy) {
        return candidate -> vacancy
                .vacancySkills()
                .stream()
                .map(objectMapper::vacancySkillDtoToVacancy)
                .allMatch(vacancySkill -> candidate
                        .getCandidateSkills()
                        .stream()
                        .anyMatch(candidateSkill -> candidateSkill
                                .getSkill()
                                .getName()
                                .equals(vacancySkill.getSkill().getName())
                                && candidateSkill
                                .getLevel()
                                .ordinal() >= vacancySkill.getLevel().ordinal()));
    }

    @Override
    public List<CandidateDto> matchCandidates(VacancyDto vacancy) {
        return doMatching(vacancy);
    }

    @Override
    public List<CandidateDto> matchCandidates(Long id) {
        VacancyDto vacancy = objectMapper.vacancyToVacancyDto(vacancyRepository.getById(id));
        return doMatching(vacancy);
    }

    @Override
    public List<Candidate> getCandidates(VacancyDto vacancy) {
        return getStreamOfFilteredSkills(vacancy)
                .collect(Collectors.toList());
    }

    //filter by skills + sort by number of skills
    @Override
    public List<Candidate> filterBySkills(VacancyDto vacancy) {
        return getStreamOfFilteredSkills(vacancy)
                .sorted(new SkillComparator(vacancy))
                .collect(Collectors.toList());
    }

    //filter by skills and level, which in demand
    // + sort by number of skills, and by level
    @Override
    public List<Candidate> filterBySkillsAndLevel(VacancyDto vacancy) {
        return candidateRepository
                .findAll()
                .stream()
                .filter(cnd -> cnd.getCandidateSkills() != null)
                .filter(cnd -> new SkillLevelComparator(vacancy).countMatches(cnd) > 0)
                .collect(Collectors.toList());
    }

    //filter by skills and language which is in priority + sort by number skills
    @Override
    public List<Candidate> filterBySkillsLang(VacancyDto vacancy, String lang) {
        return getStreamOfFilteredSkills(vacancy)
                .filter(cnd -> new LanguageComparator(lang).containsLang(cnd))
                .sorted(new SkillComparator(vacancy))
                .collect(Collectors.toList());
    }

    //filter by skills and degree from highest - Master to [lowestPossibleDegree] + sort by degree
    @Override
    public List<Candidate> filterBySkillsDegree(VacancyDto vacancy, String lowestPossibleDegree) {
        DegreeComparator degreeComparator = new DegreeComparator();
        return getStreamOfFilteredSkills(vacancy)
                .filter(cnd -> cnd.getDegree() != null)
                .filter(cnd -> degreeComparator.getActualDegree(cnd) != null)//to exclude candidates without any degree
                .filter(cnd -> (degreeComparator.getDegreeWeights().get(degreeComparator.getActualDegree(cnd)))
                        >= degreeComparator.getDegreeWeights().get(lowestPossibleDegree.toUpperCase(Locale.ROOT)))
                //to exclude candidates
                // with degree lower than required
                .sorted(degreeComparator)
                .collect(Collectors.toList());
    }

    //filter by skills and number of feedbacks + sort by number of feedbacks
    @Override
    public List<Candidate> filterBySkillsFeedbacks(VacancyDto vacancy) {
        return getStreamOfFilteredSkills(vacancy)
                .filter(cnd -> cnd.getFeedbacks() != null)
                .filter(cnd -> cnd.getFeedbacks().size() > 0)
                .sorted(new FeedbackComparator())
                .collect(Collectors.toList());
    }

    private Stream<Candidate> getStreamOfFilteredSkills(VacancyDto vacancy) {
        return candidateRepository
                .findAll()
                .stream()
                .filter(cnd -> cnd.getCandidateSkills() != null)
                .filter(cnd -> new SkillComparator(vacancy).countMatches(cnd) > 0);
    }
}
