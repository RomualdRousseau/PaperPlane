package com.rworld.PaperPlane.effects;

import android.util.FloatMath;

import com.rworld.PaperPlane.MainActivity;
import com.rworld.core.v2.graphics.Font;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.math.FastMath;

public class PulseText {
	
	public PulseText(GraphicManager graphicManager, Font font, int fontScale, float duration, float interval, String[] text) {
		mGraphicManager = graphicManager;
		mFont = font;
		mFontScale = fontScale;
		mDuration = duration;
		mInterval = interval;
		mText = text;
		mTime = mDuration;
		mTextIndex = -1;
	}
	
	public void dispose() {
	}
	
	public void update(float time) {
		if(mTextIndex < mText.length) {
			if(mTime >= (mDuration + mInterval)) {
				mTextIndex++;
				if(mTextIndex < mText.length) {
					final int l = mText[mTextIndex].length() * mFont.width * mFontScale;
					mTextX = (l < GraphicManager.displayWidth) ? MainActivity.random.nextInt(GraphicManager.displayWidth - l) : 0;
					mTextY = MainActivity.random.nextInt(GraphicManager.displayHeight - 2 * mFont.height * mFontScale) + mFont.height * mFontScale;
				}
				mTime = 0.0f;
			}
			mTime += time;
		}
	}
	
	public void update(float time, int x, int y) {
		if(mTextIndex < mText.length) {
			if(mTime >= (mDuration + mInterval)) {
				mTextIndex++;
				if(mTextIndex < mText.length) {
					mTextX = x;
					mTextY = y;
				}
				mTime = 0.0f;
			}
			mTime += time;
		}
	}
	
	public void render() {
		if((mTextIndex >= 0) && (mTextIndex < mText.length) && (mTime > mInterval)) {
			float c = FloatMath.sin(FastMath.PI * (mTime - mInterval) / mDuration);
			mGraphicManager.drawText2D(mFont, 1, new float[]{1, 1, 1, c}, mTextX, mTextY, mText[mTextIndex]);
		}
	}

	protected GraphicManager mGraphicManager = null;
	protected Font mFont = null;
	protected int mFontScale = 0;
	protected float mDuration = 0.0f;
	protected float mInterval = 0.0f;
	protected String[] mText = null;
	protected int mTextIndex = 0;
	protected int mTextX = 0;
	protected int mTextY = 0;
	protected float mTime = 0.0f;
}
