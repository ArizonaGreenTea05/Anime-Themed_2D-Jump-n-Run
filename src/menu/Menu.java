package menu;

import controller.PlayerController;
import core.ScreenSize;
import display.GameDisplay;
import game.Game;
import game.GameLoop;
import utils.ElseUtils;
import utils.FileLoader;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Menu {

    private final String GAME_VERSION;

    private static boolean controlsShown = false;

    private static final int AOD = 0;
    private static final String AOD_PATH = "game/themes/angels_of_death/";
    private static final int AOT = 1;
    private static final String AOT_PATH = "game/themes/attack_on_titan/";
    private static final String THEME_PATH = "game/themes";


    public static String[] playerName;
    public static String[] maps;

    private static final String[] playerSheetsInFolder = new String[] {"stand.png", "walk.png", "hit.png"};


    private final JFrame menu = new JFrame();
    private final int width, height;
    private final JLabel background = new JLabel();

    private final JPanel pControls = new JPanel();

    private final JButton bExit = new JButton("EXIT");
    private final JButton bChangeColor = new JButton("change colors");
    private final JButton bShowControls = new JButton("controls");
    private JButton bStartGame;
    private JButton[] bPlayers;
    private JButton[] bMaps;
    private final JButton[] bThemes = new JButton[gameThemes.length];
    private static Rectangle buttonBounds;
    private JButton bBackPlayers = new JButton();
    private JButton bBackThemes = new JButton();
    private static JButton bBackMaps = new JButton();

    public static int labelHeight;
    public static int labelWidth1;
    public static int labelWidth2;
    public static int fontSize;

    private static double highscore;

    private static final JLabel lThemeText = new JLabel(" Theme:");
    private static final JLabel lTheme = new JLabel("");
    private static final JLabel lPlayerText = new JLabel(" Character:");
    private static final JLabel lPlayer = new JLabel("");
    private static final JLabel lMapText = new JLabel(" Map:");
    private static final JLabel lMap = new JLabel("");
    private static final JLabel lHighscoreText = new JLabel(" Highscore:");
    private static final JLabel lHighscore = new JLabel("");
    private static final JLabel lScoreText = new JLabel(" Score:");
    private static final JLabel lScore = new JLabel("");

    /**
     * declaration of themes, player names and colors
     **/

    public static final String[] playerNamesAoD = FileLoader.loadFileNames(AOD_PATH + "characters", "npc", FileLoader.ALL);
    public static final String[] mapsAoD = FileLoader.loadFileNames(AOD_PATH + "maps", ".highscore" , FileLoader.ALL);
    public static final Color bgColorAoD = new Color(128, 186, 224);

    public static final String[] playerNamesAoT = FileLoader.loadFileNames(AOT_PATH + "characters", "npc", FileLoader.ALL);
    public static final String[] mapsAoT = FileLoader.loadFileNames(AOT_PATH + "maps", ".highscore" , FileLoader.ALL);
    public static final Color bgColorAoT = new Color(70, 90, 120);

    public static final String[] gameThemes = FileLoader.loadFileNames(THEME_PATH);
    private static final Color[] bgColors = new Color[]{bgColorAoD, bgColorAoT};

    private static int name;
    private static int theme;
    private static int map;

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
    private static final String[] bgImages =                    {"bg_light",  "bg_light",  "bg_dark",   "bg_dark",   "bg_nyan"};
    private static Border border;

    public static final String textFont = "Comic Sans MS";


    private static void setPlayerNames(int i){
        if(i==AOD) playerName = playerNamesAoD;
        if(i==AOT) playerName = playerNamesAoT;
    }

    private static void setMaps(int i){
        if(i==AOD) maps = mapsAoD;
        if(i==AOT) maps = mapsAoT;
    }

    public Menu(String version){
        this.colorSetting = Integer.parseInt(Objects.requireNonNull(FileLoader.load("color")));
        this.border = new LineBorder(textColor[colorSetting], 1, true);
        this.width = ScreenSize.getWidth();
        this.height = ScreenSize.getHeight();
        this.GAME_VERSION = version;

        setHighscore();

        initializeMenu();

        initializeButtons();

        initializeLabels();

        initializePanels();

        initializeTable();

        initializeBackground();

        addAll();

        menu.setVisible(true);
    }

    private void initializePanels() {
        int themeTextX = lThemeText.getX();
        int themeTextY = lThemeText.getY();
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


    private void initializeMenu() {
        menu.setSize(width,height);
        menu.setTitle("GameMenu   | " + GAME_VERSION + " |");
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

        lThemeText.setBounds(menu.getWidth()-labelWidth1-labelWidth2-22, 35, labelWidth1, labelHeight);
        lThemeText.setOpaque(true);
        lThemeText.setForeground(textColor[colorSetting]);
        lThemeText.setBackground(labelColor[colorSetting]);
        lThemeText.setBorder(border);
        lThemeText.setFont(new Font(textFont, Font.PLAIN, fontSize));

        lTheme.setBounds(lThemeText.getX()+labelWidth1-1, 35, labelWidth2, labelHeight);
        lTheme.setOpaque(true);
        lTheme.setForeground(textColor[colorSetting]);
        lTheme.setBackground(labelColor[colorSetting]);
        lTheme.setBorder(border);
        lTheme.setFont(new Font(textFont, Font.PLAIN, fontSize));

        lPlayerText.setBounds(menu.getWidth()-labelWidth1-labelWidth2-22, 33+labelHeight, labelWidth1, labelHeight);
        lPlayerText.setOpaque(true);
        lPlayerText.setForeground(textColor[colorSetting]);
        lPlayerText.setBackground(labelColor[colorSetting]);
        lPlayerText.setBorder(border);
        lPlayerText.setFont(new Font(textFont, Font.PLAIN, fontSize));

        lPlayer.setBounds(lPlayerText.getX()+labelWidth1-1, 33+labelHeight, labelWidth2, labelHeight);
        lPlayer.setOpaque(true);
        lPlayer.setForeground(textColor[colorSetting]);
        lPlayer.setBackground(labelColor[colorSetting]);
        lPlayer.setBorder(border);
        lPlayer.setFont(new Font(textFont, Font.PLAIN, fontSize));

        lMapText.setBounds(menu.getWidth()-labelWidth1-labelWidth2-22, 31+2*labelHeight, labelWidth1, labelHeight);
        lMapText.setOpaque(true);
        lMapText.setForeground(textColor[colorSetting]);
        lMapText.setBackground(labelColor[colorSetting]);
        lMapText.setBorder(border);
        lMapText.setFont(new Font(textFont, Font.PLAIN, fontSize));

        lMap.setBounds(lMapText.getX()+labelWidth1-1, 31+2*labelHeight, labelWidth2, labelHeight);
        lMap.setOpaque(true);
        lMap.setForeground(textColor[colorSetting]);
        lMap.setBackground(labelColor[colorSetting]);
        lMap.setBorder(border);
        lMap.setFont(new Font(textFont, Font.PLAIN, fontSize));

        lHighscoreText.setBounds(menu.getWidth()-labelWidth1-labelWidth2-22, 29+3*labelHeight, labelWidth1, labelHeight);
        lHighscoreText.setOpaque(true);
        lHighscoreText.setForeground(textColor[colorSetting]);
        lHighscoreText.setBackground(labelColor[colorSetting]);
        lHighscoreText.setBorder(border);
        lHighscoreText.setFont(new Font(textFont, Font.PLAIN, fontSize));

        lHighscore.setBounds(lHighscoreText.getX()+labelWidth1-1, 29+3*labelHeight, labelWidth2, labelHeight);
        lHighscore.setOpaque(true);
        lHighscore.setForeground(textColor[colorSetting]);
        lHighscore.setBackground(labelColor[colorSetting]);
        lHighscore.setBorder(border);
        lHighscore.setFont(new Font(textFont, Font.PLAIN, fontSize));
        lHighscore.setText(" " + ElseUtils.shorten(String.valueOf(highscore),4));

        lScoreText.setBounds(menu.getWidth()-labelWidth1-labelWidth2-22, 27+4*labelHeight, labelWidth1, labelHeight);
        lScoreText.setOpaque(true);
        lScoreText.setForeground(textColor[colorSetting]);
        lScoreText.setBackground(labelColor[colorSetting]);
        lScoreText.setBorder(border);
        lScoreText.setFont(new Font(textFont, Font.PLAIN, fontSize));

        lScore.setBounds(lScoreText.getX()+labelWidth1-1, 27+4*labelHeight, labelWidth2, labelHeight);
        lScore.setOpaque(true);
        lScore.setForeground(textColor[colorSetting]);
        lScore.setBackground(labelColor[colorSetting]);
        lScore.setBorder(border);
        lScore.setFont(new Font(textFont, Font.PLAIN, fontSize));
        lScore.setText(" " + ElseUtils.shorten(String.valueOf(GameDisplay.getScore()),4));
        // -Status-Anzeige-
    }

    private void addAll() {

        //bei Farbwechsel wird vorheriger Punkt beibehalten
        if(!lMap.getText().equals("")) {
            menu.add(bStartGame);
            menu.add(bBackMaps);
        } else if(!lPlayer.getText().equals("")) {
            addMaps();
            menu.add(bBackPlayers);
        } else if(!lTheme.getText().equals("")) {
            addPlayers();
            menu.add(bBackThemes);
        } else {
            addThemes();
        }



        menu.add(pControls);
        menu.add(bShowControls);
        menu.add(lTheme);
        lTheme.setVisible(true);
        menu.add(lThemeText);
        menu.add(lPlayer);
        lPlayer.setVisible(true);
        menu.add(lPlayerText);
        menu.add(lMap);
        lMap.setVisible(true);
        menu.add(lMapText);
        menu.add(lHighscore);
        lHighscore.setVisible(true);
        menu.add(lHighscoreText);
        menu.add(lScore);
        menu.add(lScoreText);
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

        Font font = new Font(textFont, Font.PLAIN, backHeight/5*3);

        bBackThemes = new JButton("<<back");
        bBackThemes.setBounds(20, 20, backWidth, backHeight);
        bBackThemes.setFont(font);
        bBackThemes.setBackground(backButtonColor[colorSetting]);
        bBackThemes.setForeground(textColor[colorSetting]);
        bBackThemes.setBorder(border);
        bBackThemes.addActionListener(getActionListenerBackThemes());

        bBackPlayers = new JButton("<<back");
        bBackPlayers.setBounds(bBackThemes.getBounds());
        bBackPlayers.setFont(font);
        bBackPlayers.setBackground(backButtonColor[colorSetting]);
        bBackPlayers.setForeground(textColor[colorSetting]);
        bBackPlayers.setBorder(border);
        bBackPlayers.addActionListener(getActionListenerBackPlayers());

        bBackMaps = new JButton("<<back");
        bBackMaps.setBounds(bBackThemes.getBounds());
        bBackMaps.setFont(font);
        bBackMaps.setBackground(backButtonColor[colorSetting]);
        bBackMaps.setForeground(textColor[colorSetting]);
        bBackMaps.setBorder(border);
        bBackMaps.addActionListener(getActionListenerBackMaps());

        // -back Buttons-


        // color change Button
        bChangeColor.setBounds(menu.getWidth()-20-backWidth, menu.getHeight()-backHeight-10, backWidth, backHeight);
        bChangeColor.setFont(new Font(textFont, Font.PLAIN, backHeight/2));
        bChangeColor.setBackground(bottomButtonColor[colorSetting]);
        bChangeColor.setForeground(textColor[colorSetting]);
        bChangeColor.setBorder(border);
        bChangeColor.addActionListener(getActionListenerColor());
        // - color change Button-


        bShowControls.setBounds(menu.getWidth()-20-backWidth, menu.getHeight()-2*backHeight-20, backWidth, backHeight);
        bShowControls.setFont(new Font(textFont, Font.PLAIN, backHeight/2));
        bShowControls.setBackground(bottomButtonColor[colorSetting]);
        bShowControls.setForeground(textColor[colorSetting]);
        bShowControls.setBorder(border);
        bShowControls.addActionListener(getActionListenerShowControls());


        // exit Button
        bExit.setBounds(20, menu.getHeight()-backHeight-10, backWidth, backHeight);
        bExit.setFont(font);
        bExit.setBackground(bottomButtonColor[colorSetting]);
        bExit.setForeground(textColor[colorSetting]);
        bExit.setBorder(border);
        bExit.addActionListener(getActionListenerExit());
        // - exit Button-
    }


    private void addPlayers(){
        bPlayers = new JButton[playerName.length];

        int height = menu.getHeight()/12;
        int width = height*10;

        for (int i = 0; i < playerName.length; i++) {
            bPlayers[i] = new JButton(ElseUtils.makeNameNice(playerName[i]));

            bPlayers[i].setBounds(menu.getWidth()/4,menu.getHeight()/10+i*(menu.getHeight()/12 + 10),width,height);
            bPlayers[i].setFont(new Font(textFont, Font.PLAIN, bPlayers[i].getHeight()/3));
            bPlayers[i].setBackground(buttonColor[colorSetting]);
            bPlayers[i].setForeground(textColor[colorSetting]);
            bPlayers[i].setBorder(border);
            bPlayers[i].addActionListener(getActionListenerPlayers(i));
            menu.add(bPlayers[i]);
            menu.getContentPane().setBackground(getBGColor());
            menu.repaint();
        }
        buttonBounds = bPlayers[0].getBounds();
        lTheme.setText(" " + ElseUtils.makeNameNice(getGameTheme()));
        menu.add(bBackThemes);
        menu.repaint();
    }

    private void addThemes(){

        int height = menu.getHeight()/12;
        int width = height*10;

        for (int i = 0; i < gameThemes.length; i++) {
            bThemes[i] = new JButton(ElseUtils.makeNameNice(gameThemes[i]));

            bThemes[i].setBounds(menu.getWidth()/4,menu.getHeight()/10+i*(menu.getHeight()/12 + 10),width,height);
            bThemes[i].setFont(new Font(textFont, Font.PLAIN, bThemes[i].getHeight()/3));
            bThemes[i].setBackground(buttonColor[colorSetting]);
            bThemes[i].setForeground(textColor[colorSetting]);
            bThemes[i].setBorder(border);
            bThemes[i].addActionListener(getActionListenerThemes(i));
            menu.add(bThemes[i]);
        }
        menu.repaint();
    }

    private void addMaps(){
        bMaps = new JButton[maps.length];

        int width = menu.getWidth()/2;
        int height = menu.getHeight()/12;

        for (int i = 0; i < maps.length; i++) {
            bMaps[i] = new JButton(ElseUtils.makeNameNice(maps[i]));

            bMaps[i].setBounds(menu.getWidth()/4,menu.getHeight()/10+i*(menu.getHeight()/12 + 10),width,height);
            bMaps[i].setFont(new Font(textFont, Font.PLAIN, bMaps[i].getHeight()/3));
            bMaps[i].setBackground(buttonColor[colorSetting]);
            bMaps[i].setForeground(textColor[colorSetting]);
            bMaps[i].setBorder(border);
            bMaps[i].addActionListener(getActionListenerMaps(i));
            menu.add(bMaps[i]);
        }
        menu.add(bBackPlayers);
        menu.repaint();
    }



    public static String[] getPlayerSheetsInFolder() {
        return playerSheetsInFolder;
    }

    private ActionListener getActionListenerShowControls() {
        return e -> {
            controlsShown = !pControls.isVisible();
            pControls.setVisible(controlsShown);
        };
    }

    private ActionListener getActionListenerColor() {
        return e -> {
            if(colorSetting < buttonColor.length-1) {
                colorSetting++;
            } else {
                colorSetting = 0;
            }
            FileLoader.save("" + colorSetting, "color");
            new Menu(GAME_VERSION);
            menu.dispose();
        };
    }

    private ActionListener getActionListenerExit() {
        return e -> System.exit(0);
    }

    private ActionListener getActionListenerStart() {
        return e -> {
            new Thread(() ->{

                GameLoop gameLoop = new GameLoop(new Game(GAME_VERSION));
                menu.dispose();
                new Thread(gameLoop).start();
            }).start();
        };
    }

    private ActionListener getActionListenerBackThemes(){
        return e-> {
            for (JButton button : bPlayers) {
                menu.remove(button);
            }
            lTheme.setText("");
            addThemes();
            menu.remove(bBackThemes);
            menu.repaint();
        };
    }

    private ActionListener getActionListenerBackPlayers() {
        return e -> {
            for (JButton button : bMaps) {
                menu.remove(button);
            }
            lPlayer.setText("");
            setHighscore();
            menu.remove(bBackPlayers);
            menu.repaint();
            addPlayers();
        };
    }

    private ActionListener getActionListenerBackMaps() {
        return e -> {
            lMap.setText("");
            menu.remove(bStartGame);
            menu.remove(bBackMaps);
            menu.repaint();
            addMaps();
        };
    }

    private ActionListener getActionListenerThemes(int in){
        return e -> {
            theme = in;
            setPlayerNames(theme);
            setMaps(theme);
            for (JButton button : bThemes) {
                menu.remove(button);
            }
            addPlayers();
            menu.add(bBackThemes);
            menu.repaint();
        };
    }


    private ActionListener getActionListenerPlayers(int in) {
        return e -> {
            name = in;
            for (JButton button : bPlayers) {
                menu.remove(button);
            }
            menu.remove(bBackThemes);
            lPlayer.setText(" " + ElseUtils.makeNameNice(getPlayerName()));
            addMaps();
            menu.add(bBackPlayers);
            menu.repaint();
        };
    }


    private ActionListener getActionListenerMaps(int in) {
        return e -> {
            map = in;
            for (JButton button : bMaps) {
                menu.remove(button);
            }
            menu.remove(bBackPlayers);
            lMap.setText(" " + ElseUtils.makeNameNice(getMapName()));
            setHighscore();
            menu.add(bBackMaps);
            menu.add(bStartGame);
            menu.repaint();
        };
    }

    private void setHighscore(){
        double highscore = 0;
        try {
            highscore = Double.parseDouble(Objects.requireNonNull(FileLoader.load(getMapName() + ".highscore", THEME_PATH + "/" + getGameTheme() + "/maps/")));
        } catch(Exception igore){}
        double score = GameDisplay.getScore();

        if (highscore >= score) {
            this.highscore = highscore;
        } else {
            FileLoader.save(String.valueOf(score),getMapName() + ".highscore", THEME_PATH + "/" + getGameTheme() + "/maps/");
            this.highscore = score;
        }
        lHighscore.setText(" " + ElseUtils.shorten(String.valueOf(highscore),4));
    }

    public static JLabel getHighscore(){
        return lHighscore;
    }

    public static JLabel getThemeLabel() {
        return lTheme;
    }

    public static JLabel getPlayerLabel() {
        return lPlayer;
    }

    public static JLabel getMapLabel(){
        return lMap;
    }

    public static JLabel getScore() {
        return lScore;
    }

    public static String getGameTheme(){
        return gameThemes[theme];
    }

    public static String getPlayerName(){
        return playerName[name];
    }

    public static String getMapName(){
        return maps[map];
    }

    public static Color getBGColor(){
        return bgColors[theme];
    }

    public static Color getButtonColor(){
        return buttonColor[colorSetting];
    }

    public static Color getTextColor(){
        return textColor[colorSetting];
    }

    public static Rectangle getBackBounds() {
        return bBackMaps.getBounds();
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

}