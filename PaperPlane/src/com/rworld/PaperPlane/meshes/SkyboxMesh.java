package com.rworld.PaperPlane.meshes;

import android.opengl.GLES11;
import android.opengl.Matrix;

import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.IMesh;

public class SkyboxMesh {

    public SkyboxMesh(GraphicManager graphicManager) {
        mGraphicManager = graphicManager;
        mMesh = mGraphicManager.meshManager.get("meshes/skybox.obj");
        mMesh.enableTexture(mGraphicManager.textureManager.get("textures/moonlight_skybox.jpg"));
    }

    public void dispose() {
        mGraphicManager.meshManager.release("meshes/skybox.obj");
        mGraphicManager.textureManager.release("meshes/moonlight_skybox.png");
    }

    public void update(float time, float[] cameraPosition) {
        mEntity.position[0] = cameraPosition[0];
        mEntity.position[1] = cameraPosition[1];
        mEntity.position[2] = cameraPosition[2];
    }

    public void render() {
        GLES11.glPushMatrix();
        GLES11.glLoadIdentity();
        GLES11.glDisable(GLES11.GL_DEPTH_TEST);
        mMesh.draw(mEntity, false);
        GLES11.glEnable(GLES11.GL_DEPTH_TEST);
        GLES11.glPopMatrix();
    }

    protected GraphicManager mGraphicManager = null;
    protected IMesh mMesh = null;
    protected GameEntity mEntity = new SkyboxEntity();

    protected class SkyboxEntity extends GameEntity {

        @Override
        public void setTransformMatrix(float[] matrix) {
            Matrix.setLookAtM(matrix, 0, 0.0f, 0.0f, 0.0f, mEntity.position[0], mEntity.position[1], mEntity.position[2], 0, 1, 0);
        }
    }
}
