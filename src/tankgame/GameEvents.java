
package tankgame;

import java.awt.event.KeyEvent;
import java.util.Observable;


public class GameEvents extends Observable {

        int type;
        Object event;
        GameObject gamObject;

        //PLAYER 1
        //handle key press events
        public void setValueKP_P1(KeyEvent e) {
            type = 1; // let's assume this mean key input. Should use CONSTANT value for this
            event = e;
            setChanged();
            // trigger notification
            notifyObservers(this);
        }
        
        //handle key release events
        public void setValueKR_P1(KeyEvent e) {
            type = 2; // let's assume this mean key input. Should use CONSTANT value for this
            event = e;
            setChanged();
            // trigger notification
            notifyObservers(this);
        }
        
        
        
        
        //PLAYER 2
        //handle key press events
        public void setValueKP_P2(KeyEvent e) {
            type = 1; // let's assume this mean key input. Should use CONSTANT value for this
            event = e;
            setChanged();
            // trigger notification
            notifyObservers(this);
        }
        
        //handle key release events
        public void setValueKR_P2(KeyEvent e) {
            type = 2; // let's assume this mean key input. Should use CONSTANT value for this
            event = e;
            setChanged();
            // trigger notification
            notifyObservers(this);
        }

        
        
        
        //handle ingame events
        public void setValue(String msg, GameObject go) {
            type = 0; // let's assume this mean key input. Should use CONSTANT value for this
            event = msg;
            gamObject = go;
            setChanged();
            // trigger notification
            notifyObservers(this);
        }
        
        
        
        
    }
