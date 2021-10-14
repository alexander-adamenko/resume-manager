package com.infopulse.resume.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String name;
    private String email;
    private String phone;
    private String aboutMe;
    private String filePath;

    //Fk
    private User user;

    //Fk can be list
    //private Skill skill;



}
