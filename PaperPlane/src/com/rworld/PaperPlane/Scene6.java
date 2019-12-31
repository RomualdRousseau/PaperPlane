package com.rworld.PaperPlane;

import com.rworld.PaperPlane.R;
import com.rworld.PaperPlane.effects.CopperBars;
import com.rworld.PaperPlane.effects.WaveHorizontalText;
import com.rworld.PaperPlane.textures.MoireBuilder;
import com.rworld.PaperPlane.textures.PlasmaBuilder;
import com.rworld.core.v2.GameActivity;
import com.rworld.core.v2.GameInput;
import com.rworld.core.v2.GameScene;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.Surface;
import com.rworld.core.v2.math.FastMath;

import android.opengl.GLES11;

public class Scene6 extends SceneBase {
	
	public Scene6(GameActivity activity) {
		super(activity);
		next = new Scene7(mActivity);
		duration = 20.0f;
		fadeInColor = new float[]{1.0f, 1.0f, 1.0f, 0.0f};
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mCopperBars != null) {
			mCopperBars.dispose();
			mCopperBars = null;
		}
		if(mSinusScroller != null) {
			mSinusScroller.dispose();
			mSinusScroller = null;
		}
		if(mMoire != null) {
			mMoire.release();
			mMoire = null;
		}
		if(mPlasma != null) {
			mPlasma.release();
			mPlasma = null;
		}
	}
	
	@Override
	protected GameScene onUpdate(float time, GameInput input) {
		if((mTimeLine >= 0) && (mTimeLine < 3)) {
			mEffect[0] -= GraphicManager.displayWidth * time / 2;
			if(mEffect[0] < 10) {
				mEffect[0] = 10;
			}
		}
		else if((mTimeLine >= (duration / 2 - 3)) && (mTimeLine < (duration / 2))) {
			mEffect[0] += GraphicManager.displayWidth * time / 2;
			if(mEffect[0] > GraphicManager.displayWidth) {
				mEffect[0] = GraphicManager.displayWidth;
			}
		}
		else if((mTimeLine >= (duration / 2)) && (mTimeLine < (duration / 2 + 3))) {
			mEffect[0] -= GraphicManager.displayWidth * time / 2;
			if(mEffect[0] < 10) {
				mEffect[0] = 10;
			}
		}
		mCopperBars.update(time);
		mSinusScroller.update(time);
		return super.onUpdate(time, input);
	}

	@Override
	protected void onRender() {
		GLES11.glClear(GLES11.GL_COLOR_BUFFER_BIT | GLES11.GL_DEPTH_BUFFER_BIT);
		// Layer 1
		mGraphicManager.enterView2D();
		if(mTimeLine < duration / 2) {
			mGraphicManager.drawSurface2D(mMoire, mEffect[0], mEffect[1], GraphicManager.displayWidth - 20, GraphicManager.displayHeight * 2 / 3 - 30);
		}
		else {
			mGraphicManager.drawSurface2D(mPlasma, mEffect[0], mEffect[1], GraphicManager.displayWidth - 20, GraphicManager.displayHeight * 2 / 3 - 30);
		}
		mCopperBars.render();
		mSinusScroller.render();
		super.onRender();
		mGraphicManager.leaveView2D();
	}
	
	@Override
	protected void onLoadAudioAssets() {
		super.onLoadAudioAssets();
		mAudioManager.loadMusic("musics/scene6.ogg", false);
	}
	
	@Override
	protected void onLoadGraphicAssets() {
		super.onLoadGraphicAssets();
		if(mPlasma == null) {
			mPlasma = new Surface(0, 0, 64, 64, mGraphicManager.createDynamicTexture());
		}
		if(mMoire == null) {
			mMoire = new Surface(0, 0, 128, 128, mGraphicManager.createDynamicTexture());
		}
		if(mSinusScroller == null) {
			int fontScale = GraphicManager.displayHeight / 240;
			mSinusScroller = new WaveHorizontalText(mGraphicManager, mFont, fontScale, GraphicManager.displayHeight - GraphicManager.displayHeight / 6 - 20, GraphicManager.displayHeight / 12, 4 * FastMath.PI, mActivity.getResources().getString(R.string.scene6_message));
		}
		if(mCopperBars == null) {
			mCopperBars = new CopperBars(mGraphicManager, GraphicManager.displayHeight - GraphicManager.displayHeight / 6 - 20, GraphicManager.displayHeight / 6, 2 * FastMath.PI);
		}
		mEffect[0] = GraphicManager.displayWidth;
	}
	
	@Override
	protected void ensureGraphicMeshes() {
		super.ensureGraphicMeshes();
		mPlasma.texture.ensureData(new PlasmaBuilder());
		mMoire.texture.ensureData(new MoireBuilder());
	}
	
	protected Surface mPlasma = null;
	protected Surface mMoire = null;
	protected WaveHorizontalText mSinusScroller = null;
	protected CopperBars mCopperBars = null;
	protected int[] mEffect = {10, 10};
}
