
package tankgame;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;




public class TankGame extends JApplet implements Runnable {

    private Thread thread;
    public static Dimension mapDim = new Dimension(1600, 1600);
    String mapName = "Resources/recyclemap.png";
    public static Dimension winDim = new Dimension(1440, 900);
    Graphics2D g2;
    private BufferedImage bimg, p1View, p2View, hudView, miniMapView, screenBuffer;
    ImageReader imgR;
    ImageObserver observer;
    static Background b;
    static Map m;
    HUD hud;
    static GameEvents gameEvents;
    Player P1, P2;
    KeyListener key;
    Projectile pb1, pb2;
    ArrayList bullets1, bullets2, walls;
    Wall wall;
    PowerUp pUp;
    
    Image player1, player2, bgTile;
    boolean gameOver;
    
    
    
    
    public void init() {
        gameOver = false;
        observer = this;
        gameEvents = new GameEvents();
        imgR = new ImageReader();
        
        m = new Map(mapName, mapDim.width/32, mapDim.height/32);
        bgTile = imgR.getSprite("Resources/Background.png");
        walls = new ArrayList();
        
        b = new Background(0, bgTile, observer);
        
        
        miniMapView = new BufferedImage(winDim.width/6, winDim.width/6, BufferedImage.TYPE_INT_BGR);
        screenBuffer = new BufferedImage(winDim.width, winDim.height, BufferedImage.TYPE_INT_BGR);
        hudView =  new BufferedImage(winDim.width, winDim.height, BufferedImage.TYPE_INT_ARGB);
        
        player1 = imgR.getSprite("Resources/Tank1_strip60.png");
        P1 = new Player(player1, (mapDim.width/10)*9, mapDim.height/10, 1, mapDim);
        gameEvents.addObserver(P1);
        
        player2 = imgR.getSprite("Resources/Tank2_strip60.png");
        P2 = new Player(player2, mapDim.width/10, (mapDim.height/10)*9, 2, mapDim);
        gameEvents.addObserver(P2);
        
        hud = new HUD(P1, P2, winDim);
        
        key = new KeyControl(gameEvents);
        this.addKeyListener(key);
        this.setFocusable(true);
    }
    
    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }
    
    public void run() {

        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();
            try {
                thread.sleep(25);
            } catch (InterruptedException e) {
                break;
            }
        }
        thread = null;
    }

    public void paint(Graphics g) {

        g2 = createGraphics2D(mapDim.width, mapDim.height);
        drawMap(mapDim.width, mapDim.height, g2);
        drawObjects(mapDim.width, mapDim.height, g2);
        g2.dispose();
        
        detectCollisions();

        miniMapView.getGraphics().drawImage(bimg.getScaledInstance(winDim.width/6, winDim.width/6, BufferedImage.SCALE_DEFAULT),0,0,null);
        hud.drawPlayerMarker(miniMapView.getGraphics(),P1);
        hud.drawPlayerMarker(miniMapView.getGraphics(),P2);
        
        p1View = bimg.getSubimage(getViewPlaceX(P1.getX()), getViewPlaceY(P1.getY()), (winDim.width/2), winDim.height);
        p2View = bimg.getSubimage(getViewPlaceX(P2.getX()), getViewPlaceY(P2.getY()), (winDim.width/2), winDim.height);
        
        
        screenBuffer.getGraphics().drawImage(p1View, (winDim.width/2)+1, 0, this);
        screenBuffer.getGraphics().drawImage(p2View, 0, 0, this);
        screenBuffer.getGraphics().drawImage(miniMapView, (winDim.width/12)*5, winDim.height-(winDim.height/3), this);
        drawHUD(winDim.width, winDim.height, screenBuffer.getGraphics());
        if(isGameOver()){
            hud.drawScoreBoard(screenBuffer.getGraphics(), observer);
        }
        g.drawImage(screenBuffer, 0, 0, this);
        
    }
    
    public int getViewPlaceX(int x){
        int viewoffset = (winDim.width/4)-32;
        int placement = x - viewoffset;
        //bounds so view does not go off the map
        if(placement <= 0){
            return 0;
        } else if ((placement+(winDim.width/2)) >= mapDim.width){
            return mapDim.width-(winDim.width/2);
        } else {
            return placement;
        }
    }
    
    public int getViewPlaceY(int y){
        int viewoffset = 288;
        int placement = y - viewoffset;
        //bounds so view does not go off the map
        if(placement <= 0){
            return 0;
        } else if ((placement+(winDim.height)) >= mapDim.height){
            return mapDim.width-(winDim.height);
        } else {
            return placement;
        }
    }
    
    public void drawMap(int w, int h, Graphics2D g2){
        b.drawBackground(w, h, g2);
        m.draw(g2,observer);
    }
    
    

    public void drawObjects(int w, int h, Graphics2D g2) {
        
        //draw health bars
        hud.drawHealthBar(g2, observer);
        
        //draw Player1
        if (!P1.dead) {
            P1.update(w, h);
            P1.draw(g2, this);
        }
        
        bullets1 = P1.getBullets();

        //draw P1 bullets
        for (int k = 0; k < bullets1.size(); k++) {
            pb1 = (Projectile) bullets1.get(k);
            if (pb1.show) {
                pb1.update();
                pb1.draw(g2, this);
            } else {
                //remove bullet from list if show == false
                bullets1.remove(b);
            }
        }
        
        
        //draw Player2
        if (!P2.dead) {
            P2.update(w, h);
            P2.draw(g2, this);
        }
        
        bullets2 = P2.getBullets();

        //draw P2 bullets
        for (int k = 0; k < bullets2.size(); k++) {
            pb2 = (Projectile) bullets2.get(k);
            if (pb2.show) {
                pb2.update();
                pb2.draw(g2, this);
            } else {
                //remove bullet from list if show == false
                bullets2.remove(b);
            }
        }
    }
    
    
    public void drawHUD(int w, int h, Graphics g1){
        hud.update(P1, P2);
        hud.draw(g1, observer);
    }
    
    
    
    public void detectCollisions(){
        
        //player - wall
        walls = m.getWalls();
        for(int i = 0; i < walls.size(); i++){
            wall = (Wall)walls.get(i);
            if((wall.show == true) && wall.collision(P1.getX(), P1.getY(), P1.getWidth(), P1.getHeight())){
                gameEvents.setValue("Stop_Player", P1);
            } else if((wall.show == true) &&(wall.collision(P2.getX(), P2.getY(), P2.getWidth(), P2.getHeight()))){
                gameEvents.setValue("Stop_Player", P2);
            }
        }
        pUp = m.getPowerUp();
        if((pUp.show == true) && pUp.collision(P1.getX(), P1.getY(), P1.getWidth(), P1.getHeight())){
                gameEvents.setValue("Upgrade", P1);
            } else if((pUp.show == true) &&(wall.collision(P2.getX(), P2.getY(), P2.getWidth(), P2.getHeight()))){
                gameEvents.setValue("Upgrade", P2);
       }
        
        
        //player1 - projectile
        bullets2 = P2.getBullets();
        for(int i = 0; i < bullets2.size(); i++){
            pb2 = (Projectile) bullets2.get(i);
            if((P1.show) && (!P1.exploding) && (!pb2.exploding) && (pb2.show) && pb2.collision(P1.getX(), P1.getY(), P1.getWidth(), P1.getHeight())){
                gameEvents.setValue("Damage_Player", P1);
                if(P1.exploding){
                    gameEvents.setValue("Score", P2);
                }
                gameEvents.setValue("Explosion_projectile", pb2);
            }
        }
        
        //player2 - projectile
        bullets1 = P1.getBullets();
        for(int i = 0; i < bullets1.size(); i++){
            pb1 = (Projectile) bullets1.get(i);
            if((P2.show) && (!P2.exploding) && (!pb1.exploding) && (pb1.show) && pb1.collision(P2.getX(), P2.getY(), P2.getWidth(), P2.getHeight())){
                gameEvents.setValue("Damage_Player", P2);
                if(P2.health == 0){
                    gameEvents.setValue("Score", P1);
                }
                gameEvents.setValue("Explosion_projectile", pb1);
            }
        }
        
        //projectile - wall
        for (int i = 0; i < walls.size(); i++) {
            wall = (Wall)walls.get(i);
            for (int j = 0; j < bullets2.size(); j++) {
                pb2 = (Projectile) bullets2.get(j);
                if ((wall.show) && (!wall.exploding) && (pb2.show) && pb2.collision(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight())) {
                    gameEvents.setValue("Explosion_projectile", pb2);
                    gameEvents.setValue("Damage_Wall", wall);
                }
            }
            for (int j = 0; j < bullets1.size(); j++) {
                pb1 = (Projectile) bullets1.get(j);
                if ((wall.show) && (!wall.exploding) && (pb1.show) && pb1.collision(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight())) {
                    gameEvents.setValue("Explosion_projectile", pb1);
                    gameEvents.setValue("Damage_Wall", wall);
                }
            }
        }
    }

    public boolean isGameOver(){
        if((P1.dead)||(P2.dead)){
            return true;
        } else {
            return false;
        }
    }
    
    
    public Graphics2D createGraphics2D(int w, int h) {
        g2 = null;
        if (bimg == null || bimg.getWidth() != w || bimg.getHeight() != h) {
            bimg = (BufferedImage) createImage(w, h);
        }
        g2 = bimg.createGraphics();
        g2.setBackground(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.clearRect(0, 0, w, h);
        return g2;
    }
    
    public static void main(String[] args) {
        final TankGame demo = new TankGame();
        demo.init();
        
        JFrame f = new JFrame("Tank Game");
        
        f.addWindowListener(new WindowAdapter() {
        });
        f.getContentPane().add("Center", demo);
        f.pack();
        f.setSize(winDim);
        f.setVisible(true);
        f.setResizable(false);
        demo.start();
    }
}
