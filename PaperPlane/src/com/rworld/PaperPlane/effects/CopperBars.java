package com.rworld.PaperPlane.effects;

import android.opengl.GLES11;
import android.util.FloatMath;

import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.Surface;
import com.rworld.core.v2.math.FastMath;

public class CopperBars {
	
	public CopperBars(GraphicManager graphicManager, int baseHeight, int amplitude, float period) {
		mGraphicManager = graphicManager;
		mBaseHeight = baseHeight;
		mAmplitude = amplitude;
		mPeriod = period;
		mSurface = new Surface(0, 0, 32, 32, mGraphicManager.textureManager.get("textures/copper.png"));
	}
	
	public void dispose() {
		mGraphicManager.textureManager.release("textures/copper.png");
	}
	
	public void update(float time) {
		mVelocity += mPeriod * time;
		mPosition[0] = mBaseHeight + (int) (mAmplitude * FloatMath.sin(mVelocity));
		mPosition[1] = mBaseHeight + (int) (mAmplitude * FloatMath.sin(mVelocity + FastMath.PI / 4));
		mPosition[2] = mBaseHeight + (int) (mAmplitude * FloatMath.sin(mVelocity + FastMath.PI / 2));
	}
	
	public void render() {
		GLES11.glColor4f(0.63671875f, 0.76953125f, 0.22265625f, 1.0f);
		mGraphicManager.drawSurface2D(mSurface, 0, mPosition[0], GraphicManager.displayWidth, 32);
		GLES11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mGraphicManager.drawSurface2D(mSurface, 0, mPosition[1], GraphicManager.displayWidth, 32);
		GLES11.glColor4f(0.640625f, 0.234375f, 0.234375f, 1.0f);
		mGraphicManager.drawSurface2D(mSurface, 0, mPosition[2], GraphicManager.displayWidth, 32);
		GLES11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	protected GraphicManager mGraphicManager = null;
	protected int mBaseHeight = 0;
	protected int mAmplitude = 0;
	protected float mPeriod = 0.0f;
	protected Surface mSurface = null;
	protected float mVelocity = 0.0f;
	protected int[] mPosition = {0, 0, 0};

}
