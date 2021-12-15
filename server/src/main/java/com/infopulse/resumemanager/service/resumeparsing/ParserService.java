package com.infopulse.resumemanager.service.resumeparsing;

import com.infopulse.resumemanager.dto.CandidateDto;

public interface ParserService {
    CandidateDto parseResume(String fileName);
}
