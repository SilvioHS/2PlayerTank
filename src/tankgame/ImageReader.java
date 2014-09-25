
package tankgame;

import java.awt.*;
import java.net.URL;
import javax.swing.*;



public class ImageReader{
    
    public Image getSprite(String name) {
        URL url = TankGame.class.getResource(name);
        ImageIcon newImg = new ImageIcon(url);
        Image img = newImg.getImage();
        return img;
    }
}
