package main.model.exceptions;

public class WrongOperandException extends RuntimeException{

    public WrongOperandException() {
        super("This number does not exist on the calculator. ");
    }

}
