package game.state;

import controller.NPCController;
import controller.PlayerController;
import core.Position;
import core.ScreenSize;
import gameObjects.*;
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

        /*
        player muss zuerst initialisiert werden, damit er in GameObjects an Index 0 zu finden ist
        und sich seine Index-Position somit nicht ändert, egal ob Objekte hinzugefügt oder entfernt werden.
         */
        initializePlayer(sMap, ground);

        initializeNPCs(sMap, ground);
    }

    private void initializePlayer(String[][] sMap, int ground) {
        for (int i = 0; i < sMap.length; i++) {
            for (int j = 0; j < sMap[i].length-1; j++) {
                String letter = sMap[i][j];
                Position relativePosition = new Position((j-1)*64, ground-(i-1)*64);
                if(letter.equalsIgnoreCase("P")){
                    this.player = new Player(
                            new PlayerController(input),
                            new PlayerMaA(3.9),
                            relativePosition,
                            spriteLibrary,
                            5,
                            this
                    );
                    gameObjects.add(player);
                }
            }
        }
    }

    private void initializeNPCs(String[][] sMap, int ground) {
        for (int i = 0; i < sMap.length; i++) {
            for (int j = 0; j < sMap[i].length-1; j++) {
                String letter = sMap[i][j];
                Position relativePosition = new Position((j-1)*64, ground-(i-1)*64);
                if(letter.equalsIgnoreCase("E")){
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
    }


    private void createMap(String[][] sMap, int ground) {


        for (int i = 0; i < sMap.length; i++) {
            for (int j = 0; j < sMap[i].length-1; j++) {
                String letter = sMap[i][j];
                Position relativePosition = new Position((j-1)*64, ground-(i-1)*64);
                if(letter.equalsIgnoreCase("G")){
                    mapObjects.add(
                            new NormalBlock(
                                    relativePosition,
                                    Block.GROUND_BLOCK
                            )
                    );
                } else if(letter.equalsIgnoreCase("W")){
                    mapObjects.add(
                            new NormalBlock(
                                    relativePosition,
                                    Block.WALL_BLOCK
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
                                    Block.GROUND_BLOCK
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
