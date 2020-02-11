package com.rworld.PaperPlane;

import android.opengl.GLES11;

import com.rworld.PaperPlane.meshes.PaperPlaneMesh;
import com.rworld.core.v2.GameActivity;
import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.GameInput;
import com.rworld.core.v2.GameScene;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.Surface;

public class Scene2 extends SceneBase {

    public Scene2(GameActivity activity) {
        super(activity);
        next = new Scene3(mActivity);
        fadeInEffect = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
		/* reused by next scene
		if(mPaperPlaneMesh != null) {
			mPaperPlaneMesh.dispose();
			mPaperPlaneMesh = null;
		}
		if(mBackground != null) {
			mGraphicManager.textureManager.release("textures/moonlight_skybox.jpg");
			mBackground = null;
		}
		*/
        if (mCamera != null) {
            mCamera.dispose();
            mCamera = null;
        }
    }

    @Override
    protected GameScene onUpdate(float time, GameInput input) {
        if (mBackgroundY < GraphicManager.displayHeight * 0.7f) {
            mBackgroundY += 50 * time;
        } else {
            mPaperPlaneMesh.entity.position[2] -= 0.05f * time;
        }
        return super.onUpdate(time, input);
    }

    @Override
    protected void onPreRender() {
        GLES11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES11.glClear(GLES11.GL_COLOR_BUFFER_BIT | GLES11.GL_DEPTH_BUFFER_BIT);
        mGraphicManager.enterView2D();
        mGraphicManager.drawSurface2D(mBackground, 0, 0, GraphicManager.displayWidth, GraphicManager.displayHeight * 2);
        mGraphicManager.leaveView2D();
    }

    @Override
    protected void onRender() {
        GLES11.glClear(GLES11.GL_COLOR_BUFFER_BIT | GLES11.GL_DEPTH_BUFFER_BIT);
        mCamera.render();
        // Layer 1
        mGraphicManager.enterView2D();
        mGraphicManager.drawSurface2D(mBackground, 0, -(int) mBackgroundY, GraphicManager.displayWidth, GraphicManager.displayHeight * 2);
        mGraphicManager.leaveView2D();
        // Layer 2
        mPaperPlaneMesh.render();
        // Layer 3
        mGraphicManager.enterView2D();
        super.onRender();
        mGraphicManager.leaveView2D();
    }

    @Override
    protected void onLoadAudioAssets() {
        super.onLoadAudioAssets();
        mAudioManager.loadMusic("musics/scene2.ogg", false);
    }

    @Override
    protected void onLoadGraphicAssets() {
        super.onLoadGraphicAssets();
        if (mCamera == null) {
            mCamera = new Camera1(mGraphicManager);
        }
        if (mBackground == null) {
            mBackground = new Surface(341, 0, -170, 256, mGraphicManager.textureManager.get("textures/moonlight_skybox.jpg"));
        }
        if (mPaperPlaneMesh == null) {
            mPaperPlaneMesh = new PaperPlaneMesh(mGraphicManager);
            mPaperPlaneMesh.entity = new GameEntity(new float[]{0.2f, 0.2f, 4.8f});
        }
    }

    protected Camera1 mCamera = null;
    protected Surface mBackground = null;
    protected float mBackgroundY = 0.0f;
    protected PaperPlaneMesh mPaperPlaneMesh = null;
}
