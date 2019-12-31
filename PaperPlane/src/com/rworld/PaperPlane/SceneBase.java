package com.rworld.PaperPlane;

import android.opengl.GLES11;

import com.rworld.core.v2.GameActivity;
import com.rworld.core.v2.GameInput;
import com.rworld.core.v2.GameScene;
import com.rworld.core.v2.graphics.Font;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.Surface;
import com.rworld.core.v2.graphics.builders.SolidColorBuilder;

public abstract class SceneBase extends GameScene {
	
	public GameScene next = null;
	public float duration = 20.0f;
	public boolean fadeInEffect = true;
	public float[] fadeInColor = {0.0f, 0.0f, 0.0f, 0.0f};
	public boolean fadeOutEffect = true;
	public float[] fadeOutColor = {0.0f, 0.0f, 0.0f, 0.0f};
	
	public SceneBase(GameActivity activity) {
		super(activity);
	}
	
	@Override
	protected GameScene onUpdate(float time, GameInput input) {
		if(input.isBackButtonTouched) {
			return null;
		}
		else if(mTimeLine >= duration) {
			return next;
		}
		else {
			mTimeLine += time;
			mBenchmark.update(time);
			return this;
		}
	}
	
	@Override
	protected void onPreRender() {
		GLES11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	@Override
	protected void onRender() {
		if(fadeInEffect && (mTimeLine >= 0.0f) && (mTimeLine < 3.0f)) {
			fadeInColor[3] = 1.0f - Math.min(mTimeLine / 2.5f, 1.0f);
			mGraphicManager.drawSurface2D(mFadeSurface, fadeInColor, 0, 0, GraphicManager.displayWidth, GraphicManager.displayHeight);
		}
		if(fadeOutEffect && (mTimeLine >= (duration - 3.0f))) {
			fadeOutColor[3] = Math.min((mTimeLine - (duration - 3.0f)) / 2.5f, 1.0f);
			mGraphicManager.drawSurface2D(mFadeSurface, fadeOutColor, 0, 0, GraphicManager.displayWidth, GraphicManager.displayHeight);
		}
	}
	
	@Override
	protected void onLoadGraphicAssets() {
		super.onLoadGraphicAssets();
		if(mFadeSurface == null) {
			mFadeSurface = new Surface(0, 0, 2, 2, mGraphicManager.createStaticTexture());
		}
		if(mFont == null) {
			mFont = new Font(32, 42, mGraphicManager.textureManager.get("fonts/megatron.png"));
		}
	}
	
	@Override
	protected void ensureGraphicMeshes() {
		super.ensureGraphicMeshes();
		mFadeSurface.texture.ensureData(SolidColorBuilder.WHITE);
	}

	protected Benchmark mBenchmark = new Benchmark();
	protected float mTimeLine = 0.0f;
	protected Surface mFadeSurface = null;
	protected Font mFont = null;
}
