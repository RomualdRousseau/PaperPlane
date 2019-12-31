package com.rworld.PaperPlane;

import android.opengl.GLES11;

import com.rworld.PaperPlane.meshes.TunnelMesh;
import com.rworld.core.v2.GameActivity;
import com.rworld.core.v2.GameInput;
import com.rworld.core.v2.GameScene;

public class Scene7 extends SceneBase {
	
	public Scene7(GameActivity activity) {
		super(activity);
		next = new Scene8(mActivity);
		duration = 23.0f;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mTunnelMesh != null) {
			mTunnelMesh.dispose();
			mTunnelMesh = null;
		}
		if(mCamera != null) {
			mCamera.dispose();
			mCamera = null;
		}
	}
	
	@Override
	protected GameScene onUpdate(float time, GameInput input) {
		mTunnelMesh.update(time);
		return super.onUpdate(time, input);
	}
	
	@Override
	protected void onPreRender() {
		super.onPreRender();
		GLES11.glHint(GLES11.GL_FOG_HINT, GLES11.GL_DONT_CARE);
		GLES11.glFogf(GLES11.GL_FOG_MODE, GLES11.GL_EXP);
		GLES11.glFogf(GLES11.GL_FOG_DENSITY, 0.2f);
        GLES11.glFogfv(GLES11.GL_FOG_COLOR, new float[]{0.0f, 0.0f, 0.0f, 0.0f}, 0);
		GLES11.glFogf(GLES11.GL_FOG_START, 2.0f);
		GLES11.glFogf(GLES11.GL_FOG_END, 100.0f);
	}
	
	@Override
	protected void onRender() {
		GLES11.glClear(GLES11.GL_COLOR_BUFFER_BIT | GLES11.GL_DEPTH_BUFFER_BIT);
		mCamera.render();
		// Layer 1
		GLES11.glEnable(GLES11.GL_FOG);
		mTunnelMesh.render();
		GLES11.glDisable(GLES11.GL_FOG);
		// Layer 2
		mGraphicManager.enterView2D();
		super.onRender();
		mGraphicManager.leaveView2D();
	}
	
	@Override
	protected void onLoadAudioAssets() {
		super.onLoadAudioAssets();
		mAudioManager.loadMusic("musics/scene7.ogg", false);
	}
	
	@Override
	protected void onLoadGraphicAssets() {
		super.onLoadGraphicAssets();
		if(mCamera == null) {
			mCamera = new Camera1(mGraphicManager);
		}
		if(mTunnelMesh == null) {
			mTunnelMesh = new TunnelMesh(mGraphicManager);
		}
	}
	
	@Override
	protected void ensureGraphicMeshes() {
		super.ensureGraphicMeshes();
		mTunnelMesh.ensureData();
	}
	
	protected Camera1 mCamera = null;
	protected TunnelMesh mTunnelMesh = null;
}
