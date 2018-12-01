package main.gui.old;

import main.model.notes.TextNote;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class TextPanel extends NotePanel<TextNote> {

    private JTextArea txt_content;

    public class TextChangeHandler implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateContent();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateContent();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateContent();
        }
    }

    public TextPanel(TextNote note) {
        super(note);

        // add text component
        txt_content = new JTextArea((String) (note).getContent());
        txt_content.setBackground(convertColor(note.getColor()));
        txt_content.setEditable(true);
        txt_content.setLineWrap(true);
        txt_content.setWrapStyleWord(true);
        txt_content.setPreferredSize(new Dimension(230, 200));
        txt_content.getDocument().addDocumentListener(new TextChangeHandler());
        add(txt_content);
    }

    protected void updateContent() {
        note.setContent(txt_content.getText());
    }

}

