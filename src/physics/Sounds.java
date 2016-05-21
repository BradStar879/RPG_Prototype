package physics;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sounds {
	
	Clip clip;
	public String name;
	
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
	
	public void setName(String name) {
		this.name = name;
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
	
	public void fadeOut() {
		try {
			for (int i = 0; i < 50; i ++) {
	        FloatControl gainControlA = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	        gainControlA.setValue(i * -1f);
	        Thread.sleep(20);
			}
	    }
		catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	
	public void fadeIn() {
		try {
			for (int i = 0; i < 50; i ++) {
				FloatControl gainControlB = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControlB.setValue(-50 + i);
				Thread.sleep(10);
			}
	    } 
		catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	
	@SuppressWarnings("static-access")
	public void resume() {
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}

}
