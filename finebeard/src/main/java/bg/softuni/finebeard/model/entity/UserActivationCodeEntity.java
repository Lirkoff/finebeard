package bg.softuni.finebeard.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "user_activation_codes")
public class UserActivationCodeEntity extends BaseEntity {

    private String activationCode;

    private Instant created;

    @ManyToOne
    private UserEntity user;

    private boolean used;

    public boolean isUsed() {
        return used;
    }

    public UserActivationCodeEntity setUsed(boolean used) {
        this.used = used;
        return this;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public UserActivationCodeEntity setActivationCode(String activationCode) {
        this.activationCode = activationCode;
        return this;
    }

    public Instant getCreated() {
        return created;
    }

    public UserActivationCodeEntity setCreated(Instant created) {
        this.created = created;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public UserActivationCodeEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}
