package bg.softuni.finebeard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


/**
 * Configuration class for setting up JavaMailSender bean.
 * This class configures the necessary properties for the JavaMailSender,
 * including the mail server host, port, username, and password.
 */
@Configuration
public class MailConfiguration {

    @Bean
    public JavaMailSender javaMailSender(
            @Value("${mail.host}") String host,
            @Value("${mail.port}") int mailPort,
            @Value("${mail.username}") String username,
            @Value("${mail.password}") String password
    ) {
        JavaMailSenderImpl javaMailSend = new JavaMailSenderImpl();

        javaMailSend.setHost(host);
        javaMailSend.setPort(mailPort);
        javaMailSend.setUsername(username);
        javaMailSend.setPassword(password);
        javaMailSend.setDefaultEncoding("UTF-8");
        javaMailSend.setJavaMailProperties(mailProperties());

        return javaMailSend;
    }

    private Properties mailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", "smtp");

        return properties;
    };
}
