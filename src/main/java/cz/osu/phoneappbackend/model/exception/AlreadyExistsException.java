package cz.osu.phoneappbackend.model.exception;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String message){
        super(message);
    }
}
