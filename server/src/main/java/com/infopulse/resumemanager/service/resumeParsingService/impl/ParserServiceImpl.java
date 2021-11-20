package com.infopulse.resumemanager.service.resumeParsingService.impl;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.dto.parsed.ExtendedCandidate;
import com.infopulse.resumemanager.service.resumeParsingService.ParserService;
import gate.util.GateException;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class ParserServiceImpl implements ParserService {
    private final ResumeParserProgram resumeParserProgram;

    @Autowired
    public ParserServiceImpl(ResumeParserProgram resumeParserProgram) {
        this.resumeParserProgram = resumeParserProgram;
    }

    @Override
    public CandidateDto parseResume(String fileName) {
        String home = System.getProperty("user.home");
        String path = home + File.separator + "resumes" + File.separator + fileName;

        File tikkaConvertedFile = null;

        try {
            tikkaConvertedFile = resumeParserProgram.parseToHTMLUsingApacheTikka(path);
        } catch (IOException | SAXException | TikaException exception) {
            throw new RuntimeException(exception.getMessage());

        }
        ExtendedCandidate extendedCandidate = null;
        if (tikkaConvertedFile != null) {
            try {
                extendedCandidate = resumeParserProgram.parseUsingGateAndAnnie(tikkaConvertedFile, path);
            } catch (GateException | IOException exception) {
                throw new RuntimeException(exception.getMessage());
            }
        }
        return new ExpandCandidateToCandidateDtoMapper().map(Objects.requireNonNull(extendedCandidate));
    }
}
