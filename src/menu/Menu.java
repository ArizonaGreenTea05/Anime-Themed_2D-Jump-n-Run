package menu;

import controller.PlayerController;
import core.ScreenSize;
import design.Theme;
import display.GameDisplay;
import game.Game;
import game.GameLoop;
import gameObjects.GameObject;
import utils.StringEditor;
import utils.FileLoader;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Menu {

    private final String GAME_VERSION;
    private static final String GAME_THEME_PATH = "game/themes";
    private static final String[] gameThemes = FileLoader.loadFileNames(GAME_THEME_PATH);
    private static final String[] playerSheetsInFolder = new String[] {"stand", "walk", "hit"};
    private static String[] playerName;
    private static String[] maps;

// menu
    private final JFrame menu = new JFrame();
    private final int width, height;
    private final JLabel background = new JLabel();
    private static int name;
    private static int theme;
    private static int map;

// controls
    private final JPanel pControls = new JPanel();
    private boolean controlsShown = false;

    public static final String textFont = "Comic Sans MS";
    public static ThemeNames colorSetting;
    private enum ColorNames
    {
        DarkPink,
        Pink,
        LightPink,
        Red,
        Orange,
        Yellow,
        Green,
        LightBlue,
        Blue,
        Purple
    }

    private enum ThemeNames
    {
        Light,
        LightContrast,
        Dark,
        DarkContrast,
        Nyan
    }

    private static final Map<ColorNames, Color> colors = Map.of(
            ColorNames.DarkPink, new Color(187, 120, 160),
            ColorNames.Pink, new Color(255, 111, 197),
            ColorNames.LightPink, new Color(250, 200, 230),
            ColorNames.Red, new Color(255, 30, 0),
            ColorNames.Orange, new Color(255, 160, 0),
            ColorNames.Yellow, new Color(255, 240, 0),
            ColorNames.Green, new Color(30, 220, 0),
            ColorNames.LightBlue, new Color(4, 162, 236),
            ColorNames.Blue, new Color(0, 140, 255),
            ColorNames.Purple, new Color(110, 64, 255)
    );

    private static final Map<ThemeNames, Theme> themes = Map.of(
            ThemeNames.Light, new Theme(colors.get(ColorNames.LightPink), colors.get(ColorNames.LightBlue), colors.get(ColorNames.LightPink), colors.get(ColorNames.LightBlue), Color.WHITE, FileLoader.loadIcon("bg_light", "/menu/", ScreenSize.getWidth(), ScreenSize.getHeight())),
            ThemeNames.LightContrast, new Theme(colors.get(ColorNames.LightPink), colors.get(ColorNames.LightBlue), colors.get(ColorNames.LightPink), colors.get(ColorNames.LightBlue), Color.BLACK, FileLoader.loadIcon("bg_light", "/menu/", ScreenSize.getWidth(), ScreenSize.getHeight())),
            ThemeNames.Dark, new Theme(colors.get(ColorNames.DarkPink), colors.get(ColorNames.DarkPink), colors.get(ColorNames.DarkPink), colors.get(ColorNames.LightBlue), Color.BLACK, FileLoader.loadIcon("bg_dark", "/menu/", ScreenSize.getWidth(), ScreenSize.getHeight())),
            ThemeNames.DarkContrast, new Theme(Color.BLACK, Color.BLACK, Color.BLACK, colors.get(ColorNames.LightBlue), colors.get(ColorNames.Pink), FileLoader.loadIcon("bg_dark", "/menu/", ScreenSize.getWidth(), ScreenSize.getHeight())),
            ThemeNames.Nyan, new Theme(colors.get(ColorNames.Blue), colors.get(ColorNames.Blue), colors.get(ColorNames.Green), colors.get(ColorNames.LightBlue), Color.WHITE, FileLoader.loadIcon("bg_nyan", "/menu/", ScreenSize.getWidth(), ScreenSize.getHeight()))
    );

    private static Border border;



// buttons

    private JButton bStartGame;
    private final JLabel[] lHeaders = new JLabel[3];
    private JButton[] bThemes;
    private JButton[] bPlayers;
    private JButton[] bMaps;

    private final JButton[] bBack = new JButton[3];
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





    public Menu(String version){
        colorSetting = ThemeNames.values()[Integer.parseInt(Objects.requireNonNull(FileLoader.load("color")))];
        border = new LineBorder(themes.get(colorSetting).textColor, 1, true);
        width = ScreenSize.getWidth();
        height = ScreenSize.getHeight();
        GAME_VERSION = version;

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
        // design
        int labelFontSize = labelHeight - 13;

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
            lInfo[i].setForeground(themes.get(colorSetting).textColor);
            lInfo[i].setBackground(themes.get(colorSetting).labelColor);
            lInfo[i].setBorder(border);
            lInfo[i].setFont(new Font(textFont, Font.PLAIN, labelFontSize));
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
            addbMaps();
            menu.add(bBack[PLAYERS]);
        } else if(!lInfo[THEME].getText().equals("")) {
            addbPlayers();
            menu.add(bBack[THEMES]);
        } else {
            addbThemes();
        }



        menu.add(pControls);
        menu.add(bShowControls);
        menu.add(bResetHighscore);
        for (JLabel jLabel : lInfo) {
            menu.add(jLabel);
        }
        menu.add(bChangeColor);
        menu.add(bExit);
    }

    private void initializeBackground() {
        background.setBounds(0,0,menu.getWidth(), menu.getHeight());
        background.setIcon(themes.get(colorSetting).backgroundImage);
        menu.setContentPane(background);
    }

    private void initializeButtons() {

        // start Button
        bStartGame = new JButton("START GAME");
        bStartGame.setBounds(menu.getWidth()/3,menu.getHeight()/3,menu.getWidth()/3,menu.getHeight()/5);
        bStartGame.setBackground(themes.get(colorSetting).buttonColor);
        bStartGame.setForeground(themes.get(colorSetting).textColor);
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
            bBack[i].setBackground(themes.get(colorSetting).backButtonColor);
            bBack[i].setForeground(themes.get(colorSetting).textColor);
            bBack[i].setBorder(border);

        }
        bBack[THEMES].addActionListener(getActionListenerBackThemes());
        bBack[PLAYERS].addActionListener(getActionListenerBackPlayers());
        bBack[MAPS].addActionListener(getActionListenerBackMaps());
        // -back Buttons-


        // color change Button
        bChangeColor.setBounds(menu.getWidth()-20-backWidth, menu.getHeight()-backHeight-10, backWidth, backHeight);
        bChangeColor.setFont(new Font(textFont, Font.PLAIN, backHeight/2));
        bChangeColor.setBackground(themes.get(colorSetting).bottomButtonColor);
        bChangeColor.setForeground(themes.get(colorSetting).textColor);
        bChangeColor.setBorder(border);
        bChangeColor.addActionListener(getActionListenerColor());
        // - color change Button-


        // control showing Button
        bShowControls.setBounds(menu.getWidth()-20-backWidth, menu.getHeight()-2*backHeight-20, backWidth, backHeight);
        bShowControls.setFont(new Font(textFont, Font.PLAIN, backHeight/2));
        bShowControls.setBackground(themes.get(colorSetting).bottomButtonColor);
        bShowControls.setForeground(themes.get(colorSetting).textColor);
        bShowControls.setBorder(border);
        bShowControls.addActionListener(getActionListenerShowControls());
        // - control showing Button-


        // highscore reset Button
        bResetHighscore.setBounds(menu.getWidth()-30-2*backWidth, menu.getHeight()-backHeight-10, backWidth, backHeight);
        bResetHighscore.setFont(new Font(textFont, Font.PLAIN, backHeight/2));
        bResetHighscore.setBackground(themes.get(colorSetting).bottomButtonColor);
        bResetHighscore.setForeground(themes.get(colorSetting).textColor);
        bResetHighscore.setBorder(border);
        bResetHighscore.addActionListener(getActionListenerResetHighscores());
        // - highscore reset Button-


        // exit Button
        bExit.setBounds(20, menu.getHeight()-backHeight-10, backWidth, backHeight);
        bExit.setFont(font);
        bExit.setBackground(themes.get(colorSetting).bottomButtonColor);
        bExit.setForeground(themes.get(colorSetting).textColor);
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
        pControls.setBackground(themes.get(colorSetting).bottomButtonColor);
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
        table.setBackground(themes.get(colorSetting).bottomButtonColor);
        table.setForeground(themes.get(colorSetting).textColor);
        table.setFont(new Font(textFont, Font.PLAIN, rowheight/4));
        table.getTableHeader().setBackground(themes.get(colorSetting).bottomButtonColor);
        table.getTableHeader().setForeground(themes.get(colorSetting).textColor);
        table.getTableHeader().setFont(new Font(textFont, Font.PLAIN, tableHeaderHeight/2));

        table.getTableHeader().setBorder(border);
        table.setGridColor(themes.get(colorSetting).textColor);
        pControls.setLayout(new BorderLayout());
        pControls.add(table.getTableHeader(), BorderLayout.PAGE_START);
        pControls.add(table, BorderLayout.CENTER);
    }



