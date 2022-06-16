package com.study.resumemanager.service.storingresume.impl;

import com.study.resumemanager.dto.CandidateDto;
import com.study.resumemanager.exception.FileExistsException;
import com.study.resumemanager.exception.UnsupportedFileException;
import com.study.resumemanager.mapper.ObjectMapper;
import com.study.resumemanager.repository.CandidateRepository;
import com.study.resumemanager.repository.UserRepository;
import com.study.resumemanager.repository.entity.Candidate;
import com.study.resumemanager.repository.entity.User;
import com.study.resumemanager.service.storingresume.CandidateService;
import com.study.resumemanager.service.storingresume.CandidateSkillService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public CandidateDto getCandidateById(Long id) {
        Optional<Candidate> byId = candidateRepository.findById(id);
        if(byId.isEmpty()){
            throw new NoSuchElementException("No Candidate with such id");
        }
        Candidate candidate = byId.get();
        return objectMapper.candidateToCandidateDto(candidate);
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

    @Override
    @Transactional
    public void deleteCandidate(String fileName) {
        Optional<Candidate> candidateByFilePath = Optional.ofNullable(candidateRepository.findCandidateByFilePath(path + fileName));
        if (candidateByFilePath.isPresent()){
            Candidate candidate = candidateByFilePath.get();
            deleteSkills(candidate);
            deleteFile(fileName);
            candidateRepository.delete(candidate);
        } else {
            throw new NoSuchElementException("Candidate not found");
        }
    }

    public boolean deleteFile(String fileName) {
        String filePath = path + fileName;
        boolean result = false;
        try {
            result = Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteSkills(Candidate candidate) {
        candidateSkillService.deleteAllSkillOfCandidate(candidate.getId());
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
