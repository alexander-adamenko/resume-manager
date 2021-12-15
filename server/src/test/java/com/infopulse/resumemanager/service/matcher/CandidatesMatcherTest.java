package com.infopulse.resumemanager.service.matcher;

import com.infopulse.resumemanager.dto.SkillDto;
import com.infopulse.resumemanager.dto.VacancyDto;
import com.infopulse.resumemanager.dto.VacancySkillDto;
import com.infopulse.resumemanager.repository.CandidateRepository;
import com.infopulse.resumemanager.repository.entity.Candidate;
import com.infopulse.resumemanager.repository.entity.CandidateSkill;
import com.infopulse.resumemanager.repository.entity.Feedback;
import com.infopulse.resumemanager.repository.entity.Skill;
import com.infopulse.resumemanager.repository.entity.enums.Degree;
import com.infopulse.resumemanager.repository.entity.enums.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.stream.Collectors;

class CandidatesMatcherTest {

    @InjectMocks
    private CandidatesMatcher candidatesMatcher;
    @Mock
    private CandidateRepository candidateRepository;

    private Skill skill1;
    private Skill skill2;
    private Skill skill3;
    private Skill english;
    private Skill notRequiredSkill;

    @BeforeEach
    public void setUp() {
        skill1 = new Skill();
        skill2 = new Skill();
        skill3 = new Skill();
        english = new Skill();
        notRequiredSkill = new Skill();
        skill1.setName("DataBases");
        skill2.setName("OOP");
        skill3.setName("JavaServer pages");
        english.setName("English - upper intermediate");
        notRequiredSkill.setName("HTML");

        MockitoAnnotations.initMocks(this);
        Mockito.when(candidateRepository.findAll()).thenReturn(getCandidates());
    }

    @Test
    void filterBySkills() {
        Assertions.assertEquals(candidatesMatcher
                .filterBySkills(getVacancyDto())
                .stream()
                .map(Candidate::getName)
                .collect(Collectors
                        .joining(" ")), "John Peter Katy");
    }

    @Test
    void filterBySkillsAndLevel() {
        Assertions.assertEquals(candidatesMatcher
                .filterBySkillsAndLevel(getVacancyDto())
                .stream()
                .map(Candidate::getName)
                .collect(Collectors
                        .joining(" ")), "John Peter");
    }

    @Test
    void filterBySkillsLang() {
        Assertions.assertEquals(candidatesMatcher
                .filterBySkillsLang(getVacancyDto(), "english")
                .stream()
                .map(Candidate::getName)
                .collect(Collectors
                        .joining(" ")), "John Peter");
    }

    @Test
    void filterBySkillsDegree() {
        Assertions.assertEquals(candidatesMatcher
                .filterBySkillsDegree(getVacancyDto(), "MASTER")
                .stream()
                .map(Candidate::getName)
                .collect(Collectors
                        .joining(" ")), "John");
        Assertions.assertEquals(candidatesMatcher
                .filterBySkillsDegree(getVacancyDto(), "BACHELOR")
                .stream()
                .map(Candidate::getName)
                .collect(Collectors
                        .joining(" ")), "John Peter");
        Assertions.assertEquals(candidatesMatcher
                .filterBySkillsDegree(getVacancyDto(), "DOCTOR")
                .stream()
                .map(Candidate::getName)
                .collect(Collectors
                        .joining(" ")), "");
    }


    @Test
    void filterBySkillsFeedbacks() {
        Assertions.assertEquals(candidatesMatcher
                .filterBySkillsFeedbacks(getVacancyDto())
                .stream()
                .map(Candidate::getName)
                .collect(Collectors
                        .joining(" ")), "Katy Peter");
    }


