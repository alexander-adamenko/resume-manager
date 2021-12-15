package com.infopulse.resumemanager.service.matcher.impl.comparators;


import com.infopulse.resumemanager.repository.entity.Candidate;
import com.infopulse.resumemanager.repository.entity.CandidateSkill;

import java.util.Comparator;
import java.util.Locale;

public record LanguageComparator(String lang) implements Comparator<Candidate> {
    @Override
    public int compare(Candidate cnd1, Candidate cnd2) {
        if (containsLang(cnd2) && !containsLang(cnd1)) return 1;
        else if (!containsLang(cnd2) && containsLang(cnd1)) return -1;
        return 0;
    }

    public boolean containsLang(Candidate cnd) {
        for (CandidateSkill candidateSkill : cnd.getCandidateSkills()) {
            if (candidateSkill
                    .getSkill()
                    .getName()
                    .toUpperCase(Locale.ROOT)
                    .contains(lang.toUpperCase(Locale.ROOT))) return true;
        }
        return false;
    }
}