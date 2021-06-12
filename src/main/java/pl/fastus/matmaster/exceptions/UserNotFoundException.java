package pl.fastus.matmaster.exceptions;

/**
 * Created by Tom - 12.06.2021
 */
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
