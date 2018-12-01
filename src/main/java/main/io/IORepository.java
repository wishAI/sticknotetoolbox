package main.io;

import main.model.Loadable;
import main.model.Saveable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class IORepository {

    private static IORepository ioRepository;

    private IORepository() {
    }

    public void read(Loadable loader) {
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

    public void write(Saveable saver) {
        System.out.println("Save status !");

        String content = saver.saveContent();
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("outputfile.txt", StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            try {
                new File("outputfile.txt").createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                writer = new PrintWriter("outputfile.txt", StandardCharsets.UTF_8);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.print(content);
        writer.close();

        System.out.println("Save complete !");
    }

    public static IORepository getInstance() {
        if(ioRepository == null) {
            ioRepository = new IORepository();
        }

        return ioRepository;
    }

}
