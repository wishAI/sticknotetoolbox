package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import main.model.Board;
import main.model.notes.Editable;
import main.model.Loadable;
import main.model.notes.ClockNote;
import main.model.notes.StickyNote;
import main.model.notes.TextNote;
import org.junit.jupiter.api.Test;

public class TestLoadable {

    private static final String DATA =
                    "main.model.notes.TextNote;" +
                    "red;Hello;" +
                    "main.model.notes.ClockNote;" +
                    "blue; ;";


    private Board board;

    @Test
    public void test() {
        board = new Board();
        testLoadContent(board);
    }

    private void testLoadContent(Loadable loadable) {
        loadable.loadContent(DATA);

        assertEquals(2, board.getNotes().size());
        StickyNote note1 = board.getNotes().get(0);
        StickyNote note2 = board.getNotes().get(1);
        assertTrue(note1 instanceof TextNote);
        assertTrue(note2 instanceof ClockNote);
        assertEquals(note1.getColor(), "red");
        assertEquals(note2.getColor(), "blue");
        assertTrue(((Editable) note1).getContent().equals("Hello"));
    }

}
