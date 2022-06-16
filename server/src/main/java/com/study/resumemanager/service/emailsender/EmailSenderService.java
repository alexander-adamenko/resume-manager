package com.study.resumemanager.service.emailsender;

import com.study.resumemanager.dto.CandidateDto;
import com.study.resumemanager.dto.VacancyDto;

import java.util.List;

public interface EmailSenderService {
    void sendInviteLetter(List<CandidateDto> candidates, VacancyDto vacancy);
}