// adder methods

    private void addbThemes(){
        // length initialized
        bThemes = new JButton[gameThemes.length];
        // height & width initialized
        int height = menu.getHeight()/12;
        int width = height*10;
        int fontSize = height/3;

        for (int i = -1; i < gameThemes.length; i++) {
            if(i < 0){
                lHeaders[THEMES] = new JLabel("CHOOSE A THEME", SwingConstants.CENTER);
                lHeaders[THEMES].setBounds(menu.getWidth()/4,menu.getHeight()/10+i*(menu.getHeight()/12 + 10),width,height);
                lHeaders[THEMES].setFont(new Font(textFont + " bold", Font.PLAIN, fontSize+2));
                lHeaders[THEMES].setForeground(themes.get(colorSetting).textColor);
                menu.add(lHeaders[THEMES]);
            } else {
                // button initialized & added
                bThemes[i] = new JButton(StringEditor.makeNameNice(gameThemes[i]));
                bThemes[i].setBounds(menu.getWidth() / 4, menu.getHeight() / 10 + i * (menu.getHeight() / 12 + 10), width, height);
                bThemes[i].setFont(new Font(textFont, Font.PLAIN, fontSize));
                bThemes[i].setBackground(themes.get(colorSetting).buttonColor);
                bThemes[i].setForeground(themes.get(colorSetting).textColor);
                bThemes[i].setBorder(border);
                bThemes[i].addActionListener(getActionListenerThemes(i));
                menu.add(bThemes[i]);
            }
        }

        // menu updated
        menu.repaint();
    }

    private void addbPlayers(){
        // length initialized
        bPlayers = new JButton[playerName.length];
        // height & width initialized
        int height = menu.getHeight()/12;
        int width = height*10;
        int fontSize = height/3;

        for (int i = -1; i < playerName.length; i++) {
            if(i < 0) {
                lHeaders[PLAYERS] = new JLabel("CHOOSE A PLAYER", SwingConstants.CENTER);
                lHeaders[PLAYERS].setBounds(menu.getWidth() / 4, menu.getHeight() / 10 + i * (menu.getHeight() / 12 + 10), width, height);
                lHeaders[PLAYERS].setFont(new Font(textFont + " bold", Font.PLAIN, fontSize+2));
                lHeaders[PLAYERS].setForeground(themes.get(colorSetting).textColor);
                menu.add(lHeaders[PLAYERS]);
            } else {
                // button initialized & added
                bPlayers[i] = new JButton(StringEditor.makeNameNice(playerName[i]));
                bPlayers[i].setBounds(menu.getWidth() / 4, menu.getHeight() / 10 + i * (menu.getHeight() / 12 + 10), width, height);
                bPlayers[i].setFont(new Font(textFont, Font.PLAIN, fontSize));
                bPlayers[i].setBackground(themes.get(colorSetting).buttonColor);
                bPlayers[i].setForeground(themes.get(colorSetting).textColor);
                bPlayers[i].setBorder(border);
                bPlayers[i].addActionListener(getActionListenerPlayers(i));
                menu.add(bPlayers[i]);
            }
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

    private void addbMaps(){
        // length initialized
        bMaps = new JButton[maps.length];
        // height & width initialized
        int height = menu.getHeight()/12;
        int width = height*10;
        int fontSize = height/3;

        for (int i = -1; i < maps.length; i++) {
            if(i < 0) {
                lHeaders[MAPS] = new JLabel("CHOOSE A MAP", SwingConstants.CENTER);
                lHeaders[MAPS].setBounds(menu.getWidth() / 4, menu.getHeight() / 10 + i * (menu.getHeight() / 12 + 10), width, height);
                lHeaders[MAPS].setFont(new Font(textFont + " bold", Font.PLAIN, fontSize+2));
                lHeaders[MAPS].setForeground(themes.get(colorSetting).textColor);
                menu.add(lHeaders[MAPS]);
            } else {
                // button initialized & added
                bMaps[i] = new JButton(StringEditor.makeNameNice(maps[i]));
                bMaps[i].setBounds(menu.getWidth() / 4, menu.getHeight() / 10 + i * (menu.getHeight() / 12 + 10), width, height);
                bMaps[i].setFont(new Font(textFont, Font.PLAIN, fontSize));
                bMaps[i].setBackground(themes.get(colorSetting).buttonColor);
                bMaps[i].setForeground(themes.get(colorSetting).textColor);
                bMaps[i].setBorder(border);
                bMaps[i].addActionListener(getActionListenerMaps(i));
                menu.add(bMaps[i]);
            }
        }

        // back [to] players button added
        menu.add(bBack[PLAYERS]);
        // menu updated
        menu.repaint();
    }




// action listeners

    private ActionListener getActionListenerResetHighscores() {
        return e -> resetHighscores();
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
            colorSetting = ThemeNames.values()[colorSetting.ordinal() < themes.size()-1 ? colorSetting.ordinal()+1 : 0];
            FileLoader.save(Integer.toString(colorSetting.ordinal()), "color");
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

                resetSprites();
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
            menu.remove(lHeaders[PLAYERS]);
            // theme text removed
            lInfo[THEME].setText("");
            // theme buttons added
            addbThemes();
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
            menu.remove(lHeaders[MAPS]);
            // player text removed
            lInfo[PLAYER].setText("");
            // highscore for map reset
            setHighscore();
            // back [to] players button removed
            menu.remove(bBack[PLAYERS]);
            // menu updated
            menu.repaint();
            // players added
            addbPlayers();
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
            addbMaps();
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
            menu.remove(lHeaders[THEMES]);
            // player buttons added
            addbPlayers();
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
            menu.remove(lHeaders[PLAYERS]);
            // back [to] themes button removed
            menu.remove(bBack[THEMES]);
            // map buttons added
            addbMaps();
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
            menu.remove(lHeaders[MAPS]);
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
        return themes.get(colorSetting).buttonColor;
    }

    public static Color getTextColor(){
        return themes.get(colorSetting).textColor;
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
        return themes.get(colorSetting).backgroundImage;
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

    private void resetSprites() {
        try {
            List<GameObject> mapObjects = Game.getState().getMapObjects();
            for (GameObject mapObject : mapObjects) {
                mapObject.resetSprites();
            }
            List<GameObject> gameObjects = Game.getState().getGameObjects();
            for (GameObject gameObject : gameObjects) {
                gameObject.resetSprites();
            }
        }catch(Exception ignored){}
    }
}