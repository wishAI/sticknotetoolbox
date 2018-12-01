package main.gui.old;

import main.model.*;
import main.model.notes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class AppFrame extends JFrame {

    public static final Color TRANSPARENT = new Color(0, 0 ,0, 0);


    private BoardPanel boardPanel;
    private CommandPanel commandPanel;
    private Saveable saver;
    private Loadable loader;


    private class CommandPanel extends JPanel {

        private JTextField txt_content;
        private JButton btn_save;
        private JButton btn_add;
        private JButton btn_record;
        private JButton btn_getRecord;
        private JButton btn_remove;

        // EFFECTS: construct a new command panel
        public CommandPanel() {
            // set properties
            setPreferredSize(new Dimension(1100, 110));
            setBackground(TRANSPARENT);

            // add buttons and inputs
            setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));
            txt_content = new JTextField();
            txt_content.setPreferredSize(new Dimension(500, 80));
            txt_content.setBackground(Color.WHITE);
            txt_content.setBorder(BorderFactory.createEmptyBorder());
            add(txt_content);
            btn_remove= new JButton("remove");
            btn_remove.setPreferredSize(new Dimension(80, 80));
            btn_remove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int id = Integer.parseInt(txt_content.getText().trim());
                    boardPanel.getBoard().removeNote(id);
                    boardPanel.render();
                    AppFrame.this.repaint();
                }
            });
            add(btn_remove);
            btn_record = new JButton("record");
            btn_record.setPreferredSize(new Dimension(80, 80));
            btn_record.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int id = Integer.parseInt(txt_content.getText().trim());
                    Recordable recordable = (Recordable) boardPanel.getBoard().getNotes().get(id);
                    recordable.record();
                }
            });
            add(btn_record);
            btn_getRecord = new JButton("get record");
            btn_getRecord.setPreferredSize(new Dimension(80, 80));
            btn_getRecord.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int id = Integer.parseInt(txt_content.getText().trim());
                    Recordable recordable = (Recordable) boardPanel.getBoard().getNotes().get(id);
                    StringBuilder message = new StringBuilder();
//                    for(int i = 0; i < recordable.recordSize(); i ++) {
//                        message.append(recordable.getRecord(i)).append("\n");
//                    }
                    JOptionPane.showMessageDialog(null, message.toString());
                }
            });
            add(btn_getRecord);
            btn_add = new JButton("add");
            btn_add.setPreferredSize(new Dimension(80, 80));
            btn_add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String[] strs = txt_content.getText().split(" ");
                    int id = Integer.parseInt(strs[0]);
                    String color = strs[1];

                    // add a note
                    StickyNote note = null;
                    if(id == 1) {
                        note = new TextNote(color);
                    } else if(id == 2) {
                        note = new ClockNote(color);
                    } else if(id == 3) {
                        note = new CalculatorNote(color);
                    } else {
                        note = new AccountingNote(color);
                    }

                    boardPanel.getBoard().addNote(note);
                    boardPanel.render();
                    AppFrame.this.repaint();
                }
            });
            add(btn_add);
            btn_save = new JButton("save");
            btn_save.setPreferredSize(new Dimension(80, 80));
            btn_save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    saveStatus();
                }
            });
            add(btn_save);
        }

    }

    // EFFECTS: construct the app frame (main window)
    public AppFrame() {
        super("Sticky Note Toolbox");

        // setup basic properties of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);

        // set main panel properties
        setBackground(new Color(0, 0, 0, 200));
        getContentPane().setPreferredSize(new Dimension(1200, 900));
        pack();
        setLocationRelativeTo(null);

        // set up board
        Board board = new Board();
        saver = board;
        loader = board;

        // load status from file
        loadStatus();

        // add panel and components
        boardPanel = new BoardPanel(board);
        add(boardPanel, BorderLayout.CENTER);
        commandPanel = new CommandPanel();
        add(commandPanel, BorderLayout.PAGE_END);

        // set visible
        setVisible(true);
    }

    private void loadStatus() {
        System.out.println("Load status !");

        String all = "";
        List<String> strs = null;
        try {
            strs = Files.readAllLines(Paths.get("outputfile.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String str: strs) {
            all += str;
        }
        loader.loadContent(all);

        System.out.println("Load complete !");
    }

    private void saveStatus() {
        System.out.println("Save status !");

        String content = saver.saveContent();
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("outputfile.txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        writer.print(content);
        writer.close();

        System.out.println("Save complete !");
    }

}
