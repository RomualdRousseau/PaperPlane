package com.rworld.PaperPlane.effects;

import android.opengl.GLES11;
import android.opengl.GLES11Ext;
import android.util.FloatMath;

import com.rworld.core.v2.graphics.Font;
import com.rworld.core.v2.graphics.GraphicManager;

public class WaveVerticalText {
	
	public WaveVerticalText(GraphicManager graphicManager, Font font, int fontScale, int center, int amplitude, float period, String message) {
		mGraphicManager = graphicManager;
		mFont = font;
		mFontScale = fontScale;
		mMessage = message + " ";
		
		mVelocity = new float[1024];
		for(int i = 0; i < 1024; i++) {
			mVelocity[i] = amplitude * FloatMath.sin(period * i / GraphicManager.displayHeight);
		}
		
		mPosition[0] = center;
		mPosition[1] = GraphicManager.displayHeight;
	}
	
	public void dispose() {
	}
	
	public void update(float time) {
		if(mPosition[1] < -mMessage.length() * mFont.height * mFontScale) {
			mPosition[1] = GraphicManager.displayHeight;
		}
		else {
			mPosition[1] += (int) (-200 * time);
		}
	}
	
	public void render() {
		mRect[0] = mPosition[0];
		mRect[1] = GraphicManager.displayHeight - mPosition[1];
		mRect[2] = mFont.width * mFontScale;
		mRect[3] = mFont.height * mFontScale;
		
		final int l = mMessage.length();
		final int clip1 = Math.max(0, (mRect[1] - GraphicManager.displayHeight) / mRect[3] - 1);
		final int clip2 = Math.min(l, mRect[1] / mRect[3]);
		mRect[1] -= clip1 * mRect[3] + 2 * mRect[3];
		mGlyph[2] = mFont.width - 1;
		mGlyph[3] = -mFont.height + 1;
		
		mFont.texture.bind();

		for(int i = clip1; i < clip2; i++) {
			if((mMessage.charAt(i) > ' ')) {
				final int c = mMessage.codePointAt(i) - 32;
				mGlyph[0] = (c & 15) * mFont.width;
				mGlyph[1] = ((c >> 4) + 1) * mFont.height;
				GLES11.glTexParameteriv(GLES11.GL_TEXTURE_2D, GLES11Ext.GL_TEXTURE_CROP_RECT_OES, mGlyph, 0);
				GLES11Ext.glDrawTexfOES(mRect[0] + mVelocity[mRect[1] + mRect[3]], mRect[1], 0.0f, mRect[2], mRect[3]);
			}
			mRect[1] -= mRect[3];
		}
	}

	protected GraphicManager mGraphicManager = null;
	protected Font mFont = null;
	protected int mFontScale = 0;
	protected String mMessage = "";
	protected float[] mVelocity = null;
	protected int[] mPosition = {0, 0};
	protected int[] mRect = {0, 0, 0, 0};
	protected int[] mGlyph = {0, 0, 0, 0};
}
