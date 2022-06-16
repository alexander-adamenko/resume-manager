package com.study.resumemanager.service.matcher;

import com.study.resumemanager.dto.CandidateDto;
import com.study.resumemanager.dto.VacancyDto;
import com.study.resumemanager.repository.entity.Candidate;

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