    private List<Candidate> getCandidates() {

        List<Candidate> candidates = new ArrayList<>();

        Candidate jack = new Candidate();
        jack.setName("Jack");

        Set<CandidateSkill> c1Skill = new HashSet<>();
        CandidateSkill cs1 = new CandidateSkill();
        cs1.setCandidate(jack);
        cs1.setSkill(notRequiredSkill);
        cs1.setLevel(Level.JUNIOR);
        c1Skill.add(cs1);

        jack.setCandidateSkills(c1Skill);
        jack.setDegree(Degree.BACHELOR);
//        jack.setDegree("Bachelor of Science in Information Technology, University of Colombo");
        Set<Feedback> c1FeedBack = new HashSet<>();
        for (int i = 0; i < 3; i++) c1FeedBack.add(new Feedback());
        jack.setFeedbacks(c1FeedBack);

        Candidate katy = new Candidate();
        katy.setName("Katy");
        Set<CandidateSkill> c2Skill = new HashSet<>();
        CandidateSkill cs2 = new CandidateSkill();
        cs2.setCandidate(katy);
        cs2.setSkill(skill3);
        cs2.setLevel(Level.DEFAULT);
        c2Skill.add(cs2);
        katy.setCandidateSkills(c2Skill);

        Set<Feedback> c2FeedBack = new HashSet<>();
        for (int i = 0; i < 12; i++) c2FeedBack.add(new Feedback());
        katy.setFeedbacks(c2FeedBack);

        Candidate john = new Candidate();
        john.setName("John");
        Set<CandidateSkill> c3Skill = new HashSet<>();
        CandidateSkill cs3 = new CandidateSkill();
        cs3.setCandidate(john);
        cs3.setSkill(skill2);
        cs3.setLevel(Level.MIDDLE);

        CandidateSkill cs4 = new CandidateSkill();
        cs4.setCandidate(john);
        cs4.setSkill(skill3);
        cs4.setLevel(Level.JUNIOR);

        CandidateSkill cs5 = new CandidateSkill();
        cs5.setCandidate(john);
        cs5.setSkill(english);
        cs5.setLevel(Level.TRAINEE);

        c3Skill.add(cs3);
        c3Skill.add(cs4);
        c3Skill.add(cs5);

        john.setCandidateSkills(c3Skill);

        john.setDegree(Degree.MASTER);
//        john.setDegree("Master of Science in Information Technology, University of Colombo");

        Candidate peter = new Candidate();
        peter.setName("Peter");
        Set<CandidateSkill> c4Skill = new HashSet<>();
        CandidateSkill cs6 = new CandidateSkill();
        cs6.setCandidate(peter);
        cs6.setSkill(skill1);
        cs6.setLevel(Level.MIDDLE);

        CandidateSkill cs7 = new CandidateSkill();
        cs7.setCandidate(peter);
        cs7.setSkill(english);
        cs7.setLevel(Level.DEFAULT);

        CandidateSkill cs8 = new CandidateSkill();
        cs8.setCandidate(peter);
        cs8.setSkill(skill2);
        cs8.setLevel(Level.TRAINEE);

        c4Skill.add(cs6);
        c4Skill.add(cs7);
        c4Skill.add(cs8);

        peter.setCandidateSkills(c4Skill);

        peter.setDegree(Degree.BACHELOR);
//        peter.setDegree("Bachelor of Science in Information Technology, University of Colombo");
        Set<Feedback> c4FeedBack = new HashSet<>();
        for (int i = 0; i < 7; i++) c4FeedBack.add(new Feedback());
        peter.setFeedbacks(c4FeedBack);

        candidates.add(jack);
        candidates.add(katy);
        candidates.add(john);
        candidates.add(peter);
        return candidates;
    }

    private VacancyDto getVacancyDto() {
        Set<VacancySkillDto> skills = new HashSet<>();

        skills.add(new VacancySkillDto(new SkillDto(skill1.getName()), Level.JUNIOR));
        skills.add(new VacancySkillDto(new SkillDto(skill2.getName()), Level.TRAINEE));
        skills.add(new VacancySkillDto(new SkillDto(skill3.getName()), Level.MIDDLE));

        return new VacancyDto(1l, "Java Developer", true,
                2, null, null,null, null, skills);

    }

}
