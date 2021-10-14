package com.infopulse.resumemanager.repository.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Size(min = 0, max = 1000, message = "About Me must be between 10 and 200 characters")
    private String body;

    @Min(value = 1, message = "Score should not be less than 1")
    @Max(value = 10, message = "Score should not be greater than 10")
    private Byte tenPointScore;

    @PastOrPresent
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
}
