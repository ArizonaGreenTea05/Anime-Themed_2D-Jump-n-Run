package core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileLoader{

    public static void save(String data, String filename){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            System.err.println("FEHLER: Beim Speichernvon " + filename + " ist ein Fehler aufgetreten,");
            //e.printStackTrace();
        }
    }
    
    public static String load(String filename){
        String input = null;
        
        File file = new File(filename);
        if(!file.exists()){
            System.err.println("FEHLER: Datei " + filename + " nicht gefunden!");
            return null;
        }
        
        try {
            input = new String(Files.readAllBytes(Path.of(filename)));
        } catch (IOException e) {
            System.err.println("FEHLER: Beim Lesen der Datei " + filename + " ist ein Fehler aufgetreten,");
            return null;
        }
        return input;
    }


    public static void main(String[] args){
        String out = "Das ist ein Test vom Laden und Speichern";
        System.out.println("SAVE: " + out);
        save(out, "FileLoader.txt");
        String in = load("FileLoader.txt");
        System.out.println("LOAD: " + in);
    }

}
