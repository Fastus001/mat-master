package pl.fastus.matmaster.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.fastus.matmaster.enums.Status;
import pl.fastus.matmaster.exceptions.UserAlreadyExistException;
import pl.fastus.matmaster.exceptions.UserNotFoundException;
import pl.fastus.matmaster.user.dto.UserRequest;
import pl.fastus.matmaster.user.dto.UserResponse;
import pl.fastus.matmaster.user.dto.UserUpdate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    public static final String LOGIN = "tomek@midex.pl";
    @Mock
    UserRepository repository;

    @Mock
    UserMapper mapper;

    @InjectMocks
    UserService service;

    UserRequest userRequest;
    User userToReturn;
    UserResponse userResponseToReturn;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest().setLogin(LOGIN)
                .setPassword("password")
                .setName("Tom")
                .setSureName("Kar");

        userToReturn = User.builder()
                .login(LOGIN)
                .password("password")
                .name("Tom")
                .sureName("Kar")
                .role("ROLE_USER")
                .status(Status.ACTIVE)
                .build();

        userResponseToReturn = new UserResponse().setLogin(LOGIN)
                .setPassword("password")
                .setName("Tom")
                .setSureName("Kar");
    }

    @Test
    void createUser() {
        given(repository.findById(any())).willReturn(Optional.empty());
        given(repository.save(any(User.class))).willReturn(userToReturn);
        given(mapper.toUserResponse(any(User.class))).willReturn(userResponseToReturn);

        final UserResponse userResponse = service.createUser(userRequest);

        assertAll(
                ()->assertEquals(LOGIN, userResponse.getLogin()),
                ()->assertEquals("password", userResponse.getPassword()),
                ()->assertEquals("Tom", userResponse.getName()),
                ()->assertEquals("Kar", userResponse.getSureName())
        );

        verify(repository, times(1)).findById(any());
        verify(repository,times(1)).save(any(User.class));
        verify(mapper, times(1)).toUserResponse(any(User.class));
    }

    @Test
    @DisplayName("create user, user already exists in DB")
    void createUserShouldThrowException() {
        given(repository.findById(any())).willReturn(Optional.of(userToReturn));

        assertThrows(UserAlreadyExistException.class, ()->service.createUser(userRequest));

        verify(repository, times(1)).findById(any());
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper);
    }

    @Test
    void getUserByLogin() {
        given(repository.findById(any())).willReturn(Optional.of(userToReturn));
        given(mapper.toUserResponse(any(User.class))).willReturn(userResponseToReturn);

        final UserResponse userResponse = service.getUserByLogin(LOGIN);

        assertAll(
                ()->assertEquals(LOGIN, userResponse.getLogin()),
                ()->assertEquals("password", userResponse.getPassword()),
                ()->assertEquals("Tom", userResponse.getName()),
                ()->assertEquals("Kar", userResponse.getSureName())
        );

        verify(repository, times(1)).findById(any());
        verify(mapper, times(1)).toUserResponse(any(User.class));
    }

    @Test
    void getUserByLoginShouldThrowException() {
        given(repository.findById(any())).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()->service.getUserByLogin(LOGIN));

        verify(repository, times(1)).findById(any());
        verifyNoInteractions(mapper);
    }

    @Test
    void updateUser() {
        final UserUpdate userUpdate = new UserUpdate().setPassword("new password")
                .setName("Greg")
                .setSureName("Karma");

        final UserResponse userResponseToReturn = new UserResponse()
                .setLogin(LOGIN)
                .setPassword("new password")
                .setName("Greg")
                .setSureName("Karma");

        given(repository.findById(any())).willReturn(Optional.of(userToReturn));
        given(mapper.toUserResponse(any(User.class))).willReturn(userResponseToReturn);

        UserResponse userResponse = service.updateUser(LOGIN, userUpdate);

        assertAll(
                ()->assertEquals(LOGIN, userResponse.getLogin()),
                ()->assertEquals("new password", userResponse.getPassword()),
                ()->assertEquals("Greg", userResponse.getName()),
                ()->assertEquals("Karma", userResponse.getSureName())
        );

        verify(repository, times(1)).findById(any());
        verify(mapper, times(1)).toUserResponse(any(User.class));
    }

    @Test
    void updateUserShouldThrowException() {
        final UserUpdate userUpdate = new UserUpdate().setPassword("new password")
                .setName("Greg")
                .setSureName("Karma");

        given(repository.findById(any())).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()->service.updateUser("wrong@mail.com", userUpdate));

        verify(repository, times(1)).findById(any());
        verifyNoInteractions(mapper);
    }

    @Test
    void deactivateUser() {
        given(repository.findById(any())).willReturn(Optional.ofNullable(userToReturn));

        String userId = service.deactivateUser(LOGIN);

        assertEquals(LOGIN, userId);
    }

    @Test
    void deactivateUserShouldThrowException() {
        given(repository.findById(any())).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()->service.deactivateUser(LOGIN));
    }
}
