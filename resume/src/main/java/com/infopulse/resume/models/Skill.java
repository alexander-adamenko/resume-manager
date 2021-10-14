package com.infopulse.resume.models;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "Skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String level;

    @ManyToMany(mappedBy = "skills")
    private Set<Vacancy> employees = new HashSet<>();

}
