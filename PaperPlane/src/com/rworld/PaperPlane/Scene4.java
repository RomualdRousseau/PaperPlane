package com.rworld.PaperPlane;

import com.rworld.PaperPlane.meshes.JetEngineHalo;
import com.rworld.PaperPlane.meshes.PaperPlaneMesh;
import com.rworld.PaperPlane.meshes.PortalMesh;
import com.rworld.PaperPlane.physics.JetEngineSystem;
import com.rworld.core.v2.GameActivity;
import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.GameInput;
import com.rworld.core.v2.GameScene;

import android.opengl.GLES11;

public class Scene4 extends SceneBase {
	
	public Scene4(GameActivity activity) {
		super(activity);
		next = new Scene5(mActivity);
		duration = 34.0f;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mPaperPlaneMesh != null) {
			mPaperPlaneMesh.dispose();
			mPaperPlaneMesh = null;
		}
		if(mPortalMesh != null) {
			mPortalMesh.dispose();
			mPortalMesh = null;
		}
		if(mCamera != null) {
			mCamera.dispose();
			mCamera = null;
		}
	}
	
	@Override
	protected GameScene onUpdate(float time, GameInput input) {
		mCamera.update(time);
		mPortalMesh.update(time);
		mExplosionSystem.update(time);
		return super.onUpdate(time, input);
	}
	
	@Override
	protected void onPreRender() {
		super.onPreRender();
		GLES11.glHint(GLES11.GL_FOG_HINT, GLES11.GL_DONT_CARE);
		GLES11.glFogf(GLES11.GL_FOG_MODE, GLES11.GL_EXP);
		GLES11.glFogf(GLES11.GL_FOG_DENSITY, 0.4f);
        GLES11.glFogfv(GLES11.GL_FOG_COLOR, new float[]{0.0f, 0.0f, 0.0f, 0.0f}, 0);
		GLES11.glFogf(GLES11.GL_FOG_START, 5.0f);
		GLES11.glFogf(GLES11.GL_FOG_END, 100.0f);
	}
	
	@Override
	protected void onRender() {
		GLES11.glClear(GLES11.GL_COLOR_BUFFER_BIT | GLES11.GL_DEPTH_BUFFER_BIT);
		mCamera.render();
		mLight.render(mPaperPlaneMesh.entity);
		// Layer 1
		GLES11.glEnable(GLES11.GL_LIGHTING);
		GLES11.glEnable(GLES11.GL_FOG);
		mPortalMesh.render(mCamera);
		GLES11.glDisable(GLES11.GL_FOG);
		GLES11.glDisable(GLES11.GL_LIGHTING);
		// Layer 2
		GLES11.glPushMatrix();
		GLES11.glLoadIdentity();
		mPaperPlaneMesh.render(false);
		mExplosionHalo.render(mExplosionSystem);
		GLES11.glPopMatrix();
		// Layer 3
		mGraphicManager.enterView2D();
		super.onRender();
		mGraphicManager.leaveView2D();
	}
	
	@Override
	protected void onLoadAudioAssets() {
		super.onLoadAudioAssets();
		mAudioManager.loadMusic("musics/scene4.ogg", false);
	}
	
	@Override
	protected void onLoadGraphicAssets() {
		super.onLoadGraphicAssets();
		if(mCamera == null) {
			mCamera = new Camera2(mGraphicManager);
		}
		if(mLight == null) {
			mLight = new Light(mGraphicManager);
		}
		if(mPortalMesh == null) {
			mPortalMesh = new PortalMesh(mGraphicManager);
		}
		if(mPaperPlaneMesh == null) {
			mPaperPlaneMesh = new PaperPlaneMesh(mGraphicManager);
			mPaperPlaneMesh.entity = mExplosionSystem.emitter;
		}
		if(mExplosionHalo == null) {
			mExplosionHalo = new JetEngineHalo(mGraphicManager);
		}
	}
	
	@Override
	protected void ensureGraphicMeshes() {
		super.ensureGraphicMeshes();
		mLight.ensureData();
		mExplosionHalo.ensureData(mExplosionSystem);
	}

	protected JetEngineSystem mExplosionSystem = new JetEngineSystem(new GameEntity(new float[]{0.0f, -0.3f, -1.5f}));
	
	protected Camera2 mCamera = null;
	protected Light mLight = null;
	protected PortalMesh mPortalMesh = null;
	protected PaperPlaneMesh mPaperPlaneMesh = null;
	protected JetEngineHalo mExplosionHalo = null;
}
