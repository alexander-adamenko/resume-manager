package com.infopulse.resumemanager.service.matcher.impl.comparators;

import com.infopulse.resumemanager.repository.entity.Candidate;

import java.util.Comparator;

public record FeedbackComparator() implements Comparator<Candidate> {

    @Override
    public int compare(Candidate cnd1, Candidate cnd2) {
        if (cnd2.getFeedbacks().size() > cnd1.getFeedbacks().size()) return 1;
        else if (cnd2.getFeedbacks().size() < cnd1.getFeedbacks().size()) return -1;
        return 0;
    }
}