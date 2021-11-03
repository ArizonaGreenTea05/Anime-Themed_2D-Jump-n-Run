package gfx;

import menu.Menu;

import java.util.HashMap;
import java.util.Map;

public class SpriteLibrary {

    private final static String PATH_TO_UNITS = "/game/themes/" + Menu.getGameTheme() + "/characters";

    private Map<String, SpriteSet> units;

    public SpriteLibrary() {
        units = new HashMap<>();
        loadSpritesFromDisk();
    }

    private void loadSpritesFromDisk() {
        String[] folderNames = Menu.playerName;

        for(String folderName: folderNames) {
            SpriteSet spriteSet = new SpriteSet();
            String pathToFolder = PATH_TO_UNITS + "/" + folderName;
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


    public SpriteSet getUnit() {
        return units.get(Menu.getPlayerName());
    }
}
