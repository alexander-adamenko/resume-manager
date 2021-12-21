package com.infopulse.resumemanager.service.matcher;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.dto.VacancyDto;
import com.infopulse.resumemanager.repository.entity.Candidate;

import java.util.List;

public interface CandidatesMatcher {
    List<CandidateDto> matchCandidates(VacancyDto vacancy);
    List<CandidateDto> matchCandidates(Long vacancyId);
//-------------------------------------------------------------------
    List<Candidate> getCandidates(VacancyDto vacancy);
    List<Candidate> filterBySkills(VacancyDto vacancy);
    List<Candidate> filterBySkillsAndLevel(VacancyDto vacancy);
    List<Candidate> filterBySkillsLang(VacancyDto vacancy, String lang);
    List<Candidate> filterBySkillsDegree(VacancyDto vacancy, String lowestPossibleDegree);
    List<Candidate> filterBySkillsFeedbacks(VacancyDto vacancy);
}
