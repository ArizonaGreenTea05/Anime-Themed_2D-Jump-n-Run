package display;

import core.ScreenSize;
import game.GameLoop;
import game.state.State;
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
    private static JLabel fps = new JLabel();

    public GameDisplay(int width, int height, Input input, String title) {
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        this.renderer = new Renderer();

        theme.setBounds(ScreenSize.getWidth() - theme.getWidth() - 5, 5 , theme.getWidth(), theme.getHeight());
        theme.setBackground(Color.WHITE);
        theme.setForeground(Menu.getBGColor());
        player.setBounds(ScreenSize.getWidth() - player.getWidth() - 5,7 + theme.getHeight() , player.getWidth(), player.getHeight());
        player.setBackground(Color.WHITE);
        player.setForeground(Menu.getBGColor());

        fps.setBounds(10, 38, player.getHeight()*5, player.getHeight());
        fps.setFont(new Font("", Font.PLAIN, fps.getHeight()));
        fps.setBackground(Color.WHITE);
        fps.setForeground(Menu.getBGColor());

        back = new JButton("<<back");
        back.setBounds(10, 10, 76, 20);
        back.addActionListener(getActionListenerBack());
        back.setFocusable(false);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);

        add(fps);
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
            GameLoop.setRunning(false);
            dispose();
        };
    }

    public void render(State state){
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        // Exception handling: wenn der button "back" gedrückt und das Fenster geschlossen wird, gibt "bufferStrategy.getDrawGraphics()" eine IllegalStateException aus, das Programm macht jedoch was es soll.
        // -> an dieser Stelle nicht notwendig, dass diese Sequenz ausgeführt wird
        // -> try-catch, damit an der Stelle kein error ausgegeben wird
        try {

            Graphics graphics = bufferStrategy.getDrawGraphics();

            graphics.setColor(Menu.getBGColor());

            graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            renderer.render(state, graphics);

            graphics.dispose();
            bufferStrategy.show();

        } catch(IllegalStateException e) {}
    }

    public static void setFPS(String frames){
        fps.setText(frames);
    }
}
