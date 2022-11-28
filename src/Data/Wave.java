package Data;

import java.io.*;

import javax.sound.sampled.*;
import javax.swing.JOptionPane;

/**
 * AUTHOR: MATHEW PHILLIPS
 * DESC: Java class to help with including sound (.wav / .wave) files
 */

public class Wave{
	private String _str;
	private Clip _clip;
	
	private AudioFormat format;
	private DataLine.Info info;
	private AudioInputStream stream;
	
	public Wave(String fileName){
		_str = fileName;
		File yourFile = new File(_str);
		try {
			stream = AudioSystem.getAudioInputStream(yourFile);
			format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    _clip = (Clip) AudioSystem.getLine(info);
		    _clip.open(stream);
		    _clip.stop();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Audio not detected or audio problem...Exiting."); //$NON-NLS-1$
			System.exit(0);
		}
	}
	
	public String getSong(){
		return _str;
	}
	
	public boolean isFinished(){
		if(_clip.getMicrosecondPosition() >= _clip.getMicrosecondLength())
			return true;
		return false;
	}
	
	public void conditionalPlay(){
		if(_clip.isRunning())			return;
		easyPlay();
	}
	
	public void easyPlay(){
		_clip.stop();
		resetWAV();
		resumeWAV();
	}
	
	public void resumeWAV(){
		_clip.start();
	}
	
	public long getAudioLength(){
		return _clip.getMicrosecondLength();
	}
	
	public long getAudioPosition(){
		return _clip.getMicrosecondPosition();
	}
	
	/* This compares the actual position of the audio to the desired (sync) position and adjusts, if necessary.
	 * We attempt to avoid unnecessary and jarring "jumps" in audio by setting an acceptable threshold for "drift"*/
	/*public void adjustAudio(long uSecsSync){
		long threshold = 160000;		// This determines how much drift we will tolerate (allow 4 frames @ 25 FPS)
		long current = _clip.getMicrosecondPosition();
		long diff = (current - uSecsSync);
		if(diff > threshold){
			_clip.setMicrosecondPosition(uSecsSync);
		}else if(diff < -(threshold)){
			_clip.setMicrosecondPosition(uSecsSync);
		}
		_clip.start();
	}*/
	
	public void setTimePosition(long uSecs){
		// This is to periodically set the audio to where it needs to be to sync with the video
		_clip.setMicrosecondPosition(uSecs);
		_clip.start();
	}
	
	public void playWAV(){
		if(_clip.isActive()){
			_clip.stop();
			_clip.setMicrosecondPosition(0);
		}
		if(_clip.getMicrosecondPosition() >=_clip.getMicrosecondLength())
			_clip.setMicrosecondPosition(0);
		_clip.start();
	}
	
	public void resetWAV(){
		_clip.setMicrosecondPosition(0);
	}
	
	public void flush(){
		_clip.stop();
		_clip.flush();
		_clip.close();
	}
	
	public void restartWAV(){
		_clip.setMicrosecondPosition(0);
		_clip.start();
	}
	
	public void pauseWAV(){
		if(_clip.isRunning()){
			_clip.stop();
		}
	}
	
	public void setLoop(){
		_clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void loadWAV(boolean loopForever){
		try {
		    if(loopForever)		_clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Audio not detected or audio problem...Exiting."); //$NON-NLS-1$
		    System.exit(0);
		}
	}
	
	public void setVolume(float newVolume){
		FloatControl gainControl = (FloatControl) _clip.getControl(FloatControl.Type.MASTER_GAIN);
		try{
			gainControl.setValue(newVolume); // Reduce volume by newVolume decibels.
		}catch(IllegalArgumentException iae){
			System.out.println("Test illegal argument...");
		}
	}
}