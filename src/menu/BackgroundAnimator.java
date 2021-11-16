package menu;

import javax.swing.*;

public class BackgroundAnimator implements Runnable{

    private String background;
    private Icon[] bgIcon;

    private BackgroundAnimator(){
        /*
        this.background = Menu.getBackground();
        for (int i = 1; i <= 12; i++) {
            bgIcon[i] = Menu.getIcon(background + "/" + i + ".png" ,Menu.getWidth(),Menu.getHeight());
        }
        for (int i = 0; true; i++) {
            Menu.background.setIcon(bgIcon[i]);
            Menu.menu.setContentPane(Menu.background);
            if(i == bgIcon.length-1) i = 0;
        }

         */
    }


    @Override
    public void run() {
        new BackgroundAnimator();
    }
}
