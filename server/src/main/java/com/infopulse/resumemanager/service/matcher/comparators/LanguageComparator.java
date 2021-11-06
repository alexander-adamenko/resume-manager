package com.infopulse.resumemanager.service.matcher.comparators;


import com.infopulse.resumemanager.repository.entity.Candidate;
import com.infopulse.resumemanager.repository.entity.Skill;

import java.util.Comparator;

public record LanguageComparator(String lang) implements Comparator<Candidate> {
    @Override
    public int compare(Candidate cnd1, Candidate cnd2) {
        if (containsLang(cnd2) && !containsLang(cnd1)) return 1;
        else if (!containsLang(cnd2) && containsLang(cnd1)) return -1;
        return 0;
    }

    public boolean containsLang(Candidate cnd) {
//        for (Skill candidateSkill : cnd.getSkills()) {
//            if (candidateSkill.getName().equalsIgnoreCase(lang)) return true;
//        }
        return false;
    }
}
