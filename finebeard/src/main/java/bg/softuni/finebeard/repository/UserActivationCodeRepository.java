package bg.softuni.finebeard.repository;

import bg.softuni.finebeard.model.entity.UserActivationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface UserActivationCodeRepository extends JpaRepository<UserActivationCodeEntity, Long> {

    UserActivationCodeEntity getByActivationCode(String activationCode);

    Long deleteByCreatedBefore(Instant cutoff);
}
