package com.rworld.core.v2.graphics.loaders;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES10;
import android.opengl.GLUtils;

import com.rworld.core.v2.graphics.TextureLoader;

public class ImageTextureLoader extends TextureLoader {
	
	@Override
	public void recycle() {
		mImage.recycle();
	}

	@Override
	public void createTextureData() {
		GLUtils.texImage2D(GLES10.GL_TEXTURE_2D, 0, mImage, 0);
	}

	@Override
	public void onLoadFromStream(InputStream stream) {
		mImage = BitmapFactory.decodeStream(stream);
	}
	
	protected Bitmap mImage = null;
}
