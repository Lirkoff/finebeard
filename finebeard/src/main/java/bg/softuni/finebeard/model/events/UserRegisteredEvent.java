package bg.softuni.finebeard.model.events;

import org.springframework.context.ApplicationEvent;

/**
 * UserRegisteredEvent is an event that gets triggered upon the registration
 * of a new user in the application.
 *
 * This event contains essential user information necessary for subsequent
 * processes such as sending registration emails or activating user accounts.
 */
public class UserRegisteredEvent extends ApplicationEvent {

    private final String userEmail;
    private final String userNames;

    public UserRegisteredEvent(Object source,
                               String userEmail,
                               String userNames) {
        super(source);
        this.userEmail = userEmail;
        this.userNames = userNames;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public String getUserNames() {
        return userNames;
    }
}
