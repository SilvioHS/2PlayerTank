
package tankgame;

import java.awt.Image;
import java.util.Random;


public class PowerUp extends GameObject{
    
    boolean spawnable;
    
    PowerUp(Image img, int x, int y) {
        this.img = imgR.getSprite("Resources/powerup.png");
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.show = true;
        this.spawnable = true;
    }

    public void update(int w, int h) {
        y += speed;
        if (y >= h) {
            y = -100;
            x = Math.abs(gen.nextInt() % (w - 30));
        }
    }
    
    public void spawn(){
        if (spawnable){
            this.x = Math.abs(gen.nextInt() % (600 - 30));
            this.y = -100;
            this.show = true;
            spawnable = false;
        }  
    }
    
    public void detonate(){
        show = false;
    }
    
}