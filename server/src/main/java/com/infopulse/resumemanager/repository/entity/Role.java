package com.infopulse.resumemanager.repository.entity;

public enum Role {
    BLOCKED,
    INTERVIEWER,
    RECRUITER,
    ADMIN
}
//BLOCKED = no access to the account
//INTERVIEWER = INTERVIEWER
//RECRUITER = RECRUITER & INTERVIEWER
//ADMIN = ADMIN & RECRUITER & INTERVIEWER