package com.rworld.PaperPlane;

import android.opengl.GLES11;

import com.rworld.PaperPlane.meshes.PaperPlaneMesh;
import com.rworld.PaperPlane.meshes.SkyboxMesh;
import com.rworld.PaperPlane.physics.PaperPlaneSystem;
import com.rworld.core.v2.GameActivity;
import com.rworld.core.v2.GameInput;
import com.rworld.core.v2.GameScene;

public class Scene3 extends SceneBase {
	
	public Scene3(GameActivity activity) {
		super(activity);
		next = new Scene4(mActivity);
		duration = 35.0f;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mSkyBox != null) {
			mSkyBox.dispose();
		}
		/* reused by next scene
		if(mPaperPlaneMesh != null) {
			mPaperPlaneMesh.dispose();
			mPaperPlaneMesh = null;
		}
		*/
		if(mCamera != null) {
			mCamera.dispose();
			mCamera = null;
		}
	}
	
	@Override
	protected GameScene onUpdate(float time, GameInput input) {
		mBoidSystem.update(time);
		mCamera.update(time, mBoidSystem.gravityCenter, -2.0f);
		mSkyBox.update(time, mCamera.mEyeDirection);
		return super.onUpdate(time, input);
	}
	
	@Override
	protected void onRender() {
		GLES11.glClear(GLES11.GL_COLOR_BUFFER_BIT | GLES11.GL_DEPTH_BUFFER_BIT);
		mCamera.render();
		// Layer 1
		mSkyBox.render();
		mPaperPlaneMesh.render(mBoidSystem);
		// Layer 2
		mGraphicManager.enterView2D();
		super.onRender();
		mGraphicManager.leaveView2D();
	}
	
	@Override
	protected void onLoadAudioAssets() {
		super.onLoadAudioAssets();
		mAudioManager.loadMusic("musics/scene3.ogg", false);
	}
	
	@Override
	protected void onLoadGraphicAssets() {
		super.onLoadGraphicAssets();
		if(mCamera == null) {
			mCamera = new Camera3(mGraphicManager);
		}
		if(mSkyBox == null) {
			mSkyBox = new SkyboxMesh(mGraphicManager);
		}
		if(mPaperPlaneMesh == null) {
			mPaperPlaneMesh = new PaperPlaneMesh(mGraphicManager);
		}
	}

	protected Camera3 mCamera = null;
	protected PaperPlaneSystem mBoidSystem = new PaperPlaneSystem();
	protected SkyboxMesh mSkyBox = null;
	protected PaperPlaneMesh mPaperPlaneMesh = null;
}
