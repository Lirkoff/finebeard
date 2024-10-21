package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.entity.ActivationAttemptEntity;
import bg.softuni.finebeard.model.entity.UserActivationCodeEntity;
import bg.softuni.finebeard.model.events.UserRegisteredEvent;
import bg.softuni.finebeard.model.rate_limiting.RateLimiterPerIp;
import bg.softuni.finebeard.repository.ActivationAttemptRepository;
import bg.softuni.finebeard.repository.UserActivationCodeRepository;
import bg.softuni.finebeard.repository.UserRepository;
import bg.softuni.finebeard.service.EmailService;
import bg.softuni.finebeard.service.MonitoringService;
import bg.softuni.finebeard.service.UserActivationService;
import bg.softuni.finebeard.service.exception.ObjectNotFoundException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;


@Service
public class UserActivationServiceImpl implements UserActivationService {

    private static final String ACTIVATION_CODE_SYMBOLS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ACTIVATION_CODE_LENGTH = 20;

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final UserActivationCodeRepository userActivationCodeRepository;
    private final ActivationAttemptRepository activationAttemptRepository;

    public UserActivationServiceImpl(EmailService emailService,
                                     UserRepository userRepository,
                                     UserActivationCodeRepository userActivationCodeRepository,
                                     ActivationAttemptRepository activationAttemptRepository,
                                     MonitoringService monitoringService) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.userActivationCodeRepository = userActivationCodeRepository;
        this.activationAttemptRepository = activationAttemptRepository;
    }

    @Override
    @EventListener(UserRegisteredEvent.class)
    public void userRegistered(UserRegisteredEvent event) {
        String activationCode = createActivationCode(event.getUserEmail());

        emailService.sendRegistrationEmail(event.getUserEmail(), event.getUserNames(), activationCode);
    }

    @Override
    public Long cleanUpObsoleteActivationEntities() {
        Instant cutoff = Instant.now().minus(24, ChronoUnit.HOURS);

        return userActivationCodeRepository.deleteByCreatedBefore(cutoff);
    }

    @Override
    public Long cleanUpObsoleteActivationAttempts() {
        Instant cutoff = Instant.now().minus(2, ChronoUnit.DAYS);

        return activationAttemptRepository.deleteByAttemptTimeBefore(cutoff);

    }



    @Override
    public String createActivationCode(String userEmail) {
        String activationCode = generateActivationCode();

        UserActivationCodeEntity userActivationCodeEntity = new UserActivationCodeEntity();
        userActivationCodeEntity.setActivationCode(activationCode);
        userActivationCodeEntity.setCreated(Instant.now());
        userActivationCodeEntity.setUsed(false);
        userActivationCodeEntity.setUser(userRepository.findByEmail(userEmail).orElseThrow(() -> new ObjectNotFoundException("User not found!")));

        userActivationCodeRepository.save(userActivationCodeEntity);

        return activationCode;
    }

    private static String generateActivationCode() {
        StringBuilder activationCode = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < ACTIVATION_CODE_LENGTH; i++) {
            int randInx = random.nextInt(ACTIVATION_CODE_SYMBOLS.length());
            activationCode.append(ACTIVATION_CODE_SYMBOLS.charAt(randInx));
        }

        return activationCode.toString();
    }


    @Override
    @RateLimiterPerIp(name = "activateUser")
    public boolean activateUser(String activationCode, String ipAddress) {
//        monitoringService.logActivationAttempts(activationCode,ipAddress);

        UserActivationCodeEntity codeEntity = userActivationCodeRepository.getByActivationCode(activationCode);

        boolean successful = false;


        if (codeEntity != null && isActivationCodeExpired(codeEntity) && !codeEntity.isUsed()) {
            codeEntity.setUsed(true);
            codeEntity.getUser().setActive(true);
            userActivationCodeRepository.save(codeEntity);
            successful = true;
        }

        ActivationAttemptEntity attempt = new ActivationAttemptEntity();
        attempt.setActivationCode(activationCode);
        attempt.setIpAddress(ipAddress);
        attempt.setAttemptTime(Instant.now());
        attempt.setSuccessful(successful);
        activationAttemptRepository.save(attempt);


        return successful;
    }


    private boolean isActivationCodeExpired(UserActivationCodeEntity codeEntity) {
        return codeEntity.getCreated().plus(24, ChronoUnit.HOURS).isAfter(Instant.now());
    }

}
