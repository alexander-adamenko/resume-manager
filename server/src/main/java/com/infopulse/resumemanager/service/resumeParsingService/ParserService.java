package com.infopulse.resumemanager.service.resumeParsingService;

import com.infopulse.resumemanager.dto.CandidateDto;
import org.springframework.web.multipart.MultipartFile;

public interface ParserService {
    CandidateDto parseResume(MultipartFile file);
}
