package com.rworld.core.v2;

import android.os.SystemClock;

public class GameMainloop extends Thread {
	
	public final static int STATE_NONE		= 0;
	public final static int STATE_PAUSE		= 1;
	public final static int STATE_RUNNING	= 2;
	public final static int STATE_TERMINATE	= 3;
	
	public GameMainloop(GameActivity activity) {
		super("MainLoopThread");
		mActivity = activity;
	}
	
	public void onPause() {
		notifyState(STATE_PAUSE);
	}
	
	public void onResume() {
		notifyState(STATE_RUNNING);
	}
	
	public void onDestroy() {
		notifyState(STATE_TERMINATE);
	}
	
	@Override
	public void run() {
		ensureScreens();
		mState = (mStartScreen != null) ? STATE_RUNNING : STATE_TERMINATE;
		while(runOnce());
    }
	
	protected boolean runOnce() {
		switch(mState) {
		
		case STATE_PAUSE:
			if(mActivity.mCurrentScreen != null) {
				mActivity.mCurrentScreen.onPause();
			}
			waitUntilStateChanged();
			if((mState == STATE_RUNNING) && (mActivity.mCurrentScreen != null)) {
				mActivity.mCurrentScreen.onResume();
				resetTime();
			}
			return true;
			
		case STATE_RUNNING:
			mActivity.mRenderer.waitRenderingComplete(mLastFrameDeltaTime);
    		if(mActivity.mCurrentScreen == null) {
    			mActivity.mCurrentScreen = mStartScreen;
    			mActivity.mCurrentScreen.onCreate();
    			mActivity.mCurrentScreen.onResume();
    			resetTime();
    		}
    		else {
    			final GameScene nextScreen = mActivity.mCurrentScreen.mNextScene;
    			if(nextScreen == null) {
    				mActivity.finish();
    			}
    			else if(mActivity.mCurrentScreen != nextScreen) {
    				mActivity.mCurrentScreen.onPause();
	    			mActivity.mCurrentScreen.onDestroy();
	    			mActivity.mCurrentScreen = null;
	    			cleanupBeforeNextScreen();
	    			mActivity.mCurrentScreen = nextScreen;
	    			mActivity.mCurrentScreen.onCreate();
	    			mActivity.mCurrentScreen.onResume();
	    			resetTime();
    			}
    		}
    		mActivity.mCurrentScreen.update(mLastFrameDeltaTime);
    		mActivity.mRenderer.requestRender();
    		recordTime();
    		return true;
    		
		case STATE_TERMINATE:
			if(mActivity.mCurrentScreen != null) {
	    		mActivity.mCurrentScreen.onDestroy();
	    		mActivity.mCurrentScreen = null;
	    	}
			return false;
		
		default:
			throw new IllegalStateException();
		}
	}
	
	protected void ensureScreens() {
		if(mStartScreen == null) {
			mStartScreen = mActivity.onCreateScreens();
		}
	}

	protected void notifyState(int state) {
		synchronized(mStateObserver) {
			mState = state;
			mStateObserver.notify();
		}
	}
	
	protected void waitUntilStateChanged() {
		synchronized(mStateObserver) {
			while(mState == STATE_PAUSE) {
    			try {
    				mStateObserver.wait();
    			} catch (InterruptedException e) {
    				mState = STATE_TERMINATE;
    			}
			}
		}
	}

	protected void cleanupBeforeNextScreen() {
		mActivity.mGLView.onPause();
		System.gc();
		mActivity.mGLView.onResume();
	}
	
	protected void resetTime() {
		mLastFrameDeltaTime = 0.0f;
		mLastFrameStartTime = SystemClock.uptimeMillis();
	}
	
	protected void recordTime() {
		final long timeToWait = 1000 / GameActivity.MAX_FPS - (SystemClock.uptimeMillis() - mLastFrameStartTime) - 1;
		if(timeToWait > 0) {
			SystemClock.sleep(timeToWait);
		}
		final long currentFrameStartTime = SystemClock.uptimeMillis();
		mLastFrameDeltaTime = (currentFrameStartTime - mLastFrameStartTime) * 0.001f;
		mLastFrameStartTime = currentFrameStartTime;
	}
	
	protected GameActivity mActivity = null;
	protected GameScene mStartScreen = null;
	protected int mState = STATE_NONE;
	protected final Object mStateObserver = new Object();
	protected float mLastFrameDeltaTime = 0.0f;
	protected long mLastFrameStartTime = 0;
}
