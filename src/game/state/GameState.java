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


// child class of State

public class GameState extends State {

    private Player player;


    public GameState(Input input, Game game) {
        super(input, game);

        int ground = ScreenSize.getGround();

        char[][] cMap = FileLoader.loadMap();

        // player needs to be initialized as first, so it is at index 0 in gameObjects, and it's position will not change, even if other gameObjects are added ore removed
        initializePlayer(cMap, ground);

        createMap(cMap, ground);

    }

    private void initializePlayer(char[][] cMap, int ground) {

        // searching through map and adding player at defined position
        for (int i = 0; i < cMap.length; i++) {
            for (int j = 0; j < cMap[i].length-1; j++) {
                char letter = cMap[i][j];
                Position relativePosition = new Position((j-1)*64, ground-(i-1)*64);
                // !, because it definetly won't be used as block
                if(letter == '!'){
                    this.player = new Player(
                            new PlayerController(input),
                            new PlayerMaA(3.5),
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


    private void createMap(char[][] cMap, int ground) {
        // searching through map and adding blocks at defined positions
        for (int i = 0; i < cMap.length; i++) {
            for (int j = 0; j < cMap[i].length-1; j++) {
                char letter = cMap[i][j];
                Position relativePosition = new Position((j - 1) * 64, ground - (i - 1) * 64);
                GameObject object = null;
                try {
                    int npc = Integer.parseInt(String.valueOf(letter));
                        gameObjects.add(new NPC(
                                new NPCController(),
                                new NPCMaA(1.5),
                                relativePosition,
                                npc,
                                spriteLibrary,
                                5,
                                this
                        )
                    );
                } catch (Exception ignored) {
                    if (letter == 'C') {
                        try {
                            int type = Integer.parseInt(String.valueOf(cMap[i][j + 1]));
                            if (type == 0) {
                                object = new twoXtwo(
                                        relativePosition,
                                        type
                                );
                            } else if (type == 1) {
                                object = new twoXtwo(
                                        relativePosition,
                                        type
                                );
                            } else if (type == 2) {
                                object = new twoXtwo(
                                        relativePosition,
                                        type
                                );
                            }
                            j++;
                        } catch (Exception ignored2) {}
                    } else if (letter == 'G') {
                        object = new NormalBlock(
                                relativePosition,
                                Block.GROUND_BLOCK,
                                true
                        );
                    } else if (letter == 'W') {
                        object = new NormalBlock(
                                relativePosition,
                                Block.WALL_BLOCK,
                                true
                        );
                    } else if (letter == 'B') {
                        object = new NormalBlock(
                                relativePosition,
                                Block.BRICKS,
                                true
                        );
                    } else if (letter == 'b') {
                        object = new NormalBlock(
                                relativePosition,
                                Block.TRANS_BRICKS,
                                false
                        );
                    } else if (letter == 'P') {
                        object = new NormalBlock(
                                relativePosition,
                                Block.PLANKS,
                                true
                        );
                    } else if (letter == 'p') {
                        object = new NormalBlock(
                                relativePosition,
                                Block.TRANS_PLANKS,
                                false
                        );
                    } else if (letter == 'g') {
                        object = new NormalBlock(
                                relativePosition,
                                Block.TRANS_GROUND_BLOCK,
                                false
                        );
                    } else if (letter == 'w') {
                        object = new NormalBlock(
                                relativePosition,
                                Block.TRANS_WALL_BLOCK,
                                false
                        );
                    } else if (letter == '?') {
                        object = new CoinBlock(
                                relativePosition,
                                Block.ACTION_BLOCK
                        );
                    } else if (letter == 'F') {
                        object = new FinishBlock(
                                relativePosition,
                                Block.GROUND_BLOCK
                        );

                    }  else if (letter == 'l') {
                        object = new NormalBlock(
                                relativePosition,
                                Block.LEAVES,
                                false
                        );

                    }  else if (letter == 'T') {
                        object = new NormalBlock(
                                relativePosition,
                                Block.TREE,
                                true
                        );

                    } else if (letter == '*') {
                        object = new Coin(
                                relativePosition,
                                StaticEntity.COIN
                        );

                    }
                }


                if(object!=null) {
                    mapObjects.add(object);
                }

                // saving lowest block for playerMaA
                if(i == 0 && j == 0){
                    lowestBlock = object;
                }
            }
        }
    }

    public MovingEntity getPlayer() {
        return player;
    }
}
