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

public class FinebeardUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public FinebeardUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(FinebeardUserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found!"));
    }

    private static UserDetails map(UserEntity user) {
        return User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(FinebeardUserDetailsService::map).toList())
                .build();
    }

    private static GrantedAuthority map(UserRolesEntity userRolesEntity) {
        return new SimpleGrantedAuthority("ROLE_" + userRolesEntity.getRole().name());
    }
}
