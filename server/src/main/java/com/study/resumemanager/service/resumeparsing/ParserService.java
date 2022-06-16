package com.study.resumemanager.service.resumeparsing;

import com.study.resumemanager.dto.CandidateDto;

public interface ParserService {
    CandidateDto parseResume(String fileName);
}
