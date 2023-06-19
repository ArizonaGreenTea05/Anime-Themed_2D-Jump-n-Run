package design;

import javax.swing.*;
import java.awt.*;

public class Theme {
    public Color buttonColor;
    public Color backButtonColor;
    public Color bottomButtonColor;
    public Color labelColor;
    public Color textColor;
    public Icon backgroundImage;

    public Theme(Color buttonColor, Color backButtonColor, Color bottomButtonColor, Color labelColor, Color textColor, Icon backgroundImage)
    {
        this.buttonColor = buttonColor;
        this.backButtonColor = backButtonColor;
        this.bottomButtonColor = bottomButtonColor;
        this.labelColor = labelColor;
        this.textColor = textColor;
        this.backgroundImage = backgroundImage;
    }
}
