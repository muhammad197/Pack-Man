package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.Clip;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import View.BoardControl;


public class Sound {

	
	private static boolean soundFX = true;
	public static Clip clip;
	private static BoardControl BoardController;
	
	/**
	 * this method returns weather the soundfx is turned on or off
	 * 
	 * @return boolean
	 */
	public static boolean isSoundFX() {
		return soundFX;
	}
	
	public static void setSoundFX(boolean soundFX) {
		Sound.soundFX = soundFX;
	}

	/**
	 * This method gets the path of the sound in the system, and controls its volume
	 * 
	 * @param soundFilePath
	 * @param volume
	 */
	public static void playSound(URL soundFilePath, double volume) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
				
					String soundFile = soundFilePath.toString();
					Media media = new Media(soundFile);
					MediaPlayer mp = new MediaPlayer(media);
					mp.setVolume(volume);
					mp.play();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	
//	public static void play() {
//		try {
//			String musicFile = "";
//			musicFile = "../resources/background.wav";
//
//			getBoardController().setMusic(true);
//			InputStream audioFile = Sound.class.getResourceAsStream(musicFile);
//			AudioInputStream sound = AudioSystem.getAudioInputStream(audioFile);
//
//			clip = AudioSystem.getClip();
//			clip.open(sound);
//			clip.loop(Clip.LOOP_CONTINUOUSLY);
//		
//		} catch (UnsupportedAudioFileException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (LineUnavailableException e) {
//			e.printStackTrace();
//		}
//	}

	public static BoardControl getBoardController() {
		return BoardController;
	}

	public static void setBoardController(BoardControl boardController) {
		BoardController = boardController;
	}

	/**
	 * This method stops the background music
	 */
	public static void stop() {
		if (clip != null)
			clip.stop();
	}
	public Sound() {
		// TODO Auto-generated constructor stub
	}

}
