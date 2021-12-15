package com.infopulse.resumemanager.controller;

import com.infopulse.resumemanager.dto.MailSendParamsDto;
import com.infopulse.resumemanager.service.emailsender.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MailController {
    private final EmailSenderService emailSenderService;

    @PostMapping("/send-mails")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmails(@RequestBody @Valid MailSendParamsDto mailSendParams) {
        emailSenderService.sendInviteLetter(mailSendParams.candidates(), mailSendParams.vacancy());
    }
}
