package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class EmailServiceImpl implements EmailService {


    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final String finebeardEmail;


    public EmailServiceImpl(TemplateEngine templateEngine,
                            JavaMailSender javaMailSender,
                            @Value("${mail.finebeard}") String finebeardEmail) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.finebeardEmail = finebeardEmail;

    }

    @Override
    public void sendRegistrationEmail(String userEmail, String userName) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setFrom(finebeardEmail);
            mimeMessageHelper.setReplyTo(finebeardEmail);
            mimeMessageHelper.setSubject("Welcome to Fine Beard!");
            mimeMessageHelper.setText(generateRegistrationEmailBody(userName), true);


            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateRegistrationEmailBody(String userName) {

        Context context = new Context();

        context.setVariable("username", userName);

        return templateEngine.process("email/registration-email.html", context);
    }
}
