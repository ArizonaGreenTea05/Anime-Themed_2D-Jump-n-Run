package display;

import core.ScreenSize;
import menu.Menu;
import utils.FileLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LoadingScreen extends JFrame implements Runnable{
    private final JLabel background = new JLabel();
    private final JLabel loading = new JLabel("loading");
    private final Icon[] loadingIMGs = new Icon[12];

    public LoadingScreen(){
        setSize(ScreenSize.getWidth(),ScreenSize.getHeight());
        setTitle("...loading...");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);


        initializeBackground();

        initializeLoading();


        setVisible(true);
    }

    private void initializeBackground() {
        // same background image as menu
        background.setBounds(0,0,getWidth(), getHeight());
        background.setIcon(Menu.getBGImage());
        setContentPane(background);
    }

    private void initializeLoading() {
        // loading label with progress circle

        int width = getWidth()/4;
        int height = getHeight()/12;

        BufferedImage fullImg = (BufferedImage) FileLoader.loadImage("loading","/loading/");

        for (int i = 0; i < loadingIMGs.length; i++) {
            Image subImg = fullImg.getSubimage(
                    0,
                    i*500,
                    500,
                    500
            ).getScaledInstance(height, height, Image.SCALE_DEFAULT);
            setIconImage(subImg);
            loadingIMGs[i] = new ImageIcon(subImg);
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

    @Override
    public void run() {
        // animates loading circle
        while(true) {
            for (Icon image : loadingIMGs) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loading.setIcon(image);
            }
        }
    }
}
