package com.infopulse.resumemanager.service.matcher;

import com.infopulse.resumemanager.dto.VacancyDto;
import com.infopulse.resumemanager.repository.entity.Candidate;

import java.util.List;
import java.util.stream.Stream;

public interface CandidatesMatcher {
    List<Candidate> getCandidates(VacancyDto vacancy);
    List<Candidate> filterBySkills(VacancyDto vacancy);
    List<Candidate> filterBySkillsAndLevel(VacancyDto vacancy);
    List<Candidate> filterBySkillsLang(VacancyDto vacancy, String lang);
    List<Candidate> filterBySkillsDegree(VacancyDto vacancy, String lowestPossibleDegree);
    List<Candidate> filterBySkillsFeedbacks(VacancyDto vacancy);
}
