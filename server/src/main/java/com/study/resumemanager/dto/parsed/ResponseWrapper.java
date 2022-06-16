package com.study.resumemanager.dto.parsed;

import com.study.resumemanager.dto.CandidateDto;
import lombok.Data;

@Data
public class ResponseWrapper {
    private Integer status;
    private CandidateDto data;
    private String message;
}
