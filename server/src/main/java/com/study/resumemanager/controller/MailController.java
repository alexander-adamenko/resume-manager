package com.study.resumemanager.controller;

import com.study.resumemanager.dto.MailSendParamsDto;
import com.study.resumemanager.service.emailsender.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MailController {
    private final EmailSenderService emailSenderService;

    @PostMapping("/send-mails")
    public void sendEmails(@RequestBody @Valid MailSendParamsDto mailSendParams) {
        emailSenderService.sendInviteLetter(mailSendParams.candidates(), mailSendParams.vacancy());
    }
}
