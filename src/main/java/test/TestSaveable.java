package test;

import main.model.Board;
import main.model.Saveable;
import main.model.notes.ClockNote;
import main.model.notes.TextNote;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestSaveable {
    private static final String DATA =
                    "main.model.notes.TextNote;" +
                    "red;Hello;" +
                    "main.model.notes.ClockNote;" +
                    "blue; ;";


    private Board board;

    @Test
    public void test() {
        board = new Board();
        testSaveable(board);
    }

    private void testSaveable(Saveable saveable) {
        board.addNote(new TextNote("Hello"));
        board.addNote(new ClockNote());
        String save = saveable.saveContent();
        assertTrue(save.trim().equals(DATA));
    }
}
