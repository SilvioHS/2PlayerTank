
package tankgame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;



public class Map{
    
    int sizeXblocks, sizeYblocks;
    
    String mapName;
    
    BufferedReader mapInput = null;
    
    ImageReader imgR = new ImageReader();
    ImageObserver obs;
    ArrayList wallArray;
    int blockSize = 32;
    BufferedImage mapData;
    Image imgW1 = imgR.getSprite("Resources/Wall1.png");
    Image imgW2 = imgR.getSprite("Resources/Wall2.png");
    Image powerup = imgR.getSprite("Resources/powerup.png");
    PowerUp pu;
    
    Map(String inputFile, int x, int y){
        mapName = inputFile;
        sizeXblocks = x;
        sizeYblocks = y;
        
        //create buffered image
        mapData = new BufferedImage(sizeXblocks, sizeYblocks, Image.SCALE_DEFAULT);
        //draw data image to buffered image
        mapData.getGraphics().drawImage(imgR.getSprite(inputFile), 0, 0, obs);
         
        wallArray = new ArrayList(sizeXblocks*sizeYblocks);
        createMapArray(mapData);
    }

    public void createMapArray(BufferedImage dataimage) {
        int width = dataimage.getWidth();
        int height = dataimage.getHeight();
        int type1colorID = -16777216;
        int type2colorID = -12629812;

        //adds wall objects to wallArray depending on the value of each
        //pixel in the dataimage.
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                //System.out.println("rgb int: " +  dataimage.getRGB(row, col) );
                if(dataimage.getRGB(row, col) == type1colorID){
                    wallArray.add( new Wall(imgW1, row * blockSize, col * blockSize, 1));
                } else if(dataimage.getRGB(row, col) == type2colorID){
                    wallArray.add( new Wall(imgW2, row * blockSize, col * blockSize, 2));
                }
            }
        }
        pu = new PowerUp(powerup, sizeXblocks*blockSize/2, sizeYblocks*blockSize/2);
    }
    
    public void draw(Graphics g, ImageObserver obs) {
        Wall w;
        for (int i = 0; i < wallArray.size(); i++) {
            w = (Wall) wallArray.get(i);
            if ((w != null)&&(w.show == true)) {
                g.drawImage(w.getImage(), w.getX(), w.getY(), obs);
            }
        }
        w = null;
        if ((pu.show == true)) {
                pu.draw(g, obs);
        }
        
        this.obs = obs;
    }
    
    public ArrayList getWalls() {
        return wallArray;
    }
    public PowerUp getPowerUp(){
        return pu;
    }
}
