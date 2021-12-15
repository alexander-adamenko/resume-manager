package com.infopulse.resumemanager.dto.parsed;

import com.infopulse.resumemanager.dto.CandidateDto;
import lombok.Data;

@Data
public class ResponseWrapper {
    private Integer status;
    private CandidateDto data;
    private String message;
}
