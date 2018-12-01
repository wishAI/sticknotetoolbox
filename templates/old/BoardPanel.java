package main.gui.old;

import main.model.Board;
import main.model.StickyNote;
import main.model.View;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class BoardPanel extends JPanel {

    private static final String TUTORIAL =
            "Welcome to Sticky Note Toolbox. " +
            "\n Here is the tutorial: " +
            "\n 1. type text and click add button to add Sticky Note. " +
            "\n 2. type rm (index) to remove a note. " +
            "\n 3. type ed (index) (text) to edit a note. " +
            "\n 4. type clean to clean the board. ";


    private Board board;


    // EFFECTS: construct the board panel
    public BoardPanel(Board board) {
        // set properties of board panel
        setBackground(AppFrame.TRANSPARENT);
        setBorder(BorderFactory.createEmptyBorder(40, 40, 0, 40));
        setLayout(new GridLayout(3, 4, 30, 30));

        this.board = board;

        render();
    }

    // REQUIRES: stick notes in board <= 12
    // MODIFIES: this
    // EFFECTS: draw the sticky notes in board to the window
    public void render() {
        removeAll();

        for(StickyNote note: board.getNotes()) {
            NotePanel model = null;
            try {
                Class<?> viewPanel = note.getClass().getAnnotation(View.class).value();
                Constructor constructor = viewPanel.getConstructor(note.getClass());
                model = (NotePanel) constructor.newInstance(note);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }

            add(model);

        }

        // add gaps to make grid layout show items properly
        for(int i = 12 - board.getNotes().size(); i > 0; i --) {
            add(new JLabel());
        }

        revalidate();
        repaint();
    }

    public Board getBoard() {
        return board;
    }

}
