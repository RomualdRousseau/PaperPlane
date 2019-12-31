package com.rworld.PaperPlane;

import com.rworld.PaperPlane.R;
import com.rworld.PaperPlane.effects.PulseText;
import com.rworld.PaperPlane.meshes.CubeTextMesh;
import com.rworld.PaperPlane.meshes.StarFieldHalo;
import com.rworld.PaperPlane.physics.StarFieldSystem;
import com.rworld.core.v2.GameActivity;
import com.rworld.core.v2.GameInput;
import com.rworld.core.v2.GameScene;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.Surface;

import android.opengl.GLES11;

public class Scene1 extends SceneBase {
	
	public Scene1(GameActivity activity) {
		super(activity);
		next = new Scene2(mActivity);
		duration = 20.0f;
		fadeOutEffect = false;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mStarFieldHalo != null) {
			mStarFieldHalo.dispose();
			mStarFieldHalo = null;
		}
		if(mTitle != null) {
			mTitle.dispose();
			mTitle = null;
		}
		if(mCredits != null) {
			mCredits.dispose();
			mCredits = null;
		}
		/* reused by next scene
		if(mBackground != null) {
			mGraphicManager.textureManager.release("textures/moonlight_skybox.jpg");
			mBackground = null;
		}
		*/
		if(mLogo != null) {
			mGraphicManager.textureManager.release("textures/logo.jpg");
			mLogo = null;
		}
		if(mCamera != null) {
			mCamera.dispose();
			mCamera = null;
		}
	}
	
	@Override
	protected GameScene onUpdate(float time, GameInput input) {
		if((mTimeLine >= 0.0f) &&  (mTimeLine < (duration * 0.5f))) {
			mStarFieldSystem.update(time);
			mCredits.update(time);
		}
		else {
			mStarFieldSystem.update(time);
			mTitle.update(time);
		}
		return super.onUpdate(time, input);
	}
	
	@Override
	protected void onPreRender() {
		GLES11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GLES11.glClear(GLES11.GL_COLOR_BUFFER_BIT | GLES11.GL_DEPTH_BUFFER_BIT);
		mGraphicManager.enterView2D();
		mGraphicManager.drawSurface2D(mLogo, GraphicManager.displayWidth / 2 - 128, GraphicManager.displayHeight / 2 - 128, 256, 256);
		mGraphicManager.drawText2D(mFont, 1, GraphicManager.displayWidth - 32 * 10, GraphicManager.displayHeight - 42, mActivity.getResources().getString(R.string.loading_label));
		mGraphicManager.leaveView2D();
	}
	
	@Override
	protected void onRender() {
		GLES11.glClear(GLES11.GL_COLOR_BUFFER_BIT | GLES11.GL_DEPTH_BUFFER_BIT);
		mCamera.render();
		if((mTimeLine >= 0.0f) &&  (mTimeLine < (duration * 0.5f))) {
			// Layer 1
			mStarFieldHalo.render(mStarFieldSystem);
			// Layer 2
			mGraphicManager.enterView2D();
			mCredits.render();
			super.onRender();
			mGraphicManager.leaveView2D();
		}
		else {
			// Layer 1
			mGraphicManager.enterView2D();
			float c = mTimeLine / (duration * 0.5f) - 1.0f;
			mGraphicManager.drawSurface2D(mBackground, new float[]{1, 1, 1, c}, 0, 0, GraphicManager.displayWidth, GraphicManager.displayHeight);
			mGraphicManager.leaveView2D();
			// Layer 2
			mStarFieldHalo.render(mStarFieldSystem);
			mTitle.render();
			// Layer 3
			mGraphicManager.enterView2D();
			super.onRender();
			mGraphicManager.leaveView2D();
		}
	}
	
	@Override
	protected void onLoadAudioAssets() {
		super.onLoadAudioAssets();
		mAudioManager.loadMusic("musics/scene1.ogg", false);
	}
	
	@Override
	protected void onLoadGraphicAssets() {
		super.onLoadGraphicAssets();
		if(mCamera == null) {
			mCamera = new Camera1(mGraphicManager);
		}
		if(mLogo == null) {
			mLogo = new Surface(0, 0, 256, 256, mGraphicManager.textureManager.get("textures/logo.jpg"));
		}
		if(mBackground == null) {
			mBackground = new Surface(341, 0, -170, 128, mGraphicManager.textureManager.get("textures/moonlight_skybox.jpg"));
		}
		if(mStarFieldHalo == null) {
			mStarFieldHalo = new StarFieldHalo(mGraphicManager);
		}
		if(mTitle == null) {
			mTitle = new CubeTextMesh(mGraphicManager, mFont, 1, mActivity.getResources().getString(R.string.app_name));
		}
		if(mCredits == null) {
			mCredits = new PulseText(mGraphicManager, mFont, 1, 3.3f, 0.0f, mActivity.getResources().getStringArray(R.array.greeting_messages));
		}
	}
	
	@Override
	protected void ensureGraphicMeshes() {
		super.ensureGraphicMeshes();
		mStarFieldHalo.ensureData(mStarFieldSystem);
		mTitle.ensureData();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	protected Camera1 mCamera = null;
	protected Surface mLogo = null;
	protected Surface mBackground = null;
	protected StarFieldSystem mStarFieldSystem = new StarFieldSystem();
	protected StarFieldHalo mStarFieldHalo = null;
	protected CubeTextMesh mTitle = null;
	protected PulseText mCredits = null;
}
