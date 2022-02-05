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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FileLoader {

    public static void save(String data, String filename){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("resources/" + filename));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            System.err.println("ERROR! failed to save file '" + filename + "'");
        }
    }

    public static String load(String filename){
        String input = null;
        
        File file = new File("resources/" + filename);
        if(!file.exists()){
            System.err.println("ERROR! file '" + filename + "' not found");
            return null;
        }
        
        try {
            input = new String(Files.readAllBytes(Path.of("resources/" + filename)));
        } catch (IOException e) {
            System.err.println("ERROR! failed to load file '" + filename + "'");
            return null;
        }
        return input;
    }


    public static String[][] loadMap(){
        String theme = Menu.getGameTheme();
        String map = Menu.getMapName();
        String path = "game/themes/" + theme + "/maps/" + map;
        String mapDoc = load(path);

        String[] temp1 = Objects.requireNonNull(mapDoc).split("\n",Integer.MAX_VALUE);
        String[] temp2 = new String[temp1.length];


        //turning array upside down, so index 0 is the ground
        for (int i = 0; i < temp2.length; i++) {
            temp2[i] = temp1[temp2.length-1-i];
        }

        char[][] cTemp1 = new char[temp2.length][temp2[0].length()];
        String[][] out = new String[cTemp1.length][cTemp1[0].length];

        for (int i = 0; i < cTemp1.length; i++) {
            cTemp1[i] = temp2[i].toCharArray();

            for (int j = 0; j < cTemp1[i].length-1; j++) {
                out[i][j] = String.valueOf(cTemp1[i][j]);
            }
        }

        return out;
    }




    public static Icon loadIcon(String image, String path, int width, int height) {
        Image img = Objects.requireNonNull(loadImage(image, path)).getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(img);
    }

    public static Image loadImage(String image, String path) {

        Image img = null;
        try {
            img = ImageIO.read(Objects.requireNonNull(FileLoader.class.getResource(path + image)));
        } catch (IOException e) {
            System.err.println("ERROR! failed to load file '" + image + "'");
        }

        return img;
    }


    public static final int ALL = 0;
    public static final int SPECIFIC = 1;

    public static String[] loadFileNames(String path){
        return loadFileNames(path, "", SPECIFIC);
    }

    public static String[] loadFileNames(String path, String ignore, int specifyIgnore){

        File directoryPath = new File("resources/" + path);

        try {
            String[] contents = directoryPath.list();
            List<String> contentsList = new LinkedList<>();

            for (int i = 0; i < contents.length; i++) {
                if (specifyIgnore == SPECIFIC) {
                    if (!contents[i].equals(ignore)) {
                        contentsList.add(contents[i]);
                    }
                } else {
                    if (!contents[i].contains(ignore)) {
                        contentsList.add(contents[i]);
                    }
                }
            }
            return contentsList.toArray(new String[0]);
        } catch (NullPointerException npe){
            System.err.println("ERROR! failed to load files from '" + path + "'");
        }
        return null;
    }
}
