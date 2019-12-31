package com.rworld.core.v2.graphics.builders;

import java.nio.IntBuffer;

import android.opengl.GLES11;

import com.rworld.core.v2.graphics.TextureBuilder;

public class SolidColorBuilder extends TextureBuilder {
	
	public final static SolidColorBuilder BLACK = new SolidColorBuilder(new float[]{0.0f, 0.0f, 0.0f, 1.0f});
	public final static SolidColorBuilder WHITE = new SolidColorBuilder(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
	public final static SolidColorBuilder RED = new SolidColorBuilder(new float[]{1.0f, 0.0f, 0.0f, 1.0f});
	public final static SolidColorBuilder GREEN = new SolidColorBuilder(new float[]{0.0f, 1.0f, 0.0f, 1.0f});
	public final static SolidColorBuilder BLUE = new SolidColorBuilder(new float[]{0.0f, 0.0f, 1.0f, 1.0f});
	
	public SolidColorBuilder(float[] color) {
		mColor[0] = (int) (color[0] * 255.0f);
		mColor[1] = (int) (color[1] * 255.0f);
		mColor[2] = (int) (color[2] * 255.0f);
		mColor[3] = (int) (color[3] * 255.0f);
	}

	@Override
	public void createTextureData() {
		int[] bitmap = new int[64 * 64];
		for(int i = 0; i < 2 * 2; i++) {
			bitmap[i] = (mColor[0] << 0) + (mColor[1] << 8) + (mColor[2] << 16) + (mColor[3] << 24);
		}
		GLES11.glTexImage2D(GLES11.GL_TEXTURE_2D, 0, GLES11.GL_RGBA, 2, 2, 0, GLES11.GL_RGBA, GLES11.GL_UNSIGNED_BYTE, IntBuffer.wrap(bitmap));
	}
	
	protected int[] mColor = {0, 0, 0, 255};
}
