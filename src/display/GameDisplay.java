package display;

import game.Game;
import game.GameLoop;
import utils.FileLoader;
import core.ScreenSize;
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
    private final Game game;
    private final int width,height;
    private JButton bPause;
    private JButton bResume;
    private final JButton[] bExit = new JButton[3];
    private final JButton[] bRestart = new JButton[3];
    private static final JLabel[] lHeadline = new JLabel[3];
    private final int FAILED = 0;
    private final int PAUSE = 1;
    private final int WON = 2;
    private JButton bInfo;
    private final JPanel pFailedWindow = new JPanel();
    private final JPanel pPauseWindow = new JPanel();
    private final JPanel pWonWindow = new JPanel();



    private static double score = 0;
    private static final JLabel lThemeText = new JLabel(" Theme:");
    private static final JLabel lTheme = Menu.getThemeLabel();
    private static final JLabel lPlayerText = new JLabel(" Character:");
    private static final JLabel lPlayer = Menu.getPlayerLabel();
    private static final JLabel lMapText = new JLabel(" Map:");
    private static final JLabel lMap = Menu.getMapLabel();
    private static final JLabel lHighScoreText = new JLabel(" Highscore:");
    private static final JLabel lHighScore = Menu.getHighscore();
    private static final JLabel lScoreText = new JLabel(" Score:");
    private static JLabel lScore = Menu.getScore();
    private static final JLabel lLifesText = new JLabel(" Lifes:");
    private static JLabel lLifes = new JLabel(" 5/5");
    private static JLabel lFps = new JLabel();

    private final Color bgColor = Menu.getBGColor();
    private final Color buttonColor = Menu.getButtonColor();

    private Rectangle scorePos = new Rectangle();
    private Rectangle scoreTextPos = new Rectangle();
    private Rectangle lifesPos = new Rectangle();
    private Rectangle lifesTextPos = new Rectangle();

    private final Color textColor = Menu.getTextColor();

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

        initializePanels();

        initializeButtons();

        initializeLabels();

        initializeCanvas();

        addAll();

        addKeyListener(input);

        pack();

        canvas.createBufferStrategy(2);

        setLocationRelativeTo(null);
        setVisible(true);
    }


    private void addAll() {
        pFailedWindow.add(lHeadline[FAILED]);
        pFailedWindow.add(bRestart[FAILED]);
        pFailedWindow.add(bExit[FAILED]);
        add(pFailedWindow);
        pPauseWindow.add(lHeadline[PAUSE]);
        pPauseWindow.add(bRestart[PAUSE]);
        pPauseWindow.add(bResume);
        pPauseWindow.add(bExit[PAUSE]);
        add(pPauseWindow);
        pWonWindow.add(lHeadline[WON]);
        pWonWindow.add(bRestart[WON]);
        pWonWindow.add(bExit[WON]);
        add(pWonWindow);
        add(lFps);
        add(lTheme);
        lTheme.setVisible(false);
        add(lThemeText);
        lThemeText.setVisible(false);
        add(lPlayer);
        lPlayer.setVisible(false);
        add(lPlayerText);
        lPlayerText.setVisible(false);
        add(lMap);
        lMap.setVisible(false);
        add(lMapText);
        lMapText.setVisible(false);
        add(lHighScore);
        lHighScore.setVisible(false);
        add(lHighScoreText);
        lHighScoreText.setVisible(false);
        add(lScore);
        add(lScoreText);
        add(lLifes);
        add(lLifesText);
        add(bPause);
        add(bInfo);
        add(canvas);
    }
    private void initializeCanvas() {
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
    }

    private void initializePanels() {
        pPauseWindow.setVisible(false);
        pPauseWindow.setBounds(1, 1, width-2, height-2);
        pPauseWindow.setLayout(null);
        pPauseWindow.setBackground(bgColor);

        pFailedWindow.setVisible(false);
        pFailedWindow.setBounds(1, 1, width-2, height-2);
        pFailedWindow.setLayout(null);
        pFailedWindow.setBackground(bgColor);

        pWonWindow.setVisible(false);
        pWonWindow.setBounds(1, 1, width-2, height-2);
        pWonWindow.setLayout(null);
        pWonWindow.setBackground(bgColor);
    }

    private void initializeButtons() {
        bPause = new JButton("PAUSE");
        bPause.setBounds(Menu.getBackBounds());
        bPause.setFont(new Font(Menu.textFont, Font.PLAIN, bPause.getHeight()/2));
        bPause.addActionListener(getActionListenerPause());
        bPause.setBackground(bgColor);
        bPause.setForeground(textColor);
        bPause.setFocusable(false);


        Rectangle bounds = Menu.getButtonBounds();

        for (int i = 0; i < bExit.length; i++) {
            bExit[i] = new JButton("EXIT");
            bExit[i].setBounds((pFailedWindow.getWidth() - bounds.width) / 2, pFailedWindow.getHeight() / 2, bounds.width, bounds.height);
            bExit[i].setFont(new Font("Consolas", Font.PLAIN, bExit[i].getHeight()/3*2));
            bExit[i].setBackground(buttonColor);
            bExit[i].setForeground(textColor);
            bExit[i].setFocusable(false);
            bExit[i].addActionListener(getActionListenerBack(i));
        }

        for (int i = 0; i < bRestart.length; i++) {
            bRestart[i] = new JButton("RESTART");
            bRestart[i].setBounds((pPauseWindow.getWidth()-bounds.width)/2, (int) (bExit[PAUSE].getY() - bExit[PAUSE].getHeight()*1.25), bounds.width, bounds.height);
            bRestart[i].setFont(new Font("Consolas", Font.PLAIN, bRestart[i].getHeight()/3*2));
            bRestart[i].setBackground(buttonColor);
            bRestart[i].setForeground(textColor);
            bRestart[i].setFocusable(false);
            bRestart[i].addActionListener(getActionListenerRestart());
        }

        bResume = new JButton("RESUME");
        bResume.setBounds((pPauseWindow.getWidth()-bounds.width)/2, (int) (bExit[PAUSE].getY() - bExit[PAUSE].getHeight()*2.5), bounds.width, bounds.height);
        bResume.setFont(new Font("Consolas", Font.PLAIN, bResume.getHeight()/3*2));
        bResume.setBackground(buttonColor);
        bResume.setForeground(textColor);
        bResume.setFocusable(false);
        bResume.addActionListener(getActionListenerResume());

        bInfo = new JButton("show info");
        bInfo.setBounds(ScreenSize.getWidth()-Menu.labelWidth1-Menu.labelWidth2-100- bPause.getWidth(), 5 , bPause.getWidth(), Menu.labelHeight);
        bInfo.setFont(new Font(Menu.textFont, Font.PLAIN,bInfo.getHeight()/3*2));
        bInfo.addActionListener(getActionListenerInfo());
        bInfo.setBackground(bgColor);
        bInfo.setForeground(textColor);
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
        lThemeText.setForeground(textColor);
        lThemeText.setFont(lTheme.getFont());

        lTheme.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 , labelWidth2, labelHeight);
        lTheme.setBackground(bgColor);

        lPlayerText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + gap + labelHeight , labelWidth1, labelHeight);
        lPlayerText.setOpaque(true);
        lPlayerText.setBackground(bgColor);
        lPlayerText.setForeground(textColor);
        lPlayerText.setFont(lTheme.getFont());

        lPlayer.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + gap +labelHeight , labelWidth2, labelHeight);
        lPlayer.setBackground(bgColor);

        lMapText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 2*gap + 2*labelHeight , labelWidth1, labelHeight);
        lMapText.setOpaque(true);
        lMapText.setBackground(bgColor);
        lMapText.setForeground(textColor);
        lMapText.setFont(lTheme.getFont());

        lMap.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + 2*gap +2*labelHeight , labelWidth2, labelHeight);
        lMap.setBackground(bgColor);

        lHighScoreText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 3*gap + 3*labelHeight , labelWidth1, labelHeight);
        lHighScoreText.setOpaque(true);
        lHighScoreText.setBackground(bgColor);
        lHighScoreText.setForeground(textColor);
        lHighScoreText.setFont(lTheme.getFont());

        lHighScore.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + 3*gap + 3*labelHeight , labelWidth2, labelHeight);
        lHighScore.setBackground(bgColor);

        lScoreText.setBounds(lThemeText.getBounds());
        lScoreText.setOpaque(true);
        lScoreText.setBackground(bgColor);
        lScoreText.setForeground(textColor);
        lScoreText.setFont(lTheme.getFont());
        scoreTextPos.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 4*gap + 4*labelHeight , labelWidth1, labelHeight);

        lScore.setBounds(lTheme.getBounds());
        lScore.setBackground(bgColor);
        scorePos.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + 4*gap + 4*labelHeight , labelWidth2, labelHeight);

        lLifesText.setBounds(lPlayerText.getBounds());
        lLifesText.setOpaque(true);
        lLifesText.setBackground(bgColor);
        lLifesText.setForeground(textColor);
        lLifesText.setFont(lTheme.getFont());
        lifesTextPos.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 5*gap + 5*labelHeight , labelWidth1, labelHeight);

        lLifes.setBounds(lPlayer.getBounds());
        lLifes.setOpaque(true);
        lLifes.setBackground(bgColor);
        lLifes.setForeground(textColor);
        lLifes.setFont(lScore.getFont());
        lifesPos.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + 5*gap + 5*labelHeight , labelWidth2, labelHeight);

        for (int i = 0; i < lHeadline.length; i++) {
            lHeadline[i] = new JLabel();
            lHeadline[i].setBounds(0, 50, pFailedWindow.getWidth(), pFailedWindow.getHeight()/5);
            lHeadline[i].setFont(new Font("Consolas", Font.PLAIN, lHeadline[i].getHeight()/2));
            lHeadline[i].setForeground(textColor);
            lHeadline[i].setHorizontalAlignment(SwingConstants.CENTER);
        }
        lHeadline[FAILED].setText("YOU FAILED");
        lHeadline[PAUSE].setText("PAUSE");
        lHeadline[WON].setText("YOU WON");

        lFps.setBounds(10, bPause.getHeight()+15, lPlayerText.getHeight()*4, lPlayerText.getHeight());
        lFps.setFont(new Font(Menu.textFont, Font.PLAIN, lFps.getHeight()/3*2));
        lFps.setOpaque(true);
        lFps.setBackground(bgColor);
        lFps.setForeground(textColor);
    }

    private ActionListener getActionListenerBack(int i) {
        if(i == FAILED || i == PAUSE) {
            return e -> {
                score = 0;
                game.stopGameLoop();
                new Menu();
                dispose();
            };
        }
        return e -> {
            game.stopGameLoop();
            new Menu();
            dispose();
        };
    }

    private ActionListener getActionListenerRestart() {
        return e-> {
            new Thread(new GameLoop(new Game())).start();
            game.stopGameLoop();
            dispose();
        };
    }

    private ActionListener getActionListenerResume() {
        return e-> {
            score = 0;
            game.stopGameLoop();
            new Thread(new GameLoop(game)).start();
            pPauseWindow.setVisible(false);
            bPause.setEnabled(true);
            bInfo.setEnabled(true);
        };
    }

    private ActionListener getActionListenerPause() {
        return e-> {
            doPauseAction();
        };
    }

    public void doPauseAction() {
        GameLoop.setRunning(false);
        bPause.setEnabled(false);
        bInfo.setEnabled(false);
        pPauseWindow.setVisible(true);
    }

    private ActionListener getActionListenerInfo() {
        return e-> {
            if(lTheme.isVisible()) {
                hideInfo();
            } else {
                showInfo();
            }
        };
    }

    private void hideInfo() {
        lTheme.setVisible(false);
        lThemeText.setVisible(false);
        lPlayer.setVisible(false);
        lPlayerText.setVisible(false);
        lMap.setVisible(false);
        lMapText.setVisible(false);
        lHighScore.setVisible(false);
        lHighScoreText.setVisible(false);
        lScore.setBounds(lTheme.getBounds());
        lScoreText.setBounds(lThemeText.getBounds());
        lLifes.setBounds(lPlayer.getBounds());
        lLifesText.setBounds(lPlayerText.getBounds());
        bInfo.setText("show info");
    }

    private void showInfo() {
        lTheme.setVisible(true);
        lThemeText.setVisible(true);
        lPlayer.setVisible(true);
        lPlayerText.setVisible(true);
        lMap.setVisible(true);
        lMapText.setVisible(true);
        lHighScore.setVisible(true);
        lHighScoreText.setVisible(true);
        lScore.setBounds(scorePos);
        lScoreText.setBounds(scoreTextPos);
        lLifes.setBounds(lifesPos);
        lLifesText.setBounds(lifesTextPos);
        bInfo.setText("hide info");
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

    public void showFailed(){
        bPause.setEnabled(false);
        bInfo.setEnabled(false);
        GameLoop.setRunning(false);
        pFailedWindow.setVisible(true);
    }

    public void showWon(){
        bPause.setEnabled(false);
        bInfo.setEnabled(false);
        GameLoop.setRunning(false);
        if(!lTheme.isVisible()) {showInfo();}
        pWonWindow.add(lTheme);
        pWonWindow.add(lThemeText);
        pWonWindow.add(lPlayer);
        pWonWindow.add(lPlayerText);
        pWonWindow.add(lMap);
        pWonWindow.add(lMapText);
        pWonWindow.add(lHighScore);
        pWonWindow.add(lHighScoreText);
        pWonWindow.add(lScore);
        pWonWindow.add(lScoreText);
        pWonWindow.add(lLifes);
        pWonWindow.add(lLifesText);
        pWonWindow.setVisible(true);
    }

    public void setFPS(String frames){
        lFps.setText(frames);
    }

    public static void setLifes(String lifes){
        lLifes.setText(" " + lifes);
    }

    public void setScoreLabel(int i) {
        score = i;
        lScore.setText(" " + score);
    }

    public static double getScore(){
        return score;
    }
}
