package com.rworld.PaperPlane.meshes;

import android.opengl.GLES11;

import com.rworld.PaperPlane.physics.JetEngineSystem;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.IBillboard;

public class JetEngineHalo {

	public JetEngineHalo(GraphicManager graphicManager) {
		mGraphicManager = graphicManager;
		mBillboard = mGraphicManager.createBillboard();
		mBillboard.setTexture(mGraphicManager.textureManager.get("textures/halo.png"));
		mBillboard.enableAlphaMask(true);
	}
	
	public void dispose() {
		mBillboard.release();
		mGraphicManager.textureManager.release("textures/halo.png");
	}
	
	public void render(JetEngineSystem explosionSystem) {
		GLES11.glDisable(GLES11.GL_DEPTH_TEST);
		GLES11.glEnable(GLES11.GL_BLEND);
		GLES11.glBlendFunc(GLES11.GL_SRC_ALPHA, GLES11.GL_ONE);
		mBillboard.draw(explosionSystem.entities, false);
		GLES11.glBlendFunc(GLES11.GL_SRC_ALPHA, GLES11.GL_ONE_MINUS_SRC_ALPHA);
		GLES11.glDisable(GLES11.GL_BLEND);
		GLES11.glEnable(GLES11.GL_DEPTH_TEST);
	}
	
	public void ensureData(JetEngineSystem explosionSystem) {
		mBillboard.ensureData(explosionSystem.getBufferSize(), 64);
	}

	protected GraphicManager mGraphicManager = null;
	protected IBillboard mBillboard = null;
}
