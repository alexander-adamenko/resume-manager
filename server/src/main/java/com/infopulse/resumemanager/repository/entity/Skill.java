package com.infopulse.resumemanager.repository.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "skills")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String level;

    @ManyToMany(mappedBy = "skills")
    Set<Vacancy> vacancies;

    @ManyToMany(mappedBy = "skills")
    Set<Candidate> candidates;
}
