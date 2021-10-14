package com.infopulse.resume.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "Feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String body;
    private byte tenPointScore;
    private LocalDate date;

    //Fk
    //private Candidate candidate;

    //Maybe Fk
    //private User author;

}
