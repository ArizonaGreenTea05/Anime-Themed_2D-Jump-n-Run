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

    private Canvas canvas;
    private final Renderer renderer;
    private int width, height;
    private JButton back;
    private static double score = 0;
    private final JLabel theme = Menu.getThemeLabel();
    private final JLabel player = Menu.getPlayerLabel();
    private static final JLabel highScore = Menu.getHighScore();
    private static final JLabel scoreLabel = Menu.getScore();
    private static final JLabel fps = new JLabel();
    private final JLabel background = new JLabel();

    public GameDisplay(Input input, String title) {
        this.width = ScreenSize.getWidth();
        this.height = ScreenSize.getHeight();

        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        this.renderer = new Renderer();

        initializeLabels();

        initializeBackButton();

        initializeCanvas();

        addAll();

        addKeyListener(input);
        pack();

        canvas.createBufferStrategy(3);

        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void addAll() {
        add(fps);
        add(theme);
        add(player);
        add(highScore);
        add(scoreLabel);
        add(back);
        add(canvas);
    }

    private void initializeCanvas() {
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
    }

    private void initializeBackButton() {
        back = new JButton("<<back");
        back.setBounds(10, 10, 76, 20);
        back.addActionListener(getActionListenerBack());
        back.setBackground(Menu.getBGColor());
        back.setForeground(Color.WHITE);
        back.setFocusable(false);
    }

    private void initializeLabels() {

        int gap = 2;

        theme.setBounds(ScreenSize.getWidth() - theme.getWidth() - 5, 5 , theme.getWidth(), theme.getHeight());
        theme.setBackground(Color.WHITE);
        theme.setForeground(Menu.getBGColor());

        player.setBounds(ScreenSize.getWidth() - player.getWidth() - 5,5 + gap + theme.getHeight() , player.getWidth(), player.getHeight());
        player.setBackground(Color.WHITE);
        player.setForeground(Menu.getBGColor());

        highScore.setBounds(ScreenSize.getWidth() - player.getWidth() - 5,5 + 2 * gap + 2 * theme.getHeight() , player.getWidth(), player.getHeight());
        highScore.setBackground(Color.WHITE);
        highScore.setForeground(Menu.getBGColor());

        scoreLabel.setBounds(ScreenSize.getWidth() - player.getWidth() - 5,5 + 3 * gap + 3 * theme.getHeight() , player.getWidth(), player.getHeight());
        scoreLabel.setBackground(Color.WHITE);
        scoreLabel.setForeground(Menu.getBGColor());
        scoreLabel.setText("Score:");

        fps.setBounds(10, 38, player.getHeight()*4, player.getHeight());
        fps.setFont(new Font("", Font.PLAIN, fps.getHeight()/6*5));
        fps.setBackground(Color.WHITE);
        fps.setForeground(Menu.getBGColor());
    }

    private ActionListener getActionListenerBack() {
        return e-> {
            GameLoop.setRunning(false);
            new Menu();
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

    public static void setScoreLabel(int i) {
        score = i;
        scoreLabel.setText("Score:          " + score);
    }

    public static double getScore(){
        return score;
    }
}
