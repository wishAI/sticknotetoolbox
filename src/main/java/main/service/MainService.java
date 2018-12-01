package main.service;

import javafx.application.Application;
import main.gui.MainApplication;


public class MainService {

    private static final String TUI_MODE = "tui";
    private static final String GUI_MODE = "gui";
    private static final String MODE = "gui";


    private static MainService mainService;


    private MainService() {}

    public static MainService getInstance() {
        if(mainService == null) {
            mainService = new MainService();
        }

        return mainService;
    }

    // EFFECTS: start the application
    public static void startApplication(String[] args) {
        if(MODE.equals(TUI_MODE)) {
        } else if(MODE.equals(GUI_MODE)) {
//            Application.launch(MainApplication.class, args);
        }
    }

}
