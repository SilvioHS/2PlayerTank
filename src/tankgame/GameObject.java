package tankgame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;


public abstract class GameObject{

    Image img;
    int x, y, sizeX, sizeY, xDirection, yDirection, rDirection, rotation, velocity, health, playerNumber; //counter?
    int count = 0;
    int speed = 1;
    ImageObserver obs;
    Dimension mapDim;
    static Random gen = new Random(1234567);
    boolean show = true;
    boolean exploding = false;
    boolean dead;
    static ImageReader imgR = new ImageReader();
    Explosion exp;
    ArrayList bullets;
    Sound explode_largeSFX = new Sound("Resources/Explosion_large.wav");
    Sound explode_smallSFX = new Sound("Resources/Explosion_small.wav");
    Sound hitSFX = new Sound("Resources/hitsound.wav");
    
    
    public void draw(Graphics g, ImageObserver obs) {
        if (show == true) {
            g.drawImage(img, x, y, obs);
            this.obs = obs;
        }
    }

    public boolean collision(int x, int y, int w, int h) {
        if ((y + h > this.y) && (y < this.y + sizeY)) { // very simple test for showing an idea -- this only checks y forwarding direction
            if ((x + w > this.x) && (x < this.x + sizeX)) {
                return true;
            }
        }
        return false;
    }
    
    
    //arg is event
    public void update(Observable obj, Object arg) {
        GameEvents ge = (GameEvents) arg;
        
        //OTHER GAME EVENTS
        if (ge.type == 0) {
            String msg = (String) ge.event;
            if (msg.equals("Explosion_player")) {
                GameObject gO = ge.gamObject;
                gO.detonate();
                System.out.println("Explosion_player");
                explode_largeSFX.play();

            } else if (msg.equals("Explosion_projectile")) {
                GameObject gO = ge.gamObject;
                gO.detonate();
                System.out.println("Explosion_projectile");
                explode_smallSFX.play();
                
            } else if (msg.equals("Damage_Player")) {
                Player gO = (Player) ge.gamObject;
                gO.hit();
                System.out.println("Damage_Player");
                hitSFX.play();
                
            } else if (msg.equals("Damage_Wall")) {
                Wall gO = (Wall) ge.gamObject;
                gO.hit();
                System.out.println("Damage_Wall");
                //hitSFX.play();
                
            } else if (msg.equals("Stop_Player")) {
                Player gO = (Player) ge.gamObject;
                gO.setVelocity(0);
                System.out.println("Stop_Player");
                
            } else if (msg.equals("Score")) {
                Player gO = (Player) ge.gamObject;
                gO.score();
                System.out.println("Score");
                
            } else if (msg.equals("Upgrade")) {
                Player gO = (Player) ge.gamObject;
                gO.upgrade();
                System.out.println("Upgrade");
            }
        } else

                
        //PLAYER 1 CONTROLS
        if ((playerNumber == 1)&&((ge.type == 1)||(ge.type == 2))) {
            //key press type
            KeyEvent e = (KeyEvent) ge.event;
            if (ge.type == 1) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    System.out.println("Left_Press");
                    setRotation(1);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    System.out.println("Right_Press");
                    setRotation(-1);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    System.out.println("Up_Press");
                    setVelocity(-5);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    System.out.println("Down_Press");
                    setVelocity(5);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    System.out.println("Fire");
                    if(dead == false){
                        shoot();
                    }
                }
            //key release type
            } else if (ge.type == 2) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    System.out.println("Left_Press");
                    setRotation(0);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    System.out.println("Right_Press");
                    setRotation(0);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    System.out.println("Up_Press");
                    setVelocity(0);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    System.out.println("Down_Press");
                    setVelocity(0);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    System.out.println("Fire_Release");
                }
            }
            
        //PLAYER 2 CONTROLS
        } else if ((playerNumber == 2)&&((ge.type == 1)||(ge.type == 2))) {
            //key press type
            KeyEvent e = (KeyEvent) ge.event;
            if (ge.type == 1) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    System.out.println("Left_Press");
                    setRotation(1);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    System.out.println("Right_Press");
                    setRotation(-1);
                } else if (e.getKeyCode() == KeyEvent.VK_W) {
                    System.out.println("Up_Press");
                    setVelocity(-5);
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    System.out.println("Down_Press");
                    setVelocity(5);
                } else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    System.out.println("Fire");
                    if(dead == false){
                        shoot();
                    }
                }
            //key release type
            } else if (ge.type == 2) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    System.out.println("Left_Press");
                    setRotation(0);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    System.out.println("Right_Press");
                    setRotation(0);
                } else if (e.getKeyCode() == KeyEvent.VK_W) {
                    System.out.println("Up_Press");
                    setVelocity(0);
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    System.out.println("Down_Press");
                    setVelocity(0);
                } else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    System.out.println("Fire_Release");
                }
            }
        }
    }
    
    
    public abstract void detonate();
    
    
    public void shoot(){
    }
    
    public void hit() {
    }
    
    public void setXDirection(int xdir){
        xDirection = xdir;
    }
    
    public void setYDirection(int ydir){
        yDirection = ydir;
    }
    
    public void setRotation(int rot){
        rDirection = rot;
    }
    
    public void setVelocity(int vel){
        velocity = vel;
    }
    
    public Image getImage(){
        return img;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public int getWidth(){
        return this.sizeX;
    }
    
    public int getHeight(){
        return this.sizeY;
    }
}
