package com.infopulse.resumemanager.repository.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String level;

    @ManyToMany(mappedBy = "skills")
    Set<Vacancy> vacancies;

    @ManyToMany(mappedBy = "skills")
    Set<Candidate> candidates;
}
