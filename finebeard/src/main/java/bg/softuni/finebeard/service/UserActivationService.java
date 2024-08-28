package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.events.UserRegisteredEvent;

public interface UserActivationService {

    void userRegistered(UserRegisteredEvent event);
}
