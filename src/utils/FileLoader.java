package utils;

import menu.Menu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FileLoader {

// save file
    public static void save(String data, String filename){
        save(data, filename,"");
    }

    public static void save(String data, String filename, String path){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("resources/" + path + filename));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            System.err.println("ERROR! failed to save file '" + filename + "'");
        }
    }

// load file
    public static String load(String filename) {
        return load(filename,"");
    }

    public static String load(String filename, String path){
        String input;
        
        File file = new File("resources/" + path + filename);

        if(!file.exists()){
            System.err.println("ERROR! file '" + filename + "' not found");
            return null;
        }
        
        try {
            input = new String(Files.readAllBytes(Path.of("resources/" + path + filename)));
        } catch (IOException e) {
            System.err.println("ERROR! failed to load file '" + filename + "'");
            return null;
        }
        return input;
    }


// load a map in a 2D char array
    public static char[][] loadMap(){
        String theme = Menu.getGameTheme();
        String map = Menu.getMapName();
        String path = "game/themes/" + theme + "/maps/" + map + ".map";
        String mapDoc = load(path);

        String[] temp1 = Objects.requireNonNull(mapDoc).split("\n",Integer.MAX_VALUE);
        String[] temp2 = new String[temp1.length];


        //turning array "upside down", so index 0 equals the lowest row of blocks
        for (int i = 0; i < temp2.length; i++) {
            temp2[i] = temp1[temp2.length-1-i];
        }

        // creating 2D-array that contains map split into single characters
        char[][] out = new char[temp2.length][temp2[0].length()];
        for (int i = 0; i < out.length; i++) {
            out[i] = temp2[i].toCharArray();
        }

        return out;
    }


// load icon with specific height/width parameters
    public static Icon loadIcon(String image, String path, int width, int height) {
        Image img = Objects.requireNonNull(loadImage(image, path)).getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(img);
    }


// load image
    public static Image loadImage(String image, String path) {
        Image img = null;
        try {
            img = ImageIO.read(Objects.requireNonNull(FileLoader.class.getResource(path + image + ".png")));
        } catch (IOException e) {
            System.err.println("ERROR! failed to load file '" + image + ".png'");
        }

        return img;
    }


// load file names

    public static final int ALL = 0;
    public static final int SPECIFIC = 1;

    // load all file-/folder-names in this folder
    public static String[] loadFileNames(String path){
        return loadFileNames(path, "", SPECIFIC);
    }

    // load all file-/folder-names in this folder beside the defined "ignore"-files
    public static String[] loadFileNames(String path, String ignore, int specifyIgnore){

        File directoryPath = new File("resources/" + path);

        try {
            // saves all file names from this directory
            String[] contents = directoryPath.list();
            // list used because it is easier to add items to it -> less code & faster
            List<String> contentsList = new LinkedList<>();

            for (String content : contents) {

                if (specifyIgnore == SPECIFIC) {
                    // if specifiyIgnore == SPECIFIC:
                    // every name that equals 'ignore' will not be loaded
                    if (!content.equals(ignore)) {
                        // if content does not need to be ignored: is added to list
                        contentsList.add(content);
                    }
                } else {
                    // if specifiyIgnore == ALL:
                    // every file name that contains 'ignore' will not be loaded
                    if (!content.contains(ignore)) {
                        // if content does not need to be ignored: is added to list
                        contentsList.add(content);
                    }
                }
            }
            // list is converted to String
            String[] out = contentsList.toArray(new String[0]);

            for (int i = 0; i < out.length; i++) {
                // file endings are removed
                out[i] = StringEditor.removeFileEnding(out[i]);
            }

            // file names are sorted
            Arrays.sort(out);

            return out;

        } catch (NullPointerException npee){
            System.err.println("ERROR! failed to load files from '" + path + "'");
        }
        return null;
    }
}
