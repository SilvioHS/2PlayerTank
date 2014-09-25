
package tankgame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Explosion{
    
    int x, y, w, h;
    int type; // 1 = small, 2 = large
    Image explodeStrip;
    BufferedImage subimage;
    ArrayList explosion;
    int counter;
    ImageReader imgR = new ImageReader();

    Explosion(int x, int y, int type){
        
        //small explosion
        if(type == 1){    
            explodeStrip = imgR.getSprite("Resources/Explosion_small_strip6.png");
            w = explodeStrip.getWidth(null);
            h = explodeStrip.getHeight(null);
        //large explosion
        } else if (type == 2){
            explodeStrip = imgR.getSprite("Resources/Explosion_large_strip7.png");
            w = explodeStrip.getWidth(null);
            h = explodeStrip.getHeight(null);
        }

        explosion = new ArrayList();
        this.counter = 0;
        
        for (int i = 0; i < (w / h); i++) {
            for (int k = 0; k < 4; k++) {
                subimage = new BufferedImage(h, h, BufferedImage.TYPE_4BYTE_ABGR);
                subimage.getGraphics().drawImage(explodeStrip, 0, 0, h ,h, i*h, 0, (i*h)+h, h, null);
                explosion.add(this.counter++,subimage);
                //System.out.println("ExplodeFrame @  i = " + i);
            }
        }
        this.counter--;
        this.x = x;
        this.y = y;
    }

    public Image nextFrame() {
        BufferedImage next;
        System.out.println("Explosion Array size: " + explosion.size());
        System.out.println("counter: " + counter);
        if (this.counter >= 0) {
            next = (BufferedImage) explosion.remove(this.counter);
            this.counter--;
            return next;
        } else  {
            this.counter = 0;
            return null;
        }
    }
}
