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

import java.util.List;

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
                ScreenSize.getGround()-64),
                spriteLibrary,
                5,
                this);
        this.player = player;

        NPC npc = new NPC(
                new NPCController(),
                new NPCMaA(2),
                new Position(ScreenSize.getLeftBorder()+64,
                ScreenSize.getGround()-64),
                spriteLibrary,
                5,
                this);

        gameObjects.addAll(List.of(player, npc));
    }


    private void createMap() {

        int ground = ScreenSize.getGround();
        int screenHeight = ScreenSize.getHeight();

        String[][] sMap = FileLoader.loadMap();

        /*
        //everything underneath the ground
        for (int posY = screenHeight; posY >= ground+64; posY-=64) {
            int posX = -64;
            for (int j = 0; j < sMap[0].length-1; j++) {

                if(sMap[0][j].equalsIgnoreCase("G")){
                    Grass grass = new Grass(posX, posY, "grass");
                    mapObjects.add(grass);
                }

                posX += 64;
            }
        }

         */


        Ground groundBlock = new Ground(sMap[0].length*64,screenHeight-ground, -64, ground+64, "ground");
        mapObjects.add(groundBlock);


        for (int i = 1; i < sMap.length; i++) {
            for (int j = 0; j < sMap[i].length; j++) {

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
                }

            }
        }

    }

    public MovingEntity getPlayer() {
        return player;
    }
}
