package com.rworld.PaperPlane;

import android.opengl.GLES11;
import android.opengl.Matrix;

import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.math.Frustrum;
import com.rworld.core.v2.math.Vector;

public class Camera4 {

    public Camera4(GraphicManager graphicManager) {
    }

    public void dispose() {
    }

    public void update(float time, GameEntity toFollow, float distance) {
        distance = distance + 0.5f * (float) Math.sin(5.0f * mVelocity);
        Vector.set(mEye, toFollow.position);
        mEye[0] += distance * (float) Math.cos(mVelocity);
        mEye[2] += distance * (float) Math.sin(mVelocity);
        Vector.set(mCenter, toFollow.position);
        mUp[0] = (float) Math.sin(0.62f * mVelocity);
        mUp[1] = (float) Math.cos(0.62f * mVelocity);
        mVelocity += time;
    }

    public void render() {
        Matrix.setLookAtM(mMatrix, 0, mEye[0], mEye[1], mEye[2], mCenter[0], mCenter[1], mCenter[2], mUp[0], mUp[1], mUp[2]);
        GLES11.glLoadMatrixf(mMatrix, 0);
        Frustrum.update();
    }

    protected float[] mMatrix = new float[16];
    protected float[] mEye = {0.0f, 0.0f, 0.0f};
    protected float[] mCenter = {0.0f, 0.0f, 0.0f};
    protected float[] mUp = {0.0f, 1.0f, 0.0f};
    protected float mVelocity = 0.0f;
}
