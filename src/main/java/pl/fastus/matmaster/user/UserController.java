package pl.fastus.matmaster.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.fastus.matmaster.user.dto.UserRequest;
import pl.fastus.matmaster.user.dto.UserResponse;
import pl.fastus.matmaster.user.dto.UserUpdate;

/**
 * Created by Tom - 12.06.2021
 */
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/{login}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUser(@PathVariable("login") String id) {
        return userService.getUserByLogin(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PutMapping("/{login}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse update(@PathVariable("login") String id, @RequestBody UserUpdate userUpdate) {
        return userService.updateUser(id, userUpdate);
    }

    @DeleteMapping("/{login}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deactivate(@PathVariable("login") String id) {
        return userService.deactivateUser(id);
    }
}
