package com.rworld.PaperPlane;

import android.opengl.GLES11;

import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.math.Vector;

public class Light {
	
	public Light(GraphicManager graphicManager) {
	}
	
	public void render(GameEntity entityToFollow) {
		GLES11.glPushMatrix();
		GLES11.glLoadIdentity();
		Vector.set(mPosition, entityToFollow.position);
		GLES11.glLightfv(GLES11.GL_LIGHT0, GLES11.GL_POSITION, mPosition, 0);
		GLES11.glPopMatrix();
	}
	
	public void ensureData() {
		GLES11.glEnable(GLES11.GL_LIGHT0);
		GLES11.glLightfv(GLES11.GL_LIGHT0, GLES11.GL_AMBIENT, new float[]{0.5f, 0.5f, 1.0f, 1.0f}, 0);
		GLES11.glLightfv(GLES11.GL_LIGHT0, GLES11.GL_DIFFUSE, new float[]{1.0f, 1.0f, 1.0f, 1.0f}, 0);
	}
	
	protected float[] mPosition = {0.0f, 0.0f, 0.0f, 1.0f};
}
