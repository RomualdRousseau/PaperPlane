package com.rworld.core.v2.graphics.GLES11;

import javax.microedition.khronos.egl.EGLConfig;

import android.opengl.GLES10;
import android.opengl.GLES11;
import android.opengl.GLES11Ext;

import com.rworld.core.v2.GameActivity;
import com.rworld.core.v2.graphics.Font;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.IBillboard;
import com.rworld.core.v2.graphics.IMesh;
import com.rworld.core.v2.graphics.ITexture;
import com.rworld.core.v2.graphics.Surface;

public class GraphicManagerImpl extends GraphicManager {

	public GraphicManagerImpl(GameActivity activity) {
		super(activity);
	}
	
	@Override
	public void configureGraphicContext(EGLConfig config) {
		GLES10.glShadeModel(GLES10.GL_SMOOTH);
		GLES10.glHint(GLES10.GL_PERSPECTIVE_CORRECTION_HINT, GLES10.GL_FASTEST);
		
		GLES10.glEnable(GLES10.GL_POINT_SMOOTH);
		GLES10.glHint(GLES10.GL_POINT_SMOOTH_HINT, GLES10.GL_FASTEST);

		GLES10.glCullFace(GLES10.GL_BACK);
		GLES10.glFrontFace(GLES10.GL_CCW);
		GLES10.glEnable(GLES10.GL_CULL_FACE);
		
		GLES10.glDepthFunc(GLES10.GL_LEQUAL);
		GLES10.glEnable(GLES10.GL_DEPTH_TEST);

		GLES10.glBlendFunc(GLES10.GL_SRC_ALPHA, GLES10.GL_ONE_MINUS_SRC_ALPHA);
		GLES10.glDisable(GLES10.GL_BLEND);
		
		GLES10.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		GLES10.glClearDepthf(1.0f);

		GLES10.glEnable(GLES10.GL_TEXTURE_2D);
	}

	@Override
	public void configureViewport(int width, int height) {
		super.configureViewport(width, height);
		GLES10.glViewport(0, 0, width, height);
		
		if (height == 0) {
			height = 1;
		}
		
		GLES10.glMatrixMode(GLES10.GL_PROJECTION);
		GLES10.glLoadIdentity();
		final float pov = (float) Math.tan(Math.toRadians(45)* 0.5f) * 0.1f;
		final float ratio = pov * width / height;
		GLES10.glFrustumf(-ratio, ratio, -pov, pov, 0.1f, displayZFar);
		GLES10.glMatrixMode(GLES10.GL_MODELVIEW);
	}
	
	@Override
	public ITexture createStaticTexture() {
		return new StaticTextureImpl();
	}
	
	@Override
	public ITexture createDynamicTexture() {
		return new DynamicTextureImpl();
	}
	
	@Override
	public IMesh createNullMesh() {
		return new NullMeshImpl();
	}
	
	@Override
	public IMesh createDynamicMesh() {
		return new DynamicMeshImpl();
	}
	
	@Override
	public IMesh createStaticMesh() {
		return new StaticMeshImpl();
	}

	@Override
	public IBillboard createBillboard() {
		return new BillboardImpl();
	}
	
	@Override
	public void enterView2D() {
		GLES10.glDisable(GLES10.GL_CULL_FACE);
		GLES10.glDisable(GLES10.GL_DEPTH_TEST);
		GLES10.glEnable(GLES10.GL_BLEND);
	}

	@Override
	public void leaveView2D() {
		GLES10.glDisable(GLES10.GL_BLEND);
		GLES10.glEnable(GLES10.GL_DEPTH_TEST);
		GLES10.glEnable(GLES10.GL_CULL_FACE);
	}
	
