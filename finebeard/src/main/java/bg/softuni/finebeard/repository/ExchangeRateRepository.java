package bg.softuni.finebeard.repository;

import bg.softuni.finebeard.model.entity.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, String> {
}
