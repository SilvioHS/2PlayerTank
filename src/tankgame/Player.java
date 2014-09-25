
package tankgame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Observer;
import java.util.ArrayList;


public class Player extends GameObject implements Observer{
    
    int score, lives, playerspeed, firemode, respawntime;
    Image original, next;
    Image shell = imgR.getSprite("Resources/Shell_strip60.png");
    Image rocket = imgR.getSprite("Resources/Rocket_strip60.png");
    Sound shootSFX = new Sound("Resources/m82_50cal.wav");
    Sound shootmissileSFX = new Sound("Resources/fireMissile.wav");
    boolean respawning;
    
    Player(Image img, int x, int y, int playerNum, Dimension winDim) {
            playerNumber = playerNum;
            this.img = img;
            this.mapDim = winDim;
            original = img;
            this.x = x;
            this.y = y;
            //using image strip
            sizeY = sizeX = img.getHeight(obs);
            rotation = 15;
            firemode = 1;
            score = 0;
            health = 4;
            lives = 3;
            count = 0;
            dead = false;
            bullets = new ArrayList();
    }
    
    
    public void update(int w, int h){
        //movement
        this.rotation += rDirection;
        if(this.rotation > 60){
            this.rotation = 0;
        } else if (this.rotation < 0){
            this.rotation = 60;
        } 
        
        this.x -= Math.cos( (rotation)*(Math.PI/30) ) * velocity;
        this.y += Math.sin( (rotation)*(Math.PI/30) ) * velocity;
        
        count++;
        
        //explosion effect
        if (exploding) {
            next = exp.nextFrame();
            if(next != null){
                this.img = exp.nextFrame();
                setVelocity(0);
            } else if (dead == true) {
                this.show = false;
            } else if(dead == false){
                this.exploding = false;
                this.img = original;
                exp = null;
                respawn();
            }
            
            
        //blinking effect
        } else if (respawning) {
            count++;
            respawntime++;
            if (respawntime < 60) {
                if ((count > 6) && (show == true)) {
                    show = false;
                    count = 0;
                } else if ((count > 6) && (show == false)) {
                    show = true;
                    count = 0;
                }
            } else {
                respawning = false;
                show = true;
            }
        }
        
        //bounds so player does not go off screen
        if(x <= 0)
            x = 0;
        if(x >= (w - sizeX))
            x = (w - sizeX);
        if(y <= 0)
            y = 0;
        if(y >= (h - sizeY))
            y = (h - sizeY);
    }

    public void hit() {
        if (count > 20) {

            if (health >= 1) {
                health--;
            }
            
            if (health == 0) {
                detonate();
            }
            count = 0;
        }
    }

    public void detonate() {
        if (this.exploding == false) {
            exp = new Explosion(this.x, this.y, 2);
            this.exploding = true;
            lives--;
        }
        if (lives == 0){
            dead = true;
        }
    }


    public void draw(Graphics g, ImageObserver obs) {
        if ((show == true)&&(exploding == false)) {
            int index;
            if(rotation == 60){
                index = 0;
            } else {
                index = rotation;
            }

            g.drawImage(img,                           //source image
                        x, y,                          //destination top left
                        x+sizeX, y+sizeY,              //destination bottom right
                        index*sizeX, 0,             //source top left
                        index*sizeX + sizeX, sizeY, //source bottom right
                        obs);                   
            this.obs = obs;
        }
        if ((show == true)&&(exploding == true)){
            g.drawImage(img, x, y, obs);
            this.obs = obs;
        }
    }

    
    
    public void respawn(){
        health = 4;
        respawning = true;
        count = 0;
        respawntime = 0;
        if(playerNumber == 1){
            x = (mapDim.width/10)*9;
            y = mapDim.height/10;
        } else if(playerNumber == 2){
            x = mapDim.width/10;
            y = (mapDim.height/10)*9;
        }
    }
    
    
    
    public void shoot() {
        if (this.show) {
            int xoffset =  this.x + 20 + (int)(Math.cos( (rotation)*(Math.PI/30) ) * 24);
            int yoffset =  this.y + 20 + (int)(Math.sin( (rotation)*(Math.PI/30) ) * -24);
            if (firemode == 1) {               
                Projectile pb = new Projectile(shell, xoffset, yoffset, rotation, 15, firemode);
                bullets.add(pb);
                shootSFX.play();
            } else if (firemode == 2){
                
                Projectile pb = new Projectile(rocket, xoffset, yoffset, rotation, 10, firemode);
                bullets.add(pb);
                shootmissileSFX.play();
            }
        }

    }


    public void upgrade(){
        firemode = 2;
    }
    
    public void score(){
        score++;
    }
    
    public int getScore(){
        return score/2;
    }
    
    public int getHealth(){
        return health;
    }
    
    public int getLives(){
        return lives;
    }
    public int getPlayerNumber(){
        return playerNumber;
    }

    public ArrayList getBullets() {
        return bullets;
    }

}
