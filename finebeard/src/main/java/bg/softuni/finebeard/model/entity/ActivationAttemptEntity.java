package bg.softuni.finebeard.model.entity;

import jakarta.persistence.Entity;

import java.time.Instant;

@Entity
public class ActivationAttemptEntity extends BaseEntity {

    private String activationCode;

    private String ipAddress;

    private Instant attemptTime;

    private boolean successful;

    public String getActivationCode() {
        return activationCode;
    }

    public ActivationAttemptEntity setActivationCode(String activationCode) {
        this.activationCode = activationCode;
        return this;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public ActivationAttemptEntity setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public Instant getAttemptTime() {
        return attemptTime;
    }

    public ActivationAttemptEntity setAttemptTime(Instant attemptTime) {
        this.attemptTime = attemptTime;
        return this;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public ActivationAttemptEntity setSuccessful(boolean successful) {
        this.successful = successful;
        return this;
    }
}
