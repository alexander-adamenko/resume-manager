package com.study.resumemanager.repository.entity;

import com.study.resumemanager.repository.entity.enums.City;
import com.study.resumemanager.repository.entity.enums.Degree;
import com.study.resumemanager.repository.entity.enums.EnglishLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "vacancies")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String positionTitle;
    private Boolean isActive;
    private Integer minimumYearsOfExperience;
    @Enumerated(EnumType.STRING)
    private Degree degree;
    @Enumerated(EnumType.STRING)
    private City location;
    @Enumerated(EnumType.STRING)
    private EnglishLevel englishLevel;
    @Column(length = 6000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.ALL)
    private Set<VacancySkill> vacancySkills = new HashSet<>();

    @OneToMany(mappedBy = "vacancy")
    private List<Hiring> hiringList;

}
