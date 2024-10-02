package bg.softuni.finebeard.repository;

import bg.softuni.finebeard.model.entity.BrandEntity;
import bg.softuni.finebeard.model.enums.BrandEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    Optional<BrandEntity> findByName(BrandEnum name);
}
