package com.infopulse.resumemanager.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String degree;

    @Column(name = "about_me")
    private String aboutMe;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "candidate")
    private Set<Feedback> feedbacks;

    @OneToMany(mappedBy = "candidate")
    private Set<CandidateSkill> candidateSkills;

    @OneToMany(mappedBy = "candidate")
    private List<Hiring> hiringList;

}
