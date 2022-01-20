package display;

import game.Game;
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
    private Game game;
    private int width, height;
    private JButton bBack;
    private JButton bInfo;
    private static double score = 0;
    private static final JLabel lThemeText = new JLabel(" Theme:");
    private static final JLabel lTheme = Menu.getThemeLabel();
    private static final JLabel lPlayerText = new JLabel(" Character:");
    private static final JLabel lPlayer = Menu.getPlayerLabel();
    private static final JLabel lMapText = new JLabel(" Map:");
    private static final JLabel lMapLabel = Menu.getMapLabel();
    private static final JLabel lHighScoreText = new JLabel(" Highscore:");
    private static final JLabel lHighScore = Menu.getHighscore();
    private static final JLabel lScoreLabelText = new JLabel(" Score:");
    private static JLabel lScore = Menu.getScore();
    private static JLabel lFps = new JLabel();

    private Color bgColor = Menu.getBGColor();

    private Rectangle scorePos = new Rectangle();
    private Rectangle scoreTextPos = new Rectangle();

    private static final JLabel lFailed = new JLabel("!you failed!");
    private final Color[] textColor = Menu.textColor;

    public GameDisplay(Input input, String title, Game game) {
        this.game = game;
        this.width = ScreenSize.getWidth();
        this.height = ScreenSize.getHeight();

        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setIconImage(FileLoader.loadImage("sakura_icon.png","/"));

        this.renderer = new Renderer();

        initializeButtons();

        initializeLabels();

        initializeCanvas();

        addAll();

        addKeyListener(input);

        pack();

        canvas.createBufferStrategy(3);

        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void addAll() {
        add(lFps);
        add(lTheme);
        lTheme.setVisible(false);
        add(lThemeText);
        lThemeText.setVisible(false);
        add(lPlayer);
        lPlayer.setVisible(false);
        add(lPlayerText);
        lPlayerText.setVisible(false);
        add(lMapLabel);
        lMapLabel.setVisible(false);
        add(lMapText);
        lMapText.setVisible(false);
        add(lHighScore);
        lHighScore.setVisible(false);
        add(lHighScoreText);
        lHighScoreText.setVisible(false);
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
        bBack.setFont(new Font(Menu.textFont, Font.PLAIN,bBack.getHeight()/2));
        bBack.addActionListener(getActionListenerBack());
        bBack.setBackground(bgColor);
        bBack.setForeground(textColor[Menu.colorSetting]);
        bBack.setFocusable(false);

        bInfo = new JButton("show info");
        bInfo.setBounds(ScreenSize.getWidth()-Menu.labelWidth1-Menu.labelWidth2-100-bBack.getWidth(), 5 , bBack.getWidth(), Menu.labelHeight);
        bInfo.setFont(new Font(Menu.textFont, Font.PLAIN,bInfo.getHeight()/3*2));
        bInfo.addActionListener(getActionListenerInfo());
        bInfo.setBackground(bgColor);
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
        lThemeText.setBackground(bgColor);
        lThemeText.setForeground(textColor[Menu.colorSetting]);
        lThemeText.setFont(lTheme.getFont());

        lTheme.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 , labelWidth2, labelHeight);
        lTheme.setBackground(bgColor);

        lPlayerText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + gap + labelHeight , labelWidth1, labelHeight);
        lPlayerText.setOpaque(true);
        lPlayerText.setBackground(bgColor);
        lPlayerText.setForeground(textColor[Menu.colorSetting]);
        lPlayerText.setFont(lTheme.getFont());

        lPlayer.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + gap +labelHeight , labelWidth2, labelHeight);
        lPlayer.setBackground(bgColor);

        lMapText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 2*gap + 2*labelHeight , labelWidth1, labelHeight);
        lMapText.setOpaque(true);
        lMapText.setBackground(bgColor);
        lMapText.setForeground(textColor[Menu.colorSetting]);
        lMapText.setFont(lTheme.getFont());

        lMapLabel.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + 2*gap +2*labelHeight , labelWidth2, labelHeight);
        lMapLabel.setBackground(bgColor);

        lHighScoreText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 3*gap + 3*labelHeight , labelWidth1, labelHeight);
        lHighScoreText.setOpaque(true);
        lHighScoreText.setBackground(bgColor);
        lHighScoreText.setForeground(textColor[Menu.colorSetting]);
        lHighScoreText.setFont(lTheme.getFont());

        lHighScore.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + 3*gap + 3*labelHeight , labelWidth2, labelHeight);
        lHighScore.setBackground(bgColor);

        lScoreLabelText.setBounds(lThemeText.getBounds());
        lScoreLabelText.setOpaque(true);
        lScoreLabelText.setBackground(bgColor);
        lScoreLabelText.setForeground(textColor[Menu.colorSetting]);
        lScoreLabelText.setFont(lTheme.getFont());
        scoreTextPos.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 4*gap + 4*labelHeight , labelWidth1, labelHeight);

        lScore.setBounds(lTheme.getBounds());
        lScore.setBackground(bgColor);
        scorePos.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + 4*gap + 4*labelHeight , labelWidth2, labelHeight);

        lFailed.setVisible(false);
        lFailed.setBounds(0,0,width, height);
        lFailed.setFont(new Font(Menu.textFont, Font.PLAIN, lFailed.getHeight()/4));
        lFailed.setForeground(Color.RED);
        lFailed.setHorizontalAlignment(SwingConstants.CENTER);

        lFps.setBounds(10, bBack.getHeight()+15, lPlayerText.getHeight()*4, lPlayerText.getHeight());
        lFps.setFont(new Font(Menu.textFont, Font.PLAIN, lFps.getHeight()/3*2));
        lFps.setOpaque(true);
        lFps.setBackground(bgColor);
        lFps.setForeground(textColor[Menu.colorSetting]);
    }

    private ActionListener getActionListenerBack() {
        return e-> {
            score = 0;
            game.hasFinished();
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
