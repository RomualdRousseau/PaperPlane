package com.rworld.PaperPlane;

import android.opengl.GLES11;

import com.rworld.PaperPlane.effects.PulseText;
import com.rworld.PaperPlane.meshes.PaperPlaneMesh;
import com.rworld.core.v2.GameActivity;
import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.GameInput;
import com.rworld.core.v2.GameScene;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.Surface;

public class Scene9 extends SceneBase {

    public Scene9(GameActivity activity) {
        super(activity);
        next = null;
        duration = 6.0f;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPaperPlaneMesh != null) {
            mPaperPlaneMesh.dispose();
            mPaperPlaneMesh = null;
        }
        if (mBackground != null) {
            mGraphicManager.textureManager.release("textures/theend.jpg");
            mBackground = null;
        }
        if (mCamera != null) {
            mCamera.dispose();
            mCamera = null;
        }
    }

    @Override
    protected GameScene onUpdate(float time, GameInput input) {
        mPaperPlaneMesh.entity.position[0] -= 0.8f * time;
        mPaperPlaneMesh.entity.position[1] -= 0.06f * time;
        mPaperPlaneMesh.entity.position[2] += 0.8f * time;
        mTheEnd.update(time, GraphicManager.displayWidth - 32 * 5, GraphicManager.displayHeight - 42);
        return super.onUpdate(time, input);
    }

    @Override
    protected void onRender() {
        GLES11.glClear(GLES11.GL_COLOR_BUFFER_BIT | GLES11.GL_DEPTH_BUFFER_BIT);
        mCamera.render();
        // Layer 1
        mGraphicManager.enterView2D();
        mGraphicManager.drawSurface2D(mBackground, 0, 0, GraphicManager.displayWidth, GraphicManager.displayHeight);
        mGraphicManager.leaveView2D();
        // Layer 2
        mPaperPlaneMesh.render();
        // Layer 3
        mGraphicManager.enterView2D();
        mTheEnd.render();
        super.onRender();
        mGraphicManager.leaveView2D();
    }

    @Override
    protected void onLoadGraphicAssets() {
        super.onLoadGraphicAssets();
        if (mCamera == null) {
            mCamera = new Camera1(mGraphicManager);
        }
        if (mBackground == null) {
            mBackground = new Surface(0, 0, 512, 256, mGraphicManager.textureManager.get("textures/theend.jpg"));
        }
        if (mPaperPlaneMesh == null) {
            mPaperPlaneMesh = new PaperPlaneMesh(mGraphicManager);
            mPaperPlaneMesh.entity = new GameEntity(new float[]{2.0f, 0.0f, 2.0f}, new float[]{0.0f, 135.0f, 0.0f});
        }
        if (mTheEnd == null) {
            mTheEnd = new PulseText(mGraphicManager, mFont, 1, 3.0f, 1.0f, mActivity.getResources().getStringArray(R.array.theend_messages));
        }
    }

    protected Camera1 mCamera = null;
    protected Surface mBackground = null;
    protected PaperPlaneMesh mPaperPlaneMesh = null;
    protected PulseText mTheEnd = null;
}
