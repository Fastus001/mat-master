package pl.fastus.matmaster.user;

import org.junit.jupiter.api.Test;
import pl.fastus.matmaster.enums.Status;
import pl.fastus.matmaster.user.dto.UserResponse;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    UserMapper mapper = new UserMapperImpl();

    @Test
    void toUserResponse() {
        final User user = User.builder()
                .login("tomek@midex.pl")
                .password("password")
                .name("Tom")
                .sureName("Kar")
                .role("ROLE_USER")
                .status(Status.ACTIVE)
                .build();

        final UserResponse userResponse = mapper.toUserResponse(user);

        assertAll(
                ()->assertEquals("tomek@midex.pl", userResponse.getLogin()),
                ()->assertEquals("password", userResponse.getPassword()),
                ()->assertEquals("Tom", userResponse.getName()),
                ()->assertEquals("Kar", userResponse.getSureName())
        );
    }
}
