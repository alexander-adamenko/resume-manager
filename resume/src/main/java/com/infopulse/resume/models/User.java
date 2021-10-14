package com.infopulse.resume.models;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    //todo ask Roman: how to impl roles here
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "recruiter_id")
    private List<Vacancy> vacancies = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "recruiter_id")
    private List<Candidate> candidates = new ArrayList<>();

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "author_id")
    private List<Feedback> feedbacks = new ArrayList<>();
}
