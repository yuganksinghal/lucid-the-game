package edu.virginia.engine;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import edu.virginia.lab1test.Lucid;

public class SoundManager {
	public SoundManager() {
		
	}
	
	public static void loadSound(String mp3) {
		
	}
	public static void playSound(String url) {
	      try {
	    	  
		        Clip clip = AudioSystem.getClip();
		        
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
		          Lucid.class.getResourceAsStream("/res/sounds/" + url));
		        
		        clip.open(inputStream);
		        clip.start(); 
		        
		      } catch (Exception e) {
		    	  e.printStackTrace();
		      }
	}
	public static void loadMusic(String mp3) {
		
	}
	public static void playMusic(String url) {
		System.out.println("res/music/" + url);
			      try {
			    	  
			        Clip clip = AudioSystem.getClip();
			        
			        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
			          SoundManager.class.getResourceAsStream("res/music/" + url));
			          
			        clip.open(inputStream);
			        clip.start(); 
			        
			      } catch (Exception e) {
			    	  e.printStackTrace();
			      }
	}
	
	
	public static synchronized void playSoundSO(final String url) {
			Clip clip;
			try {
				clip = AudioSystem.getClip();
				AudioInputStream in = AudioSystem.getAudioInputStream(new File("res" + File.separator + "sounds" + File.separator +url));
				clip.open(in);
				clip.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
}
