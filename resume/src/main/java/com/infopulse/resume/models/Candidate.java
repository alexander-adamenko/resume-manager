package com.infopulse.resume.models;

public class Candidate {
    private String name;
    private String email;
    private String phone;
    private String aboutMe;
    private String filePath;

    //Fk
    private User user;

    //Fk can be list
    private Skill skill;



}
