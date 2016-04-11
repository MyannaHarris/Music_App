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

    public MusicPlayer() {
    	currFrame = 0;
    }
    
    public void restart() {
    	clip.stop();
    	clip.setFramePosition(0);
    	clip.start();
    }
    
    public void play() {
        if (currFrame < clip.getFrameLength()) {
            clip.setFramePosition(currFrame);
        } else {
            clip.setFramePosition(0);
        }
        clip.start();
    }
    
    public void play(String path) {
    	if (clip.isRunning()) {
    		clip.stop();
    	}
    	try {
			loadClip(new File(path));
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
    	clip.start();
    	
    }
    
    public void pause() {
        if (clip.isRunning()) {
            currFrame = clip.getFramePosition();
            clip.stop();
        }
    }

    protected void loadClip(File audioFile) throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        this.clip = (Clip) AudioSystem.getLine(info);
        this.clip.open(audioStream);

    }
}