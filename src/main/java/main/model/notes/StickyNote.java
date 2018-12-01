package main.model.notes;

import javafx.geometry.Dimension2D;
import main.model.Board;
import main.model.exceptions.NoteColorMismatchException;


public abstract class StickyNote extends Subject {

    public static final String[] COLORS = {
            "red",
            "yellow",
            "green",
            "cyan",
            "blue",
            "purple",
            "grey"
    };


    private String color;
    private Dimension2D size;
    private Board board;
    private Location location;

    public class Location {

        public int x;
        public int y;

        public Location() {
            x = 0;
            y = 0;
        }

    }


	// REQUIRES: color should be able to find in this.colors
	// EFFECT: construct a new sticky note with content and color
    public StickyNote() {
        this.color = COLORS[0];
        location = new Location();
        size = new Dimension2D(200, 200);
    }

    public void setBoard(Board board) {
        if (this.board != board && board != null) {
            this.board = board;
            board.addNote(this);
        }
    }

    public void removeFromBoard() {
        if (this.board != null) {
            Board board = this.board;
            this.board = null;
            board.removeNote(this);
            onRemove();
        }
    }

    // MODIFIES: this
    // EFFECTS: change the color of the sticky note to the parameters
    //          Throw NoteColorMismatchException if color cannot be found in COLORS
    public void setColor(String color) throws NoteColorMismatchException {
        for(String c: COLORS) {
            if(c.equals(color)) {
                this.color = color;
                return;
            }
        }
        throw new NoteColorMismatchException(color);
    }
    // EFFECT: Return the color of sticky note
    public String getColor() {
        return color;
    }

    public Board getBoard() {
        return board;
    }

    public void replace(StickyNote note) {
        board.replaceNote(this, note);
    }

    protected void onRemove() {
    }

}
