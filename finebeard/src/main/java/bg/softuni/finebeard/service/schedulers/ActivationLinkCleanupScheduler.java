package bg.softuni.finebeard.service.schedulers;


import bg.softuni.finebeard.service.UserActivationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Component
public class ActivationLinkCleanupScheduler {

    private final UserActivationService userActivationService;
    private final Logger logger = LoggerFactory.getLogger(ActivationLinkCleanupScheduler.class);

    public ActivationLinkCleanupScheduler(UserActivationService userActivationService) {
        this.userActivationService = userActivationService;
    }

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void cleanUp() {
        System.out.println("Trigger cleanup of activation links. " + LocalDateTime.now());

        try {
            long deletedCount = userActivationService.cleanUpObsoleteActivationLinks();
            logger.info("Deleted {} expired activation codes", deletedCount);
        } catch (Exception e) {
            logger.error("Error while cleaning up expired activation codes", e);
        }

    }
}
