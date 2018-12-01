package main.model.exceptions;

public class NoteImpossibleItemException extends RuntimeException {

    public NoteImpossibleItemException(int id) {
        super("Invalid index when accessing elements at " + id);
    }
}
