package com.rworld.core.v2;

import javax.microedition.khronos.opengles.GL10;

import com.rworld.core.v2.audio.AudioManager;
import com.rworld.core.v2.graphics.GraphicManager;

import android.opengl.GLES10;

public class GameScene {
	
	public GameScene(GameActivity activity) {
		mActivity = activity;
	}
	
	public void update(float time) {
		if(mAssetLoadingProgression <= 10) {
			if(mAssetLoadingProgression >= 6) {
				ensureAudioAssets();
			}
			if(mAssetLoadingProgression >= 9) {
				System.gc();
			}
			mNextScene = this;
		}
		else {
			mNextScene = onUpdate(time, mActivity.mInput);
		}
	}
	
	public void render() {
		if(mAssetLoadingProgression <= 10) {
			if(mAssetLoadingProgression == 0) {
				ensureGraphicAssets();
				ensureGraphicTextures();
			}
			if(mAssetLoadingProgression == 5) {
				ensureGraphicMeshes();
			}
			mAssetLoadingProgression++;
			onPreRender();
		}
		else {
			onRender();
		}
	}
	
	public void onCreate() {
		Log.d("Game creates a new screen:" + this.toString());
	}

	public void onPause() {
		Log.d("Game pauses the screen:" + this.toString());
		if(mAudioManager != null) {
			mAudioManager.dispose();
			mAudioManager = null;
		}
	}
	
	public void onResume() {
		Log.d("Game resumes the screen:" + this.toString());
		if(mDialog == null) {
			Log.d("Game connects the screen to the dialog manager:" + this.toString());
			mDialog = mActivity.mDialog;
		}
		if(mGraphicManager == null) {
			Log.d("Game connects the screen to the graphic manager:" + this.toString());
			mGraphicManager = mActivity.mRenderer.mGraphicManager;
		}
		if(mAudioManager == null) {
			Log.d("Game connects the screen to the music manager:" + this.toString());
			mAudioManager = new AudioManager(mActivity);
		}
		mAudioAssetsDirty = true;
		mGraphicAssetsDirty = true;
		mAssetLoadingProgression = 0;
	}
	
	public void onDestroy() {
		Log.d("Game destroys the screen:" + this.toString());
	}
	
	protected GameScene onUpdate(float time, GameInput event) {
		return this;
	}
	
	protected void onPreRender() {
		GLES10.glClear(GLES10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	}
	
	protected void onRender() {
		GLES10.glClear(GLES10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	}

	protected void onLoadAudioAssets() {
		Log.d("Game loads the audio assets of screen:" + this.toString());
	}
	
	protected void onLoadGraphicAssets() {
		Log.d("Game loads the graphic assets of screen:" + this.toString());
	}
	
	protected void ensureAudioAssets() {
		if(mAudioAssetsDirty) {
			onLoadAudioAssets();
			mAudioAssetsDirty = false;
		}
	}

	protected void ensureGraphicAssets() {
		if(mGraphicAssetsDirty) {
			onLoadGraphicAssets();
			mGraphicAssetsDirty = false;
		}
	}
	
	protected void ensureGraphicTextures() {
		mGraphicManager.textureManager.ensureTextures();
	}
	
	protected void ensureGraphicMeshes() {
		mGraphicManager.meshManager.ensureMeshes();
	}
	
	protected GameActivity mActivity = null;
	protected AudioManager mAudioManager = null;
	protected GraphicManager mGraphicManager = null;
	protected GameDialog mDialog = null;
	protected GameScene mNextScene = null;
	protected boolean mAudioAssetsDirty = true;
	protected boolean mGraphicAssetsDirty = true;
	protected volatile int mAssetLoadingProgression = 0;
}
