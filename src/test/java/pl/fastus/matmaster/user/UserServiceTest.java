package pl.fastus.matmaster.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.fastus.matmaster.user.dto.UserRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createUser() {
        final UserRequest userRequest = new UserRequest().setLogin("tomek@midex.pl")
                .setPassword("password")
                .setName("Tom")
                .setSureName("Kar");

        final User userToReturn = User.builder()
                .login("tomek@midex.pl")
                .password("password")
                .name("Tom")
                .sureName("Kar")
                .role("ROLE_USER")
                .build();

        given(repository.findById(any())).willReturn(Optional.empty());
        given(repository.save(any(User.class))).willReturn(userToReturn);

        final User user = service.createUser(userRequest);

        assertAll(
                ()->assertEquals("tomek@midex.pl", user.getLogin()),
                ()->assertEquals("password", user.getPassword()),
                ()->assertEquals("Tom", user.getName()),
                ()->assertEquals("Kar", user.getSureName()),
                ()->assertEquals("ROLE_USER", user.getRole())
        );
    }
}
