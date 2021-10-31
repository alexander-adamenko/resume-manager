package com.infopulse.resumemanager.service.matcher;

import com.infopulse.resumemanager.dto.VacancyDto;
import com.infopulse.resumemanager.repository.CandidateRepository;
import com.infopulse.resumemanager.repository.entity.Candidate;
import com.infopulse.resumemanager.repository.entity.Feedback;
import com.infopulse.resumemanager.repository.entity.Skill;
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

    @BeforeEach
    public void setUp() {
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
        Skill skill1 = new Skill();
        Skill skill2 = new Skill();
        Skill skill3 = new Skill();
        Skill english = new Skill();
        Skill notRequiredSkill = new Skill();

        skill1.setName("DataBases");
        skill2.setName("OOP");
        skill3.setName("JavaServer pages");
        english.setName("English");
        notRequiredSkill.setName("HTML");

        List<Candidate> candidates = new ArrayList<>();

        Candidate c1 = new Candidate();
        c1.setName("Jack");
        Set<Skill> c1Skill = new HashSet<>();
        c1Skill.add(notRequiredSkill);
        c1.setSkills(c1Skill);
        c1.setDegree("Bachelor of Science in Information Technology, University of Colombo");
        Set<Feedback> c1FeedBack = new HashSet<>();
        for (int i = 0; i < 3; i++) c1FeedBack.add(new Feedback());
        c1.setFeedbacks(c1FeedBack);

        Candidate c2 = new Candidate();
        c2.setName("Katy");
        Set<Skill> c2Skill = new HashSet<>();
        c2Skill.add(skill3);
        c2.setSkills(c2Skill);
        Set<Feedback> c2FeedBack = new HashSet<>();
        for (int i = 0; i < 12; i++) c2FeedBack.add(new Feedback());
        c2.setFeedbacks(c2FeedBack);

        Candidate c3 = new Candidate();
        c3.setName("John");
        Set<Skill> c3Skill = new HashSet<>();
        c3Skill.add(skill2);
        c3Skill.add(skill3);
        c3Skill.add(english);
        c3.setSkills(c3Skill);
        c3.setDegree("Master of Science in Information Technology, University of Colombo");

        Candidate c4 = new Candidate();
        c4.setName("Peter");
        Set<Skill> c4Skill = new HashSet<>();
        c4Skill.add(skill1);
        c4Skill.add(english);
        c4.setSkills(c4Skill);
        c4.setDegree("Bachelor of Science in Information Technology, University of Colombo");
        Set<Feedback> c4FeedBack = new HashSet<>();
        for (int i = 0; i < 7; i++) c4FeedBack.add(new Feedback());
        c4.setFeedbacks(c4FeedBack);

        candidates.add(c1);
        candidates.add(c2);
        candidates.add(c3);
        candidates.add(c4);
        return candidates;
    }

    private VacancyDto getVacancyDto() {
        Set<Skill> skills = new HashSet<>();
        Skill skill1 = new Skill();
        Skill skill2 = new Skill();
        Skill skill3 = new Skill();
        Skill skill4 = new Skill();
        skill1.setName("DataBases");
        skill2.setName("OOP");
        skill3.setName("JavaServer pages");
        skill4.setName("English");
        skills.add(skill1);
        skills.add(skill2);
        skills.add(skill3);
        skills.add(skill4);

        return new VacancyDto("Java Developer", true,
                2, null, null, skills);

    }
}