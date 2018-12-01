package main.model.exceptions;


public class NoteColorMismatchException extends RuntimeException {

    public NoteColorMismatchException(String color) {
        super("The sticky note cannot be set to the color: " + color);
    }

}
