package main.gui.old;

import main.model.NoteUpdateListener;
import main.model.notes.ClockNote;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;


public class ClockPanel extends NotePanel<ClockNote> implements NoteUpdateListener  {

    private JLabel txt_time;

    public ClockPanel(ClockNote note) {
        super(note);

//        txt_time = new JLabel(makeTimeStr());
        Map<TextAttribute, Object> att = new HashMap<>();
        att.put(TextAttribute.FAMILY, Font.SANS_SERIF);
        att.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_EXTRA_LIGHT);
        att.put(TextAttribute.SIZE, 35);
        txt_time.setFont(Font.getFont(att));
        txt_time.setBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0));
        add(txt_time);

        note.setUpdateListener(this);
    }


    @Override
    public void onNoteUpdate() {
//        txt_time.setText(makeTimeStr());
        repaint();
    }

//    private String makeTimeStr() {
//        String hour = ((Integer) ClockNote.getHour()).toString();
//        String minutes = ((Integer) ClockNote.getMinute()).toString();
//        String second = ((Integer) ClockNote.getSecond()).toString();
//        if(hour.length() < 2)
//            hour = 0 + hour;
//        if(minutes.length() < 2)
//            minutes = 0 + minutes;
//        if(second.length() < 2)
//            second = 0 + second;
//
//        return hour + ":" + minutes + ":" + second;
//    }

}
