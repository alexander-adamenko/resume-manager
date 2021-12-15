package com.infopulse.resumemanager.service.resumeparsing.impl;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.dto.parsed.ExtendedCandidate;
import com.infopulse.resumemanager.service.resumeparsing.ParserService;
import gate.util.GateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
public class ParserServiceImpl implements ParserService {
    private final ResumeParserProgram resumeParserProgram;

    @Autowired
    public ParserServiceImpl(ResumeParserProgram resumeParserProgram) {
        this.resumeParserProgram = resumeParserProgram;
    }

    @Override
    public synchronized CandidateDto parseResume(String fileName) {
        String home = System.getProperty("user.home");
        String path = home + File.separator + "resumes" + File.separator + fileName;

        ExtendedCandidate extendedCandidate;
        try {
            extendedCandidate = resumeParserProgram.parseUsingGateAndAnnie(path);
            } catch (GateException | IOException exception) {
                throw new RuntimeException(exception.getMessage());
            }
        return new ExpandCandidateToCandidateDtoMapper().map(Objects.requireNonNull(extendedCandidate));
    }
}
