package game.state;

import controller.PlayerController;
import core.ScreenSize;
import core.Size;
import entity.Grass;
import entity.Player;
import input.Input;

public class GameState extends State {


    public GameState(Input input) {
        super(input);

        Grass grass = new Grass(64, 64, "grass");
        Player player = new Player(new PlayerController(input), spriteLibrary);

        gameObjects.add(grass);
        gameObjects.add(player);

        gameMap = new GameMap((new Size(
                ScreenSize.getMapWidth(),
                ScreenSize.getMapHeight()
        )),spriteLibrary);
    }
}
