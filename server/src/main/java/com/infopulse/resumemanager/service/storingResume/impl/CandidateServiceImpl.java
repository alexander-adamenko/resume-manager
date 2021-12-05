package com.infopulse.resumemanager.service.storingResume.impl;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.exception.FileExistsException;
import com.infopulse.resumemanager.exception.UnsupportedFileException;
import com.infopulse.resumemanager.mapper.ObjectMapper;
import com.infopulse.resumemanager.repository.CandidateRepository;
import com.infopulse.resumemanager.repository.UserRepository;
import com.infopulse.resumemanager.repository.entity.Candidate;
import com.infopulse.resumemanager.repository.entity.User;
import com.infopulse.resumemanager.service.storingResume.CandidateService;
import com.infopulse.resumemanager.service.storingResume.CandidateSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final CandidateSkillService candidateSkillService;
    String path = System.getProperty("user.home") + File.separator + "resumes" + File.separator;


    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository, UserRepository userRepository, ObjectMapper objectMapper, CandidateSkillService candidateSkillService) {
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.candidateSkillService = candidateSkillService;
    }

    @Override
    public CandidateDto saveCandidateResume(MultipartFile file) throws UnsupportedFileException, FileExistsException {
        String filePath = path + file.getOriginalFilename();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (extension != null && !(extension.equals("pdf") || extension.equals("docx") || extension.equals("doc"))) {
            throw new UnsupportedFileException(extension);
        }

        if (checkIfFileExists(filePath)) {
            throw new FileExistsException(file.getOriginalFilename());
        }

        try {
            File fileByPath = new File(filePath);
            if (!fileByPath.exists()) {
                fileByPath.mkdirs();
            }
            file.transferTo(fileByPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Candidate candidate = new Candidate();
        candidate.setFilePath(filePath);
        candidate.setAuthor(getRequestAuthor());

        Candidate save = candidateRepository.save(candidate);
        return objectMapper.candidateToCandidateDto(save);
    }

    @Override
    public List<CandidateDto> getAllCandidates() {
        return candidateRepository.findAll().stream()
                .filter(candidate -> candidate.getAuthor().equals(getRequestAuthor()))
                .map(objectMapper::candidateToCandidateDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getNamesUploadedFiles() {
        return candidateRepository.findAll().stream()
                .filter(candidate -> candidate.getAuthor().equals(getRequestAuthor()))
                .map(Candidate::getFilePath)
                .map(path -> new File(path).getName())
                .collect(Collectors.toList());
    }

    @Override
    public CandidateDto saveCandidateWithSkills(CandidateDto candidateDtoAfterParsing) {
        Optional<Candidate> candidate = candidateRepository.findAll().stream()
                .filter(c -> c.getFilePath().equals(candidateDtoAfterParsing.filePath()))
                .findFirst();

        Candidate candidateAfterParsing = objectMapper.candidateDtoToCandidate(candidateDtoAfterParsing);
        if(candidate.isPresent()){
            Candidate candidateFromDb = candidate.get();
            candidateAfterParsing.setId(candidateFromDb.getId());
            candidateAfterParsing.setFilePath(candidateFromDb.getFilePath());
            candidateAfterParsing.setAuthor(candidateFromDb.getAuthor());

            //saving candidate`s skills
            candidateDtoAfterParsing
                    .candidateSkills()
                    .forEach(candidateSkillDto -> candidateSkillService.saveCandidateSkill(
                            candidateSkillDto,
                            candidateFromDb.getId()));

        } else throw new NoSuchElementException("No candidate with such resume");

        return objectMapper.candidateToCandidateDto(candidateRepository.save(candidateAfterParsing));
    }


    private boolean checkIfFileExists(String filepath){
        return new File(filepath).exists();
    }

    private User getRequestAuthor(){
        return userRepository.findByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
    }
}
