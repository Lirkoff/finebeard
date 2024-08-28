package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.events.UserRegisteredEvent;
import bg.softuni.finebeard.service.EmailService;
import bg.softuni.finebeard.service.UserActivationService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;


@Service
public class UserActivationServiceImpl implements UserActivationService {


    private final EmailService emailService;

    public UserActivationServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    @EventListener(UserRegisteredEvent.class)
    public void userRegistered(UserRegisteredEvent event) {

        emailService.sendRegistrationEmail(event.getUserEmail(), event.getUserNames());
    }
}
