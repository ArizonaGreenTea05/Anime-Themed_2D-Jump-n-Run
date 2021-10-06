package game.state;

import controller.PlayerController;
import entity.Player;
import input.Input;

public class GameState extends State {


    public GameState(Input input) {
        super(input);

        Player player = new Player(new PlayerController(input), spriteLibrary);
        gameObjects.add(player);
    }
}
