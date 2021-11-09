package menu;

import core.FileLoader;
import core.ScreenSize;
import display.GameDisplay;
import game.Game;
import game.GameLoop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Menu {

    public static String[] playerName;
    public static String[] maps;


    private static final String[] playerSheetsInFolder = new String[] {"stand.png", "walk.png"};

    private final JButton exit = new JButton("EXIT");
    private final JButton color = new JButton("change colors");

    private final JFrame menu = new JFrame("Menu");
    private JButton startGame;
    private JButton[] playerButtons;
    private JButton[] mapButtons;
    private final JButton[] themeButtons = new JButton[gameThemes.length];
    private JButton backPlayers = new JButton();
    private JButton backThemes = new JButton();
    private JButton backMaps = new JButton();
    private final int width, height;
    private final JLabel background = new JLabel();

    public static int labelHeight;
    public static int labelWidth1;
    public static int labelWidth2;
    public static int fontSize;

    private static double highScore;

    private static JLabel themeTextLabel = new JLabel("Theme:");
    private static JLabel playerTextLabel = new JLabel("Character:");
    private static JLabel mapTextLabel = new JLabel("Map:");
    private static JLabel highScoreTextLabel = new JLabel("Highscore:");
    private static JLabel scoreTextLabel = new JLabel("Score:");
    private static JLabel themeLabel = new JLabel("");
    private static JLabel playerLabel = new JLabel("");
    private static JLabel mapLabel = new JLabel("");
    private static JLabel highScoreLabel = new JLabel("");
    private static JLabel scoreLabel = new JLabel("");

    /**
     * declaration of themes, player names and colors
     **/
    public static final String[] gameThemes = new String[] {"attack_on_titan", "angels_of_death"};

    public static final String[] playerNamesAoT = new String[] {"levi_ackerman", "mikasa_ackerman", "sasha_braus"};
    public static final String[] mapsAoT = new String[] {"test"};
    public static final Color bgColorAoT = new Color(70, 90, 120);
    public static final String[] playerNamesAoD = new String[] {"rachel_gardner"};
    public static final String[] mapsAoD = new String[] {"test"};
    public static final Color bgColorAoD = new Color(128, 186, 224);
    private static final Color[] bgColors = new Color[]{bgColorAoT, bgColorAoD};
    private static int name;
    private static int theme;
    private static int map;

    public static int colorSetting = 0;

    private static final Color color1 = new Color(250, 200, 230);
    private static final Color color2 = new Color(4, 162, 236);
    private static final Color color3 = new Color(187, 120, 160);
    private static final Color color4 = new Color(255, 111, 197);
    private static final Color clear = new Color(0,0,0,0);

    public static final Color[] buttonColor =           { color1,         color1,         color3,        Color.BLACK};
    private static final Color[] backButtonColor =      { color2,         color2,         color3,        Color.BLACK};
    public static final Color[] labelColor =            { color2,         color2,         color3,        Color.BLACK};
    public static final Color[] textColor =             { Color.WHITE,    Color.BLACK,    Color.BLACK,   color4};
    private final String[] bgImage =                    {"bg_light.png", "bg_light.png", "bg_dark.png", "bg_dark.png"};

    public static final String textFont = "Comic Sans MS";


    private static void setPlayerNames(int i){
        if(i==0) playerName = playerNamesAoT;
        if(i==1) playerName = playerNamesAoD;
    }

    private static void setMaps(int i){
        if(i==0) maps = mapsAoT;
        if(i==1) maps = mapsAoD;
    }

    public Menu(){
        this.colorSetting = Integer.parseInt(FileLoader.load("color.txt"));
        this.width = ScreenSize.getWidth();
        this.height = ScreenSize.getHeight();

        setHighscore();

        initializeMenu();

        initializeButtons();

        initializeLabels();

        initializeBackground();

        addAll();

        menu.setVisible(true);
    }


    private void initializeMenu() {
        // 16/9-Format, halb so breit wie Bildschirm, Höhe dementsprechend angepasst
        menu.setSize(width/2,width/32*9);
        menu.setIconImage(getImage("../sakura_icon.png"));
        menu.setDefaultCloseOperation(EXIT_ON_CLOSE);
        menu.getContentPane().setBackground(new Color(23, 139, 221));
        menu.setResizable(false);
        menu.setLayout(null);
        menu.setLocationRelativeTo(null);
    }

    private void initializeLabels() {
        // Status-Anzeige
        labelHeight = menu.getHeight()/30;
        labelWidth1 = menu.getWidth()/8-30;
        labelWidth2 = menu.getWidth()/8-5;
        fontSize = labelHeight-6;

        themeTextLabel.setBounds(menu.getWidth()-labelWidth1-labelWidth2-22, 10, labelWidth1, labelHeight);
        themeTextLabel.setOpaque(true);
        themeTextLabel.setForeground(textColor[colorSetting]);
        themeTextLabel.setBackground(labelColor[colorSetting]);
        themeTextLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));

        themeLabel.setBounds(menu.getWidth()-labelWidth2-20, 10, labelWidth2, labelHeight);
        themeLabel.setOpaque(true);
        themeLabel.setForeground(textColor[colorSetting]);
        themeLabel.setBackground(labelColor[colorSetting]);
        themeLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));

        playerTextLabel.setBounds(menu.getWidth()-labelWidth1-labelWidth2-22, 13+labelHeight, labelWidth1, labelHeight);
        playerTextLabel.setOpaque(true);
        playerTextLabel.setForeground(textColor[colorSetting]);
        playerTextLabel.setBackground(labelColor[colorSetting]);
        playerTextLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));

        playerLabel.setBounds(menu.getWidth()-labelWidth2-20, 13+labelHeight, labelWidth2, labelHeight);
        playerLabel.setOpaque(true);
        playerLabel.setForeground(textColor[colorSetting]);
        playerLabel.setBackground(labelColor[colorSetting]);
        playerLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));

        mapTextLabel.setBounds(menu.getWidth()-labelWidth1-labelWidth2-22, 16+2*labelHeight, labelWidth1, labelHeight);
        mapTextLabel.setOpaque(true);
        mapTextLabel.setForeground(textColor[colorSetting]);
        mapTextLabel.setBackground(labelColor[colorSetting]);
        mapTextLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));

        mapLabel.setBounds(menu.getWidth()-labelWidth2-20, 16+2*labelHeight, labelWidth2, labelHeight);
        mapLabel.setOpaque(true);
        mapLabel.setForeground(textColor[colorSetting]);
        mapLabel.setBackground(labelColor[colorSetting]);
        mapLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));

        highScoreTextLabel.setBounds(menu.getWidth()-labelWidth1-labelWidth2-22, 19+3*labelHeight, labelWidth1, labelHeight);
        highScoreTextLabel.setOpaque(true);
        highScoreTextLabel.setForeground(textColor[colorSetting]);
        highScoreTextLabel.setBackground(labelColor[colorSetting]);
        highScoreTextLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));

        highScoreLabel.setBounds(menu.getWidth()-labelWidth2-20, 19+3*labelHeight, labelWidth2, labelHeight);
        highScoreLabel.setOpaque(true);
        highScoreLabel.setForeground(textColor[colorSetting]);
        highScoreLabel.setBackground(labelColor[colorSetting]);
        highScoreLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));
        highScoreLabel.setText(" " + highScore);

        scoreTextLabel.setBounds(menu.getWidth()-labelWidth1-labelWidth2-22, 22+4*labelHeight, labelWidth1, labelHeight);
        scoreTextLabel.setOpaque(true);
        scoreTextLabel.setForeground(textColor[colorSetting]);
        scoreTextLabel.setBackground(labelColor[colorSetting]);
        scoreTextLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));

        scoreLabel.setBounds(menu.getWidth()-labelWidth2-20, 22+4*labelHeight, labelWidth2, labelHeight);
        scoreLabel.setOpaque(true);
        scoreLabel.setForeground(textColor[colorSetting]);
        scoreLabel.setBackground(labelColor[colorSetting]);
        scoreLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));
        scoreLabel.setText(" " + GameDisplay.getScore());
        // -Status-Anzeige-
    }

    private void addAll() {

        //bei Farbwechsel wird vorheriger Punkt beibehalten
        if(!mapLabel.getText().equals("")) {
            menu.add(startGame);
            menu.add(backMaps);
        } else if(!playerLabel.getText().equals("")) {
            addMaps();
            menu.add(backPlayers);
        } else if(!themeLabel.getText().equals("")) {
            addPlayers();
            menu.add(backThemes);
        } else {
            addThemes();
        }


        menu.add(themeLabel);
        themeLabel.setVisible(true);
        menu.add(themeTextLabel);
        menu.add(playerLabel);
        playerLabel.setVisible(true);
        menu.add(playerTextLabel);
        menu.add(mapLabel);
        mapLabel.setVisible(true);
        menu.add(mapTextLabel);
        menu.add(highScoreLabel);
        highScoreLabel.setVisible(true);
        menu.add(highScoreTextLabel);
        menu.add(scoreLabel);
        menu.add(scoreTextLabel);
        menu.add(color);
        menu.add(exit);
    }

    private void initializeBackground() {
        background.setBounds(0,0,menu.getWidth(), menu.getHeight());
        background.setIcon(getIcon(bgImage[colorSetting], menu.getWidth(), menu.getHeight()));
        menu.setContentPane(background);
    }

    private void initializeButtons() {

        // start Button
        startGame = new JButton("START GAME");
        startGame.setBounds(menu.getWidth()/3,menu.getHeight()/3,menu.getWidth()/3,menu.getHeight()/5);
        startGame.setBackground(buttonColor[colorSetting]);
        startGame.setForeground(textColor[colorSetting]);
        startGame.setFont(new Font(textFont, Font.PLAIN, startGame.getHeight()/4));
        startGame.addActionListener(getActionListenerStart());
        // -start Button-


        // back Buttons
        /*
         min Größe so groß, dass Text angezeigt werden kann: 76 · 10
        → wenn relative Maße größer werden diese benutzt, wenn nicht die min Maße
         */
        int backWidth = 100;
        int backHeight = 10;
        if (menu.getHeight()/6 > backWidth){
            backWidth = menu.getHeight()/6;
        }
        if(menu.getHeight()/20 > backHeight){
            backHeight = menu.getHeight()/20;
        }

        Font font = new Font(textFont, Font.PLAIN, backHeight/5*3);

        backThemes = new JButton("<<back");
        backThemes.setBounds(10, 10, backWidth, backHeight);
        backThemes.setFont(font);
        backThemes.setBackground(backButtonColor[colorSetting]);
        backThemes.setForeground(textColor[colorSetting]);
        backThemes.addActionListener(getActionListenerBackThemes());

        backPlayers = new JButton("<<back");
        backPlayers.setBounds(10, 10, backWidth, backHeight);
        backPlayers.setFont(font);
        backPlayers.setBackground(backButtonColor[colorSetting]);
        backPlayers.setForeground(textColor[colorSetting]);
        backPlayers.addActionListener(getActionListenerBackPlayers());

        backMaps = new JButton("<<back");
        backMaps.setBounds(10, 10, backWidth, backHeight);
        backMaps.setFont(font);
        backMaps.setBackground(backButtonColor[colorSetting]);
        backMaps.setForeground(textColor[colorSetting]);
        backMaps.addActionListener(getActionListenerBackMaps());

        // -back Buttons-


        // color change Button
        color.setBounds(menu.getWidth()-25-backWidth*3/2, menu.getHeight()-backHeight-45, backWidth*3/2, backHeight);
        color.setFont(font);
        color.setBackground(buttonColor[colorSetting]);
        color.setForeground(textColor[colorSetting]);
        color.addActionListener(getActionListenerColor());
        // - color change Button-


        // exit Button
        exit.setBounds(10, menu.getHeight()-backHeight-45, backWidth, backHeight);
        exit.setFont(font);
        exit.setBackground(buttonColor[colorSetting]);
        exit.setForeground(textColor[colorSetting]);
        exit.addActionListener(getActionListenerExit());
        // - exit Button-
    }


    private Icon getIcon(String image, int width, int height) {
        Image img = null;
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/menu/" + image)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        img = Objects.requireNonNull(img).getScaledInstance(width, height, Image.SCALE_DEFAULT);

        return new ImageIcon(img);
    }

    public static Image getImage(String image) {
        Image img = null;
        try {
            img = ImageIO.read(Objects.requireNonNull(Menu.class.getResource("/menu/" + image)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }




    private void addPlayers(){
        playerButtons = new JButton[playerName.length];

        for (int i = 0; i < playerName.length; i++) {
            playerButtons[i] = new JButton(makeNameNice(playerName[i]));

            playerButtons[i].setBounds(menu.getWidth()/4,menu.getHeight()/10+i*(menu.getHeight()/12 + 5),menu.getWidth()/2,menu.getHeight()/12);
            playerButtons[i].setFont(new Font(textFont, Font.PLAIN, playerButtons[i].getHeight()/3));
            playerButtons[i].setBackground(buttonColor[colorSetting]);
            playerButtons[i].setForeground(textColor[colorSetting]);
            playerButtons[i].addActionListener(getActionListenerPlayers(i));
            menu.add(playerButtons[i]);
            menu.getContentPane().setBackground(getBGColor());
            menu.repaint();
        }
        themeLabel.setText(" " + makeNameNice(getGameTheme()));
        menu.add(backThemes);
        menu.repaint();
    }

    private void addThemes(){

        for (int i = 0; i < gameThemes.length; i++) {
            themeButtons[i] = new JButton(makeNameNice(gameThemes[i]));

            themeButtons[i].setBounds(menu.getWidth()/4,menu.getHeight()/10+i*(menu.getHeight()/12 + 5),menu.getWidth()/2,menu.getHeight()/12);
            themeButtons[i].setFont(new Font(textFont, Font.PLAIN, themeButtons[i].getHeight()/3));
            themeButtons[i].setBackground(buttonColor[colorSetting]);
            themeButtons[i].setForeground(textColor[colorSetting]);

            themeButtons[i].addActionListener(getActionListenerThemes(i));
            menu.add(themeButtons[i]);
        }
        menu.repaint();
    }

    private void addMaps(){
        mapButtons = new JButton[maps.length];

        for (int i = 0; i < maps.length; i++) {
            mapButtons[i] = new JButton(makeNameNice(maps[i]));

            mapButtons[i].setBounds(menu.getWidth()/4,menu.getHeight()/10+i*(menu.getHeight()/12 + 5),menu.getWidth()/2,menu.getHeight()/12);
            mapButtons[i].setFont(new Font(textFont, Font.PLAIN, mapButtons[i].getHeight()/3));
            mapButtons[i].setBackground(buttonColor[colorSetting]);
            mapButtons[i].setForeground(textColor[colorSetting]);

            mapButtons[i].addActionListener(getActionListenerMaps(i));
            menu.add(mapButtons[i]);
        }
        menu.add(backPlayers);
        menu.repaint();
    }



    public static String[] getPlayerSheetsInFolder() {
        return playerSheetsInFolder;
    }

    private ActionListener getActionListenerColor() {
        return e -> {
            menu.dispose();
            if(colorSetting < buttonColor.length-1) {
                colorSetting++;
            } else {
                colorSetting = 0;
            }
            FileLoader.save("" + colorSetting, "color.txt");
            new Menu();
        };
    }

    private ActionListener getActionListenerExit() {
        return e -> System.exit(0);
    }

    private ActionListener getActionListenerStart() {
        return e -> {
            new Thread(new GameLoop(new Game())).start();
            menu.dispose();
        };
    }

    private ActionListener getActionListenerBackThemes(){
        return e-> {
            for (JButton button : playerButtons) {
                menu.remove(button);
            }
            themeLabel.setText("");
            addThemes();
            menu.remove(backThemes);
            menu.repaint();
        };
    }

    private ActionListener getActionListenerBackPlayers() {
        return e -> {
            for (JButton button : mapButtons) {
                menu.remove(button);
            }
            playerLabel.setText("");
            menu.remove(backPlayers);
            menu.repaint();
            addPlayers();
        };
    }

    private ActionListener getActionListenerBackMaps() {
        return e -> {
            mapLabel.setText("");
            menu.remove(startGame);
            menu.remove(backMaps);
            menu.repaint();
            addMaps();
        };
    }

    private ActionListener getActionListenerThemes(int in){
        return e -> {
            theme = in;
            setPlayerNames(theme);
            setMaps(theme);
            for (JButton button : themeButtons) {
                menu.remove(button);
            }
            addPlayers();
            menu.add(backThemes);
            menu.repaint();
        };
    }


    private ActionListener getActionListenerPlayers(int in) {
        return e -> {
            name = in;
            for (JButton button : playerButtons) {
                menu.remove(button);
            }
            menu.remove(backThemes);
            playerLabel.setText(" " + makeNameNice(getPlayerName()));
            addMaps();
            menu.add(backPlayers);
            menu.repaint();
        };
    }


    private ActionListener getActionListenerMaps(int in) {
        return e -> {
            map = in;
            for (JButton button : mapButtons) {
                menu.remove(button);
            }
            menu.remove(backPlayers);
            mapLabel.setText(" " + makeNameNice(getMapName()));
            menu.add(backMaps);
            menu.add(startGame);
            menu.repaint();
        };
    }



    public static String makeNameNice(String s){
        char[] c = s.toCharArray();
        StringBuilder out = new StringBuilder("" + (char) (c[0] - 32));

        for (int i = 1; i < c.length; i++) {
            if(c[i] != '_'){
                out.append(c[i]);
            } else {
                out.append(" ").append((char) (c[i + 1] - 32));
                i++;
            }
        }

        return out.toString();
    }

    private void setHighscore(){
        double highscore = Double.parseDouble(FileLoader.load("HighScore.txt"));
        double score = GameDisplay.getScore();

        if (highscore >= score) {
            highScore = highscore;
        } else {
            FileLoader.save(String.valueOf(score), "HighScore.txt");
            highScore = score;
        }
    }

    public static JLabel getHighscore(){
        return highScoreLabel;
    }

    public static JLabel getThemeLabel() {
        return themeLabel;
    }

    public static JLabel getPlayerLabel() {
        return playerLabel;
    }

    public static JLabel getMapLabel(){
        return mapLabel;
    }

    public static JLabel getScore() {
        return scoreLabel;
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


}