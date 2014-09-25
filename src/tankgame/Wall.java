
package tankgame;

import java.awt.Image;

public class Wall extends GameObject {
    
    
    Wall(Image img, int x, int y, int type){
        this.img = img;
        this.x = x;
        this.y = y;
        this.show = true;
        sizeX = img.getWidth(obs);
        sizeY = img.getHeight(obs);
        if     (type == 1){
            this.health = 2;
        } else if (type == 2){
            this.health = 5;
        }
    }
    
    
    public void hit() {

            if (health >= 1) {
                health--;
            }
            
            if (health == 0) {
                detonate();
            }
    }
    
    
    public void detonate(){
        show = false;
    }
}
