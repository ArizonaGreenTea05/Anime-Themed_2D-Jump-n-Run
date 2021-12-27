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
        loadTiles();
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

    private void loadTiles(){
        BufferedImage image = new BufferedImage(Game.SPRITE_SIZE,Game.SPRITE_SIZE,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        graphics.setColor(Color.red);
        graphics.drawRect(0,0,Game.SPRITE_SIZE,Game.SPRITE_SIZE);

        graphics.dispose();
        tiles.put("default",image);
    }

    private static String[] getSheetsInFolder() {
        return Menu.getPlayerSheetsInFolder();
    }


    public SpriteSet getUnit() {
        return units.get(Menu.getPlayerName());
    }

    public  Image getTile(String name){
        return tiles.get(name);
    }
}
