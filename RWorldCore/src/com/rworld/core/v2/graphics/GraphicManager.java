package com.rworld.core.v2.graphics;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.rworld.core.v2.GameActivity;

public abstract class GraphicManager {

	public static int displayWidth = 0;
	public static int displayHeight = 0;
	public static float displayZFar = 10.0f;
	
	public GL10 gl = null;
	public TextureManager textureManager = null;
	public MeshManager meshManager = null;
	
	public abstract ITexture createStaticTexture();
	
	public abstract ITexture createDynamicTexture();
	
	public abstract IMesh createNullMesh();
	
	public abstract IMesh createStaticMesh();
	
	public abstract IMesh createDynamicMesh();
	
	public abstract IBillboard createBillboard();
	
	public abstract void configureGraphicContext(EGLConfig config);
	
	public abstract void enterView2D();
	
	public abstract void leaveView2D();
	
	public abstract void drawSurface2D(Surface surface, int x, int y, int width, int height);
	
	public abstract void drawSurface2D(Surface surface, float[] color, int x, int y, int width, int height);
	
	public abstract void drawText2D(Font font, int scale, int x, int y, String text);
	
	public abstract void drawText2D(Font font, int scale, float[] color, int x, int y, String text);
	
	public GraphicManager(GameActivity activity) {
		mActivity = activity;
		textureManager = new TextureManager(this);
		meshManager = new MeshManager(this);
	}
	
	public void configureViewport(int width, int height) {
		displayWidth = width;
		displayHeight = height;
	}

	protected GameActivity mActivity;
}
