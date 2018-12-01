package main.gui.old;

import main.model.StickyNote;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public abstract class NotePanel<N extends StickyNote> extends JPanel {

    private JTextArea txt_content;

    protected N note;


    private class Repainter implements MouseListener {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            repaint();
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
            repaint();
        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent e) {
            repaint();
        }

        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            repaint();
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            repaint();
        }
    }

    // EFFECTS: construct a new note panel with content
    public NotePanel(N note) {
        super();

        // set panel style
        setBorder(BorderFactory.createEmptyBorder(10, 0 , 0, 0));
        setBackground(convertColor(note.getColor()));
        addMouseListener(new Repainter());

        this.note = note;
    }

    public N getNote() {
        return note;
    }

    protected static Color convertColor(String color) {
         Color[] rgbColors = {
                new Color(255, 230, 105, 255),
                new Color(255, 99, 105, 255),
                new Color(176, 224, 99,255),
                new Color(125, 209, 240,255),
                new Color(194, 181, 250,255)
         };

         for(int i = 0; i < StickyNote.COLORS.length; i ++) {
             if(StickyNote.COLORS[i].equals(color)) {
                 return rgbColors[i];
             }
         }

         return null;
    }
}
