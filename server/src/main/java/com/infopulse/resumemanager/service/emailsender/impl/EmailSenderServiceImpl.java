package com.infopulse.resumemanager.service.emailsender.impl;

import com.infopulse.resumemanager.dto.CandidateDto;
import com.infopulse.resumemanager.dto.UserDto;
import com.infopulse.resumemanager.dto.VacancyDto;
import com.infopulse.resumemanager.service.emailsender.EmailSenderService;
import com.infopulse.resumemanager.service.usermanagement.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

@Service
@AllArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private final UserServiceImpl userService;

    @Override
    public void sendInviteLetter(List<CandidateDto> candidates, VacancyDto vacancy) {
        for (CandidateDto candidate : candidates) {
            String userName = "0778526fb0bba8";
            String password = "8c8e28ef9c4ce9";
            String host = "smtp.mailtrap.io";

            UserDto user = userService.getCurrentUserDto();
            String from = (user.firstName() + "." + user.lastName() + "@gmail.com").toLowerCase(Locale.ROOT);
            String subject = vacancy.positionTitle();
            String premsg = """
                    <!doctype html>
                    <html>
                      <head>
                        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                      </head>
                      <body style="font-family: sans-serif;">
                        <div style="display: block; margin: auto; max-width: 600px;" class="main">
                          <h1 style="font-size: 18px; font-weight: bold; margin-top: 20px">Hi, %s</h1>
                          <p>We have opened a vacancy that is right for you!</p>
                          <p>If you think so - send a reply and we will discuss the details.</p>
                          <pre style="font-size: 14px; font-family: sans-serif;">%s</pre>
                          <hr/>
                          <p>Best regards,</p>
                          <p>%s %s</p>
                          <p>Operations Dept., HR Manager</p>
                          <p>E-mail: <a href="%s"><u>%s</u></a></p>
                          <p><a href="www.infopulse.com" style="font-size:8.0pt"><u>www.infopulse.com</u></a></p>
                          <img src="https://img2-frankfurt.cf-rabota.com.ua/infopulse/infopulse_5_top.jpg">
                        </div>
                      </body>
                    </html>""";
            String msg = premsg.formatted(candidate.name(), vacancy.description(), user.firstName(), user.lastName(), from, from);

            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(host);
            mailSender.setPort(2525);
            mailSender.setUsername(userName);
            mailSender.setPassword(password);

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(msg, true);
            helper.setTo(candidate.email());
            helper.setSubject(subject);
            helper.setFrom(from);
            mailSender.send(mimeMessage);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
