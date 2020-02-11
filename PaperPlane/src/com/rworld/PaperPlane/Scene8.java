package com.rworld.PaperPlane;

import android.opengl.GLES11;

import com.rworld.PaperPlane.effects.PulseText;
import com.rworld.PaperPlane.meshes.CityMesh;
import com.rworld.PaperPlane.meshes.PaperPlaneMesh;
import com.rworld.PaperPlane.physics.PathEntity;
import com.rworld.core.v2.GameActivity;
import com.rworld.core.v2.GameInput;
import com.rworld.core.v2.GameScene;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.Surface;

public class Scene8 extends SceneBase {

    public Scene8(GameActivity activity) {
        super(activity);
        next = new Scene9(mActivity);
        duration = 32.0f;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
		/* reused by next scene
		if(mPaperPlane != null) {
			mPaperPlane.dispose();
			mPaperPlane = null;
		}
		*/
        if (mCredits != null) {
            mCredits.dispose();
            mCredits = null;
        }
        if (mCityMesh != null) {
            mCityMesh.dispose();
            mCityMesh = null;
        }
        if (mBackground != null) {
            mGraphicManager.textureManager.release("textures/city_skybox.jpg");
            mBackground = null;
        }
        if (mCamera != null) {
            mCamera.dispose();
            mCamera = null;
        }
    }

    @Override
    protected GameScene onUpdate(float time, GameInput input) {
        mCamera.update(time, mPaperPlaneMesh.entity, 1.0f);
        mPaperPlanePath.update(time);
        mCredits.update(time, 10, GraphicManager.displayHeight * 2 / 3);
        return super.onUpdate(time, input);
    }

    @Override
    protected void onPreRender() {
        super.onPreRender();
        GLES11.glHint(GLES11.GL_FOG_HINT, GLES11.GL_DONT_CARE);
        GLES11.glFogf(GLES11.GL_FOG_MODE, GLES11.GL_EXP);
        GLES11.glFogf(GLES11.GL_FOG_DENSITY, 0.4f);
        GLES11.glFogfv(GLES11.GL_FOG_COLOR, new float[]{0.0f, 0.0f, 0.0f, 0.0f}, 0);
        GLES11.glFogf(GLES11.GL_FOG_START, 2.0f);
        GLES11.glFogf(GLES11.GL_FOG_END, 100.0f);
    }

    @Override
    protected void onRender() {
        GLES11.glClear(GLES11.GL_COLOR_BUFFER_BIT | GLES11.GL_DEPTH_BUFFER_BIT);
        mCamera.render();
        // Layer 1
        mGraphicManager.enterView2D();
        mGraphicManager.drawSurface2D(mBackground, 0, 0, GraphicManager.displayWidth, GraphicManager.displayHeight / 2);
        mGraphicManager.leaveView2D();
        // Layer 2
        GLES11.glEnable(GLES11.GL_FOG);
        mCityMesh.render();
        GLES11.glDisable(GLES11.GL_FOG);
        mPaperPlaneMesh.render();
        // Layer 3
        mGraphicManager.enterView2D();
        mCredits.render();
        super.onRender();
        mGraphicManager.leaveView2D();
    }

    @Override
    protected void onLoadAudioAssets() {
        super.onLoadAudioAssets();
        mAudioManager.loadMusic("musics/scene8.ogg", false);
    }

    @Override
    protected void onLoadGraphicAssets() {
        super.onLoadGraphicAssets();
        if (mCamera == null) {
            mCamera = new Camera3(mGraphicManager);
        }
        if (mBackground == null) {
            mBackground = new Surface(0, 0, 128, 128, mGraphicManager.textureManager.get("textures/city_skybox.jpg"));
        }
        if (mPaperPlaneMesh == null) {
            mPaperPlaneMesh = new PaperPlaneMesh(mGraphicManager);
            mPaperPlaneMesh.entity = mPaperPlanePath;
        }
        if (mCityMesh == null) {
            mCityMesh = new CityMesh(mGraphicManager);
        }
        if (mCredits == null) {
            mCredits = new PulseText(mGraphicManager, mFont, 1, 3.0f, 2.0f, mActivity.getResources().getStringArray(R.array.credit_messages));
        }
    }

    @Override
    protected void ensureGraphicMeshes() {
        super.ensureGraphicMeshes();
        mCityMesh.ensureData();
    }

    protected Camera3 mCamera = null;
    protected Surface mBackground = null;
    protected PaperPlaneMesh mPaperPlaneMesh = null;
    protected CityMesh mCityMesh = null;
    protected PulseText mCredits = null;
    protected PathEntity mPaperPlanePath = new PathEntity(new float[][]{
            {2.621639f, 3.301407f, -2.909666f, 1.0f},
            {2.892473f, 3.239686f, -2.472166f, 1.0f},
            {2.985663f, 2.363812f, -0.002011f, 1.0f},
            {2.908153f, 1.475025f, 2.450045f, 1.0f},
            {2.470652f, 1.281880f, 2.887545f, 1.0f},
            {-0.002352f, 0.860549f, 2.985202f, 1.0f},
            {-2.496523f, 0.459614f, 2.908005f, 1.0f},
            {-2.934023f, 0.404327f, 2.470504f, 1.0f},
            {-2.990781f, 0.402411f, 0.000876f, 1.0f},
            {-2.922904f, 0.406369f, -2.460868f, 1.0f},
            {-2.652071f, 0.464397f, -2.898368f, 1.0f},
            {-2.000823f, 0.681782f, -2.986322f, 1.0f},
            {-1.356976f, 1.011698f, -2.909503f, 1.0f},
            {-1.086143f, 1.272464f, -2.555336f, 1.0f},
            {-0.999479f, 1.449929f, -1.000125f, 1.0f},
            {-0.908123f, 1.580312f, 0.553957f, 1.0f},
            {-0.658123f, 1.674478f, 0.908123f, 1.0f},
            {-0.243542f, 1.804861f, 0.993542f, 1.0f},
            {0.244333f, 1.971461f, 0.993542f, 1.0f},
            {0.666831f, 2.101844f, 0.908123f, 1.0f},
            {0.916831f, 2.196010f, 0.553957f, 1.0f},
            {1.000270f, 2.326392f, -1.000521f, 1.0f},
            {1.086143f, 2.503858f, -2.559690f, 1.0f},
            {1.356977f, 2.764624f, -2.913857f, 1.0f},
            {1.998056f, 3.093943f, -2.987745f, 1.0f},
            // copy of the 2 first lines; trick to avoid modulo in lerps
            {2.621639f, 3.301407f, -2.909666f, 1.0f},
            {2.892473f, 3.239686f, -2.472166f, 1.0f},
    });
}
