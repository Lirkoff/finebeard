package bg.softuni.finebeard.service.schedulers;


import bg.softuni.finebeard.service.UserActivationService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ActivationLinkCleanupScheduler {

    private final UserActivationService userActivationService;

    public ActivationLinkCleanupScheduler(UserActivationService userActivationService) {
        this.userActivationService = userActivationService;
    }

//    @Scheduled(fixedRate = 10_000,
//    initialDelay = 10_000)
//    public void cleanUp() {
//        System.out.println("Trigger cleanup of activation links. " + LocalDateTime.now());
//        userActivationService.cleanUpObsoleteActivationLinks();
//    }
}
