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


    public static String[] loadMap(){
        String theme = Menu.getGameTheme();
        String map = Menu.getMapName();
        String path = "game/themes/" + theme + "/maps/" + map + ".txt";
        String mapDoc = load(path);

        String[] out = Objects.requireNonNull(mapDoc).split("\n",Integer.MAX_VALUE);

        return out;
    }




    public static Icon loadIcon(String image, String path, int width, int height) {
        Image img = null;
        img = Objects.requireNonNull(loadImage(image, path)).getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(img);
    }

    public static Image loadImage(String image, String path) {
        Image img = null;
        try {
            img = ImageIO.read(Objects.requireNonNull(FileLoader.class.getResource(path + image)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }
}
