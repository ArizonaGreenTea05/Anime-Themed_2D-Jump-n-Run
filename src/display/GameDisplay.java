package display;

import utils.FileLoader;
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
    private JButton bBack;
    private JButton bInfo;
    private static double score = 0;
    private static final JLabel lThemeText = new JLabel(" Theme:");
    private static final JLabel lTheme = Menu.getThemeLabel();
    private static final JLabel lPlayerText = new JLabel(" Player:");
    private static final JLabel lPlayer = Menu.getPlayerLabel();
    private static final JLabel lMapText = new JLabel(" Map:");
    private static final JLabel lMapLabel = Menu.getMapLabel();
    private static final JLabel lHighScoreText = new JLabel(" Highscore:");
    private static final JLabel lHighScore = Menu.getHighscore();
    private static final JLabel lScoreLabelText = new JLabel(" Score:");
    private static JLabel lScore = Menu.getScore();
    private static JLabel lFps = new JLabel();

    private Rectangle scorePos;
    private Rectangle scoreTextPos;

    private static final JLabel lFailed = new JLabel("!you failed!");
    private final Color[] labelColor = Menu.labelColor;
    private final Color[] textColor = Menu.textColor;
    private final Color[] buttonColor = Menu.buttonColor;

    public GameDisplay(Input input, String title) {
        this.width = ScreenSize.getWidth();
        this.height = ScreenSize.getHeight();

        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setIconImage(FileLoader.loadImage("sakura_icon.png","/"));

        this.renderer = new Renderer();

        initializeLabels();

        initializeButtons();

        initializeCanvas();

        addAll();

        addKeyListener(input);

        pack();

        canvas.createBufferStrategy(4);

        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void addAll() {
        add(lFps);
        add(lTheme);
        add(lThemeText);
        lThemeText.setVisible(true);
        add(lPlayer);
        add(lPlayerText);
        lPlayerText.setVisible(true);
        add(lMapLabel);
        add(lMapText);
        lMapText.setVisible(true);
        add(lHighScore);
        add(lHighScoreText);
        lHighScoreText.setVisible(true);
        add(lScore);
        add(lScoreLabelText);
        add(bBack);
        add(bInfo);
        add(lFailed);
        add(canvas);
    }

    private void initializeCanvas() {
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
    }

    private void initializeButtons() {
        bBack = new JButton("<<back");
        bBack.setBounds(Menu.getBackBounds());
        bBack.addActionListener(getActionListenerBack());
        bBack.setBackground(labelColor[Menu.colorSetting]);
        bBack.setForeground(textColor[Menu.colorSetting]);
        bBack.setFocusable(false);

        bInfo = new JButton("hide info");
        bInfo.setBounds(ScreenSize.getWidth()-Menu.labelWidth1-Menu.labelWidth2-200, 5 , 120, Menu.labelHeight);
        bInfo.addActionListener(getActionListenerInfo());
        bInfo.setBackground(labelColor[Menu.colorSetting]);
        bInfo.setForeground(textColor[Menu.colorSetting]);
        bInfo.setFocusable(false);
    }

    private void initializeLabels() {

        int gap = 2;

        int labelHeight = Menu.labelHeight;
        int labelWidth1 = Menu.labelWidth2;
        int labelWidth2 = Menu.labelWidth2;

        lThemeText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22, 5 , labelWidth1, labelHeight);
        lThemeText.setOpaque(true);
        lThemeText.setBackground(labelColor[Menu.colorSetting]);
        lThemeText.setForeground(textColor[Menu.colorSetting]);
        lThemeText.setFont(new Font(Menu.textFont, Font.PLAIN, lTheme.getHeight()-4));

        lTheme.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 , labelWidth2, labelHeight);

        lPlayerText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + gap + labelHeight , labelWidth1, labelHeight);
        lPlayerText.setOpaque(true);
        lPlayerText.setBackground(labelColor[Menu.colorSetting]);
        lPlayerText.setForeground(textColor[Menu.colorSetting]);
        lPlayerText.setFont(new Font(Menu.textFont, Font.PLAIN, lTheme.getHeight()-4));

        lPlayer.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + gap +labelHeight , labelWidth2, labelHeight);

        lMapText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 2*gap + 2*labelHeight , labelWidth1, labelHeight);
        lMapText.setOpaque(true);
        lMapText.setBackground(labelColor[Menu.colorSetting]);
        lMapText.setForeground(textColor[Menu.colorSetting]);
        lMapText.setFont(new Font(Menu.textFont, Font.PLAIN, lTheme.getHeight()-4));

        lMapLabel.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + 2*gap +2*labelHeight , labelWidth2, labelHeight);

        lHighScoreText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 3*gap + 3*labelHeight , labelWidth1, labelHeight);
        lHighScoreText.setOpaque(true);
        lHighScoreText.setBackground(labelColor[Menu.colorSetting]);
        lHighScoreText.setForeground(textColor[Menu.colorSetting]);
        lHighScoreText.setFont(new Font(Menu.textFont, Font.PLAIN, lTheme.getHeight()-4));

        lHighScore.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + 3*gap + 3*labelHeight , labelWidth2, labelHeight);

        lScoreLabelText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 4*gap + 4*labelHeight , labelWidth1, labelHeight);
        lScoreLabelText.setOpaque(true);
        lScoreLabelText.setBackground(labelColor[Menu.colorSetting]);
        lScoreLabelText.setForeground(textColor[Menu.colorSetting]);
        lScoreLabelText.setFont(new Font(Menu.textFont, Font.PLAIN, lTheme.getHeight()-4));
        scoreTextPos = lScoreLabelText.getBounds();

        lScore.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + 4*gap + 4*labelHeight , labelWidth2, labelHeight);
        scorePos = lScore.getBounds();

        lFailed.setVisible(false);
        lFailed.setBounds(0,0,width, height);
        lFailed.setFont(new Font(Menu.textFont, Font.PLAIN, lFailed.getHeight()/4));
        lFailed.setForeground(Color.RED);
        lFailed.setHorizontalAlignment(SwingConstants.CENTER);

        lFps.setBounds(10, 38, lPlayerText.getHeight()*4, lPlayerText.getHeight());
        lFps.setFont(new Font("", Font.PLAIN, lFps.getHeight()/6*5));
        lFps.setOpaque(true);
        lFps.setBackground(labelColor[Menu.colorSetting]);
        lFps.setForeground(textColor[Menu.colorSetting]);
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
            if(lTheme.isVisible()) {
                lTheme.setVisible(false);
                lThemeText.setVisible(false);
                lPlayer.setVisible(false);
                lPlayerText.setVisible(false);
                lMapLabel.setVisible(false);
                lMapText.setVisible(false);
                lHighScore.setVisible(false);
                lHighScoreText.setVisible(false);
                lScore.setBounds(lTheme.getBounds());
                lScoreLabelText.setBounds(lThemeText.getBounds());
                bInfo.setText("show info");
            } else {
                lTheme.setVisible(true);
                lThemeText.setVisible(true);
                lPlayer.setVisible(true);
                lPlayerText.setVisible(true);
                lMapLabel.setVisible(true);
                lMapText.setVisible(true);
                lHighScore.setVisible(true);
                lHighScoreText.setVisible(true);
                lScore.setBounds(scorePos);
                lScoreLabelText.setBounds(scoreTextPos);
                bInfo.setText("hide info");
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
        lFailed.setVisible(true);
    }

    public static void setFPS(String frames){
        lFps.setText(frames);
    }

    public static void setScoreLabel(int i) {
        score = i;
        lScore.setText(" " + score);
    }

    public static double getScore(){
        return score;
    }
}
