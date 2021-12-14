package com.infopulse.resumemanager.service.emailsender;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.dto.VacancyDto;
import com.infopulse.resumemanager.repository.entity.Candidate;
import com.infopulse.resumemanager.repository.entity.Vacancy;

import java.util.List;

public interface EmailSenderService {
    void sendInviteLetter(List<CandidateDto> candidates, VacancyDto vacancy);
}
