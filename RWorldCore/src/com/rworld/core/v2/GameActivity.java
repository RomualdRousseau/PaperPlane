package com.rworld.core.v2;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

import com.rworld.core.v2.graphics.GraphicManager;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public abstract class GameActivity extends Activity implements OnTouchListener, SensorEventListener {

	public final static int MAX_FPS	= 50;

	public static boolean Debug = true;
	public static boolean UseSensor = false;
	public static boolean RenderMeshBoundaries = true;
	
	protected abstract GameScene onCreateScreens();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Droid creates the activity");
        
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;
        getWindow().setAttributes(layoutParams);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        if(GameActivity.UseSensor) {
        	mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        	mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        }
        
		mDialog = new GameDialog(this);
		mInput = new GameInput();
        mRenderer = new GameRenderer(this);
        mGLView = new GLSurfaceView(this);
        if(GameActivity.Debug) {
        	mGLView.setDebugFlags(GLSurfaceView.DEBUG_CHECK_GL_ERROR | GLSurfaceView.DEBUG_LOG_GL_CALLS);
        }
        mGLView.setEGLConfigChooser(new EGLConfigChooser() {
        	@Override
        	public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
                int[] attributes={
                     EGL10.EGL_DEPTH_SIZE,
                     16,
                     EGL10.EGL_NONE
                };
                EGLConfig[] configs=new EGLConfig[1];
                int[] result=new int[1];
                egl.eglChooseConfig(display, attributes, configs, 1, result);
                return configs[0];
           }
        });
        mGLView.setRenderer(mRenderer);
        mGLView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        mGLView.setOnTouchListener(this);
        setContentView(mGLView);
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	Log.d("Droid pauses the activity");
    	
    	if(mMainloop != null) {
    		mMainloop.onPause();
    	}
    	
    	if(mSensorManager != null) {
    		mSensorManager.unregisterListener(this);
    	}
    	
    	mGLView.onPause();
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	Log.d("Droid resumes the activity");
    	
    	mGLView.onResume();
    	
    	if(mSensorManager != null) {
    		mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    	}
    	
    	if(mMainloop == null) {
    		mMainloop = new GameMainloop(this);
    		mMainloop.start();
    	}
    	else {
    		mMainloop.onResume();
    	}
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	Log.d("Droid destroys the activity");
    	
    	if(mMainloop != null) {
    		mMainloop.onDestroy();
    		try {
				mMainloop.join();
			} catch (InterruptedException e) {
				mMainloop.interrupt();
			}
    		mMainloop = null;
    	}
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    switch (keyCode) {
	    
	    case KeyEvent.KEYCODE_BACK:
	    	mInput.isBackButtonTouched = true; 
	        return true;
	    
	    case KeyEvent.KEYCODE_MENU:
	    	mInput.isMenuButtonTouched = true; 
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
	    switch (keyCode) {
	    
	    case KeyEvent.KEYCODE_BACK:
	    	mInput.isBackButtonTouched = false;
	        return true;
	        
	    case KeyEvent.KEYCODE_MENU:
	    	mInput.isMenuButtonTouched = false; 
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
    
    @Override
	public boolean onTouch(View v, MotionEvent event) {
    	if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
    		mInput.isScreenTouched = true;
    		mInput.screenTouchCoord[0] = (int) event.getX();
    		mInput.screenTouchCoord[1] = GraphicManager.displayHeight - (int) event.getY();
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			mInput.isScreenTouched = false;
		}
		return true;
	}
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

    @Override
	public void onSensorChanged(SensorEvent event) {
    	mInput.sensorAcceleration[0] = event.values[0];
    	mInput.sensorAcceleration[1] = event.values[1];
    	mInput.sensorAcceleration[2] = event.values[2];
	}
    
    protected SensorManager mSensorManager = null;
    protected GLSurfaceView mGLView = null;
	protected GameDialog mDialog = null;
	protected GameInput mInput = null;
	protected GameRenderer mRenderer = null;
    protected GameMainloop mMainloop = null;
    protected volatile GameScene mCurrentScreen = null;
    
}