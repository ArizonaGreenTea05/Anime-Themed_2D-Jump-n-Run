package gfx;

import game.Game;
import menu.Menu;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {

    private Map<String, SpriteSet> units;
    private Map<String, Image> tiles;

    public SpriteLibrary() {
        units = new HashMap<>();
        tiles = new HashMap<>();
        loadSpritesFromDisk();
    }

    private void loadSpritesFromDisk() {
        loadUnits();
    }

    private void loadUnits() {
        String[] folderNames = Menu.playerName;

        for(String folderName: folderNames) {
            SpriteSet spriteSet = new SpriteSet();
            String pathToFolder = "/game/themes/" + Menu.getGameTheme() + "/characters/" + folderName;
            String[] sheetsInFolder = getSheetsInFolder();

            for(String sheetName: sheetsInFolder) {
                spriteSet.addSheet(
                        sheetName.substring(0, sheetName.length() - 4),
                        ImageUtils.loadImage(pathToFolder + "/" + sheetName));
            }

            units.put(folderName, spriteSet);
        }
    }

    private static String[] getSheetsInFolder() {
        return Menu.getPlayerSheetsInFolder();
    }


    public SpriteSet getUnit(String name) {
        return units.get(name);
    }

    public  Image getTile(String name){
        return tiles.get(name);
    }
}
