package display;

import core.ScreenSize;
import game.Game;
import input.Input;
import menu.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

public class GameDisplay extends JFrame {

    private final Canvas canvas;
    private final Renderer renderer;
    private final JButton back;
    private final JLabel theme = Menu.getThemeLabel();
    private final JLabel player = Menu.getPlayerLabel();

    public GameDisplay(int width, int height, Input input, String title) {
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        this.renderer = new Renderer();

        theme.setBounds(width-200, 5, 200, 15);
        theme.setFocusable(false);
        player.setBounds(width-200, 22, 200, 15);
        player.setFocusable(false);

        back = new JButton("<<back");
        back.setBounds(10, 10, 76, 20);
        back.addActionListener(getActionListenerBack());
        back.setFocusable(false);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);

        add(theme);
        add(player);
        add(back);
        add(canvas);
        addKeyListener(input);
        pack();

        canvas.createBufferStrategy(3);


        setLocationRelativeTo(null);
        setVisible(true);
    }

    private ActionListener getActionListenerBack() {
        return e-> {
            new Menu(ScreenSize.getWidth(), ScreenSize.getHeight());
            dispose();
        };
    }

    public void render(Game game){
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        // Exception handling: wenn der button "back" gedrückt und das Fenster geschlossen wird, gibt "bufferStrategy.getDrawGraphics()" eine IllegalStateException aus, das Programm macht jedoch was es soll.
        // -> an dieser Stelle nicht notwendig, dass diese Sequenz ausgeführt wird
        // -> try-catch, damit an der Stelle kein error ausgegeben wird
        try {
            Graphics graphics = bufferStrategy.getDrawGraphics();

            graphics.setColor(Menu.getBGColor());

            graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            renderer.render(game, graphics);

            graphics.dispose();
            bufferStrategy.show();

        } catch(IllegalStateException e) {}
    }
}
