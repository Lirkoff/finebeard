package bg.softuni.finebeard.repository;

import bg.softuni.finebeard.model.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

}
