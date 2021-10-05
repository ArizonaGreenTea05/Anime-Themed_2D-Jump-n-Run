package menu;

import game.Game;
import game.GameLoop;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Menu {
    public static final String[] playerNameAoT = new String[] {"levi_ackerman", "mikasa_ackerman", "sasha_braus"};
    public static final String[] playerNameJojo = new String[] {"dio", "kakyoin_noriaki"};

    public static String[] playerName;

    private static int name;
    public static final String[] gameTheme = new String[] {"attack_on_titan"};
    private static int theme;
    private static final Color[] bgColor = new Color[]{(new Color(70, 90, 120))};

    private static final String[] playerSheetsInFolder = new String[] {"stand.png", "walk.png"};

    private final JFrame menu=new JFrame("Menu");
    private final JButton startGame;
    private static JButton[] players;
    private final JButton[] themes = new JButton[gameTheme.length];
    private final JButton backPlayers;
    private final JButton backThemes;
    private final int width, height;

    public Menu(int width, int height){
        this.width = width;
        this.height = height;

        menu.setSize(width/2,height/2);
        menu.setDefaultCloseOperation(EXIT_ON_CLOSE);
        menu.getContentPane().setBackground(new Color(23, 139, 221));
        menu.setResizable(false);
        menu.setLayout(null);
        menu.setLocationRelativeTo(null);


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


        addThemes();

        menu.setVisible(true);
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
        menu.add(backThemes);
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

    private ActionListener getActionListenerStart() {
        return e -> {
            new Thread(new GameLoop(new Game(width, height))).start();
            menu.dispose();
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

    private static void setPlayerName(int i){
        if(i==0) playerName = playerNameAoT;
        if(i==1) playerName = playerNameJojo;
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