package display;

import game.Game;
import game.GameLoop;
import utils.*;
import core.ScreenSize;
import game.state.State;
import input.Input;
import menu.Menu;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;


public class GameDisplay extends JFrame {
    private final String GAME_VERSION;

    private Canvas canvas;
    private final Renderer renderer;
    private final Game game;
    private final int width,height;
    private final Border border = Menu.getBorder();



// buttons

    private JButton bInfo;
    private JButton bPause;
    private JButton bResume;
    private final JButton[] bExit = new JButton[3];
    private final JButton[] bRestart = new JButton[3];



// panels

    private final JPanel pFailedWindow = new JPanel();
    private final JPanel pPauseWindow = new JPanel();
    private final JPanel pWonWindow = new JPanel();
    private final int FAILED = 0;
    private final int PAUSE = 1;
    private final int WON = 2;



// labels


    private static final JLabel[] lHeadline = new JLabel[3];
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
    private static final JLabel lScore = Menu.getScore();
    private static final JLabel lLifesText = new JLabel(" Lifes:");
    private static final JLabel lLifes = new JLabel(" 5/5");
    private static final JLabel lFps = new JLabel();



// colors

    private final Color bgColor = Menu.getBGColor();
    private final Color buttonColor = Menu.getButtonColor();
    private final Color textColor = Menu.getTextColor();



// bounds of info-labels so their position can be swapped depending on whether info shall be shown or not
    private final Rectangle scorePos = new Rectangle();
    private final Rectangle scoreTextPos = new Rectangle();
    private final Rectangle lifesPos = new Rectangle();
    private final Rectangle lifesTextPos = new Rectangle();



