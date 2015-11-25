package physics;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sounds {
	
	Clip clip;
	
	public Sounds(String x){
		try {
	        AudioInputStream audio = AudioSystem.getAudioInputStream(new File(x));
	        clip = AudioSystem.getClip();
	        clip.open(audio);
	    }
	    catch(UnsupportedAudioFileException uae) {
	        System.out.println("1");
	    }
	    catch(IOException ioe) {
	        System.out.println("2");
	    }
	    catch(LineUnavailableException lua) {
	        System.out.println("3");
	    }
	}
	
	public void play() {
		clip.setFramePosition(0);
		clip.start();
	}
	
	@SuppressWarnings("static-access")
	public void loop() {
		clip.setFramePosition(0);
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	
	@SuppressWarnings("static-access")
	public void resume() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}

}
