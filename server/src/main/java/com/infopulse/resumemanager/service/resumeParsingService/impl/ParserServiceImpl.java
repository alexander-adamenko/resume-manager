package com.infopulse.resumemanager.service.resumeParsingService.impl;

import com.infopulse.resumemanager.record.parsed.CandidateExpand;
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

@Service
public class ParserServiceImpl implements ParserService {
    private final ResumeParserProgram resumeParserProgram;

    @Autowired
    public ParserServiceImpl(ResumeParserProgram resumeParserProgram) {
        this.resumeParserProgram = resumeParserProgram;
    }

    @Override
    public CandidateExpand parseResume(MultipartFile file) {
        String uploadedFolder = System.getProperty("user.dir");
        if (uploadedFolder != null && !uploadedFolder.isEmpty()) {
            uploadedFolder += "/Resumes/";
        } else
            throw new RuntimeException("User Directory not found");
        File tikkaConvertedFile = null;
        byte[] bytes = null;
        try {
            bytes = file.getBytes();
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        Path path = null;
        try {
            path = Paths.get(uploadedFolder + file.getOriginalFilename());
            if (!Files.exists(path.getParent()))
                Files.createDirectories(path.getParent());
            Files.write(path, bytes);
        } catch (IOException exception) {
            throw new RuntimeException(exception.getMessage());

        }
        try {
            tikkaConvertedFile = resumeParserProgram.parseToHTMLUsingApacheTikka(path.toAbsolutePath().toString());
        } catch (IOException | SAXException | TikaException exception) {
            throw new RuntimeException(exception.getMessage());

        }
        CandidateExpand candidateExpand = null;
        if (tikkaConvertedFile != null) {
            try {
                candidateExpand = resumeParserProgram.parseUsingGateAndAnnie(tikkaConvertedFile, path.toAbsolutePath().toString());
            } catch (GateException | IOException exception) {
                throw new RuntimeException(exception.getMessage());
            }
        }
        return candidateExpand;
    }
}
