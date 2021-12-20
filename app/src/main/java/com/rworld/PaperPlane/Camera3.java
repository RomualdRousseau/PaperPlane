package com.rworld.PaperPlane;

import android.opengl.GLES11;
import android.opengl.Matrix;

import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.math.Frustrum;
import com.rworld.core.v2.math.Vector;

public class Camera3 {

    public Camera3(GraphicManager graphicManager) {
    }

    public void dispose() {
    }

    public void update(float time, GameEntity follow, float distance) {
        Vector.set(mEye, follow.position);
        Vector.set(mCenter, follow.position);
        Vector.set(mEyeDirection, follow.position, follow.rotation);
        Vector.normalize(mEyeDirection);
        Vector.mul(mEyeDirection, distance);
        Vector.sub(mEye, mEyeDirection);
    }

    public void render() {
        Matrix.setLookAtM(mMatrix, 0, mEye[0], mEye[1], mEye[2], mCenter[0], mCenter[1], mCenter[2], 0.0f, 1.0f, 0.0f);
        GLES11.glLoadMatrixf(mMatrix, 0);
        Frustrum.update();
    }

    protected float[] mMatrix = new float[16];
    protected float[] mEye = new float[3];
    protected float[] mCenter = new float[3];
    protected float[] mEyeDirection = new float[3];
}
