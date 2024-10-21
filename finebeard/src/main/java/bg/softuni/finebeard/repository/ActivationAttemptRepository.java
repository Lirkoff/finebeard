package bg.softuni.finebeard.repository;


import bg.softuni.finebeard.model.entity.ActivationAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;


public interface ActivationAttemptRepository extends JpaRepository<ActivationAttemptEntity, Long> {
    Long deleteByAttemptTimeBefore(Instant cutoff);
}
