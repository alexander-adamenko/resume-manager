package com.infopulse.resumemanager.repository.entity;

/**
 * BLOCKED = no access to the account,
 * INTERVIEWER = INTERVIEWER,
 * RECRUITER = RECRUITER & INTERVIEWER,
 * ADMIN = ADMIN & RECRUITER & INTERVIEWER
 */
public enum ERole {
    BLOCKED,
    INTERVIEWER,
    RECRUITER,
    ADMIN
}
