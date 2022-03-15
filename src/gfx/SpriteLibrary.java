package gfx;

import menu.Menu;
import utils.FileLoader;
import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {

    private final Map<String, SpriteSet> units;

    public SpriteLibrary() {
        units = new HashMap<>();
        loadSpritesFromDisk();
    }

// loads sprite sheets
    private void loadSpritesFromDisk() {
        loadUnits();
    }

    private void loadUnits() {
        String gameTheme = Menu.getGameTheme();
        String THEME_PATH = "/game/themes/";

        String[] folderNames = FileLoader.loadFileNames( THEME_PATH + gameTheme + "/characters/");

        for(String folderName: folderNames) {
            SpriteSet spriteSet = new SpriteSet();
            String pathToFolder = THEME_PATH + gameTheme + "/characters/" + folderName + "/";
            String[] sheetsInFolder = Menu.getPlayerSheetsInFolder();

            for(String sheetName: sheetsInFolder) {
                spriteSet.addSheet(
                        sheetName,
                        FileLoader.loadImage(sheetName, pathToFolder));
            }

            units.put(folderName, spriteSet);
        }
    }

// getter for sprites of specific entity
    public SpriteSet getUnit(String name) {
        return units.get(name);
    }
}
