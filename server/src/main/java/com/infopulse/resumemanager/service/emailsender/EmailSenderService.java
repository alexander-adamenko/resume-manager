package com.infopulse.resumemanager.service.emailsender;

import com.infopulse.resumemanager.repository.entity.Candidate;
import com.infopulse.resumemanager.repository.entity.Vacancy;

import java.util.List;

public interface EmailSenderService {
    void sendInviteLetter(List<Candidate> candidates, Vacancy vacancy);
}
