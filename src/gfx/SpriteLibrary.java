package gfx;

import menu.Menu;
import utils.FileLoader;

import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {

    private Map<String, SpriteSet> units;

    public SpriteLibrary() {
        units = new HashMap<>();
        loadSpritesFromDisk();
    }

    private void loadSpritesFromDisk() {
        loadUnits();
    }

    private void loadUnits() {
        String gameTheme = Menu.getGameTheme();

        String[] folderNames = FileLoader.loadFileNames("/game/themes/" + gameTheme + "/characters/");

        for(String folderName: folderNames) {
            SpriteSet spriteSet = new SpriteSet();
            String pathToFolder = "/game/themes/" + gameTheme + "/characters/" + folderName;
            String[] sheetsInFolder = Menu.getPlayerSheetsInFolder();

            for(String sheetName: sheetsInFolder) {
                spriteSet.addSheet(
                        sheetName.substring(0, sheetName.length() - 4),
                        ImageUtils.loadImage(pathToFolder + "/" + sheetName));
            }

            units.put(folderName, spriteSet);
        }
    }


    public SpriteSet getUnit(String name) {
        return units.get(name);
    }
}
