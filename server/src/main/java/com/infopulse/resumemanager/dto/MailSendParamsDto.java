package com.infopulse.resumemanager.dto;

import java.util.List;

public record MailSendParamsDto(
        List<CandidateDto> candidates,
        VacancyDto vacancy
) {
}
