package tankgame;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Projectile extends GameObject{

    int type;
    Image next;
    
    Projectile(Image img, int x, int y, int rot, int vel, int t) {
        this.img = img;
        rotation = rot;
        velocity = vel;
        this.x = x;
        this.y = y;
        type = t;
        this.show = true;
        this.exploding = false;
        sizeX = sizeY = img.getHeight(obs);
    }

    public void update() {   
        //explosion effect
        if (exploding == true) {
            next = exp.nextFrame();
            if(next != null){
                this.img = next;
                setVelocity(0);
            } else if (next == null) {
                this.show = false;
                this.exploding = false;
                exp = null;
            } 
        } else {      
            x += (int)(Math.cos( (rotation)*(Math.PI/30) ) * velocity); 
            y -= (int)(Math.sin( (rotation)*(Math.PI/30) ) * velocity);
       }
    }
    
    public void draw(Graphics g, ImageObserver obs) {
        if ((show == true)&&(exploding == false)) {
            g.drawImage(img,                           //source image
                        x, y,                          //destination top left
                        x+sizeX, y+sizeY,              //destination bottom right
                        rotation*sizeX, 0,             //source top left
                        rotation*sizeX + sizeX, sizeY, //source bottom right
                        obs);                   
            this.obs = obs;
        } else if ((show == true)&&(exploding == true)) {
            g.drawImage(img, x, y, obs);        
        }
    }
    
    public void detonate(){ 
        if ((this.exploding == false)&&(this.type == 1)) {
            exp = new Explosion(this.x, this.y, 1);
            this.exploding = true;
        } else if ((this.exploding == false)&&(this.type == 2)) {
            exp = new Explosion(this.x, this.y, 2);
            this.exploding = true;
        }
    }
}