    public GameDisplay(Input input, String title, String version, Game game) {
        this.game = game;
        this.width = ScreenSize.getWidth();
        this.height = ScreenSize.getHeight();
        this.GAME_VERSION = version;

        initializeMenu(title);

        this.renderer = new Renderer();

        initializePanels();

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


    public void render(State state){
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();

        try {

            // getting graphics
            Graphics graphics = bufferStrategy.getDrawGraphics();
            // setting background color
            graphics.setColor(Menu.getBGColor());
            graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            // rendering
            renderer.render(state, graphics);
            // showing rendered canvas
            bufferStrategy.show();

        } catch(IllegalStateException ignored) {}
    }



// initializer methods

    private void initializeMenu(String title) {
        setTitle(title + GAME_VERSION);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setIconImage(FileLoader.loadImage("sakura_icon","/"));
    }



    private void initializeCanvas() {
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
    }



    private void initializePanels() {
        Rectangle panelBounds = new Rectangle(1, 1, width-2, height-2);

        pPauseWindow.setVisible(false);
        pPauseWindow.setBounds(panelBounds);
        pPauseWindow.setLayout(null);
        pPauseWindow.setBackground(bgColor);

        pFailedWindow.setVisible(false);
        pFailedWindow.setBounds(panelBounds);
        pFailedWindow.setLayout(null);
        pFailedWindow.setBackground(bgColor);

        pWonWindow.setVisible(false);
        pWonWindow.setBounds(panelBounds);
        pWonWindow.setLayout(null);
        pWonWindow.setBackground(bgColor);
    }



    private void initializeButtons() {

        // button has same bounds as back buttons in menu

        String font = Menu.textFont;

        bPause = new JButton("PAUSE");
        bPause.setBounds(Menu.getBackBounds());
        bPause.setFont(new Font(font, Font.PLAIN, bPause.getHeight()/2));
        bPause.addActionListener(getActionListenerPause());
        bPause.setBackground(bgColor);
        bPause.setForeground(textColor);
        bPause.setBorder(border);
        bPause.setFocusable(false);


        // button has width of back buttons and height of labels

        bInfo = new JButton("show info");
        bInfo.setBounds(ScreenSize.getWidth()-Menu.labelWidth1-Menu.labelWidth2-100- bPause.getWidth(), 5 , bPause.getWidth(), Menu.labelHeight);
        bInfo.setFont(new Font(font, Font.PLAIN,bInfo.getHeight()/3*2));
        bInfo.addActionListener(getActionListenerInfo());
        bInfo.setBackground(bgColor);
        bInfo.setForeground(textColor);
        bInfo.setBorder(border);
        bInfo.setFocusable(false);



        // buttons have same bounds as theme, player and map buttons in Menu

        Rectangle bounds = Menu.getButtonBounds();

        for (int i = 0; i < bExit.length; i++) {
            bExit[i] = new JButton("EXIT");
            bExit[i].setBounds((pFailedWindow.getWidth() - bounds.width) / 2, pFailedWindow.getHeight() / 2, bounds.width, bounds.height);
            bExit[i].setFont(new Font(font, Font.PLAIN, bExit[i].getHeight()/3*2));
            bExit[i].setBackground(buttonColor);
            bExit[i].setForeground(textColor);
            bExit[i].setBorder(border);
            bExit[i].setFocusable(false);
            bExit[i].addActionListener(getActionListenerBack(i));
        }

        for (int i = 0; i < bRestart.length; i++) {
            bRestart[i] = new JButton("RESTART");
            bRestart[i].setBounds((pPauseWindow.getWidth()-bounds.width)/2, (int) (bExit[PAUSE].getY() - bExit[PAUSE].getHeight()*1.25), bounds.width, bounds.height);
            bRestart[i].setFont(new Font(font, Font.PLAIN, bRestart[i].getHeight()/3*2));
            bRestart[i].setBackground(buttonColor);
            bRestart[i].setForeground(textColor);
            bRestart[i].setBorder(border);
            bRestart[i].setFocusable(false);
            bRestart[i].addActionListener(getActionListenerRestart());
        }

        bResume = new JButton("RESUME");
        bResume.setBounds((pPauseWindow.getWidth()-bounds.width)/2, (int) (bExit[PAUSE].getY() - bExit[PAUSE].getHeight()*2.5), bounds.width, bounds.height);
        bResume.setFont(new Font(font, Font.PLAIN, bResume.getHeight()/3*2));
        bResume.setBackground(buttonColor);
        bResume.setForeground(textColor);
        bResume.setBorder(border);
        bResume.setFocusable(false);
        bResume.addActionListener(getActionListenerResume());
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
        lThemeText.setBorder(border);
        lThemeText.setFont(lTheme.getFont());

        lTheme.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 , labelWidth2, labelHeight);
        lTheme.setBorder(border);
        lTheme.setBackground(bgColor);

        lPlayerText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + gap + labelHeight , labelWidth1, labelHeight);
        lPlayerText.setOpaque(true);
        lPlayerText.setBackground(bgColor);
        lPlayerText.setForeground(textColor);
        lPlayerText.setBorder(border);
        lPlayerText.setFont(lTheme.getFont());

