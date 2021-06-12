package pl.fastus.matmaster.exceptions;

/**
 * Created by Tom - 12.06.2021
 */
public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
