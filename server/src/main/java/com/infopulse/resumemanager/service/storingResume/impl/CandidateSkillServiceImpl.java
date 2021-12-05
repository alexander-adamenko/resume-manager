package com.infopulse.resumemanager.service.storingResume.impl;

import com.infopulse.resumemanager.dto.CandidateSkillDto;
import com.infopulse.resumemanager.mapper.ObjectMapper;
import com.infopulse.resumemanager.repository.CandidateRepository;
import com.infopulse.resumemanager.repository.CandidateSkillRepository;
import com.infopulse.resumemanager.repository.SkillRepository;
import com.infopulse.resumemanager.repository.entity.Candidate;
import com.infopulse.resumemanager.repository.entity.CandidateSkill;
import com.infopulse.resumemanager.repository.entity.Skill;
import com.infopulse.resumemanager.service.storingResume.CandidateSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CandidateSkillServiceImpl implements CandidateSkillService {
    private final CandidateSkillRepository candidateSkillRepository;
    private final SkillRepository skillRepository;
    private final CandidateRepository candidateRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public CandidateSkillServiceImpl(CandidateSkillRepository candidateSkillRepository, SkillRepository skillRepository, CandidateRepository candidateRepository, ObjectMapper objectMapper) {
        this.candidateSkillRepository = candidateSkillRepository;
        this.skillRepository = skillRepository;
        this.candidateRepository = candidateRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Set<CandidateSkillDto> getSkillsByCandidateId(Long candidateId) {
        return candidateSkillRepository.findAll().stream()
                .filter(candidateSkill -> candidateSkill.getCandidate().getId().equals(candidateId))
                .map(objectMapper::candidateSkillToCandidateSkillDto)
                .collect(Collectors.toSet());
    }

    @Override
    public CandidateSkillDto saveCandidateSkill(CandidateSkillDto candidateSkilldto, long candidateId) {
        Skill savedSkill = saveSkillIfNotExist(candidateSkilldto.skill().name());
        var candidateSkill = new CandidateSkill();
        candidateSkill.setSkill(savedSkill);
        candidateSkill.setLevel(candidateSkilldto.level());

        Optional<Candidate> candidate = candidateRepository.findById(candidateId);
        if(candidate.isPresent()){
            candidateSkill.setCandidate(candidate.get());
        } else throw new NoSuchElementException("No candidate with such id");

        return objectMapper.candidateSkillToCandidateSkillDto(
                candidateSkillRepository.save(candidateSkill));
    }

    @Override
    public void deleteCandidateSkill(CandidateSkill candidateSkill) {
        candidateSkillRepository.delete(candidateSkill);
    }

    @Override
    public void deleteAllSkillOfCandidate(Long candidateId) {
        candidateSkillRepository.deleteAllSkillsOfCandidate(candidateId);
    }

    private Skill saveSkillIfNotExist(String name){
        Optional<Skill> skill = Optional.ofNullable(skillRepository.findByName(name));
        Skill savedSkill;
        if(skill.isPresent()){
            savedSkill = skill.get();
        } else {
            var skillToSave = new Skill();
            skillToSave.setName(name);
            savedSkill = skillRepository.save(skillToSave);
        }
        return savedSkill;
    }
}