        lPlayer.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + gap +labelHeight , labelWidth2, labelHeight);
        lPlayer.setBorder(border);
        lPlayer.setBackground(bgColor);

        lMapText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 2*gap + 2*labelHeight , labelWidth1, labelHeight);
        lMapText.setOpaque(true);
        lMapText.setBackground(bgColor);
        lMapText.setForeground(textColor);
        lMapText.setBorder(border);
        lMapText.setFont(lTheme.getFont());

        lMap.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + 2*gap +2*labelHeight , labelWidth2, labelHeight);
        lMap.setBorder(border);
        lMap.setBackground(bgColor);

        lHighScoreText.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 3*gap + 3*labelHeight , labelWidth1, labelHeight);
        lHighScoreText.setOpaque(true);
        lHighScoreText.setBackground(bgColor);
        lHighScoreText.setForeground(textColor);
        lHighScoreText.setBorder(border);
        lHighScoreText.setFont(lTheme.getFont());

        lHighScore.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + 3*gap + 3*labelHeight , labelWidth2, labelHeight);
        lHighScore.setBorder(border);
        lHighScore.setBackground(bgColor);

        lScoreText.setBounds(lThemeText.getBounds());
        lScoreText.setOpaque(true);
        lScoreText.setBackground(bgColor);
        lScoreText.setForeground(textColor);
        lScoreText.setBorder(border);
        lScoreText.setFont(lTheme.getFont());
        scoreTextPos.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 4*gap + 4*labelHeight , labelWidth1, labelHeight);

        lScore.setBounds(lTheme.getBounds());
        lScore.setBorder(border);
        lScore.setBackground(bgColor);
        scorePos.setBounds(ScreenSize.getWidth()-labelWidth2-20, 5 + 4*gap + 4*labelHeight , labelWidth2, labelHeight);

        lLifesText.setBounds(lPlayerText.getBounds());
        lLifesText.setOpaque(true);
        lLifesText.setBackground(bgColor);
        lLifesText.setForeground(textColor);
        lLifesText.setBorder(border);
        lLifesText.setFont(lTheme.getFont());
        lifesTextPos.setBounds(ScreenSize.getWidth()-labelWidth1-labelWidth2-22,5 + 5*gap + 5*labelHeight , labelWidth1, labelHeight);

        lLifes.setBounds(lPlayer.getBounds());
        lLifes.setOpaque(true);
        lLifes.setBorder(border);
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

        lFps.setBounds(bPause.getX(), bPause.getY() + bPause.getHeight()-1, lPlayerText.getHeight()*4, lPlayerText.getHeight());
        lFps.setFont(new Font(Menu.textFont, Font.PLAIN, lFps.getHeight()/3*2));
        lFps.setOpaque(true);
        lFps.setBackground(bgColor);
        lFps.setForeground(textColor);
        lFps.setBorder(border);
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



// action listeners

    private ActionListener getActionListenerBack(int i) {
        if(i == FAILED || i == PAUSE) {
            return e -> {
                game.getGameLoop().resetScore();
                score = 0;
                game.stopGameLoop();
                new Menu(GAME_VERSION);
                dispose();
            };
        }
        return e -> {
            game.getGameLoop().resetScore();
            game.stopGameLoop();
            new Menu(GAME_VERSION);
            dispose();
        };
    }

    private ActionListener getActionListenerRestart() {
        return e-> {
            game.getGameLoop().resetScore();
            new Thread(new GameLoop(new Game(GAME_VERSION))).start();
            game.stopGameLoop();
            dispose();
        };
    }

    private ActionListener getActionListenerResume() {
        return e-> {
            game.stopGameLoop();
            new Thread(new GameLoop(game)).start();
            pPauseWindow.setVisible(false);
            bPause.setEnabled(true);
            bInfo.setEnabled(true);
        };
    }

    private ActionListener getActionListenerPause() {
        return e-> doPauseAction();
    }

    public void doPauseAction() {
        GameLoop.setRunning(false);
        bPause.setEnabled(false);
        bInfo.setEnabled(false);
        pPauseWindow.setVisible(true);
    }

    private ActionListener getActionListenerInfo() {
        return e-> doInfo();
    }

    public void doInfo() {
        if(lTheme.isVisible()) {
            hideInfo();
        } else {
            showInfo();
        }
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
        if(score > Double.parseDouble(ElseUtils.removeSpaces(lHighScore.getText()))){
            lHighScore.setText(" " + ElseUtils.shorten(String.valueOf(score),4));
        }
        pWonWindow.setVisible(true);
    }

    public void setFPS(int frames){
        lFps.setText("FPS: " + frames);
    }

    public static void setLifes(String lifes){
        lLifes.setText(" " + lifes);
    }

    public void setScoreLabel(double i) {
        score = i;
        lScore.setText(" " + ElseUtils.shorten(String.valueOf(score),4));
    }

    public static double getScore(){
        return score;
    }
}
