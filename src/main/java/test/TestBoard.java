package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import main.model.Board;
import main.model.notes.CalculatorNote;
import main.model.notes.ClockNote;
import main.model.notes.StickyNote;
import main.model.notes.TextNote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBoard {

    private static final String COLOR1 = "blue";
    private static final String CONTENT1 = "Hello, World";
    private static final String COLOR2 = "red";
    private static final String COLOR3 = "green";


    private Board board;
    private StickyNote note1;
    private StickyNote note2;
    private StickyNote note3;


    @BeforeEach
    public void setup() {
        board = new Board();
        note1 = new TextNote();
        note1.setColor(CONTENT1);
        note1.setColor(COLOR1);
        note2 = new CalculatorNote();
        note2.setColor(COLOR2);
        note3 = new ClockNote();
        note3.setColor(COLOR3);
        board.addNote(note1);
    }

    @Test
    public void testConstructor() {
        board = new Board();
        assertEquals(board.getNotes().size(), 0);
    }

    // Test addNote() with one stickyNote
    @Test
    public void testAddNoteOne() {
        // check status after
        assertEquals(board.getNotes().size(), 1);
        assertTrue(board.getNotes().get(0).equals(note1));
    }

    // Test addNote() with multiple sticky notes
    @Test
    public void testAddNoteMulti() {
        // do add note
        board.addNote(note2);

        // check status after
        assertEquals(board.getNotes().size(), 2);
        assertTrue(board.getNotes().get(0).equals(note1));
        assertTrue(board.getNotes().get(1).equals(note2));
    }


    // Test removeNote with the one sticky note
    @Test
    public void testRemoveNoteOne() {
        // check initial status
        assertEquals(board.getNotes().size(), 1);

        // do remove
//        board.removeNote(0);

        // check status after
        assertEquals(board.getNotes().size(), 0);
    }

    // Test removeNote with multiple sticky notes
    @Test
    public void testRemoveNoteMulti() {
        board.addNote(note2);
        board.addNote(note3);

        // check initial status
        assertEquals(board.getNotes().size(), 3);

        // do remove
//        board.removeNote(1);

        // check status after
        assertEquals(board.getNotes().size(), 2);
    }

    @Test
    public void testEmpty() {
        board.addNote(note2);
        board.addNote(note3);

         // check initial status
        assertEquals(board.getNotes().size(), 3);

        // do empty
        board.empty();

        // check status after
        assertEquals(board.getNotes().size(), 0);
    }

}
