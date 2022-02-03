package gfx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtils {
    public static Image loadImage(String filePath) {
        try {
            return ImageIO.read(ImageUtils.class.getResource(filePath));
        } catch (IOException e) {
            System.out.println("Could not load image from path: " + filePath);
        }
        return null;
    }

    public static Image loadImage(String filePath, int width, int height) {
        try {
            BufferedImage bImage = ImageIO.read(ImageUtils.class.getResource(filePath));
            return bImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            System.out.println("Could not load image from path: " + filePath);
        }
        return null;
    }
}