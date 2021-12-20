package com.rworld.core.v2.audio;

import java.io.IOException;

import com.rworld.core.v2.GameActivity;
import com.rworld.core.v2.Log;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class AudioManager {

	public static final int NUMBER_OF_STREAMS = 5;
	
	public AudioManager(GameActivity activity) {
		mActivity = activity;
		mActivity.setVolumeControlStream(android.media.AudioManager.STREAM_MUSIC);
		mAudioManager = (android.media.AudioManager) mActivity.getSystemService(Context.AUDIO_SERVICE);
		mMediaPlayer = new MediaPlayer();
		mSoundPool = new SoundPool(AudioManager.NUMBER_OF_STREAMS, android.media.AudioManager.STREAM_MUSIC, 0);
	}
	
	public void dispose() {
		mMediaPlayer.stop();
		mMediaPlayer.release();
		mMediaPlayer = null;
		mSoundPool.release();
		mSoundPool = null;
	}

	public void loadMusic(String fileName, boolean looping) {
		try {
			loadMusic(mActivity.getAssets().openFd(fileName), looping);
		} catch (Exception e) {
			Log.e("", e);
		}
	}
	
	public void loadMusic(AssetFileDescriptor fd, boolean looping) throws IOException {
		mMediaPlayer.reset();
		mMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
		mMediaPlayer.prepare();
		mMediaPlayer.setLooping(looping);
		mMediaPlayer.start();
	}
	
	public int loadSound(String fileName) {
		try {
			return loadSound(mActivity.getAssets().openFd(fileName));
		} catch (Exception e) {
			Log.e("", e);
			return 0;
		}
	}
	
	public int loadSound(AssetFileDescriptor fd) {
		return mSoundPool.load(fd, 1);
	}
	
	public void playMusic() {
		mMediaPlayer.start();
	}
	
	public void stopMusic() {
		mMediaPlayer.stop();
	}
	
	public void pauseMusic() {
		mMediaPlayer.pause();
	}
	
	public void seekToMusic(int time) {
		mMediaPlayer.seekTo(time);
	}

	public void playSound(int soundId) {
		mSoundPool.stop(mPlayingStreamId[mCurrentPlayingStream % AudioManager.NUMBER_OF_STREAMS]);
		float streamVolumeCurrent = mAudioManager.getStreamVolume(android.media.AudioManager.STREAM_MUSIC);
	    float streamVolumeMax = mAudioManager.getStreamMaxVolume(android.media.AudioManager.STREAM_MUSIC);    
	    float volume = streamVolumeCurrent / streamVolumeMax;
	    mPlayingStreamId[mCurrentPlayingStream % AudioManager.NUMBER_OF_STREAMS] = mSoundPool.play(soundId, volume, volume, 1, 0, 1);
	    mCurrentPlayingStream++;
	}

	protected GameActivity mActivity = null;
	protected android.media.AudioManager mAudioManager = null;
	protected MediaPlayer mMediaPlayer = null;
	protected SoundPool mSoundPool = null;
    protected int[] mPlayingStreamId = new int[5];
	protected int mCurrentPlayingStream = 0;
}
