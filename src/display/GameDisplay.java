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
    private JButton info;
    private static double score = 0;
    private static final JLabel themeText = new JLabel(" Theme:");
    private static final JLabel theme = Menu.getThemeLabel();
    private static final JLabel playerText = new JLabel(" Player:");
    private static final JLabel player = Menu.getPlayerLabel();
    private static final JLabel mapText = new JLabel(" Map:");
    private static final JLabel mapLabel = Menu.getMapLabel();
    private static final JLabel highScoreText = new JLabel(" Highscore:");
    private static final JLabel highScore = Menu.getHighscore();
    private static final JLabel scoreLabelText = new JLabel(" Score:");
    private static JLabel scoreLabel = Menu.getScore();
    private static JLabel fps = new JLabel();

    private Rectangle scorePos;
    private Rectangle scoreTextPos;

    private static final JLabel failed = new JLabel("!you failed!");
    private final Color[] labelColor = Menu.labelColor;
    private final Color[] textColor = Menu.textColor;
    private final Color[] buttonColor = Menu.buttonColor;

    public GameDisplay(Input input, String title) {
        this.width = ScreenSize.getWidth();
        this.height = ScreenSize.getHeight();

        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setIconImage(Menu.getImage("../sakura_icon.png"));

        this.renderer = new Renderer();

        initializeLabels();

        initializeButtons();

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
        add(themeText);
        add(player);
        add(playerText);
        add(mapLabel);
        add(mapText);
        add(highScore);
        add(highScoreText);
        add(scoreLabel);
        add(scoreLabelText);
        add(back);
        add(info);
        add(failed);
        add(canvas);
    }

    private void initializeCanvas() {
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
    }

    private void initializeButtons() {
        back = new JButton("<<back");
        back.setBounds(10, 10, 76, 20);
        back.addActionListener(getActionListenerBack());
        back.setBackground(labelColor[Menu.colorSetting]);
        back.setForeground(textColor[Menu.colorSetting]);
        back.setFocusable(false);

        info = new JButton("hide info");
        info.setBounds(ScreenSize.getWidth()-Menu.labelWidth1-Menu.labelWidth2-200, 5 , 120, Menu.labelHeight);
        info.addActionListener(getActionListenerInfo());
        info.setBackground(labelColor[Menu.colorSetting]);
        info.setForeground(textColor[Menu.colorSetting]);
        info.setFocusable(false);
    }

    private void initializeLabels() {

        int gap = 2;

        int labelHeight = Menu.labelHeight;
        int labelWidth1 = Menu.labelWidth2;
        int labelWidth2 = Menu.labelWidth2;

        themeText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22, 5 , labelWidth1, labelHeight);
        themeText.setOpaque(true);
        themeText.setBackground(labelColor[Menu.colorSetting]);
        themeText.setForeground(textColor[Menu.colorSetting]);
        themeText.setFont(new Font(Menu.textFont, Font.PLAIN, theme.getHeight()-4));

        theme.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 , labelWidth2, labelHeight);

        playerText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + gap + labelHeight , labelWidth1, labelHeight);
        playerText.setOpaque(true);
        playerText.setBackground(labelColor[Menu.colorSetting]);
        playerText.setForeground(textColor[Menu.colorSetting]);
        playerText.setFont(new Font(Menu.textFont, Font.PLAIN, theme.getHeight()-4));

        player.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + gap +labelHeight , labelWidth2, labelHeight);

        mapText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 2*gap + 2*labelHeight , labelWidth1, labelHeight);
        mapText.setOpaque(true);
        mapText.setBackground(labelColor[Menu.colorSetting]);
        mapText.setForeground(textColor[Menu.colorSetting]);
        mapText.setFont(new Font(Menu.textFont, Font.PLAIN, theme.getHeight()-4));

        mapLabel.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + 2*gap +2*labelHeight , labelWidth2, labelHeight);

        highScoreText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 3*gap + 3*labelHeight , labelWidth1, labelHeight);
        highScoreText.setOpaque(true);
        highScoreText.setBackground(labelColor[Menu.colorSetting]);
        highScoreText.setForeground(textColor[Menu.colorSetting]);
        highScoreText.setFont(new Font(Menu.textFont, Font.PLAIN, theme.getHeight()-4));

        highScore.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + 3*gap + 3*labelHeight , labelWidth2, labelHeight);

        scoreLabelText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 4*gap + 4*labelHeight , labelWidth1, labelHeight);
        scoreLabelText.setOpaque(true);
        scoreLabelText.setBackground(labelColor[Menu.colorSetting]);
        scoreLabelText.setForeground(textColor[Menu.colorSetting]);
        scoreLabelText.setFont(new Font(Menu.textFont, Font.PLAIN, theme.getHeight()-4));
        scoreTextPos = scoreLabelText.getBounds();

        scoreLabel.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + 4*gap + 4*labelHeight , labelWidth2, labelHeight);
        scorePos = scoreLabel.getBounds();

        failed.setVisible(false);
        failed.setBounds(0,0,width, height);
        failed.setFont(new Font(Menu.textFont, Font.PLAIN, failed.getHeight()/4));
        failed.setForeground(Color.RED);
        failed.setHorizontalAlignment(SwingConstants.CENTER);

        fps.setBounds(10, 38, playerText.getHeight()*4, playerText.getHeight());
        fps.setFont(new Font("", Font.PLAIN, fps.getHeight()/6*5));
        fps.setOpaque(true);
        fps.setBackground(labelColor[Menu.colorSetting]);
        fps.setForeground(textColor[Menu.colorSetting]);
    }

    private ActionListener getActionListenerBack() {
        return e-> {
            GameLoop.setRunning(false);
            score = 0;
            new Menu();
            dispose();
        };
    }

    private ActionListener getActionListenerInfo() {
        return e-> {
            if(theme.isVisible()) {
                theme.setVisible(false);
                themeText.setVisible(false);
                player.setVisible(false);
                playerText.setVisible(false);
                mapLabel.setVisible(false);
                mapText.setVisible(false);
                highScore.setVisible(false);
                highScoreText.setVisible(false);
                scoreLabel.setBounds(theme.getBounds());
                scoreLabelText.setBounds(themeText.getBounds());
                info.setText("show info");
            } else {
                theme.setVisible(true);
                themeText.setVisible(true);
                player.setVisible(true);
                playerText.setVisible(true);
                mapLabel.setVisible(true);
                mapText.setVisible(true);
                highScore.setVisible(true);
                highScoreText.setVisible(true);
                scoreLabel.setBounds(scorePos);
                scoreLabelText.setBounds(scoreTextPos);
                info.setText("hide info");
            }
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

    public static void showFailed(){
        failed.setVisible(true);
    }

    public static void setFPS(String frames){
        fps.setText(frames);
    }

    public static void setScoreLabel(int i) {
        score = i;
        scoreLabel.setText(" " + score);
    }

    public static double getScore(){
        return score;
    }
}
