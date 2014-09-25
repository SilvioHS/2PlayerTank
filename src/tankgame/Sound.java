
package tankgame;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public class Sound {
    private Clip clip;

    Sound(String soundFile) {
        try {
            // Use URL (instead of File) to read from disk and JAR.
            URL url = TankGame.class.getResource(soundFile);
            // Set up an audio input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
   // Play or Re-play the sound effect from the beginning, by rewinding.
   public void play() {
         if (clip.isRunning())
            clip.stop();   // Stop the player if it is still running
         clip.setFramePosition(0); // rewind to the beginning
         clip.start();     // Start playing
   }
   
   public boolean isPlaying(){
       if (clip.isRunning()){
           return true;
       } else {
           return false;
       }
   }
}
