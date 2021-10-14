package com.infopulse.resume.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String level;

}
