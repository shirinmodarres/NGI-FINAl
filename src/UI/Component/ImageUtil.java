package UI.Component;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {

        public static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
            g2d.dispose();
            return resizedImage;
        }

}
