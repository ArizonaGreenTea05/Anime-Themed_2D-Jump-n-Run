package game.state;

import controller.NPCController;
import controller.PlayerController;
import core.ScreenSize;
import core.Size;
import entity.Grass;
import entity.NPC;
import entity.Player;
import input.Input;
import org.apache.groovy.parser.antlr4.GroovyParser;
import utils.FileLoader;

import java.util.Arrays;
import java.util.List;

public class GameState extends State {


    public GameState(Input input) {
        super(input);

        createMap();

        initializeCharacters();
    }

    private void initializeCharacters() {

        Player player = new Player(new PlayerController(input), spriteLibrary);
        NPC npc = new NPC(new NPCController(), spriteLibrary);

        gameObjects.addAll(List.of(player,npc));
    }


    private void createMap() {
        int ground = ScreenSize.getGround();
        int screenHeight = ScreenSize.getHeight();

        String[][] sMap = FileLoader.loadMap();


        //everything underneath the ground
        for (int posY = screenHeight; posY >= ground+64; posY-=64) {
            int posX = 0;
            for (int j = 0; j < sMap[0].length-1; j++) {

                if(sMap[0][j].equalsIgnoreCase("G")){
                    Grass grass = new Grass(posX, posY, "grass");
                    gameObjects.add(grass);
                }

                posX += 64;
            }
        }



    }
}
