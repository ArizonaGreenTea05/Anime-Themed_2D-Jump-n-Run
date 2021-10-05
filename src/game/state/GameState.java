package game.state;

import controller.PlayerController;
import entity.Player;
import input.Input;

public class GameState extends State {


    public GameState(Input input) {
        super(input);

        gameObjects.add(new Player(new PlayerController(input), spriteLibrary));
        //gameObjects.add(new Block());
    }
}
