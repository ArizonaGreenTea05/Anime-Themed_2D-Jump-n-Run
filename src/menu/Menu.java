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

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Menu {

    public static String[] playerName;
    public static String[] maps;


    private static final String[] playerSheetsInFolder = new String[] {"stand.png", "walk.png"};

    private final JButton exit = new JButton("EXIT");

    private final JFrame menu=new JFrame("Menu");
    private JButton startGame = new JButton();
    private JButton[] players;
    private JButton[] mapButton;
    private final JButton[] themes = new JButton[gameTheme.length];
    private JButton backPlayers = new JButton();
    private JButton backThemes = new JButton();
    private JButton backMaps = new JButton();
    private final int width, height;
    private final JLabel background = new JLabel();

    private static double highScore;

    private static final JLabel themeTextLabel = new JLabel("Theme:");
    private static final JLabel playerTextLabel = new JLabel("Character:");
    private static final JLabel mapTextLabel = new JLabel("Map:");
    private static final JLabel highScoreTextLabel = new JLabel("Highscore:");
    private static final JLabel scoreTextLabel = new JLabel("Score:");
    private static final JLabel themeLabel = new JLabel("");
    private static final JLabel playerLabel = new JLabel("");
    private static final JLabel mapLabel = new JLabel("");
    private static final JLabel highScoreLabel = new JLabel("");
    private static final JLabel scoreLabel = new JLabel("");

    /**
     * declaration of themes, player names and colors
     **/
    public static final String[] gameTheme = new String[] {"attack_on_titan", "angels_of_death"};

    public static final String[] playerNameAoT = new String[] {"levi_ackerman", "mikasa_ackerman", "sasha_braus"};
    public static final String[] mapsAoT = new String[] {"test"};
    public static final String[] playerNameAoD = new String[] {"rachel_gardner"};
    public static final String[] mapsAoD = new String[] {"test"};
    //public static final String[] playerNameJojo = new String[] {"dio", "kakyoin_noriaki"};
    private static final Color[] bgColor = new Color[]{(new Color(70, 90, 120)), (new Color(128, 186, 224))};
    private static int name;
    private static int theme;
    private static int map;


    private final Color buttonColor1 = new Color(250, 200, 230);
    private final Color buttonColor2 = new Color(4, 162, 236);
    public static final String textFont = "Comic Sans MS";


    private static void setPlayerNames(int i){
        if(i==0) playerName = playerNameAoT;
        if(i==1) playerName = playerNameAoD;
    }

    private static void setMaps(int i){
        if(i==0) maps = mapsAoT;
        if(i==1) maps = mapsAoD;
    }


    public Menu(){
        this.width = ScreenSize.getWidth();
        this.height = ScreenSize.getHeight();

        double highscore = Double.parseDouble(FileLoader.load("HighScore.txt"));
        double score = GameDisplay.getScore();

        if (highscore >= score) {
            highScore = highscore;
        } else {
            FileLoader.save(String.valueOf(score), "HighScore.txt");
            highScore = score;
        }

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
        menu.setIconImage(getImage("sakura_icon.png"));
        menu.setDefaultCloseOperation(EXIT_ON_CLOSE);
        menu.getContentPane().setBackground(new Color(23, 139, 221));
        menu.setResizable(false);
        menu.setLayout(null);
        menu.setLocationRelativeTo(null);
    }

    private void initializeLabels() {
        // Status-Anzeige
        int labelHeight = menu.getHeight()/30;
        int labelWidth = menu.getWidth()/4;
        int fontSize = labelHeight-4;
        themeTextLabel.setBounds(menu.getWidth()/4*3+10, 10, labelWidth, labelHeight);
        themeTextLabel.setForeground(Color.WHITE);
        themeTextLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));

        themeLabel.setBounds(menu.getWidth()/7*6-5, 10, labelWidth, labelHeight);
        themeLabel.setForeground(Color.WHITE);
        themeLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));

        playerTextLabel.setBounds(menu.getWidth()/4*3+10, 13+labelHeight, labelWidth, labelHeight);
        playerTextLabel.setForeground(Color.WHITE);
        playerTextLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));

        playerLabel.setBounds(menu.getWidth()/7*6-5, 13+labelHeight, labelWidth, labelHeight);
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));

        highScoreTextLabel.setBounds(menu.getWidth()/4*3+10, 16+2*labelHeight, labelWidth, labelHeight);
        highScoreTextLabel.setForeground(Color.WHITE);
        highScoreTextLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));

        highScoreLabel.setBounds(menu.getWidth()/7*6-5, 16+2*labelHeight, labelWidth, labelHeight);
        highScoreLabel.setForeground(Color.WHITE);
        highScoreLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));
        highScoreLabel.setText("" + highScore);

        scoreTextLabel.setBounds(menu.getWidth()/4*3+10, 19+3*labelHeight, labelWidth, labelHeight);
        scoreTextLabel.setForeground(Color.WHITE);
        scoreTextLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));

        scoreLabel.setBounds(menu.getWidth()/7*6-5, 19+3*labelHeight, labelWidth, labelHeight);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font(textFont, Font.PLAIN, fontSize));
        scoreLabel.setText("" + GameDisplay.getScore());
        // -Status-Anzeige-
    }

    private void addAll() {
        addThemes();
        menu.add(themeTextLabel);
        menu.add(themeLabel);
        menu.add(playerTextLabel);
        menu.add(playerLabel);
        menu.add(highScoreTextLabel);
        menu.add(highScoreLabel);
        menu.add(scoreTextLabel);
        menu.add(scoreLabel);
        menu.add(exit);
    }

    private void initializeBackground() {
        background.setBounds(0,0,menu.getWidth(), menu.getHeight());
        background.setIcon(getIcon("bg.png", menu.getWidth(), menu.getHeight()));
        menu.setContentPane(background);
    }

    private void initializeButtons() {

        // start Button
        startGame = new JButton("START GAME");
        startGame.setBounds(menu.getWidth()/3,menu.getHeight()/3,menu.getWidth()/3,menu.getHeight()/5);
        startGame.setBackground(buttonColor1);
        startGame.setForeground(Color.WHITE);
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
        backThemes.setBackground(buttonColor2);
        backThemes.setForeground(Color.WHITE);
        backThemes.addActionListener(getActionListenerBackThemes());

        backPlayers = new JButton("<<back");
        backPlayers.setBounds(10, 10, backWidth, backHeight);
        backPlayers.setFont(font);
        backPlayers.setBackground(buttonColor2);
        backPlayers.setForeground(Color.WHITE);
        backPlayers.addActionListener(getActionListenerBackPlayers());

        backMaps = new JButton("<<back");
        backMaps.setBounds(10, 10, backWidth, backHeight);
        backMaps.setFont(font);
        backMaps.setBackground(buttonColor2);
        backMaps.setForeground(Color.WHITE);
        backMaps.addActionListener(getActionListenerBackMaps());

        // -back Buttons-


        // exit Button
        exit.setBounds(10, menu.getHeight()-backHeight-45, backWidth, backHeight);
        exit.setFont(font);
        exit.setBackground(buttonColor1);
        exit.setForeground(Color.WHITE);
        exit.addActionListener(getActionListenerExit());
        // - exit Button-
    }


    private Icon getIcon(String image, int width, int height) {
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("/menu/" + image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);

        return new ImageIcon(img);
    }

    private Image getImage(String image) {
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("/menu/" + image));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }




    private void addPlayers(){
        players = new JButton[playerName.length];

        for (int i = 0; i < playerName.length; i++) {
            players[i] = new JButton(makeNameNice(playerName[i]));

            players[i].setBounds(menu.getWidth()/4,menu.getHeight()/10+i*(menu.getHeight()/12 + 5),menu.getWidth()/2,menu.getHeight()/12);
            players[i].setFont(new Font(textFont, Font.PLAIN, players[i].getHeight()/3));
            players[i].setBackground(buttonColor1);
            players[i].setForeground(Color.WHITE);
            players[i].addActionListener(getActionListenerPlayers(i));
            menu.add(players[i]);
            menu.getContentPane().setBackground(getBGColor());
            menu.repaint();
        }
        themeLabel.setText(makeNameNice(getGameTheme()));
        menu.add(backThemes);
        menu.repaint();
    }

    private void addThemes(){

        for (int i = 0; i < gameTheme.length; i++) {
            themes[i] = new JButton(makeNameNice(gameTheme[i]));

            themes[i].setBounds(menu.getWidth()/4,menu.getHeight()/10+i*(menu.getHeight()/12 + 5),menu.getWidth()/2,menu.getHeight()/12);
            themes[i].setFont(new Font(textFont, Font.PLAIN, themes[i].getHeight()/3));
            themes[i].setBackground(buttonColor1);
            themes[i].setForeground(Color.WHITE);

            themes[i].addActionListener(getActionListenerThemes(i));
            menu.add(themes[i]);
        }
        menu.repaint();
    }

    private void addMaps(){
        mapButton = new JButton[maps.length];

        for (int i = 0; i < maps.length; i++) {
            mapButton[i] = new JButton(makeNameNice(maps[i]));

            mapButton[i].setBounds(menu.getWidth()/4,menu.getHeight()/10+i*(menu.getHeight()/12 + 5),menu.getWidth()/2,menu.getHeight()/12);
            mapButton[i].setFont(new Font(textFont, Font.PLAIN, mapButton[i].getHeight()/3));
            mapButton[i].setBackground(buttonColor1);
            mapButton[i].setForeground(Color.WHITE);

            mapButton[i].addActionListener(getActionListenerMaps(i));
            menu.add(mapButton[i]);
        }
        menu.repaint();
    }



    public static String[] getPlayerSheetsInFolder() {
        return playerSheetsInFolder;
    }

    private ActionListener getActionListenerExit() {
        return e -> {
          System.exit(0);
        };
    }

    private ActionListener getActionListenerStart() {
        return e -> {
            new Thread(new GameLoop(new Game())).start();
            menu.dispose();
        };
    }

    private ActionListener getActionListenerBackThemes(){
        return e-> {
            for (JButton button : players) {
                menu.remove(button);
            }
            addThemes();
            menu.repaint();
            menu.remove(backThemes);
            menu.repaint();
        };
    }

    private ActionListener getActionListenerBackPlayers() {
        return e -> {
            for (JButton button : mapButton) {
                menu.remove(button);
            }
            menu.remove(backPlayers);
            menu.repaint();
            addPlayers();
        };
    }

    private ActionListener getActionListenerBackMaps() {
        return e -> {
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
            for (JButton button : themes) {
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
            for (JButton button : players) {
                menu.remove(button);
            }
            menu.remove(backThemes);
            playerLabel.setText(makeNameNice(getPlayerName()));
            addMaps();
            menu.add(backPlayers);
            menu.repaint();
        };
    }


    private ActionListener getActionListenerMaps(int in) {
        return e -> {
            name = in;
            for (JButton button : mapButton) {
                menu.remove(button);
            }
            menu.remove(backPlayers);
            playerLabel.setText(makeNameNice(getMapName()));
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


    public static JLabel getHighscore(){
        return highScoreLabel;
    }

    public static JLabel getThemeLabel() {
        return themeLabel;
    }

    public static JLabel getPlayerLabel() {
        return playerLabel;
    }

    public static JLabel getScore() {
        return scoreLabel;
    }

    public static String getGameTheme(){
        return gameTheme[theme];
    }

    public static String getPlayerName(){
        return playerName[name];
    }

    public static String getMapName(){
        return maps[map];
    }

    public static Color getBGColor(){
        return bgColor[theme];
    }


}