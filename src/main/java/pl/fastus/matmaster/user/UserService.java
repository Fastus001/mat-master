package pl.fastus.matmaster.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.fastus.matmaster.enums.Status;
import pl.fastus.matmaster.exceptions.UserAlreadyExistException;
import pl.fastus.matmaster.exceptions.UserNotFoundException;
import pl.fastus.matmaster.user.dto.UserRequest;
import pl.fastus.matmaster.user.dto.UserResponse;
import pl.fastus.matmaster.user.dto.UserUpdate;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserResponse createUser(UserRequest request){
        final Optional<User> optionalUser = repository.findById(request.getLogin());
        if(optionalUser.isEmpty()){
            return mapper.toUserResponse(repository.save(
                    User.builder()
                    .login(request.getLogin())
                            //todo - password encoder when security comes
                    .password(request.getPassword())
                    .name(request.getName())
                    .sureName(request.getSureName())
                    .role("ROLE_USER")
                    .status(Status.ACTIVE)
                    .build()
            ));
        } else {
            throw new UserAlreadyExistException("There is already user with that login!!");
        }
    }

    public UserResponse getUserByLogin(String login) {
        return repository.findById(login)
                .map(mapper::toUserResponse)
                .orElseThrow(()->new UserNotFoundException("There is not User with given login!"));
    }

    @Transactional
    public UserResponse updateUser(String login, UserUpdate userUpdate) {
        final User user = repository.findById(login)
                .orElseThrow(() -> new UserNotFoundException("There is not User with given login!"));
        //todo - add encrypt!!
        user.setPassword(userUpdate.getPassword());
        user.setName(userUpdate.getName());
        user.setSureName(userUpdate.getSureName());

        return mapper.toUserResponse(user);
    }

    @Transactional
    public String deactivateUser(String login) {
        final User user = repository.findById(login)
                .orElseThrow(() -> new UserNotFoundException("There is not User with given login!"));
        user.setStatus(Status.INACTIVE);

        return user.getLogin();
    }
}
