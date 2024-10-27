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


/**
 * EmailServiceImpl is an implementation of the EmailService interface. It provides
 * functionalities to send emails, specifically registration emails to newly registered users.
 */
@Service
public class EmailServiceImpl implements EmailService {
    /**
     * TemplateEngine instance used to process email templates and generate
     * the content of registration emails with appropriate user-provided data.
     */
    private final TemplateEngine templateEngine;

    /**
     * Responsible for sending emails. Used to create and send MimeMessage instances
     * to provided recipients. Typically interacts with the email server to deliver
     * messages.
     */
    private final JavaMailSender javaMailSender;

    /**
     * The email address from which the registration emails will be sent.
     * This value is typically configured via a properties file
     * and injected at runtime using the @Value annotation.
     */
    private final String finebeardEmail;


    /**
     * Constructs a new EmailServiceImpl with the provided template engine, mail sender, and Fine Beard email address.
     *
     * @param templateEngine the TemplateEngine used for processing email templates
     * @param javaMailSender the JavaMailSender used for sending emails
     * @param finebeardEmail the email address from which emails will be sent
     */
    public EmailServiceImpl(TemplateEngine templateEngine,
                            JavaMailSender javaMailSender,
                            @Value("${mail.finebeard}") String finebeardEmail) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.finebeardEmail = finebeardEmail;

    }

    /**
     * Sends a registration email to a newly registered user.
     *
     * @param userEmail the email address of the user to whom the registration email will be sent
     * @param userName the name of the user, which will be included in the registration email
     * @param activationCode the activation code that will be included in the registration email
     */
    @Override
    public void sendRegistrationEmail(String userEmail, String userName, String activationCode) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setFrom(finebeardEmail);
            mimeMessageHelper.setReplyTo(finebeardEmail);
            mimeMessageHelper.setSubject("Welcome to Fine Beard!");
            mimeMessageHelper.setText(generateRegistrationEmailBody(userName,activationCode), true);


            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates the body of the registration email.
     *
     * @param userName the name of the user who is registering
     * @param activationCode the activation code for the user's account
     * @return the generated email body as a string
     */
    private String generateRegistrationEmailBody(String userName, String activationCode) {

        Context context = new Context();

        String activationLink = "http://localhost:8080/users/activate/" + activationCode;

        context.setVariable("username", userName);
        context.setVariable("activation_link", activationLink);
        return templateEngine.process("email/registration-email.html", context);
    }

}
