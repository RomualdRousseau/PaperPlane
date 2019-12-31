package com.rworld.PaperPlane.physics;

import android.opengl.Matrix;

import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.math.Vector;

public class PathEntity extends GameEntity {

	public PathEntity(float[][] coolPath) {
		mCoolPath = coolPath;
		mWayPoint = 0;
		Vector.set(position, mCoolPath[mWayPoint]);
	}
	
	public void update(float time) {
		mAcceleration += mCoolPath[mWayPoint][3] * time;
		if(mAcceleration >= 1.0f) {
			mAcceleration = 0.0f;
			mWayPoint = (mWayPoint + 1) % (mCoolPath.length - 2);
		}
		Vector.lerp(position, mCoolPath[mWayPoint], mCoolPath[mWayPoint + 1], mAcceleration);
		Vector.lerp(rotation, mCoolPath[mWayPoint + 1], mCoolPath[mWayPoint + 2], mAcceleration);
	}
	
	@Override
	public void setTransformMatrix(float[] matrix) {
		//Matrix.setLookAtM(matrix, 0, -position[0], -position[1], -position[2], rotation[0], rotation[1], rotation[2], 0.0f, 1.0f, 0.0f);
		Matrix.setIdentityM(matrix, 0);
		Matrix.translateM(matrix, 0, position[0], position[1], position[2]);
		Matrix.rotateM(matrix, 0, (float) (RAGTODEG * (Math.atan2(rotation[0], rotation[2]) + Math.PI)), 0, 1, 0);
	}

	protected final float[][] mCoolPath;
	protected int mWayPoint = 0;
	protected float mAcceleration = 0.0f;
	
	protected static final double RAGTODEG = 180.0f / Math.PI;
}
