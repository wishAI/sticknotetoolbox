package main.model.exceptions;

import main.model.notes.StickyNote;

public class CalculationErrorException extends StickyNoteException {

    public CalculationErrorException(String message, StickyNote note) {
        super(message, note);
    }

}
