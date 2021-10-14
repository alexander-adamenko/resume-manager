package com.infopulse.resume.models;

import lombok.Data;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "Vacancy")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String positionTitle;
    private boolean needVerifiedApplicants;
    private int minimumYearsOfExperience;
    private String degree;
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    private User recruiter;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "vacancy_skill",
            joinColumns = { @JoinColumn(name = "vacancy_id") },
            inverseJoinColumns = { @JoinColumn(name = "skill_id") }
    )
    private Set<Skill> skills = new HashSet<>();
}
