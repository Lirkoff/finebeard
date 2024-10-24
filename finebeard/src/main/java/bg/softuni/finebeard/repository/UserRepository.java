package bg.softuni.finebeard.repository;

import bg.softuni.finebeard.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);


    @EntityGraph(attributePaths = {"roles"})
    Page<UserEntity> findAll(Pageable pageable);
    
    Optional<UserEntity> findByProviderId(String providerId);
    
}
