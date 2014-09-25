
package tankgame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.*;


public class Background {
    
    ImageReader imgR;
    Image tile;
    int TileWidth, TileHeight, NumberX, NumberY, move, speed;
    ImageObserver observer;

    public Background(int spd, Image bgTile, ImageObserver obs) {
        observer = obs;
        imgR = new ImageReader();
        tile = bgTile;
        TileWidth = tile.getWidth(obs);
        TileHeight = tile.getHeight(obs);
        move = 0;
        speed = spd;
    }
    
    //drawBackGroundWithTileImage
    public void drawBackground(int w, int h, Graphics2D g2){
        
        NumberX = (int) (w / TileWidth);
        NumberY = (int) (h / TileHeight);
        
        for (int i = -1; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {         

                g2.drawImage(tile, j * TileWidth, i * TileHeight + (move % TileHeight), TileWidth, TileHeight, observer);
            }
        }
    }
    
    public void updateBackground(){
        move += speed;
    }
}
