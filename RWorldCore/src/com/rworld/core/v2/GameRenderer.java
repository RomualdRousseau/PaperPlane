package com.rworld.core.v2;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.GraphicManagerFactory;

import android.opengl.GLSurfaceView;

public class GameRenderer implements GLSurfaceView.Renderer {

	public GameRenderer(GameActivity activity) {
		mActivity = activity;
		mGraphicManager = GraphicManagerFactory.createInstance(activity);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d("DEVICE        : "    + android.os.Build.DEVICE);
        Log.d("MODEL         : "    + android.os.Build.MODEL);
        Log.d("PRODUCT       : "    + android.os.Build.PRODUCT);

        Log.d("GL_VENDOR     : "    + gl.glGetString(GL10.GL_VENDOR));
        Log.d("GL_RENDERER   : "    + gl.glGetString(GL10.GL_RENDERER));
        Log.d("GL_VERSION    : "    + gl.glGetString(GL10.GL_VERSION));
        Log.d("GL_EXTENSIONS :\n  " + gl.glGetString(GL10.GL_EXTENSIONS).trim().replace(" ", "\n  "));

        int[] params=new int[2];

        gl.glGetIntegerv(GL10.GL_DEPTH_BITS, params, 0);
        Log.d("GL_DEPTH_BITS               : " + params[0]);
        gl.glGetIntegerv(GL10.GL_STENCIL_BITS, params, 0);
        Log.d("GL_STENCIL_BITS             : " + params[0]);

        gl.glGetIntegerv(GL10.GL_MAX_LIGHTS, params, 0);
        Log.d("GL_MAX_LIGHTS               : " + params[0]);
        gl.glGetIntegerv(GL10.GL_MAX_TEXTURE_SIZE, params, 0);
        Log.d("GL_MAX_TEXTURE_SIZE         : " + params[0]);
        gl.glGetIntegerv(GL10.GL_MAX_TEXTURE_UNITS, params, 0);
        Log.d("GL_MAX_TEXTURE_UNITS        : " + params[0]);

        gl.glGetIntegerv(GL10.GL_ALIASED_LINE_WIDTH_RANGE, params, 0);
        Log.d("GL_ALIASED_LINE_WIDTH_RANGE : " + params[0] + ", " + params[1]);
        gl.glGetIntegerv(GL10.GL_SMOOTH_LINE_WIDTH_RANGE, params, 0);
        Log.d("GL_SMOOTH_LINE_WIDTH_RANGE  : " + params[0] + ", " + params[1]);

        gl.glGetIntegerv(GL10.GL_ALIASED_POINT_SIZE_RANGE, params, 0);
        Log.d("GL_ALIASED_POINT_SIZE_RANGE : " + params[0] + ", " + params[1]);
        gl.glGetIntegerv(GL10.GL_SMOOTH_POINT_SIZE_RANGE, params, 0);
        Log.d("GL_SMOOTH_POINT_SIZE_RANGE  : " + params[0] + ", " + params[1]);
        
        if (gl instanceof GL11) {
            Log.d("Implements GL11");
        }
        else {
            Log.e("No GL11 available");
            mActivity.finish();
            return;
        }

		mGraphicManager.gl = gl;
		mGraphicManager.configureGraphicContext(config);
		mGraphicManager.gl = null;
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		mGraphicManager.gl = gl;
		mGraphicManager.configureViewport(width, height);
		mGraphicManager.gl = null;
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		if(mActivity.mCurrentScreen != null) {
			mGraphicManager.gl = gl;
			mActivity.mCurrentScreen.render();
			mGraphicManager.gl = null;
		}
		mSpinLock--;
	}
	
	protected void waitRenderingComplete(float time) {
		while(mSpinLock > 0) {
			Thread.yield();
		}
	}
	
	protected void requestRender() {
		mActivity.mGLView.requestRender();
		mSpinLock++;
	}
	
	protected GameActivity mActivity = null;
	protected GraphicManager mGraphicManager = null;
	protected volatile int mSpinLock = 0;
}
