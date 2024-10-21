package bg.softuni.finebeard.service;

import bg.softuni.finebeard.model.events.UserRegisteredEvent;

public interface UserActivationService {

    void userRegistered(UserRegisteredEvent event);

    Long cleanUpObsoleteActivationEntities();

    String createActivationCode(String userEmail);

    boolean activateUser(String activationCode, String ipAddress);

    Long cleanUpObsoleteActivationAttempts();
}
