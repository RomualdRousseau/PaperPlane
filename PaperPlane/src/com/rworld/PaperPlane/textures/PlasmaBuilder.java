package com.rworld.PaperPlane.textures;

import java.nio.IntBuffer;

import android.opengl.GLES11;

import com.rworld.core.v2.graphics.TextureBuilder;
import com.rworld.core.v2.math.FixedMath;

public class PlasmaBuilder extends TextureBuilder {
	
	public PlasmaBuilder() {
		for(int i = 0; i < 256; i++) {
			int r = (0xFF * (256 - i) + 0xFF * i) >> 8;
			int g = (0xFF * (256 - i) + 0x86 * i) >> 8;
			int b = (0x00 * (256 - i) + 0x00 * i) >> 8;
			mColor[i] = (r << 0) + (g << 8) + (b << 16) + (255 << 24);
		}
	}

	
	@Override
	public void createTextureData() {
		build();
		GLES11.glTexImage2D(GLES11.GL_TEXTURE_2D, 0, GLES11.GL_RGBA, 64, 64, 0, GLES11.GL_RGBA, GLES11.GL_UNSIGNED_BYTE, IntBuffer.wrap(mBitmap));
	}
	
	@Override
	public void updateTextureData() {
		build();
		GLES11.glTexSubImage2D(GLES11.GL_TEXTURE_2D, 0, 0, 0, 64, 64, GLES11.GL_RGBA, GLES11.GL_UNSIGNED_BYTE, IntBuffer.wrap(mBitmap));
	}
	
	protected void build() {
		final int a = FixedMath.cos(mTime);
		final int b = FixedMath.sin(mTime);
		final int t = FixedMath.ONE - a;
		final int c = FixedMath.cos(t);
		final int d = FixedMath.sin(t);
		
		int t1c = FixedMath.cos(t * 4);
		int t1s = FixedMath.sin(t * 4);
		int t2c = (c * a + d * b) >> FixedMath.PRECISION;
		int t2s = (d * a + c * b) >> FixedMath.PRECISION;
		int t3c = t1c;
		int t3s = t1s;
		int t4c = t2c;
		int t4s = t2s;
		
		int cos = 0;
		int sin = 0;
		int off = 0;
		
		for(int i = 0; i < 64; i++) {
			for(int j = 0; j < 64; j++) {
				mBitmap[off++] = mColor[(FixedMath.SINTABLE[((t3s << 1) + (t4s << 2)) & 2047] >> 3) + 128];
				
				cos = t3c; sin = t3s;
				t3c = cos + (sin >> 4);
				t3s = sin - (cos >> 4);
				
				cos = t4c; sin = t4s;
				t4c = cos + (sin >> 4);
				t4s = sin - (cos >> 4);
			}
			
			cos = t1c; sin = t1s;
			t1c = cos + (sin >> 4);
			t1s = sin - (cos >> 4);
			
			t3c = t1c;
			t3s = t1s;
			t4c = t2c;
			t4s = t2s;
		}
		
		mTime += 25;
	}
	
	protected int[] mBitmap = new int[64 * 64];
	protected int[] mColor = new int[256];
	protected int mTime = 0;
}
