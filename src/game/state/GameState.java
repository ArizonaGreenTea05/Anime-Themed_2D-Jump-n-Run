package game.state;

import controller.NPCController;
import controller.PlayerController;
import core.Position;
import core.ScreenSize;
import entity.*;
import entity.motionAndAbilities.NPCMaA;
import entity.motionAndAbilities.PlayerMaA;
import game.Game;
import input.Input;
import utils.FileLoader;

public class GameState extends State {

    private Player player;


    public GameState(Input input, Game game) {
        super(input, game);

        createMap();

        initializeCharacters();
    }

    private void initializeCharacters() {

        Player player = new Player(
                new PlayerController(input),
                new PlayerMaA(2),
                new Position(ScreenSize.getLeftBorder(),
                0),
                spriteLibrary,
                5,
                this
        );
        this.player = player;

        gameObjects.add(player);
    }


    private void createMap() {

        int ground = ScreenSize.getGround();

        String[][] sMap = FileLoader.loadMap();


        for (int i = 0; i < sMap.length; i++) {
            for (int j = 0; j < sMap[i].length-1; j++) {

                if(sMap[i][j].equalsIgnoreCase("G")){
                    mapObjects.add(
                            new Grass(
                                    new Position(
                                            (j-1)*64,
                                            ground-(i-1)*64
                                    ),
                                    Block.GRASS_BLOCK
                            )
                    );
                } else if(sMap[i][j].equalsIgnoreCase("A")){
                    mapObjects.add(
                            new ActionBlock(
                                    new Position(
                                            (j-1)*64,
                                            ground-(i-1)*64
                                    ),
                                    Block.ACTION_BLOCK
                            )
                    );
                } else if(sMap[i][j].equalsIgnoreCase("F")){
                    mapObjects.add(
                            new FinishBlock(
                                    new Position(
                                            (j-1)*64,
                                            ground-(i-1)*64
                                    ),
                                    Block.ACTION_BLOCK
                            )
                    );
                } else if(sMap[i][j].equalsIgnoreCase("E")){
                    gameObjects.add(
                            new NPC(
                                    new NPCController(),
                                    new NPCMaA(2),
                                    new Position(
                                            ScreenSize.getLeftBorder()+64,
                                            0
                                    ),
                                    spriteLibrary,
                                    5,
                                    this
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
