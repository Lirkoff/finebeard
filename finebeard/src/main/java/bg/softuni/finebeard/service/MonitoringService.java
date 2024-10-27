package bg.softuni.finebeard.service;


/**
 * MonitoringService is an interface responsible for logging various events within the system,
 * particularly related to product searches and activation attempts. Implementations of this
 * interface can be used to track these events for monitoring and analytical purposes.
 */
public interface MonitoringService {
    /**
     * Logs an event indicating a product search operation.
     * This method should be called whenever a product search is performed,
     * to track and monitor search activities for analytical purposes.
     */
    void logProductSearch();

    /**
     * Logs the attempts made to activate a product or service within the system.
     * This method is typically invoked after a rate-limited activation attempt,
     * allowing the system to track and monitor the number of such occurrences.
     */
    void logActivationAttempts();
}
