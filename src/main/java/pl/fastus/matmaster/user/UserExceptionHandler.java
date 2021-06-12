package pl.fastus.matmaster.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.fastus.matmaster.exceptions.UserAlreadyExistException;
import pl.fastus.matmaster.exceptions.UserNotFoundException;

/**
 * Created by Tom - 12.06.2021
 */
@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({
            UserAlreadyExistException.class,
            UserNotFoundException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleExceptions(RuntimeException e) {
        return e.getMessage();
    }
}
