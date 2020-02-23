package com.rworld.PaperPlane.effects;

import android.opengl.GLES11;
import android.opengl.GLES11Ext;

import com.rworld.core.v2.graphics.Font;
import com.rworld.core.v2.graphics.GraphicManager;

public class WaveHorizontalText {

    public WaveHorizontalText(GraphicManager graphicManager, Font font, int fontScale, int baseHeight, int amplitude, float period, String message) {
        mGraphicManager = graphicManager;
        mFont = font;
        mFontScale = fontScale;
        mMessage = message + " ";

        mVelocity = new float[1024];
        for (int i = 0; i < 1024; i++) {
            mVelocity[i] = amplitude * (float) Math.sin(period * i / 1024);
        }

        mPosition[0] = GraphicManager.displayWidth;
        mPosition[1] = baseHeight;
    }

    public void dispose() {
    }

    public void update(float time) {
        if (mPosition[0] < -mMessage.length() * mFont.width * mFontScale) {
            mPosition[0] = GraphicManager.displayWidth;
        } else {
            mPosition[0] += (int) (-300 * time);
        }
    }

    public void render() {
        mRect[0] = mPosition[0];
        mRect[1] = GraphicManager.displayHeight - mPosition[1];
        mRect[2] = mFont.width * mFontScale;
        mRect[3] = mFont.height * mFontScale;

        final int l = mMessage.length();
        final int clip1 = Math.max(-mRect[0], 0) / mRect[2];
        final int clip2 = l - Math.max(mRect[0] + l * mRect[2] - GraphicManager.displayWidth, 0) / mRect[2];
        mRect[0] += clip1 * mRect[2];
        mRect[1] -= mRect[3];
        mGlyph[2] = mFont.width - 1;
        mGlyph[3] = -mFont.height + 1;

        mFont.texture.bind();

        for (int i = clip1; i < clip2; i++) {
            if ((mMessage.charAt(i) > ' ')) {
                final int c = mMessage.codePointAt(i) - 32;
                mGlyph[0] = (c & 15) * mFont.width;
                mGlyph[1] = ((c >> 4) + 1) * mFont.height;
                GLES11.glTexParameteriv(GLES11.GL_TEXTURE_2D, GLES11Ext.GL_TEXTURE_CROP_RECT_OES, mGlyph, 0);
                GLES11Ext.glDrawTexfOES(mRect[0], mRect[1] + mVelocity[(mRect[0] + mRect[2]) % 1024], 0.0f, mRect[2], mRect[3]);
            }
            mRect[0] += mRect[2];
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
