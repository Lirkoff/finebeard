package bg.softuni.finebeard.service.schedulers;


import bg.softuni.finebeard.service.UserActivationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class ActivationDataCleanupScheduler {

    private final UserActivationService userActivationService;
    private final Logger logger = LoggerFactory.getLogger(ActivationDataCleanupScheduler.class);

    public ActivationDataCleanupScheduler(UserActivationService userActivationService) {
        this.userActivationService = userActivationService;
    }

    @Scheduled(cron = "* * 1 * * 7")
    @Transactional
    public void cleanUpActivationCodes() {
        try {
            long deletedActivationCodeEntitiesCount = userActivationService.cleanUpObsoleteActivationEntities();
            logger.info("Deleted {} expired activation codes.", deletedActivationCodeEntitiesCount);
        } catch (Exception e) {
            logger.error("Error while cleaning up expired activation codes", e);
        }

    }


    @Scheduled(cron = "* * 1 * * 6")
    @Transactional
    public void cleanUpActivationAttempts() {
        try {
            long deletedActivationAttemptsCount = userActivationService.cleanUpObsoleteActivationAttempts();
            logger.info("Deleted {} activation attempts.", deletedActivationAttemptsCount);
        } catch (Exception e) {
            logger.error("Error while cleaning up activation attempts", e);
        }

    }



}
