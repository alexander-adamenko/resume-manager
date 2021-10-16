package com.infopulse.resumemanager.repository.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    private Set<Vacancy> vacancies;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    private Set<Candidate> candidates;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
    private Set<Feedback> feedbacks;
}
