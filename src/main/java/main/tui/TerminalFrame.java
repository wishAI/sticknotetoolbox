//package main.tui;
//
//import main.model.Board;
//import main.model.notes.TextNote;
//
//import java.util.Scanner;
//
//public class TerminalFrame {
//    private static final String COLOR_RESET = "\u001B[0m";
//    private static final int NOTE_COLUMN = 45;
//    private static final int NOTE_ROW = 13;
//    private static final int ROW_NOTE_NUM = 3;
//    private static final String TUTORIAL =
//            "How To Use: " +
//            "select (num)\n -- select a sticky note\n" +
//            "color (color)\n -- change current color\n" +
//            "add (text)\n -- add a sticky note\n" +
//            "show\n -- show all the sticky notes\n" +
//            "edit (text)\n -- edit select sticky note\n" +
//            "remove\n -- remove selected sticky note\n" +
//            "clean\n -- remove all sticky notes";
//
//    private int selectedNote;
//    private String currentColor;
//    private Board board;
//    private Scanner scan;
//
//    // EFFECTS: construct a new terminal frame
//    public TerminalFrame() {
//        // initialize scanner for input
//        scan = new Scanner(System.in);
//
//        // setup board and default color
//        int selectedNote = 0;
//        currentColor = TextNote.COLORS[0];
//        board = new Board();
//
//        TextNote tutorial = new TextNote(TUTORIAL, currentColor);
//        board.addNote(tutorial);
//        printNotes();
//        loopInteraction();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: provide the main loop interaction for users to add, edit, remove, change color of notes
//    private void loopInteraction() {
//
//        // initialize the loop
//        boolean runtimeFlag = true;
//
//        // welcome and show what user can do
//        System.out.println("Welcome to the Sticky Notes Toolbox application. What would you like to do?");
//
//        while(runtimeFlag) {
//
//            System.out.println("\u001B[32m" + "Sticky Note Toolbox" + COLOR_RESET);
//            System.out.print(">> ");
//
//            // get and process with command from user
//            String command = scan.nextLine().trim();
//            int index = -1;
//            for(int i = 0; i < command.length(); i ++) {
//                if(command.charAt(i) == ' ') {
//                    index = i;
//                    break;
//                }
//            }
//            String cmd, arg = "";
//            if(index == -1) {
//                cmd = command;
//            } else {
//                cmd = command.substring(0, index);
//                if(index + 1 < command.length()) {
//                    arg = command.substring(index + 1).trim();
//                }
//            }
//
//            // execute based on the choice
//            switch(cmd) {
//                // add sticky note to board
//                case "add":
//                    addNote(arg);
//                    break;
//                // Show all the sticky notes
//                case "show":
//                    showNotes();
//                    break;
//                // Edit a sticky note
//                case "edit":
//                    editNote(arg);
//                    break;
//                // Remove a Sticky Note
//                case "remove":
//                    removeNote();
//                    break;
//                // Clean the board
//                case "clean":
//                    cleanBoard();
//                    break;
//                // Select sticky note
//                case "select":
//                    selectNote(arg);
//                    break;
//                // Change color
//                case "color":
//                    chooseColor(arg);
//                    break;
//                // Exit the program
//                case "exit":
//                    exit();
//                    runtimeFlag = false;
//                    break;
//                default:
//                    System.out.println("Invalid command !!");
//                    break;
//            }
//        }
//
//        scan.close();
//    }
//
//    // REQUIRES: arg can be converted to integer notes
//    // MODIFIES: this
//    // EFFECTS: change selectedNote to integer version of arg if valid
//    private void selectNote(String arg) {
//        int id = Integer.parseInt(arg);
//        if(id > 0 && id <= board.getNotes().size()) {
//            selectedNote = id;
//            System.out.println("Sticy Note #" + id + " selected. ");
//        } else {
//            System.out.println("Invalid index !!");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: replace currentColor with the color entered by the user
//    private void chooseColor(String arg) {
//        for(String color: TextNote.COLORS) {
//            if(arg.equals(color)) {
//                currentColor = color;
//                System.out.println("Color is set to " + color);
//                return;
//            }
//        }
//        System.out.println("Invalid color !!");
//    }
//
//    // EFFECTS: show all the sticky note in board.notes with content and color
//    private void showNotes() {
//        // print all the content of sticky notes with index
//        System.out.println("The list of Sticky Notes: ");
//        printNotes();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: add a new sticky note to board.notes with the content entered by user
//    private void addNote(String arg) {
//        // create the sticky note and with content
//        TextNote note = new TextNote(arg, currentColor);
//        board.addNote(note);
//
//        printNotes();
//        System.out.println("A sticky note is added successfully.");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: replace the content and color of selected TextNote with arg and currentColor
//    private void editNote(String arg) {
//        // replace content in the sticky note
//        if(selectedNote > 0 && selectedNote <= board.getNotes().size()) {
//            board.editNote(selectedNote - 1, arg, currentColor);
//
//            printNotes();
//            System.out.println("Sticky note Edited. ");
//        } else {
//            System.out.println("Invalid index !!");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: remove the sticky note in board.notes with the index entered by the user
//    private void removeNote() {
//        // remove the sticky note
//        if(selectedNote > 0 && selectedNote <= board.getNotes().size()) {
//            board.removeNote(selectedNote);
//
//            printNotes();
//            System.out.println("Sticky note Removed. ");
//        } else {
//            System.out.println("Invalid index !!");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: remove all the sticky notes in board.notes if confirmed by the user
//    private void cleanBoard() {
//        // make sure the user really want to do that
//        System.out.println("Are you sure that you want to delete all the sticky notes? (y/n)");
//        boolean isConfirmed = scan.nextLine().charAt(0) == 'y';
//
//        // clean the board if confirmed
//        if(isConfirmed) {
//            board.empty();
//            System.out.println("Board cleaned. ");
//        }
//    }
//
//    // EFFECTS: print a message before exiting
//    private void exit() {
//        System.out.println("Thanks for using. Bye. ");
//    }
//
//    // EFFECTS: print all the sticky notes in board.notes with content and color
//    private void printNotes() {
//        // for each ROW_NOTE_NUM sticky notes that will print in the same row
//        for(int id = 0; id < board.getNotes().size(); id += ROW_NOTE_NUM) {
//
//            int[] cursors = new int[NOTE_COLUMN];
//            for(int i = 0; i < NOTE_COLUMN; i ++) {
//                cursors[i] = 0;
//            }
//
//            // for each row inside sticky note
//            for(int r = 0; r <= NOTE_ROW; r ++) {
//
//                // for each sticky note in the big row
//                for(int i = id; i < id + ROW_NOTE_NUM; i ++) {
//
//                    // stop printing when i >= size of notes
//                    if(i >= board.getNotes().size()) {
//                        break;
//                    }
//
//                    // get print content, add to enough chars if it is not long enough
//                    String content = board.getNotes().get(i).getContent();
//                    String rowCon = "";
//                    int count = 0;
//                    while(cursors[i] < content.length() && count < NOTE_COLUMN) {
//                        if(content.charAt(cursors[i]) == '\n') {
//                            cursors[i] ++;
//                            break;
//                        }
//                        rowCon += content.charAt(cursors[i]);
//                        cursors[i] ++;
//                        count ++;
//                    }
//                    while(rowCon.length() < NOTE_COLUMN) {
//                        rowCon += " ";
//                    }
//
//                    // print content
//                    System.out.print(convertColor(board.getNotes().get(i).getColor()) + "\u001B[30m" + rowCon + COLOR_RESET);
//                    System.out.print("  ");
//                }
//                System.out.println();
//            }
//            System.out.println();
//        }
//    }
//
//    // REQUIRES: color should be able to find in TextNote.COLORS
//    // EFFECTS: Return the color with format that can be printed in terminal
//    private static String convertColor(String color) {
//        switch (color) {
//            case "red":
//                return "\u001B[41m";
//            case "green":
//                return "\u001B[42m";
//            case "blue":
//                return "\u001B[44m";
//            case "purple":
//                return "\u001B[45m";
//            case "yellow":
//            default:
//                return "\u001B[43m";
//        }
//    }
//
//}
