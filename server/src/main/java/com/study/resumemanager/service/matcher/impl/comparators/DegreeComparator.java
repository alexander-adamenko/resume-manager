package com.study.resumemanager.service.matcher.impl.comparators;

import com.study.resumemanager.repository.entity.Candidate;
import com.study.resumemanager.repository.entity.enums.Degree;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DegreeComparator implements Comparator<Candidate> {
    private final Map<String, Integer> degreeWeights;

    public DegreeComparator() {
        degreeWeights = new HashMap<>();
        degreeWeights.put(Degree.NONE.name(), 0);
        degreeWeights.put(Degree.BACHELOR.name(), 1);
        degreeWeights.put(Degree.MASTER.name(), 2);
        degreeWeights.put(Degree.DOCTOR.name(), 3);
    }

    public Map<String, Integer> getDegreeWeights() {
        return degreeWeights;
    }

    @Override
    public int compare(Candidate cnd1, Candidate cnd2) {
        if (degreeWeights.get(getActualDegree(cnd2)) > degreeWeights.get(getActualDegree(cnd1)))
            return 1;
        else if (degreeWeights.get(getActualDegree(cnd2)) < degreeWeights.get(getActualDegree(cnd1)))
            return -1;
        return 0;
    }

    public String getActualDegree(Candidate candidate) {
        for (Degree d : Degree.values())
            if (candidate.getDegree().toString().toUpperCase(Locale.ROOT).contains(d.name())) {
                return d.name();
            }
        return null;
    }
}
