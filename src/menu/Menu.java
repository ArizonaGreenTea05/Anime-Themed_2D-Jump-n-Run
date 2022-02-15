package menu;

import controller.PlayerController;
import core.ScreenSize;
import display.GameDisplay;
import game.Game;
import game.GameLoop;
import utils.StringEditor;
import utils.FileLoader;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Menu {
    // Constant GAME_VERSION contains the game-version defined in Launcher.java
    private final String GAME_VERSION;


    // defines whether Panel with controls-information is shown
    // is static -> stays open during color-update
    private static boolean controlsShown = false;




    private static final String GAME_THEME_PATH = "game/themes";
    public static final String[] gameThemes = FileLoader.loadFileNames(GAME_THEME_PATH);
    public static String[] playerName;
    public static String[] maps;
    private static final String[] playerSheetsInFolder = new String[] {"stand", "walk", "hit"};



// menu

    private final JFrame menu = new JFrame();
    private final int width, height;
    private final JLabel background = new JLabel();

    private static int name;
    private static int theme;
    private static int map;




// panel

    private final JPanel pControls = new JPanel();



// font

    public static int fontSize;
    public static final String textFont = "Comic Sans MS";



// buttons

    private JButton bStartGame;
    private JButton[] bThemes;
    private JButton[] bPlayers;
    private JButton[] bMaps;

    private JButton[] bBack = new JButton[3];
    private final int PLAYERS = 0;
    private final int THEMES = 1;
    private final int MAPS = 2;
    private static Rectangle backBounds;

    private final JButton bExit = new JButton("EXIT");
    private final JButton bChangeColor = new JButton("change colors");
    private final JButton bShowControls = new JButton("controls");
    private final JButton bResetHighscore = new JButton("reset highscores");
    private static Rectangle buttonBounds;



// labels

    public static int labelHeight;
    public static int labelWidth1;
    public static int labelWidth2;
    private static double highscore;
    private static final JLabel[] lInfo = new JLabel[10];
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



// colors

    public static int colorSetting = 0;

    private static final Color color1 = new Color(250, 200, 230);
    private static final Color color2 = new Color(4, 162, 236);
    private static final Color color3 = new Color(187, 120, 160);
    private static final Color color4 = new Color(255, 111, 197);

    private static final Color red =    new Color(255, 30, 0);
    private static final Color orange = new Color(255, 160, 0);
    private static final Color yellow = new Color(255, 240, 0);
    private static final Color green =  new Color(30, 220, 0);
    private static final Color blue =   new Color(0, 140, 255);
    private static final Color purple = new Color(110, 64, 255);

    private static final Color[] buttonColor =           { color1,      color1,      color3,      Color.BLACK, blue};
    private static final Color[] backButtonColor =       { color2,      color2,      color3,      Color.BLACK, orange};
    private static final Color[] bottomButtonColor =     { color1,      color1,      color3,      Color.BLACK, green};
    private static final Color[] labelColor =            { color2,      color2,      color3,      Color.BLACK, red};
    private static final Color[] textColor =             { Color.WHITE, Color.BLACK, Color.BLACK, color4,      Color.WHITE};
    private static final String[] bgImages =             {"bg_light",  "bg_light",  "bg_dark",   "bg_dark",   "bg_nyan"};
    private static Border border;




    public Menu(String version){
        this.colorSetting = Integer.parseInt(Objects.requireNonNull(FileLoader.load("color")));
        this.border = new LineBorder(textColor[colorSetting], 1, true);
        this.width = ScreenSize.getWidth();
        this.height = ScreenSize.getHeight();
        this.GAME_VERSION = version;

        initializeMenu();

        initializeButtons();

        initializeLabels();

        initializePanels();

        initializeTable();

        initializeBackground();

        addAll();

        setHighscore();

        menu.setVisible(true);
    }


// initializer methods

    private void initializeMenu() {
        menu.setSize(width,height);
        menu.setTitle("GameMenu  | " + GAME_VERSION + " |");
        menu.setIconImage(FileLoader.loadImage("sakura_icon", "/"));
        menu.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        menu.setUndecorated(true);
        menu.setResizable(false);
        menu.setLayout(null);
        menu.setLocationRelativeTo(null);
    }

    private void initializeLabels() {

        // Status-Anzeige
        labelHeight = menu.getHeight()/32;
        labelWidth1 = labelHeight*4;
        labelWidth2 = (int) ((double) labelHeight*6.3);
        fontSize = labelHeight-13;

        lInfo[THEME_TEXT] = new JLabel(" Theme:");
        lInfo[PLAYER_TEXT] = new JLabel(" Character:");
        lInfo[MAP_TEXT] = new JLabel(" Map:");
        lInfo[HIGHSCORE_TEXT] = new JLabel(" Highscore:");
        lInfo[SCORE_TEXT] = new JLabel(" Score:");

        int x1 = menu.getWidth()-labelWidth1-labelWidth2-22;
        int x2 = x1+labelWidth1-1;
        int line = 0;
        for (int i = 0; i < lInfo.length; i++) {
            int posY = 35 - line * 2 + line * labelHeight;
            if(i % 2 == 0) {
                lInfo[i].setBounds(x1, posY, labelWidth1, labelHeight);
            } else {
                lInfo[i] = new JLabel("");
                lInfo[i].setBounds(x2, posY, labelWidth2, labelHeight);
                line++;
            }
            lInfo[i].setOpaque(true);
            lInfo[i].setForeground(textColor[colorSetting]);
            lInfo[i].setBackground(labelColor[colorSetting]);
            lInfo[i].setBorder(border);
            lInfo[i].setFont(new Font(textFont, Font.PLAIN, fontSize));
        }

        lInfo[HIGHSCORE].setText(String.format(" %.1f", highscore));
        lInfo[SCORE].setText(String.format(" %.1f", GameDisplay.getScore()));
        // -Status-Anzeige-
    }

    private void addAll() {

        // bei Farbwechsel wird vorheriger Punkt beibehalten
        if(!lInfo[MAP].getText().equals("")) {
            menu.add(bStartGame);
            menu.add(bBack[MAPS]);
        } else if(!lInfo[PLAYER].getText().equals("")) {
            addMaps();
            menu.add(bBack[PLAYERS]);
        } else if(!lInfo[THEME].getText().equals("")) {
            addPlayers();
            menu.add(bBack[THEMES]);
        } else {
            addThemes();
        }



        menu.add(pControls);
        menu.add(bShowControls);
        menu.add(bResetHighscore);
        for (int i = 0; i < lInfo.length; i++) {
            menu.add(lInfo[i]);
        }
        /*
        lInfo[THEME].setVisible(true);
        lInfo[PLAYER].setVisible(true);
        lInfo[MAP].setVisible(true);
        lInfo[HIGHSCORE].setVisible(true);

         */
        menu.add(bChangeColor);
        menu.add(bExit);
    }

    private void initializeBackground() {
        background.setBounds(0,0,menu.getWidth(), menu.getHeight());
        Icon bgImage = FileLoader.loadIcon(bgImages[colorSetting], "/menu/", menu.getWidth(), menu.getHeight());
        background.setIcon(bgImage);
        menu.setContentPane(background);
    }

    private void initializeButtons() {

        // start Button
        bStartGame = new JButton("START GAME");
        bStartGame.setBounds(menu.getWidth()/3,menu.getHeight()/3,menu.getWidth()/3,menu.getHeight()/5);
        bStartGame.setBackground(buttonColor[colorSetting]);
        bStartGame.setForeground(textColor[colorSetting]);
        bStartGame.setFont(new Font(textFont, Font.PLAIN, bStartGame.getHeight()/4));
        bStartGame.addActionListener(getActionListenerStart());
        bStartGame.setBorder(border);
        // -start Button-


        // back Buttons
        int backHeight = menu.getHeight()/22;
        int backWidth = backHeight*4;
        backBounds = new Rectangle(20, 20, backWidth, backHeight);

        Font font = new Font(textFont, Font.PLAIN, backHeight/5*3);

        for (int i = 0; i < bBack.length; i++) {
            bBack[i] = new JButton("<<back");
            bBack[i].setBounds(backBounds);
            bBack[i].setFont(font);
            bBack[i].setBackground(backButtonColor[colorSetting]);
            bBack[i].setForeground(textColor[colorSetting]);
            bBack[i].setBorder(border);

        }
        bBack[THEMES].addActionListener(getActionListenerBackThemes());
        bBack[PLAYERS].addActionListener(getActionListenerBackPlayers());
        bBack[MAPS].addActionListener(getActionListenerBackMaps());
        // -back Buttons-


        // color change Button
        bChangeColor.setBounds(menu.getWidth()-20-backWidth, menu.getHeight()-backHeight-10, backWidth, backHeight);
        bChangeColor.setFont(new Font(textFont, Font.PLAIN, backHeight/2));
        bChangeColor.setBackground(bottomButtonColor[colorSetting]);
        bChangeColor.setForeground(textColor[colorSetting]);
        bChangeColor.setBorder(border);
        bChangeColor.addActionListener(getActionListenerColor());
        // - color change Button-


        // control showing Button
        bShowControls.setBounds(menu.getWidth()-20-backWidth, menu.getHeight()-2*backHeight-20, backWidth, backHeight);
        bShowControls.setFont(new Font(textFont, Font.PLAIN, backHeight/2));
        bShowControls.setBackground(bottomButtonColor[colorSetting]);
        bShowControls.setForeground(textColor[colorSetting]);
        bShowControls.setBorder(border);
        bShowControls.addActionListener(getActionListenerShowControls());
        // - control showing Button-


        // highscore reset Button
        bResetHighscore.setBounds(menu.getWidth()-30-2*backWidth, menu.getHeight()-backHeight-10, backWidth, backHeight);
        bResetHighscore.setFont(new Font(textFont, Font.PLAIN, backHeight/2));
        bResetHighscore.setBackground(bottomButtonColor[colorSetting]);
        bResetHighscore.setForeground(textColor[colorSetting]);
        bResetHighscore.setBorder(border);
        bResetHighscore.addActionListener(getActionListenerResetHighscores());
        // - highscore reset Button-


        // exit Button
        bExit.setBounds(20, menu.getHeight()-backHeight-10, backWidth, backHeight);
        bExit.setFont(font);
        bExit.setBackground(bottomButtonColor[colorSetting]);
        bExit.setForeground(textColor[colorSetting]);
        bExit.setBorder(border);
        bExit.addActionListener(getActionListenerExit());
        // - exit Button-
    }

    private void initializePanels() {
        int themeTextX = lInfo[THEME_TEXT].getX();
        int themeTextY = lInfo[THEME_TEXT].getY();
        Rectangle bounds = new Rectangle(themeTextX, themeTextY, width-themeTextX - 20, bShowControls.getY() - 2*themeTextY);

        pControls.setVisible(controlsShown);
        pControls.setBounds(bounds);
        pControls.setLayout(null);
        pControls.setBackground(bottomButtonColor[colorSetting]);
        pControls.setBorder(border);
    }

    private void initializeTable() {
        String[] columnNames = {"Action",
                "Key"};

        Object[][] data = PlayerController.getData();

        JTable table = new JTable(data, columnNames);

        table.getTableHeader().setBounds(0,0,pControls.getWidth(),(int) (1.5*labelHeight));

        int tableHeaderHeight = table.getTableHeader().getHeight();
        int rowCount = table.getRowCount();
        int rowheight = (
                pControls.getHeight()           //  Panel-Höhe
                        -tableHeaderHeight              //- Header-Höhe
        )                                   //= Resthöhe, die die Zeilen ausfüllen müssen
                /rowCount;                          //Resthöhe / Zeilenzahl = Höhe der Zeile
        table.setRowHeight(rowheight);

        table.setEnabled(false);
        table.setShowGrid(true);
        table.setBackground(bottomButtonColor[colorSetting]);
        table.setForeground(textColor[colorSetting]);
        table.setFont(new Font(textFont, Font.PLAIN, rowheight/4));
        table.getTableHeader().setBackground(bottomButtonColor[colorSetting]);
        table.getTableHeader().setForeground(textColor[colorSetting]);
        table.getTableHeader().setFont(new Font(textFont, Font.PLAIN, tableHeaderHeight/2));

        table.getTableHeader().setBorder(border);
        table.setGridColor(textColor[colorSetting]);
        pControls.setLayout(new BorderLayout());
        pControls.add(table.getTableHeader(), BorderLayout.PAGE_START);
        pControls.add(table, BorderLayout.CENTER);
    }



// adder methods

    private void addThemes(){
        // length initialized
        bThemes = new JButton[gameThemes.length];
        // height & width initialized
        int height = menu.getHeight()/12;
        int width = height*10;

        for (int i = 0; i < gameThemes.length; i++) {
            // button initialized & added
            bThemes[i] = new JButton(StringEditor.makeNameNice(gameThemes[i]));
            bThemes[i].setBounds(menu.getWidth()/4,menu.getHeight()/10+i*(menu.getHeight()/12 + 10),width,height);
            bThemes[i].setFont(new Font(textFont, Font.PLAIN, bThemes[i].getHeight()/3));
            bThemes[i].setBackground(buttonColor[colorSetting]);
            bThemes[i].setForeground(textColor[colorSetting]);
            bThemes[i].setBorder(border);
            bThemes[i].addActionListener(getActionListenerThemes(i));
            menu.add(bThemes[i]);
        }

        // menu updated
        menu.repaint();
    }

    private void addPlayers(){
        // length initialized
        bPlayers = new JButton[playerName.length];
        // height & width initialized
        int height = menu.getHeight()/12;
        int width = height*10;

        for (int i = 0; i < playerName.length; i++) {
            // button initialized & added
            bPlayers[i] = new JButton(StringEditor.makeNameNice(playerName[i]));
            bPlayers[i].setBounds(menu.getWidth()/4,menu.getHeight()/10+i*(menu.getHeight()/12 + 10),width,height);
            bPlayers[i].setFont(new Font(textFont, Font.PLAIN, bPlayers[i].getHeight()/3));
            bPlayers[i].setBackground(buttonColor[colorSetting]);
            bPlayers[i].setForeground(textColor[colorSetting]);
            bPlayers[i].setBorder(border);
            bPlayers[i].addActionListener(getActionListenerPlayers(i));
            menu.add(bPlayers[i]);
        }

        // back [to] themes button added
        menu.add(bBack[THEMES]);
        // button bounds saved (later needed in GameDisplay.java)
        buttonBounds = bPlayers[0].getBounds();
        // label set
        lInfo[THEME].setText(" " + StringEditor.makeNameNice(getGameTheme()));
        // menu updated
        menu.repaint();
    }

    private void addMaps(){
        // length initialized
        bMaps = new JButton[maps.length];
        // height & width initialized
        int width = menu.getWidth()/2;
        int height = menu.getHeight()/12;

        for (int i = 0; i < maps.length; i++) {
            // button initialized & added
            bMaps[i] = new JButton(StringEditor.makeNameNice(maps[i]));
            bMaps[i].setBounds(menu.getWidth()/4,menu.getHeight()/10+i*(menu.getHeight()/12 + 10),width,height);
            bMaps[i].setFont(new Font(textFont, Font.PLAIN, bMaps[i].getHeight()/3));
            bMaps[i].setBackground(buttonColor[colorSetting]);
            bMaps[i].setForeground(textColor[colorSetting]);
            bMaps[i].setBorder(border);
            bMaps[i].addActionListener(getActionListenerMaps(i));
            menu.add(bMaps[i]);
        }

        // back [to] players button added
        menu.add(bBack[PLAYERS]);
        // menu updated
        menu.repaint();
    }




// action listeners

    private ActionListener getActionListenerResetHighscores() {
        return e -> {
            resetHighscores();
        };
    }

    private ActionListener getActionListenerShowControls() {
        return e -> {
            // when controls are shown they will be hidden
            // when controls are hidden they will be shown
            controlsShown = !pControls.isVisible();
            pControls.setVisible(controlsShown);
        };
    }

    private ActionListener getActionListenerColor() {
        return e -> {
            // when button clicked the next color setting will be shown
            // if it's the last color setting in the array the first color setting will be shown
            if(colorSetting < buttonColor.length-1) {
                colorSetting++;
            } else {
                colorSetting = 0;
            }
            // color setting is saved in text-file
            FileLoader.save("" + colorSetting, "color");
            // menu updated by launching a new and disposing the old
            new Menu(GAME_VERSION);
            menu.dispose();
        };
    }

    private ActionListener getActionListenerExit() {
        // system will be exited
        return e -> System.exit(0);
    }

    private ActionListener getActionListenerStart() {
        return e -> {
            // in Thread, so anything can initialize without being counted as 'button action'
            new Thread(() ->{
                // start button can't be used anymore
                bStartGame.setEnabled(false);
                // game loop is created
                GameLoop gameLoop = new GameLoop(new Game(GAME_VERSION));
                // menu is disposed
                menu.dispose();
                // game loop has started
                new Thread(gameLoop).start();
            }).start();
        };
    }

    private ActionListener getActionListenerBackThemes(){
        return e-> {
            // player buttons removed
            remove(bPlayers);
            // theme text removed
            lInfo[THEME].setText("");
            // theme buttons added
            addThemes();
            // back [to] themes button removed
            menu.remove(bBack[THEMES]);
            // menu updated
            menu.repaint();
        };
    }

    private ActionListener getActionListenerBackPlayers() {
        return e -> {
            // map buttons removed
            remove(bMaps);
            // player text removed
            lInfo[PLAYER].setText("");
            // highscore for map reset
            setHighscore();
            // back [to] players button removed
            menu.remove(bBack[PLAYERS]);
            // menu updated
            menu.repaint();
            // players added
            addPlayers();
        };
    }

    private ActionListener getActionListenerBackMaps() {
        return e -> {
            // map text removed
            lInfo[MAP].setText("");
            // start button removed
            menu.remove(bStartGame);
            // back [to] maps button removed
            menu.remove(bBack[MAPS]);
            // menu updated
            menu.repaint();
            // map buttons added
            addMaps();
        };
    }

    private ActionListener getActionListenerThemes(int i){
        return e -> {
            // theme set
            theme = i;
            // player names set
            setPlayerNames();
            // maps set
            setMaps();
            // the´me buttons removed
            remove(bThemes);
            // player buttons added
            addPlayers();
            // back [to] themes button added
            menu.add(bBack[THEMES]);
            // menu updated
            menu.repaint();
        };
    }

    private ActionListener getActionListenerPlayers(int i) {
        return e -> {
            // player name set
            name = i;
            lInfo[PLAYER].setText(" " + StringEditor.makeNameNice(playerName[name]));
            // player buttons removed
            remove(bPlayers);
            // back [to] themes button removed
            menu.remove(bBack[THEMES]);
            // map buttons added
            addMaps();
            // back [to] players button added
            menu.add(bBack[PLAYERS]);
            // menu updated
            menu.repaint();
        };
    }

    private ActionListener getActionListenerMaps(int i) {
        return e -> {
            // map set
            map = i;
            lInfo[MAP].setText(" " + StringEditor.makeNameNice(maps[map]));
            // map buttons removed
            remove(bMaps);
            // back [to] players button removed
            menu.remove(bBack[PLAYERS]);
            // highscore of map set
            setHighscore();
            // back [to] maps button added
            menu.add(bBack[MAPS]);
            // start button added
            menu.add(bStartGame);
            // menu updated
            menu.repaint();
        };
    }




// setter methods

    private void resetHighscores(){
        double score = 0;
        this.highscore = score;

        for (String gameTheme : gameThemes) {
            String[] mapNames = FileLoader.loadFileNames(GAME_THEME_PATH + "/" + gameTheme + "/" + "maps", ".highscore" , FileLoader.ALL);
            for (String map : mapNames) {
                FileLoader.save(String.valueOf(score), map + ".highscore", GAME_THEME_PATH + "/" + gameTheme + "/maps/");
            }
        }
        setHighscore();
        lInfo[HIGHSCORE].setText(String.format(" %.1f", highscore));
    }

    private void setHighscore(){
        double highscore = 0;
        try {
            highscore = Double.parseDouble(Objects.requireNonNull(FileLoader.load(getMapName() + ".highscore", GAME_THEME_PATH + "/" + getGameTheme() + "/maps/")));
        } catch(Exception ignored){}
        double score = GameDisplay.getScore();

        if (highscore >= score) {
            this.highscore = highscore;
        } else {
            FileLoader.save(String.valueOf(score),getMapName() + ".highscore", GAME_THEME_PATH + "/" + getGameTheme() + "/maps/");
            this.highscore = score;
        }
        lInfo[HIGHSCORE].setText(String.format(" %.1f", highscore));
    }

    private static void setPlayerNames(){
        playerName = FileLoader.loadFileNames(GAME_THEME_PATH + "/" + getGameTheme() + "/" + "characters", "npc", FileLoader.ALL);
    }

    private static void setMaps(){
        maps = FileLoader.loadFileNames(GAME_THEME_PATH + "/" + getGameTheme() + "/" + "maps", ".highscore" , FileLoader.ALL);
    }



// getter methods

    public static JLabel[] getInfo(){
        return lInfo;
    }

    public static String getPlayerName(){
        return playerName[name];
    }

    public static String getMapName(){
        return maps[map];
    }

    public static Color getBGColor(){
        String gameTheme = getGameTheme();
        return StringEditor.stringToColor(FileLoader.load(gameTheme + ".color", GAME_THEME_PATH + "/" + gameTheme + "/"));
    }

    public static Color getButtonColor(){
        return buttonColor[colorSetting];
    }

    public static Color getTextColor(){
        return textColor[colorSetting];
    }

    public static Rectangle getBackBounds() {
        return backBounds;
    }

    public static Rectangle getButtonBounds(){
        return buttonBounds;
    }

    public static Border getBorder(){
        return border;
    }

    public static Icon getBGImage(){
        return FileLoader.loadIcon(bgImages[colorSetting], "/menu/", ScreenSize.getWidth(), ScreenSize.getHeight());
    }

    public static String getFont(){
        return textFont;
    }

    public static String getGameTheme(){
        return gameThemes[theme];
    }

    public static String[] getPlayerSheetsInFolder() {
        return playerSheetsInFolder;
    }



// else methods

    private void remove(Component[] components) {
        for (Component component : components) {
            menu.remove(component);
        }
    }
}