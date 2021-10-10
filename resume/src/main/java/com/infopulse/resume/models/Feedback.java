package com.infopulse.resume.models;

import java.time.LocalDate;

public class Feedback {
    private String title;
    private String body;
    private byte tenPointScore;
    private LocalDate date;

    //Fk
    private Candidate candidate;

    //Maybe Fk
    private User author;

}
