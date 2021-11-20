package com.infopulse.resumemanager.service.matcher.comparators;

import com.infopulse.resumemanager.dto.VacancyDto;
import com.infopulse.resumemanager.dto.VacancySkillDto;
import com.infopulse.resumemanager.repository.entity.Candidate;
import com.infopulse.resumemanager.repository.entity.CandidateSkill;
import com.infopulse.resumemanager.repository.entity.enums.Level;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SkillLevelComparator implements Comparator<Candidate> {
    private final VacancyDto vacancy;
    private final Map<Object, Integer> skillLevelsWeights;

    public SkillLevelComparator(VacancyDto vacancy) {
        this.vacancy = vacancy;
        skillLevelsWeights = new HashMap<>();
        skillLevelsWeights.put(Level.DEFAULT.name(), 0);
        skillLevelsWeights.put(Level.TRAINEE.name(), 1);
        skillLevelsWeights.put(Level.JUNIOR.name(), 2);
        skillLevelsWeights.put(Level.MIDDLE.name(), 3);
        skillLevelsWeights.put(Level.SENIOR.name(), 4);
    }

    @Override
    public int compare(Candidate cnd1, Candidate cnd2) {
        if (countMatches(cnd2) > countMatches(cnd1)) return 1;
        else if (countMatches(cnd2) < countMatches(cnd1)) return -1;
        return 0;
    }

    public int countMatches(Candidate cnd) {
        int count = 0;
        for (VacancySkillDto vacancySkillDto : vacancy.vacancySkills()) {
            for (CandidateSkill candidateSkill : cnd.getCandidateSkills()) {
                if (vacancySkillDto.skill().name().equals(candidateSkill.getSkill().getName())){
                    if(skillLevelsWeights.get(candidateSkill.getLevel().name())
                    >= skillLevelsWeights.get(vacancySkillDto.level().name()))
                        count++;
                }
            }
        }
        return count;
    }
}
