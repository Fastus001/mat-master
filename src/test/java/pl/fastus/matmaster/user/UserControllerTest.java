package pl.fastus.matmaster.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.fastus.matmaster.user.dto.UserRequest;
import pl.fastus.matmaster.user.dto.UserResponse;
import pl.fastus.matmaster.user.dto.UserUpdate;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    public static final String LOGIN = "tomek@midex.pl";
    public static final LocalDateTime DATE =  LocalDateTime.of(2020, 6, 5, 0, 1);

    @Mock
    UserService service;

    @InjectMocks
    UserController controller;

    MockMvc mockMvc;

    UserRequest userRequest;
    UserResponse userResponse;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        setUserRequestAndResponse();
    }

    @Test
    void getUserByLogin() throws Exception {
        given(service.getUserByLogin(any())).willReturn(userResponse);

        mockMvc.perform(get("/api/v1/user/"+LOGIN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login", is(LOGIN)))
                .andExpect(jsonPath("$.name", is("Tom")))
                .andExpect(jsonPath("$.sureName", is("Kar")))
                .andExpect(jsonPath("$.created[0]", is(2020)))
                .andExpect(jsonPath("$.created[1]", is(6)))
                .andExpect(jsonPath("$.created[2]", is(5)))
                .andExpect(jsonPath("$.created[3]", is(0)))
                .andExpect(jsonPath("$.created[4]", is(1)));
    }

    @Test
    void createUser() throws Exception {
        given(service.createUser(userRequest)).willReturn(userResponse);

        mockMvc.perform(post("/api/v1/user/create")
                .content(asJsonString(userRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.login", is(LOGIN)))
                .andExpect(jsonPath("$.name", is("Tom")))
                .andExpect(jsonPath("$.sureName", is("Kar")))
                .andExpect(jsonPath("$.created[0]", is(2020)))
                .andExpect(jsonPath("$.created[1]", is(6)))
                .andExpect(jsonPath("$.created[2]", is(5)))
                .andExpect(jsonPath("$.created[3]", is(0)))
                .andExpect(jsonPath("$.created[4]", is(1)));

    }

    @Test
    void update() throws Exception {
        given(service.updateUser(any(), any(UserUpdate.class))).willReturn(userResponse);

        mockMvc.perform(put("/api/v1/user/"+LOGIN)
                .content(asJsonString(userRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login", is(LOGIN)))
                .andExpect(jsonPath("$.name", is("Tom")))
                .andExpect(jsonPath("$.sureName", is("Kar")))
                .andExpect(jsonPath("$.created[0]", is(2020)))
                .andExpect(jsonPath("$.created[1]", is(6)))
                .andExpect(jsonPath("$.created[2]", is(5)))
                .andExpect(jsonPath("$.created[3]", is(0)))
                .andExpect(jsonPath("$.created[4]", is(1)));
    }

    @Test
    void deactivate() throws Exception {
        given(service.deactivateUser(any())).willReturn(LOGIN);

        final String content = mockMvc.perform(delete("/api/v1/user/" + LOGIN))
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(LOGIN, content);
    }

    private void setUserRequestAndResponse() {
        userRequest = new UserRequest()
                .setLogin(LOGIN)
                .setPassword("password")
                .setName("Tom")
                .setSureName("Kar");

        userResponse = new UserResponse()
                .setLogin(LOGIN)
                .setName("Tom")
                .setSureName("Kar")
                .setCreated(DATE);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
