package com.study.resumemanager.service.matcher.impl.comparators;

import com.study.resumemanager.dto.VacancyDto;
import com.study.resumemanager.dto.VacancySkillDto;
import com.study.resumemanager.repository.entity.Candidate;
import com.study.resumemanager.repository.entity.CandidateSkill;

import java.util.Comparator;

public record SkillComparator(
        VacancyDto vacancy) implements Comparator<Candidate> {

    @Override
    public int compare(Candidate cnd1, Candidate cnd2) {
        if (countMatches(cnd2) > countMatches(cnd1)) return 1;
        else if (countMatches(cnd2) < countMatches(cnd1)) return -1;
        return 0;
    }

    public int countMatches(Candidate cnd) {
        int count = 0;
        for (VacancySkillDto vacancySkill : vacancy.vacancySkills()) {
            for (CandidateSkill candidateSkill : cnd.getCandidateSkills()) {
                if (vacancySkill.skill().name()
                        .equals(candidateSkill.getSkill().getName())){
                    count++;
                }
            }
        }
        return count;
    }
}
