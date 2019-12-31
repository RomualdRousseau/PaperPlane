package com.rworld.PaperPlane;

import android.opengl.GLES11;

import com.rworld.PaperPlane.R;
import com.rworld.PaperPlane.effects.WaveVerticalText;
import com.rworld.PaperPlane.meshes.TentacleMesh;
import com.rworld.core.v2.GameActivity;
import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.GameInput;
import com.rworld.core.v2.GameScene;
import com.rworld.core.v2.math.FastMath;

public class Scene5 extends SceneBase {
	
	public Scene5(GameActivity activity) {
		super(activity);
		next = new Scene6(mActivity);
		duration = 23.0f;
		fadeOutColor = new float[]{1.0f, 1.0f, 1.0f, 0.0f};
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mTentacleMesh != null) {
			mTentacleMesh.dispose();
			mTentacleMesh = null;
		}
		if(mSinusScroller1 != null) {
			mSinusScroller1.dispose();
			mSinusScroller1 = null;
		}
		if(mSinusScroller2 != null) {
			mSinusScroller2.dispose();
			mSinusScroller2 = null;
		}
		if(mCamera != null) {
			mCamera.dispose();
			mCamera = null;
		}
	}
	
	@Override
	protected GameScene onUpdate(float time, GameInput input) {
		mCamera.update(time, mTentacleMesh.entity, 2.0f);
		mTentacleMesh.update(time);
		mSinusScroller1.update(time);
		mSinusScroller2.update(time);
		return super.onUpdate(time, input);
	}
	
	@Override
	protected void onRender() {
		GLES11.glClear(GLES11.GL_COLOR_BUFFER_BIT | GLES11.GL_DEPTH_BUFFER_BIT);
		mCamera.render();
		mLight.render(GameEntity.NULL);
		// Layer 1
		GLES11.glEnable(GLES11.GL_LIGHTING);
		mTentacleMesh.render();
		GLES11.glDisable(GLES11.GL_LIGHTING);
		// Layer 2
		mGraphicManager.enterView2D();
		mSinusScroller1.render();
		mSinusScroller2.render();
		super.onRender();
		mGraphicManager.leaveView2D();
	}
	
	@Override
	protected void onLoadAudioAssets() {
		super.onLoadAudioAssets();
		mAudioManager.loadMusic("musics/scene5.ogg", false);
	}
	
	@Override
	protected void onLoadGraphicAssets() {
		super.onLoadGraphicAssets();
		if(mCamera == null) {
			mCamera = new Camera4(mGraphicManager);
		}
		if(mLight == null) {
			mLight = new Light(mGraphicManager);
		}
		if(mSinusScroller1 == null) {
			mSinusScroller1 = new WaveVerticalText(mGraphicManager, mFont, 2, 100, 50, 2 * FastMath.PI, mActivity.getResources().getString(R.string.scene5_message));
		}
		if(mSinusScroller2 == null) {
			mSinusScroller2 = new WaveVerticalText(mGraphicManager, mFont, 2, 100, 50, -2 * FastMath.PI, mActivity.getResources().getString(R.string.scene5_message));
		}
		if(mTentacleMesh == null) {
			mTentacleMesh = new TentacleMesh(mGraphicManager);
		}
	}
	
	@Override
	protected void ensureGraphicMeshes() {
		super.ensureGraphicMeshes();
		mLight.ensureData();
		mTentacleMesh.ensureData();
	}

	protected Camera4 mCamera = null;
	protected Light mLight = null;
	protected WaveVerticalText mSinusScroller1 = null;
	protected WaveVerticalText mSinusScroller2 = null;
	protected TentacleMesh mTentacleMesh = null;
}
