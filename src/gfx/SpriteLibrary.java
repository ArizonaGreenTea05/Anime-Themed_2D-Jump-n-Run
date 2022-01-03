package gfx;

import menu.Menu;
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
        String[] pn = Menu.playerName;
        String[] npcn = Menu.getNPCs();

        int pnl = pn.length;
        int npcnl = npcn.length;

        int l = pnl + npcnl;

        String[] folderNames = new String[l];

        System.arraycopy(pn, 0, folderNames, 0, pnl);
        System.arraycopy(npcn, 0, folderNames, pnl, npcnl);

        for(String folderName: folderNames) {
            SpriteSet spriteSet = new SpriteSet();
            String pathToFolder = "/game/themes/" + Menu.getGameTheme() + "/characters/" + folderName;
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
