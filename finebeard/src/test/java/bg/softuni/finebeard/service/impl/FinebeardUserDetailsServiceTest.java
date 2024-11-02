package bg.softuni.finebeard.service.impl;


import bg.softuni.finebeard.model.entity.UserEntity;
import bg.softuni.finebeard.model.entity.UserRolesEntity;
import bg.softuni.finebeard.model.enums.UserRoleEnum;
import bg.softuni.finebeard.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class FinebeardUserDetailsServiceTest {

    private FinebeardUserDetailsService serviceToTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    public void setUp() {
        serviceToTest = new FinebeardUserDetailsService(
            mockUserRepository
        );
    }

    @Test
    void testUserNotFound() {

        when(mockUserRepository.findByEmail("pesho@softuni.bg"))
                .thenReturn(Optional.of(new UserEntity()));

        assertThrows(UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("pesho@example.com")
        );
    }

    @Test
    void testUserFoundException() {
        UserEntity testUserEntity = createTestUser();
        when(mockUserRepository.findByEmail(testUserEntity.getEmail()))
                .thenReturn(Optional.of(testUserEntity));


        UserDetails userDetails =
                serviceToTest.loadUserByUsername(testUserEntity.getEmail());


        assertNotNull(userDetails);
        assertEquals(testUserEntity.getEmail(),
                userDetails.getUsername(),
                "Username is not mapped to email!");

        assertEquals(testUserEntity.getPassword(), userDetails.getPassword());
        assertEquals(2, userDetails.getAuthorities().size());
        assertTrue(containsAuthority(userDetails, "ROLE_" + UserRoleEnum.ADMIN),
                "The user is not admin!");
        assertTrue(containsAuthority(userDetails, "ROLE_" + UserRoleEnum.USER),
                "The user is not user!");

    }

    private boolean containsAuthority(UserDetails userDetails, String expectedAuthority) {
        return userDetails
                .getAuthorities()
                .stream()
                .anyMatch(a -> expectedAuthority.equals(a.getAuthority()));

    }

    private static UserEntity createTestUser() {
        UserEntity testUser = new UserEntity()
                .setFirstName("firstName")
                .setLastName("lastName")
                .setEmail("pesho@softuni.bg")
                .setActive(false)
                .setPassword("topsecret")
                .setRoles(
                        new UserRolesEntity().setRole(UserRoleEnum.USER)
                );
        testUser.getRoles().add(new UserRolesEntity().setRole(UserRoleEnum.ADMIN));

        return testUser;
    }
}
