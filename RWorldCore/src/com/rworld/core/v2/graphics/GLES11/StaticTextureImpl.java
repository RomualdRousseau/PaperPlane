package com.rworld.core.v2.graphics.GLES11;

import android.opengl.GLES11;

import com.rworld.core.v2.graphics.ITexture;
import com.rworld.core.v2.graphics.TextureBuilder;

public class StaticTextureImpl implements ITexture {

	@Override
	public void release() {
		int[] textures = new int[1];
		textures[0] = mTextureID;
		GLES11.glDeleteTextures(1, textures, 0);
	}
	
	@Override
	public void bind() {
		GLES11.glBindTexture(GLES11.GL_TEXTURE_2D, mTextureID);
	}
	
	@Override
	public void ensureData(TextureBuilder builder) {
		int[] textures = new int[1];
		GLES11.glGenTextures(1, textures, 0);
		
		mTextureID = textures[0];
		GLES11.glBindTexture(GLES11.GL_TEXTURE_2D, mTextureID);
		GLES11.glTexParameterf(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MIN_FILTER, GLES11.GL_LINEAR);
		GLES11.glTexParameterf(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MAG_FILTER, GLES11.GL_LINEAR);
		GLES11.glTexParameterf(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_WRAP_S, GLES11.GL_REPEAT); //.GL_CLAMP_TO_EDGE);
		GLES11.glTexParameterf(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_WRAP_T, GLES11.GL_REPEAT); //.GL_CLAMP_TO_EDGE);
		
		builder.createTextureData();
		builder.recycle();
	}

	protected int mTextureID = 0;
}
