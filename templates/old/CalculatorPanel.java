package main.gui.old;

import main.model.notes.CalculatorNote;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class CalculatorPanel extends NotePanel<CalculatorNote> {

    private JLabel lb_result;


    public class NumClickHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String str = ((JButton) e.getSource()).getText();
            int num = Integer.parseInt(str);
            note.pushOperand(num);
            updateResult();
        }

    }

    public class SignClickHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String str = ((JButton) e.getSource()).getText();
            char c = str.charAt(0);
            note.pushOperator(c);
            updateResult();
        }

    }

    public CalculatorPanel(CalculatorNote note) {
        super(note);

        // add result ,num panel and sign panel
        lb_result = new JLabel("0");
        Map<TextAttribute, Object> att = new HashMap<>();
        att.put(TextAttribute.FAMILY, Font.SANS_SERIF);
        att.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_EXTRA_LIGHT);
        att.put(TextAttribute.SIZE, 35);
        lb_result.setFont(Font.getFont(att));
        lb_result.setPreferredSize(new Dimension(170, 50));
        lb_result.setHorizontalAlignment(JLabel.RIGHT);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(convertColor(note.getColor()));
        JPanel numPanel = new JPanel();
        numPanel.setBackground(convertColor(note.getColor()));
        numPanel.setLayout(new GridLayout(4, 3, 0, 0));
        NumClickHandler handler = new NumClickHandler();
        for(int i = 1; i <= 9; i ++) {
            JButton btn = new JButton(String.valueOf(i));
            btn.setPreferredSize(new Dimension(50, 30));
            btn.addActionListener(handler);
            numPanel.add(btn);
        }
        JButton zero = new JButton("0");
        zero.setPreferredSize(new Dimension(50, 30));
        zero.addActionListener(handler);
        numPanel.add(zero);
        JPanel signPanel = new JPanel();
        signPanel.setBackground(convertColor(note.getColor()));
        signPanel.setLayout(new GridLayout(3, 2, 0, 0));
        char[] signs = {'+', '-', '*', '/', 'c', '='};
        for(char s: signs) {
            JButton btn = new JButton(String.valueOf(s));
            btn.setPreferredSize(new Dimension(50, 30));
            btn.addActionListener(new SignClickHandler());
            signPanel.add(btn);
        }
        panel.add(numPanel);
        panel.add(signPanel);
        add(lb_result);
        add(panel);
    }

    private void updateResult() {
        lb_result.setText(String.valueOf(note.getValue()));
        repaint();
    }

}
