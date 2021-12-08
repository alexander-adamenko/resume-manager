package com.infopulse.resumemanager.service.emailsender;

import com.infopulse.resumemanager.dto.UserDto;
import com.infopulse.resumemanager.repository.entity.Candidate;
import com.infopulse.resumemanager.repository.entity.Vacancy;
import com.infopulse.resumemanager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
@AllArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private final UserService userService;

    @Override
    public void sendInviteLetter(List<Candidate> candidates, Vacancy vacancy) {
//        candidates.forEach();
        Candidate candidate = new Candidate();
        candidate.setEmail("viktor-puhach@gmail.com");
        candidate.setName("Viktor Puhach");
        vacancy.setPositionTitle("Trainee Java Developer");
        vacancy.setDescription(
                """
                        Our client is the pioneer and market leader in Experience Management.
                        The client develops an award-winning SaaS platform, the Experience Cloud, leads the market in the understanding and management of experience for customers, employees and citizens. The client captures experience signals created on daily journeys in person, digital and IoT interactions and applies proprietary AI technology to reveal personalized and predictive insights that can drive action with tremendous business results. Using the client’s Experience Cloud, customers can reduce churn, turn detractors into promoters and buyers, and create in-the-moment cross-sell and up-sell opportunities, providing clear and potent returns on investment.
                                                
                        Responsibilities:
                                                
                        Work on the existing system and deliver customer-driven enhancements
                        Communicate with the client’s team directly on a daily basis, attend daily meetings
                        Contribute to the complete software development life cycle process
                        Develop high performance API
                                                
                        Requirements:
                                                
                        0.5 + year of Java commercial experience/pet projects
                        Strong Java 11 knowledge
                        Experience with Spring Boot
                        Understanding of Rest API
                        Experience with AWS would be a plus
                        Spoken and written English on Upper-Intermediate level
                        Open-minded person with strong analytical and communication skills who ready for actively studying
                                                
                        We offer:
                                                
                        Flexible working hours
                                                
                        A competitive salary and good compensation package
                        Possibility of partial remote work
                        The Best hardware
                        A masseur and a corporate doctor
                        Healthcare & sport benefits
                        An inspiring and comfy office
                                                
                        Professional growth:
                                                
                        Challenging tasks and innovative projects
                        Meetups and events for professional development
                        An individual development plan
                        Mentorship program
                                                
                        Fun:
                                                
                        Corporate events and outstanding parties
                        Exciting team buildings
                        Memorable anniversary presents
                        A fun zone where you can play video games, foosball, ping pong, and more
                        """
        );
        System.out.println(vacancy.getDescription());
        UserDto user = userService.getCurrentUser();

        String userName = "a08b8c05007276";
        String password = "765c1226be800b";
        String host = "smtp.mailtrap.io";
        String from = "from@example.com";//(user.firstName() + "-" + user.lastName() + "@gmail.com").toLowerCase(Locale.ROOT)
        String to = "test@example.com";
        String subject = vacancy.getPositionTitle();
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
                      <p>Best regards,</p>
                      <p>%s %s</p>
                      <p>Operations Dept., Trainee HR Manager</p>
                      <p>E-mail: <a href="%s"><u>%s</u></a></p>
                      <img src="https://mail.google.com/mail/u/0?ui=2&ik=8157c261b8&attid=0.2&permmsgid=msg-f:1708863721913901179&th=17b71a804a3ae07b&view=fimg&fur=ip&sz=s0-l75-ft&attbid=ANGjdJ8RD155vQjlD_K5VAsGeZuJr920KU5ltAAL4AJALjj_MCYBte2eEikNOr1m7iuBWJYYxRNvvb0Ij7FmchOvLMlBCfHAV8gmWI7cFOu7NRGbhd27nQSXm_Jnj7g&disp=emb">
                      <p><a href="www.infopulse.com" style="font-size:8.0pt"><u>www.infopulse.com</u></a></p>
                      <img src="https://mail.google.com/mail/u/0?ui=2&ik=8157c261b8&attid=0.3&permmsgid=msg-f:1708863721913901179&th=17b71a804a3ae07b&view=fimg&fur=ip&sz=s0-l75-ft&attbid=ANGjdJ8KmMtMRb-mCSlI2SZI_pshODFV8wvtMlXzDXBk7S8MgzbI_LoVmuLUmrwpY8Z9Pr9zPsOXAj-NSh71negli8mbwXg19Tfomg3OZ85Nw5TidqjJVoi5ZP22Mig&disp=emb">

                    </div>
                  </body>
                </html>""";
        String msg = premsg.formatted(candidate.getName(), vacancy.getDescription(), user.firstName(), user.lastName(), from, from);

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
        helper.setTo(candidate.getEmail());
        helper.setSubject(subject);
        helper.setFrom(from);
        mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
