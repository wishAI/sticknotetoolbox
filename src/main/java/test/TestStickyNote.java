package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import main.model.notes.StickyNote;
import main.model.exceptions.NoteColorMismatchException;
import main.model.notes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestStickyNote {

    private StickyNote note;


    @BeforeEach
    void setup() {
        note = new TextNote();
    }


    @Test
    void testConstructor() {
        assertTrue(note.getColor().equals(StickyNote.COLORS[0]));
    }

    @Test
    void testSetColor() {
        try {
            note.setColor("blue");
        } catch (NoteColorMismatchException e) {
            fail("");
        }
        assertTrue(note.getColor().equals("blue"));
    }

    @Test
    void testSetColorMismatchException() {
        try {
            note.setColor("window");

            fail("");
        } catch (NoteColorMismatchException e) {
            System.out.println("Exception catched. ");
        }
    }

}
