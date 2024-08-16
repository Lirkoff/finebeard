package bg.softuni.finebeard.repository;

import bg.softuni.finebeard.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

//    @Query("SELECT u.email, u.roles FROM UserEntity u ORDER BY u.email")
//    Map<String, Set<UserRolesEntity>> findAllUserEntityEmailsAndRoles();


    List<UserEntity> findAll();
}
