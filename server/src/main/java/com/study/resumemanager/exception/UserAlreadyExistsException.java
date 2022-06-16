package com.study.resumemanager.exception;

public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException(String username) {
        super("User " + username + " is already exists.");
    }
}
