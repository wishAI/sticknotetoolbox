package main.model.exceptions;

public class WrongOperatorException extends RuntimeException {

    public WrongOperatorException() {
        super("The operator does not exist. ");
    }


}
