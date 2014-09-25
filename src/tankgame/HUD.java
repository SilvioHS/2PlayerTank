
package tankgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.*;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

public class HUD {
    
    Player p1, p2;
    Image [] healthIMG = new Image[5]; 
    Image p1livesIMG, p2livesIMG;
    Dimension winDim;
    Dimension scoreBord = new Dimension(300, 200);
            
    HUD (Player p1, Player p2, Dimension wDim){
        this.p1 = p1;
        this.p2 = p2;
        this.winDim = wDim;
        ImageReader imgR = new ImageReader();
        healthIMG[4] = imgR.getSprite("Resources/health.png");
        healthIMG[3] = imgR.getSprite("Resources/health1.png");
        healthIMG[2] = imgR.getSprite("Resources/health2.png");
        healthIMG[1] = imgR.getSprite("Resources/health3.png");
        healthIMG[0] = imgR.getSprite("Resources/health4.png");
        p1livesIMG = imgR.getSprite("Resources/tank1_size_experiment.png");
        p2livesIMG = imgR.getSprite("Resources/tank1_size_experiment.png");
    }
    
    public void update(Player p1, Player p2){
        
        this.p1 = p1;
        this.p2 = p2;
    }
    
    public void draw(Graphics g, ImageObserver obs){
        g.setColor(Color.yellow);
        g.drawString("P2 KILLS: " + p2.getScore(), 20, 20);
        //g.drawString("P2 HITS:  " + p2.getScore(), 20, 40);
        g.drawString("P1 KILLS: " + p1.getScore(), winDim.width - 140, 20);
        //g.drawString("P1 HITS:  " + p1.getScore(), winDim.width - 140, 40);
        
        int lifesheight = winDim.height-100;
        if(p1.getLives() == 3){
            g.drawImage(p1livesIMG, winDim.width-60, lifesheight, obs);
            g.drawImage(p1livesIMG, winDim.width-92, lifesheight, obs);
            g.drawImage(p1livesIMG, winDim.width-124, lifesheight, obs);
        } else if(p1.getLives() == 2){
            g.drawImage(p1livesIMG, winDim.width-60, lifesheight, obs);
            g.drawImage(p1livesIMG, winDim.width-92, lifesheight, obs);
        } else if(p1.getLives() == 1){
            g.drawImage(p1livesIMG, winDim.width-60, lifesheight, obs);
        }
        
        if(p2.getLives() == 3){
            g.drawImage(p2livesIMG, 20, lifesheight, obs);
            g.drawImage(p2livesIMG, 52, lifesheight, obs);
            g.drawImage(p2livesIMG, 84, lifesheight, obs);
        } else if(p2.getLives() == 2){
            g.drawImage(p2livesIMG, 20, lifesheight, obs);
            g.drawImage(p2livesIMG, 52, lifesheight, obs);
        } else if(p2.getLives() == 1){
            g.drawImage(p2livesIMG, 20, lifesheight, obs);
        }
    }
    
    public void drawHealthBar(Graphics g, ImageObserver obs){
        if((p1.getHealth() > 0)&&(p1.getHealth() <= 4)&&(p1.dead == false)){
            g.drawImage(healthIMG[p1.getHealth()], p1.getX(), p1.getY() - 5, 20, 5, obs);
        }
        
        if ((p2.getHealth() > 0)&&(p2.getHealth() <= 4)&&(p2.dead == false)){
            g.drawImage(healthIMG[p2.getHealth()], p2.getX(), p2.getY()-5, 20, 5, obs);
        }
    }
    
    public void drawPlayerMarker(Graphics minimap, Player p){
        if (p.dead == false) {
            minimap.drawString("P"+p.getPlayerNumber(), p.getX()/6, p.getY()/6);
        }
    }
    
    
    public void drawScoreBoard(Graphics g, ImageObserver obs){
        g.setColor(Color.red);
        g.drawRect((winDim.width/2)-(scoreBord.width/2), 
                  (winDim.height/2)-(scoreBord.height/2),
                   scoreBord.width, 
                   scoreBord.height);
        g.drawString("GAME OVER" , winDim.width/2, (winDim.height/2)-20);
        g.drawString("P2 KILLS: " + p2.getScore(), winDim.width/2, (winDim.height/2)+10);
        g.drawString("P1 KILLS: " + p1.getScore(), winDim.width/2, (winDim.height/2)+30);
    }
}
