package com.infopulse.resumemanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping(value = "/lalala")
    public String hello() {
        return "lalala";
    }
}
