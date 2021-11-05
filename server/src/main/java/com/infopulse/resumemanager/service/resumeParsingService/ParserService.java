package com.infopulse.resumemanager.service.resumeParsingService;

import com.infopulse.resumemanager.record.parsed.CandidateExpand;
import org.springframework.web.multipart.MultipartFile;

public interface ParserService {
    CandidateExpand parseResume(MultipartFile file);
}
