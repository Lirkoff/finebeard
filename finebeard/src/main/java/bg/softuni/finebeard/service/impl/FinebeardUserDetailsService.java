package bg.softuni.finebeard.service.impl;

import bg.softuni.finebeard.model.entity.UserEntity;
import bg.softuni.finebeard.model.entity.UserRolesEntity;
import bg.softuni.finebeard.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



/**
 * A service that provides user details information for authentication.
 *
 * This service is responsible for loading user-specific data from the database and
 * transforming it into a format that Spring Security can use for authentication.
 */
public class  FinebeardUserDetailsService implements UserDetailsService {
    /**
     * Repository for CRUD operations on UserEntity.
     *
     * This repository provides methods to interact with the UserEntity data in the database.
     * It extends JpaRepository, providing a range of data handling operations and custom
     * queries like finding user by their email.
     */
    private final UserRepository userRepository;

    /**
     * Constructs a new FinebeardUserDetailsService with the specified UserRepository.
     *
     * @param userRepository the repository used for accessing user data.
     */
    public FinebeardUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    /**
     * Loads the user by their email for authentication purposes.
     *
     * @param email the email of the user to be loaded.
     * @return UserDetails containing the user's authentication information.
     * @throws UsernameNotFoundException if the user with the specified email is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(FinebeardUserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found!"));
    }

    /**
     * Transforms a UserEntity object into a Spring Security UserDetails object.
     *
     * @param userEntity The user entity to be transformed.
     * @return UserDetails object containing user credentials and authorities.
     */
    private static UserDetails map(UserEntity userEntity) {
        return User
                .withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRoles().stream().map(FinebeardUserDetailsService::map).toList())
                .disabled(!userEntity.isActive())
                .build();
    }

    /**
     * Converts a given UserRolesEntity instance to a GrantedAuthority.
     *
     * @param userRolesEntity The UserRolesEntity instance to be converted.
     * @return A GrantedAuthority object corresponding to the user's role.
     */
    private static GrantedAuthority map(UserRolesEntity userRolesEntity) {
        return new SimpleGrantedAuthority("ROLE_" + userRolesEntity.getRole().name());
    }
}
