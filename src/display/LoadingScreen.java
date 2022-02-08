package display;

import core.ScreenSize;
import menu.Menu;
import utils.FileLoader;

import javax.swing.*;
import java.awt.*;

public class LoadingScreen extends JFrame implements Runnable{
    private final JLabel background = new JLabel();
    private final JLabel loading = new JLabel("loading");
    private final Icon[] loadingIMGs = new Icon[12];

    public LoadingScreen(){
        setSize(ScreenSize.getWidth(),ScreenSize.getHeight());
        setTitle("...loading...");
        setIconImage(FileLoader.loadImage("loading_0", "/loading/"));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);


        initializeBackground();

        initializeLoading();


        setVisible(true);
    }

    private void initializeLoading() {
        int width = getWidth()/4;
        int height = getHeight()/12;

        for (int i = 0; i < loadingIMGs.length; i++) {
            loadingIMGs[i] = FileLoader.loadIcon("loading_" + i, "/loading/", height, height);
        }

        loading.setOpaque(true);
        loading.setBounds(getWidth()/8*3,getHeight()/2-height/2,width,height);
        loading.setFont(new Font(Menu.getFont(), Font.PLAIN, height/2));
        loading.setBackground(Menu.getButtonColor());
        loading.setForeground(Menu.getTextColor());
        loading.setBorder(Menu.getBorder());
        loading.setIcon(loadingIMGs[0]);
        add(loading);
    }

    private void initializeBackground() {
        background.setBounds(0,0,getWidth(), getHeight());
        background.setIcon(Menu.getBGImage());
        setContentPane(background);
    }

    @Override
    public void run() {
        while(true) {
            for (int i = 0; i < loadingIMGs.length; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loading.setIcon(loadingIMGs[i]);
            }
        }}
}