	@Override
	public void drawSurface2D(Surface surface, int x, int y, int width, int height) {
		mRect[0] = x;
		mRect[1] = displayHeight - y - height;
		mRect[2] = width;
		mRect[3] = height;
		
		mCrop[0] = surface.x;
		mCrop[1] = surface.y + surface.height;
		mCrop[2] = surface.width;
		mCrop[3] = -surface.height;
		
		surface.texture.bind();
		
		GLES11.glTexParameteriv(GLES10.GL_TEXTURE_2D, GLES11Ext.GL_TEXTURE_CROP_RECT_OES, mCrop, 0);
		GLES11Ext.glDrawTexiOES(mRect[0], mRect[1], 0, mRect[2], mRect[3]);
	}
	
	@Override
	public void drawSurface2D(Surface surface, float[] color, int x, int y, int width, int height) {
		mRect[0] = x;
		mRect[1] = displayHeight - y - height;
		mRect[2] = width;
		mRect[3] = height;
		
		mCrop[0] = surface.x;
		mCrop[1] = surface.y + surface.height;
		mCrop[2] = surface.width;
		mCrop[3] = -surface.height;
		
		surface.texture.bind();
		
		GLES10.glColor4f(color[0], color[1], color[2], color[3]);
		GLES11.glTexParameteriv(GLES10.GL_TEXTURE_2D, GLES11Ext.GL_TEXTURE_CROP_RECT_OES, mCrop, 0);
		GLES11Ext.glDrawTexiOES(mRect[0], mRect[1], 0, mRect[2], mRect[3]);
		GLES10.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	@Override
	public void drawText2D(Font font, int scale, int x, int y, String text) {
		mRect[0] = x;
		mRect[1] = displayHeight - y - font.height * scale;
		mRect[2] = font.width * scale;
		mRect[3] = font.height * scale;
		
		mCrop[2] = font.width - 1;
		mCrop[3] = -font.height + 1;
		
		font.texture.bind();
		
		final int originX = mRect[0];
		final int l = text.length();
		for(int i = 0; i < l; i++) {
			if(text.charAt(i) == '\n') {
				mRect[0] = originX - mRect[2];
				mRect[1] -= mRect[3];
			}
			else if(text.charAt(i) >= ' ') {
				final int c = text.codePointAt(i) - 32;
				mCrop[0] = (c & 15) * font.width;
				mCrop[1] = ((c >> 4) + 1) * font.height;
				GLES11.glTexParameteriv(GLES10.GL_TEXTURE_2D, GLES11Ext.GL_TEXTURE_CROP_RECT_OES, mCrop, 0);
				GLES11Ext.glDrawTexiOES(mRect[0], mRect[1], 0, mRect[2], mRect[3]);
			}
			mRect[0] += mRect[2];
		}
	}
	
	@Override
	public void drawText2D(Font font, int scale, float[] color, int x, int y, String text) {
		mRect[0] = x;
		mRect[1] = displayHeight - y - font.height * scale;
		mRect[2] = font.width * scale;
		mRect[3] = font.height * scale;
		
		mCrop[2] = font.width - 1;
		mCrop[3] = -font.height + 1;
		
		font.texture.bind();
		
		GLES10.glColor4f(color[0], color[1], color[2], color[3]);
		final int originX = mRect[0];
		final int l = text.length();
		for(int i = 0; i < l; i++) {
			if(text.charAt(i) == '\n') {
				mRect[0] = originX - mRect[2];
				mRect[1] -= mRect[3];
			}
			else if(text.charAt(i) >= ' ') {
				final int c = text.codePointAt(i) - 32;
				mCrop[0] = (c & 15) * font.width;
				mCrop[1] = ((c >> 4) + 1) * font.height;
				GLES11.glTexParameteriv(GLES10.GL_TEXTURE_2D, GLES11Ext.GL_TEXTURE_CROP_RECT_OES, mCrop, 0);
				GLES11Ext.glDrawTexiOES(mRect[0], mRect[1], 0, mRect[2], mRect[3]);
			}
			mRect[0] += mRect[2];
		}
		GLES10.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	protected int[] mRect = {0, 0, 0, 0};
	protected int[] mCrop = {0, 0, 0, 0};
}
