package main.service;

import main.io.IORepository;
import main.model.Board;


public class StoreService {


    private IORepository ioRepository;

    private static StoreService noteService;


    private StoreService() {
        ioRepository = IORepository.getInstance();
    }

    public static StoreService getInstance() {
        if(noteService == null) {
            noteService = new StoreService();
        }
        return noteService;
    }

    public void saveNotes(Board board) {
        ioRepository.write(board);
    }

    public void loadBoard(Board board) {
        ioRepository.read(board);
    }

}
