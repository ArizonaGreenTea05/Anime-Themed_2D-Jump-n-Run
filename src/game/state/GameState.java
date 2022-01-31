package game.state;

import controller.NPCController;
import controller.PlayerController;
import core.Position;
import core.ScreenSize;
import entity.*;
import motionAndAbilities.NPCMaA;
import motionAndAbilities.PlayerMaA;
import game.Game;
import input.Input;
import utils.FileLoader;

public class GameState extends State {

    private Player player;


    public GameState(Input input, Game game) {
        super(input, game);

        int ground = ScreenSize.getGround();

        String[][] sMap = FileLoader.loadMap();

        createMap(sMap, ground);

        initializeCharacters(sMap, ground);
    }

    private void initializeCharacters(String[][] sMap, int ground) {
        for (int i = 0; i < sMap.length; i++) {
            for (int j = 0; j < sMap[i].length-1; j++) {
                String letter = sMap[i][j];
                Position relativePosition = new Position((j-1)*64, ground-(i-1)*64);
                if(letter.equalsIgnoreCase("P")){
                    Player player = new Player(
                            new PlayerController(input),
                            new PlayerMaA(2),
                            relativePosition,
                            spriteLibrary,
                            5,
                            this
                    );
                    this.player = player;
                    gameObjects.add(player);
                } else if(letter.equalsIgnoreCase("E")){
                    gameObjects.add(
                            new NPC(
                                    new NPCController(),
                                    new NPCMaA(1.5),
                                    relativePosition,
                                    spriteLibrary,
                                    5,
                                    this
                            )
                    );
                }
            }
        }

        gameObjects.add(player);
    }


    private void createMap(String[][] sMap, int ground) {


        for (int i = 0; i < sMap.length; i++) {
            for (int j = 0; j < sMap[i].length-1; j++) {
                String letter = sMap[i][j];
                Position relativePosition = new Position((j-1)*64, ground-(i-1)*64);
                if(letter.equalsIgnoreCase("G")){
                    mapObjects.add(
                            new Grass(
                                    relativePosition,
                                    Block.GRASS_BLOCK
                            )
                    );
                } else if(letter.equalsIgnoreCase("A")){
                    mapObjects.add(
                            new ActionBlock(
                                    relativePosition,
                                    Block.ACTION_BLOCK
                            )
                    );
                } else if(letter.equalsIgnoreCase("F")){
                    mapObjects.add(
                            new FinishBlock(
                                    relativePosition,
                                    Block.ACTION_BLOCK
                            )
                    );
                }
            }
        }
    }

    public MovingEntity getPlayer() {
        return player;
    }
}
