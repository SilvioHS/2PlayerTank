/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tankgame;

import java.awt.event.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Silvio
 */
    public class KeyControl extends KeyAdapter {
        
        GameEvents game;

    public KeyControl(GameEvents gameEvents) {
        game = gameEvents;
    }

    public void keyPressed(KeyEvent e) {

        if ((e.getKeyCode() == KeyEvent.VK_LEFT)
                || (e.getKeyCode() == KeyEvent.VK_RIGHT)
                || (e.getKeyCode() == KeyEvent.VK_UP)
                || (e.getKeyCode() == KeyEvent.VK_DOWN)
                || (e.getKeyCode() == KeyEvent.VK_SPACE)) {

            game.setValueKP_P1(e);
        } else {
            game.setValueKP_P2(e);
        }
    }

    public void keyReleased(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_LEFT)
                || (e.getKeyCode() == KeyEvent.VK_RIGHT)
                || (e.getKeyCode() == KeyEvent.VK_UP)
                || (e.getKeyCode() == KeyEvent.VK_DOWN)
                || (e.getKeyCode() == KeyEvent.VK_SPACE)) {

            game.setValueKR_P1(e);
        } else {
            game.setValueKR_P2(e);
        }
    }

}
