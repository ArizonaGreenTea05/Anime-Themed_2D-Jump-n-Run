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
    private final JLabel themeText = new JLabel("Theme:");
    private final JLabel theme = Menu.getThemeLabel();
    private final JLabel playerText = new JLabel("Player:");
    private final JLabel player = Menu.getPlayerLabel();
    private final JLabel highScoreText = new JLabel("Highscore:");
    private final JLabel highScore = Menu.getHighscore();
    private final JLabel scoreLabelText = new JLabel("Score:");
    private static JLabel scoreLabel = Menu.getScore();
    private static JLabel fps = new JLabel();
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
        add(themeText);
        add(theme);
        add(playerText);
        add(player);
        add(highScoreText);
        add(highScore);
        add(scoreLabelText);
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

        themeText.setBounds(ScreenSize.getWidth() - theme.getWidth() - 5, 5 , theme.getWidth(), theme.getHeight());
        themeText.setBackground(Color.WHITE);
        themeText.setForeground(Menu.getBGColor());
        themeText.setFont(new Font(Menu.textFont, Font.PLAIN, theme.getHeight()-4));

        theme.setBounds(ScreenSize.getWidth() - themeText.getWidth()/2, 5 , themeText.getWidth(), themeText.getHeight());
        theme.setBackground(Color.WHITE);
        theme.setForeground(Menu.getBGColor());

        playerText.setBounds(ScreenSize.getWidth() - themeText.getWidth() - 5,5 + gap + themeText.getHeight() , themeText.getWidth(), themeText.getHeight());
        playerText.setBackground(Color.WHITE);
        playerText.setForeground(Menu.getBGColor());
        playerText.setFont(new Font(Menu.textFont, Font.PLAIN, theme.getHeight()-4));

        player.setBounds(ScreenSize.getWidth() - themeText.getWidth()/2, 5 + gap + themeText.getHeight() , themeText.getWidth(), themeText.getHeight());
        player.setBackground(Color.WHITE);
        player.setForeground(Menu.getBGColor());

        highScoreText.setBounds(ScreenSize.getWidth() - themeText.getWidth() - 5,5 + 2 * gap + 2 * themeText.getHeight() , themeText.getWidth(), themeText.getHeight());
        highScoreText.setBackground(Color.WHITE);
        highScoreText.setForeground(Menu.getBGColor());
        highScoreText.setFont(new Font(Menu.textFont, Font.PLAIN, theme.getHeight()-4));

        highScore.setBounds(ScreenSize.getWidth() - themeText.getWidth()/2, 5 + 2 * gap + 2 * themeText.getHeight() , themeText.getWidth(), themeText.getHeight());
        highScore.setBackground(Color.WHITE);
        highScore.setForeground(Menu.getBGColor());

        scoreLabelText.setBounds(ScreenSize.getWidth() - themeText.getWidth() - 5,5 + 3 * gap + 3 * themeText.getHeight() , themeText.getWidth(), themeText.getHeight());
        scoreLabelText.setBackground(Color.WHITE);
        scoreLabelText.setForeground(Menu.getBGColor());
        scoreLabelText.setText("Score:");
        scoreLabelText.setFont(new Font(Menu.textFont, Font.PLAIN, theme.getHeight()-4));

        scoreLabel.setBounds(ScreenSize.getWidth() - themeText.getWidth()/2, 5 + 3 * gap + 3 * themeText.getHeight() , themeText.getWidth(), themeText.getHeight());
        scoreLabel.setBackground(Color.WHITE);
        scoreLabel.setForeground(Menu.getBGColor());

        fps.setBounds(10, 38, playerText.getHeight()*4, playerText.getHeight());
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

        } catch(IllegalStateException ignored) {}
    }

    public static void setFPS(String frames){
        fps.setText(frames);
    }

    public static void setScoreLabel(int i) {
        score = i;
        scoreLabel.setText("" + score);
    }

    public static double getScore(){
        return score;
    }
}
