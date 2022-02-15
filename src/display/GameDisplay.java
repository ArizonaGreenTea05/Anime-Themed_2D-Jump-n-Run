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

    private final JPanel[] pWindow = new JPanel[3];
    private final int FAILED = 0;
    private final int PAUSE = 1;
    private final int WON = 2;



// labels

    private static final JLabel lFps = new JLabel();
    private static final JLabel[] lHeadline = new JLabel[3];
    private static double score = 0;
    private static JLabel[] lInfo;
    private static final int THEME_TEXT = 0;
    private static final int THEME = 1;
    private static final int PLAYER_TEXT = 2;
    private static final int PLAYER = 3;
    private static final int MAP_TEXT = 4;
    private static final int MAP = 5;
    private static final int HIGHSCORE_TEXT = 6;
    private static final int HIGHSCORE = 7;
    private static final int SCORE_TEXT = 8;
    private static final int SCORE = 9;
    private static final int LIFES_TEXT = 10;
    private static final int LIFES = 11;




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

        initializeDisplay(title);

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

    private void initializeDisplay(String title) {
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

        for (int i = 0; i < pWindow.length; i++) {
            pWindow[i] = new JPanel();
            pWindow[i].setVisible(false);
            pWindow[i].setBounds(panelBounds);
            pWindow[i].setLayout(null);
            pWindow[i].setBackground(bgColor);
        }

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
            bExit[i].setBounds((pWindow[FAILED].getWidth() - bounds.width) / 2, pWindow[FAILED].getHeight() / 2, bounds.width, bounds.height);
            bExit[i].setFont(new Font(font, Font.PLAIN, bExit[i].getHeight()/3*2));
            bExit[i].setBackground(buttonColor);
            bExit[i].setForeground(textColor);
            bExit[i].setBorder(border);
            bExit[i].setFocusable(false);
            bExit[i].addActionListener(getActionListenerBack(i));
        }

        for (int i = 0; i < bRestart.length; i++) {
            bRestart[i] = new JButton("RESTART");
            bRestart[i].setBounds((pWindow[PAUSE].getWidth()-bounds.width)/2, (int) (bExit[PAUSE].getY() - bExit[PAUSE].getHeight()*1.25), bounds.width, bounds.height);
            bRestart[i].setFont(new Font(font, Font.PLAIN, bRestart[i].getHeight()/3*2));
            bRestart[i].setBackground(buttonColor);
            bRestart[i].setForeground(textColor);
            bRestart[i].setBorder(border);
            bRestart[i].setFocusable(false);
            bRestart[i].addActionListener(getActionListenerRestart());
        }

        bResume = new JButton("RESUME");
        bResume.setBounds((pWindow[PAUSE].getWidth()-bounds.width)/2, (int) (bExit[PAUSE].getY() - bExit[PAUSE].getHeight()*2.5), bounds.width, bounds.height);
        bResume.setFont(new Font(font, Font.PLAIN, bResume.getHeight()/3*2));
        bResume.setBackground(buttonColor);
        bResume.setForeground(textColor);
        bResume.setBorder(border);
        bResume.setFocusable(false);
        bResume.addActionListener(getActionListenerResume());
    }



    private void initializeLabels() {
        JLabel[] menuInfo = Menu.getInfo();
        JLabel[] lifesInfo = new JLabel[]{new JLabel(" Lifes:"), new JLabel(" 5/5")};
        lInfo = new JLabel[menuInfo.length + lifesInfo.length];

        System.arraycopy(menuInfo, 0, lInfo, 0, menuInfo.length);
        System.arraycopy(lifesInfo, 0, lInfo, menuInfo.length, lifesInfo.length);



        int gap = 2;

        int labelHeight = Menu.labelHeight;
        int labelWidth1 = Menu.labelWidth2;
        int labelWidth2 = Menu.labelWidth2;

        int line = 0;
        for (int i = 0; i < lInfo.length;i++) {
            int posY = 5 + line * gap + line * labelHeight;

            if(i % 2 == 0) {
                lInfo[i].setBounds(ScreenSize.getWidth() - labelWidth1 - labelWidth2 - 22, posY, labelWidth1, labelHeight);
            } else {
                lInfo[i].setBounds(ScreenSize.getWidth()-labelWidth2-20, posY, labelWidth2, labelHeight);
                line++;
            }
            lInfo[i].setOpaque(true);
            lInfo[i].setBackground(bgColor);
            lInfo[i].setForeground(textColor);
            lInfo[i].setBorder(border);
            lInfo[i].setFont(lInfo[THEME].getFont());
        }


        scoreTextPos.setBounds(lInfo[SCORE_TEXT].getBounds());
        lInfo[SCORE_TEXT].setBounds(lInfo[0].getBounds());

        scorePos.setBounds(lInfo[SCORE].getBounds());
        lInfo[SCORE].setBounds(lInfo[1].getBounds());

        lifesTextPos.setBounds(lInfo[LIFES_TEXT].getBounds());
        lInfo[LIFES_TEXT].setBounds(lInfo[2].getBounds());

        lifesPos.setBounds(lInfo[LIFES].getBounds());
        lInfo[LIFES].setBounds(lInfo[3].getBounds());




        for (int i = 0; i < lHeadline.length; i++) {
            lHeadline[i] = new JLabel();
            lHeadline[i].setBounds(0, 50, pWindow[FAILED].getWidth(), pWindow[FAILED].getHeight()/5);
            lHeadline[i].setFont(new Font("Consolas", Font.PLAIN, lHeadline[i].getHeight()/2));
            lHeadline[i].setForeground(textColor);
            lHeadline[i].setHorizontalAlignment(SwingConstants.CENTER);
        }
        lHeadline[FAILED].setText("YOU FAILED");
        lHeadline[PAUSE].setText("PAUSE");
        lHeadline[WON].setText("YOU WON");

        lFps.setBounds(bPause.getX(), bPause.getY() + bPause.getHeight()-1, lInfo[PLAYER_TEXT].getHeight()*4, lInfo[PLAYER_TEXT].getHeight());
        lFps.setFont(new Font(Menu.textFont, Font.PLAIN, lFps.getHeight()/3*2));
        lFps.setOpaque(true);
        lFps.setBackground(bgColor);
        lFps.setForeground(textColor);
        lFps.setBorder(border);
    }



    private void addAll() {
        pWindow[FAILED].add(lHeadline[FAILED]);
        pWindow[FAILED].add(bRestart[FAILED]);
        pWindow[FAILED].add(bExit[FAILED]);
        add(pWindow[FAILED]);
        pWindow[PAUSE].add(lHeadline[PAUSE]);
        pWindow[PAUSE].add(bRestart[PAUSE]);
        pWindow[PAUSE].add(bResume);
        pWindow[PAUSE].add(bExit[PAUSE]);
        add(pWindow[PAUSE]);
        pWindow[WON].add(lHeadline[WON]);
        pWindow[WON].add(bRestart[WON]);
        pWindow[WON].add(bExit[WON]);
        add(pWindow[WON]);
        add(lFps);
        add(lInfo[THEME]);
        lInfo[THEME].setVisible(false);
        add(lInfo[THEME_TEXT]);
        lInfo[THEME_TEXT].setVisible(false);
        add(lInfo[PLAYER]);
        lInfo[PLAYER].setVisible(false);
        add(lInfo[PLAYER_TEXT]);
        lInfo[PLAYER_TEXT].setVisible(false);
        add(lInfo[MAP]);
        lInfo[MAP].setVisible(false);
        add(lInfo[MAP_TEXT]);
        lInfo[MAP_TEXT].setVisible(false);
        add(lInfo[HIGHSCORE]);
        lInfo[HIGHSCORE].setVisible(false);
        add(lInfo[HIGHSCORE_TEXT]);
        lInfo[HIGHSCORE_TEXT].setVisible(false);
        add(lInfo[SCORE]);
        add(lInfo[SCORE_TEXT]);
        add(lInfo[LIFES]);
        add(lInfo[LIFES_TEXT]);
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
            pWindow[PAUSE].setVisible(false);
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
        pWindow[PAUSE].setVisible(true);
    }

    private ActionListener getActionListenerInfo() {
        return e-> doInfo();
    }

    public void doInfo() {
        if(lInfo[THEME].isVisible()) {
            hideInfo();
        } else {
            showInfo();
        }
    }

    private void hideInfo() {
        for (int i = 0; i < lInfo.length-4; i++) {
            lInfo[i].setVisible(false);
        }
        lInfo[SCORE].setBounds(lInfo[THEME].getBounds());
        lInfo[SCORE_TEXT].setBounds(lInfo[THEME_TEXT].getBounds());
        lInfo[LIFES].setBounds(lInfo[PLAYER].getBounds());
        lInfo[LIFES_TEXT].setBounds(lInfo[PLAYER_TEXT].getBounds());
        bInfo.setText("show info");
    }

    private void showInfo() {
        for (int i = 0; i < lInfo.length-4; i++) {
            lInfo[i].setVisible(true);
        }
        lInfo[SCORE].setBounds(scorePos);
        lInfo[SCORE_TEXT].setBounds(scoreTextPos);
        lInfo[LIFES].setBounds(lifesPos);
        lInfo[LIFES_TEXT].setBounds(lifesTextPos);
        bInfo.setText("hide info");
    }


    public void showFailed(){
        bPause.setEnabled(false);
        bInfo.setEnabled(false);
        GameLoop.setRunning(false);
        pWindow[FAILED].setVisible(true);
    }

    public void showWon(){
        bPause.setEnabled(false);
        bInfo.setEnabled(false);
        GameLoop.setRunning(false);
        if(!lInfo[THEME].isVisible()) {showInfo();}
        for (JLabel jLabel : lInfo) {
            pWindow[WON].add(jLabel);
        }
        if(score > Double.parseDouble(StringEditor.removeSpaces(lInfo[HIGHSCORE].getText()))){
            lInfo[HIGHSCORE].setText(String.format(" %.1f", score));
        }
        pWindow[WON].setVisible(true);
    }

    public void setFPS(int frames){
        lFps.setText("FPS: " + frames);
    }

    public static void setLifes(String lifes){
        lInfo[LIFES].setText(" " + lifes);
    }

    public void setScoreLabel(double i) {
        score = i;
        lInfo[SCORE].setText(String.format(" %.1f", score));
    }

    public static double getScore(){
        return score;
    }
}
