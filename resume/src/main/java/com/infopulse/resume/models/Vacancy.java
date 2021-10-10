package com.infopulse.resume.models;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "Vacancy")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String positionTitle;
    private boolean needVerifiedApplicants;
    private int minimumYearsOfExperience;
    private String degree;
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;




}
