package com.rworld.PaperPlane.meshes;

import android.opengl.GLES11;
import android.opengl.Matrix;

import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.IMesh;
import com.rworld.core.v2.graphics.MeshBuilder;
import com.rworld.core.v2.graphics.builders.CubeBuilder;
import com.rworld.core.v2.math.FastMath;

public class TentacleMesh {

    public GameEntity entity = new GameEntity();

    public TentacleMesh(GraphicManager graphicManager) {
        mGraphicManager = graphicManager;

        for (int i = 0; i < mMeshes.length; i++) {
            mMeshes[i] = mGraphicManager.createStaticMesh();
            mMeshes[i].enableNormals(true);
        }

        entity.position = new float[]{0.0f, 0.0f, 0.0f};
        entity.color = new float[]{0.0f, 0.5f, 1.0f, 1.0f};
    }

    public void dispose() {
        for (int i = 0; i < mMeshes.length; i++) {
            mMeshes[i].release();
        }
    }

    public void update(float time) {
        mBoneAngle += 1.5f * time;
    }

    public void render() {
        final float a = (float) Math.cos(mBoneAngle) * 0.8f;
        final float b = -a * 180.0f / FastMath.PI;
        final float x = 0.3f;
        final float y = (float) Math.sin(a) * 0.1f;

        GLES11.glMaterialfv(GLES11.GL_FRONT_AND_BACK, GLES11.GL_DIFFUSE, entity.color, 0);

        GLES11.glPushMatrix();
        GLES11.glTranslatef(entity.position[0], entity.position[1], entity.position[2]);
        mMeshes[0].draw(GameEntity.NULL, false);

        Matrix.setIdentityM(mBoneMatrix, 0);
        Matrix.translateM(mBoneMatrix, 0, x, 0.0f, y);
        Matrix.rotateM(mBoneMatrix, 0, b, 0.0f, 1.0f, 0.0f);
        GLES11.glPushMatrix();
        for (int i = 1; i < mMeshes.length; i++) {
            GLES11.glMultMatrixf(mBoneMatrix, 0);
            mMeshes[i].draw(GameEntity.NULL, false);
        }
        GLES11.glPopMatrix();

        Matrix.setIdentityM(mBoneMatrix, 0);
        Matrix.translateM(mBoneMatrix, 0, -x, 0.0f, -y);
        Matrix.rotateM(mBoneMatrix, 0, b, 0.0f, 1.0f, 0.0f);
        GLES11.glPushMatrix();
        mMeshes[0].draw(GameEntity.NULL, false);
        for (int i = 1; i < mMeshes.length; i++) {
            GLES11.glMultMatrixf(mBoneMatrix, 0);
            mMeshes[i].draw(GameEntity.NULL, false);
        }
        GLES11.glPopMatrix();

        Matrix.setIdentityM(mBoneMatrix, 0);
        Matrix.translateM(mBoneMatrix, 0, y, x, 0.0f);
        Matrix.rotateM(mBoneMatrix, 0, b, 0.0f, 0.0f, 1.0f);
        GLES11.glPushMatrix();
        mMeshes[0].draw(GameEntity.NULL, false);
        for (int i = 1; i < mMeshes.length; i++) {
            GLES11.glMultMatrixf(mBoneMatrix, 0);
            mMeshes[i].draw(GameEntity.NULL, false);
        }
        GLES11.glPopMatrix();

        Matrix.setIdentityM(mBoneMatrix, 0);
        Matrix.translateM(mBoneMatrix, 0, -y, -x, 0.0f);
        Matrix.rotateM(mBoneMatrix, 0, b, 0.0f, 0.0f, 1.0f);
        GLES11.glPushMatrix();
        mMeshes[0].draw(GameEntity.NULL, false);
        for (int i = 1; i < mMeshes.length; i++) {
            GLES11.glMultMatrixf(mBoneMatrix, 0);
            mMeshes[i].draw(GameEntity.NULL, false);
        }
        GLES11.glPopMatrix();

        Matrix.setIdentityM(mBoneMatrix, 0);
        Matrix.translateM(mBoneMatrix, 0, 0.0f, y, x);
        Matrix.rotateM(mBoneMatrix, 0, b, 1.0f, 0.0f, 0.0f);
        GLES11.glPushMatrix();
        mMeshes[0].draw(GameEntity.NULL, false);
        for (int i = 1; i < mMeshes.length; i++) {
            GLES11.glMultMatrixf(mBoneMatrix, 0);
            mMeshes[i].draw(GameEntity.NULL, false);
        }
        GLES11.glPopMatrix();

        Matrix.setIdentityM(mBoneMatrix, 0);
        Matrix.translateM(mBoneMatrix, 0, 0.0f, -y, -x);
        Matrix.rotateM(mBoneMatrix, 0, b, 1.0f, 0.0f, 0.0f);
        GLES11.glPushMatrix();
        mMeshes[0].draw(GameEntity.NULL, false);
        for (int i = 1; i < mMeshes.length; i++) {
            GLES11.glMultMatrixf(mBoneMatrix, 0);
            mMeshes[i].draw(GameEntity.NULL, false);
        }
        GLES11.glPopMatrix();

        GLES11.glPopMatrix();
    }

    public void ensureData() {
        final float[] position = {0.0f, 0.0f, 0.0f};
        final float[] size = {1.0f, 1.0f, 1.0f};
        final float[] uvcoord = {0.0f, 0.0f, 0.0f, 0.0f};
        CubeBuilder cubeGenerator = new CubeBuilder();
        cubeGenerator.createOptions = MeshBuilder.OPTIONS_NORMAL;
        for (int i = 0; i < mMeshes.length; i++) {
            size[0] = (mMeshes.length - i) * 0.02f + 0.03f;
            size[1] = (mMeshes.length - i) * 0.02f + 0.03f;
            size[2] = (mMeshes.length - i) * 0.02f + 0.03f;
            cubeGenerator.build(position, size, uvcoord);
            mMeshes[i].ensureData(cubeGenerator);
        }
    }

    protected GraphicManager mGraphicManager = null;
    protected IMesh[] mMeshes = new IMesh[5];
    protected float mBoneAngle = 0.0f;
    protected float[] mBoneMatrix = new float[16];
    protected float mDuration = 0.0f;
}
