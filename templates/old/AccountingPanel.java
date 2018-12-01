package main.gui.old;

import main.model.notes.AccountingNote;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class AccountingPanel extends NotePanel<AccountingNote> {

    private JLabel lb_item;
    private JLabel lb_value;
    private JTextField txt_item;
    private JTextField txt_value;

    public class ContentChangeHandler implements DocumentListener {

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

    public AccountingPanel(AccountingNote note) {
        super(note);

        lb_item = new JLabel("Item");
        lb_value = new JLabel("Value");
        lb_item.setHorizontalAlignment(JLabel.CENTER);
        lb_value.setHorizontalAlignment(JLabel.CENTER);
        Map<TextAttribute, Object> att = new HashMap<>();
        att.put(TextAttribute.FAMILY, Font.MONOSPACED);
        att.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_EXTRA_LIGHT);
        att.put(TextAttribute.SIZE, 15);
        lb_item.setFont(Font.getFont(att));
        lb_value.setFont(Font.getFont(att));
        txt_item = new JTextField();
        txt_value = new JTextField();
        txt_item.setPreferredSize(new Dimension(80, 30));
        txt_value.setPreferredSize(new Dimension(80, 30));
        ContentChangeHandler handler = new ContentChangeHandler();
        txt_item.getDocument().addDocumentListener(handler);
        txt_value.getDocument().addDocumentListener(handler);
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(60,0 ,0 ,0 ));
        panel.setLayout(new GridLayout(2, 2, 10, 10));
        panel.setBackground(NotePanel.convertColor(note.getColor()));
        panel.add(lb_item);
        panel.add(lb_value);
        panel.add(txt_item);
        panel.add(txt_value);
        add(panel);
    }

    private void updateContent() {
//        note.setContent(lb_item + " " + lb_value);
    }

}
