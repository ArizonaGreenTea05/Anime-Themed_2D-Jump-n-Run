package menu;

import game.Game;
import game.GameLoop;
import gfx.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Menu {

    public static String[] playerName;


    private static final String[] playerSheetsInFolder = new String[] {"stand.png", "walk.png"};

    private JButton exit = new JButton("EXIT");

    private final JFrame menu=new JFrame("Menu");
    private final JButton startGame;
    private JButton[] players;
    private final JButton[] themes = new JButton[gameTheme.length];
    private final JButton backPlayers;
    private final JButton backThemes;
    private final int width, height;
    private JLabel background = new JLabel();

    private static JLabel themeLabel = new JLabel("Theme:");
    private static JLabel playerLabel = new JLabel("Character:");

    /**
     * declaration of themes, player names and colors
     **/
    public static final String[] playerNameAoT = new String[] {"levi_ackerman", "mikasa_ackerman", "sasha_braus"};
    //public static final String[] playerNameJojo = new String[] {"dio", "kakyoin_noriaki"};
    private static int name;
    public static final String[] gameTheme = new String[] {"attack_on_titan"};
    private static int theme;
    private static final Color[] bgColor = new Color[]{(new Color(70, 90, 120))};

    private static void setPlayerName(int i){
        if(i==0) playerName = playerNameAoT;
        //if(i==1) playerName = playerNameJojo;
    }


    public Menu(int width, int height){
        this.width = width;
        this.height = height;

        menu.setSize(width/2,height/2);
        menu.setDefaultCloseOperation(EXIT_ON_CLOSE);
        menu.getContentPane().setBackground(new Color(23, 139, 221));
        menu.setResizable(false);
        menu.setLayout(null);
        menu.setLocationRelativeTo(null);

        background.setBounds(0,0,menu.getWidth(), menu.getHeight());
        background.setIcon(getBackground());


        startGame = new JButton("START GAME");
        startGame.setBounds(menu.getWidth()/3,menu.getHeight()/3,menu.getWidth()/3,menu.getHeight()/5);
        startGame.addActionListener(getActionListenerStart());


        // min Größe dass Text angezeigt werden kann: 76*10
        // -> wenn relative Maße größer werden diese benutzt, wenn nicht die min Maße
        int backWidth = 76;
        int backHeight = 10;
        if (menu.getHeight()/20 > backWidth){
            backWidth = menu.getHeight()/20;
        }
        if(menu.getHeight()/20 > backHeight){
            backHeight = menu.getHeight()/20;
        }

        backThemes = new JButton("<<back");
        backThemes.setBounds(10, 10, backWidth, backHeight);
        backThemes.addActionListener(getActionListenerBackThemes());

        backPlayers = new JButton("<<back");
        backPlayers.setBounds(10, 10, backWidth, backHeight);
        backPlayers.addActionListener(getActionListenerBackPlayers());


        exit.setBounds(10, menu.getHeight()-backHeight-45, backWidth, backHeight);
        exit.setFont(new Font("", Font.PLAIN, backHeight/3*2));
        exit.addActionListener(getActionListenerExit());

        int labelHeight = menu.getHeight()/35;
        int labelWidth = menu.getWidth()/4;
        themeLabel.setBounds(menu.getWidth()/4*3+10, 10, labelWidth, labelHeight);
        themeLabel.setForeground(Color.WHITE);
        themeLabel.setFont(new Font("", Font.PLAIN, labelHeight));
        playerLabel.setBounds(menu.getWidth()/4*3+10, 13+labelHeight, labelWidth, labelHeight);
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setFont(new Font("", Font.PLAIN, labelHeight));


        menu.add(background);

        addThemes();

        menu.add(themeLabel);
        menu.add(playerLabel);
        menu.add(exit);
        menu.setVisible(true);
    }




    private Icon getBackground() {
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("/menu/bg.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image scaledImage = img.getScaledInstance(menu.getWidth(), menu.getHeight(), Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(scaledImage);

        return icon;
    }




    private void addPlayers(){
        players = new JButton[playerName.length];
        for (int i = 0; i < playerName.length; i++) {
            players[i] = new JButton(makeNameNice(playerName[i]));

            players[i].setBounds(menu.getWidth()/4,menu.getHeight()/10+i*(menu.getHeight()/12 + 5),menu.getWidth()/2,menu.getHeight()/12);
            players[i].addActionListener(getActionListenerPlayers(i));
            menu.add(players[i]);
            menu.getContentPane().setBackground(getBGColor());
            menu.repaint();
        }
        themeLabel.setText("Theme:       " + makeNameNice(getGameTheme()));
        menu.add(backThemes);
        menu.repaint();
    }

    private void addThemes(){
        for (int i = 0; i < gameTheme.length; i++) {
            themes[i] = new JButton(makeNameNice(gameTheme[i]));

            themes[i].setBounds(menu.getWidth()/4,menu.getHeight()/10+i*(menu.getHeight()/12 + 5),menu.getWidth()/2,menu.getHeight()/12);
            themes[i].addActionListener(getActionListenerThemes(i));
            menu.add(themes[i]);
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
            new Thread(new GameLoop(new Game(width, height))).start();
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
            menu.remove(startGame);
            menu.remove(backPlayers);
            menu.repaint();
            addPlayers();
        };
    }

    private ActionListener getActionListenerThemes(int in){
        return e -> {
            theme = in;
            setPlayerName(theme);
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
            playerLabel.setText("Character:  " + makeNameNice(getPlayerName()));
            menu.add(backPlayers);
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

    public static JLabel getThemeLabel(){
        return themeLabel;
    }

    public static JLabel getPlayerLabel(){
        return playerLabel;
    }


    public static String getGameTheme(){
        return gameTheme[theme];
    }

    public static String getPlayerName(){
        return playerName[name];
    }


    public static Color getBGColor(){
        return bgColor[theme];
    }


}