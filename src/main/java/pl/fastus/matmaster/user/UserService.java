package pl.fastus.matmaster.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.fastus.matmaster.exceptions.UserAlreadyExistException;
import pl.fastus.matmaster.user.dto.UserRequest;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    public User createUser(UserRequest request){
        final Optional<User> optionalUser = repository.findById(request.getLogin());
        if(optionalUser.isEmpty()){
            return repository.save(
                    User.builder()
                    .login(request.getLogin())
                            //todo - password encoder when security comes
                    .password(request.getPassword())
                    .name(request.getName())
                    .sureName(request.getSureName())
                    .role("ROLE_USER")
                    .build()
            );
        } else {
            throw new UserAlreadyExistException("There is already user with that login!!");
        }
    }
}
