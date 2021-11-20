package com.infopulse.resumemanager.repository.entity;

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
    private String degree;
    private String location;
    @Column(length = 6000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "vacancy")
    private Set<VacancySkill> vacancySkills = new HashSet<>();

    @OneToMany(mappedBy = "vacancy")
    private List<Hiring> hiringList;

}
