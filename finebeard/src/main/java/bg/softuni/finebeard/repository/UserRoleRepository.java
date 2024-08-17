package bg.softuni.finebeard.repository;

import bg.softuni.finebeard.model.entity.UserRolesEntity;
import bg.softuni.finebeard.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRolesEntity,Long> {
    UserRolesEntity getByRole(UserRoleEnum userRoleEnum);

}
