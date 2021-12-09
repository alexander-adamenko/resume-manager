package com.infopulse.resumemanager.repository.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "candidates")
@ToString
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String degree;

    @Column(name = "about_me")
    @Length(max = 2000)
    private String aboutMe;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "candidate")
    private Set<Feedback> feedbacks;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER)
    private Set<CandidateSkill> candidateSkills = new HashSet<>();

    @OneToMany(mappedBy = "candidate")
    private List<Hiring> hiringList;

}
