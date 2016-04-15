import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer {
	private Clip clip;
	private int currFrame;
	private boolean currSong;

    public MusicPlayer() {
    	currFrame = 0;
    	currSong = false;
    	clip = null;
    }
    
    public void stop() {
    	if (clip != null) {
    		clip.stop();
    		currSong = false;
    	}
    }
    
    public void restart() {
    	if (clip != null) {
    		if (clip.isRunning()) {
    			clip.stop();
    			clip.setFramePosition(0);
    			clip.start();
    		}
    		else if (currSong) {
    			clip.setFramePosition(0);
    		}
    	}
    }
    
    public void play() {
    	if (clip != null) {
    		if (currSong && !clip.isRunning()) {
    			if (currFrame < clip.getFrameLength()) {
    				clip.setFramePosition(currFrame);
    			} else {
    				clip.setFramePosition(0);
    			}
    			clip.start();
    		}
    	}
    }
    
    public void play(String path) {
    	if (clip != null) {
    		if (clip.isRunning()) {
    			clip.stop();
    			clip = null;
    			currSong = false;
    		}
    	}
    	try {
    		loadClip(new File(path));
    		currSong = true;
			clip.setFramePosition(0);
    		clip.start();
    	} catch (LineUnavailableException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (UnsupportedAudioFileException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
    
    public void pause() {
    	if (clip != null) {
    		if (clip.isRunning()) {
    			currFrame = clip.getFramePosition();
    			clip.stop();
    		}
    	}
    }
    
    public boolean isPlaying() {
    	if (clip != null) {
    		return clip.isRunning();
    	}
    	return false;
    }

    protected void loadClip(File audioFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        this.clip = (Clip) AudioSystem.getLine(info);
        this.clip.open(audioStream);

    }
}