package com.infopulse.resumemanager.service.matcher;

import com.infopulse.resumemanager.dto.VacancyDto;
import com.infopulse.resumemanager.repository.CandidateRepository;
import com.infopulse.resumemanager.repository.entity.Candidate;
import com.infopulse.resumemanager.service.matcher.comparators.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CandidatesMatcher {

    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidatesMatcher(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }
    private Stream<Candidate> getStreamOfFilteredSkills(VacancyDto vacancy){

        return candidateRepository
                .findAll()
                .stream()
                .filter(cnd ->  cnd.getCandidateSkills()!=null)
                .filter(cnd -> new SkillComparator(vacancy).countMatches(cnd) > 0);
    }

    //filter by skills + sort by number of skills
    public List<Candidate> filterBySkills(VacancyDto vacancy) {
        return getStreamOfFilteredSkills(vacancy)
                .sorted(new SkillComparator(vacancy))
                .collect(Collectors.toList());
    }

    //filter by skills and level, which in demand
    // + sort by number of skills, and by level
    public List<Candidate> filterBySkillsAndLevel(VacancyDto vacancy) {
        return candidateRepository
                .findAll()
                .stream()
                .filter(cnd ->  cnd.getCandidateSkills()!=null)
                .filter(cnd -> new SkillLevelComparator(vacancy).countMatches(cnd) > 0)
                .collect(Collectors.toList());
    }

    //filter by skills and language which is in priority + sort by number skills
    public List<Candidate> filterBySkillsLang(VacancyDto vacancy, String lang) {
        return getStreamOfFilteredSkills(vacancy)
                .filter(cnd -> new LanguageComparator(lang).containsLang(cnd))
                .sorted(new SkillComparator(vacancy))
                .collect(Collectors.toList());
    }

    //filter by skills and degree from highest - Master to [lowestPossibleDegree] + sort by degree
    public List<Candidate> filterBySkillsDegree(VacancyDto vacancy, String lowestPossibleDegree) {
        DegreeComparator degreeComparator = new DegreeComparator();
        return getStreamOfFilteredSkills(vacancy)
                .filter(cnd -> cnd.getDegree()!=null)
                .filter(cnd -> degreeComparator.getActualDegree(cnd)!=null)//to exclude candidates without any degree
                .filter(cnd -> (degreeComparator.getDegreeWeights().get(degreeComparator.getActualDegree(cnd)))
                        >=degreeComparator.getDegreeWeights().get(lowestPossibleDegree.toUpperCase(Locale.ROOT)))
                //to exclude candidates
                // with degree lower than required
                .sorted(degreeComparator)
                .collect(Collectors.toList());
    }

    //filter by skills and number of feedbacks + sort by number of feedbacks
    public List<Candidate> filterBySkillsFeedbacks(VacancyDto vacancy) {
        return getStreamOfFilteredSkills(vacancy)
                .filter(cnd -> cnd.getFeedbacks()!=null)
                .filter(cnd -> cnd.getFeedbacks().size()>0)
                .sorted(new FeedbackComparator())
                .collect(Collectors.toList());
    }


}

