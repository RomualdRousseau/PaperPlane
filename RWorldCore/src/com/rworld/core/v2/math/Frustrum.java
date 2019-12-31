package com.rworld.core.v2.math;

import android.opengl.GLES11;
import android.opengl.Matrix;

public class Frustrum {

	public static void update() {
		GLES11.glGetFloatv(GLES11.GL_PROJECTION_MATRIX, mProjectionMatrice, 0);
		GLES11.glGetFloatv(GLES11.GL_MODELVIEW_MATRIX, mViewMatrice, 0);
		Matrix.multiplyMM(mFrustrumMatrice, 0, mProjectionMatrice, 0, mViewMatrice, 0);
		setFrustrumPlane(mFrustrumPlanes, 0 * 4,
				mFrustrumMatrice[3 + 0 * 4] - mFrustrumMatrice[0 + 0 * 4],
				mFrustrumMatrice[3 + 1 * 4] - mFrustrumMatrice[0 + 1 * 4],
				mFrustrumMatrice[3 + 2 * 4] - mFrustrumMatrice[0 + 2 * 4],
				mFrustrumMatrice[3 + 3 * 4] - mFrustrumMatrice[0 + 3 * 4]);
		setFrustrumPlane(mFrustrumPlanes, 1 * 4,
				mFrustrumMatrice[3 + 0 * 4] + mFrustrumMatrice[0 + 0 * 4],
				mFrustrumMatrice[3 + 1 * 4] + mFrustrumMatrice[0 + 1 * 4],
				mFrustrumMatrice[3 + 2 * 4] + mFrustrumMatrice[0 + 2 * 4],
				mFrustrumMatrice[3 + 3 * 4] + mFrustrumMatrice[0 + 3 * 4]);
		setFrustrumPlane(mFrustrumPlanes, 2 * 4,
				mFrustrumMatrice[3 + 0 * 4] - mFrustrumMatrice[1 + 0 * 4],
				mFrustrumMatrice[3 + 1 * 4] - mFrustrumMatrice[1 + 1 * 4],
				mFrustrumMatrice[3 + 2 * 4] - mFrustrumMatrice[1 + 2 * 4],
				mFrustrumMatrice[3 + 3 * 4] - mFrustrumMatrice[1 + 3 * 4]);
		setFrustrumPlane(mFrustrumPlanes, 3 * 4,
				mFrustrumMatrice[3 + 0 * 4] + mFrustrumMatrice[1 + 0 * 4],
				mFrustrumMatrice[3 + 1 * 4] + mFrustrumMatrice[1 + 1 * 4],
				mFrustrumMatrice[3 + 2 * 4] + mFrustrumMatrice[1 + 2 * 4],
				mFrustrumMatrice[3 + 3 * 4] + mFrustrumMatrice[1 + 3 * 4]);
		setFrustrumPlane(mFrustrumPlanes, 4 * 4, 
				mFrustrumMatrice[3 + 0 * 4] - mFrustrumMatrice[2 + 0 * 4],
				mFrustrumMatrice[3 + 1 * 4] - mFrustrumMatrice[2 + 1 * 4], 
				mFrustrumMatrice[3 + 2 * 4] - mFrustrumMatrice[2 + 2 * 4],
				mFrustrumMatrice[3 + 3 * 4] - mFrustrumMatrice[2 + 3 * 4]);
		setFrustrumPlane(mFrustrumPlanes, 5 * 4,
				mFrustrumMatrice[3 + 0 * 4] + mFrustrumMatrice[2 + 0 * 4],
				mFrustrumMatrice[3 + 1 * 4] + mFrustrumMatrice[2 + 1 * 4], 
				mFrustrumMatrice[3 + 2 * 4] + mFrustrumMatrice[2 + 2 * 4],
				mFrustrumMatrice[3 + 3 * 4] + mFrustrumMatrice[2 + 3 * 4]);
	}
	
	public static boolean isPointVisible(float[] point) {
		for(int i = 0; i < 6 * 4; i += 4) {
			if((mFrustrumPlanes[i + 0] * point[0] + mFrustrumPlanes[i + 1] * point[1] + mFrustrumPlanes[i + 2] * point[2] + mFrustrumPlanes[i + 3]) <= 0) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isSphereVisible(float[] point, float radius) {
		for(int i = 0; i < 6 * 4; i += 4) {
			if((mFrustrumPlanes[i + 0] * point[0] + mFrustrumPlanes[i + 1] * point[1] + mFrustrumPlanes[i + 2] * point[2] + mFrustrumPlanes[i + 3]) <= -radius) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isBoundingBoxVisible(float[] bbox) {
		for(int i = 0; i < 6 * 4; i += 4) {
			final float a = (mFrustrumPlanes[i + 0] > 0) ? bbox[3] : bbox[0];
			final float b = (mFrustrumPlanes[i + 1] > 0) ? bbox[4] : bbox[1];
			final float c = (mFrustrumPlanes[i + 2] > 0) ? bbox[5] : bbox[2];
			if((mFrustrumPlanes[i + 0] * a + mFrustrumPlanes[i + 1] * b + mFrustrumPlanes[i + 2] * c + mFrustrumPlanes[i + 3]) <= 0) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isObjectVisible(float[] points) {
		int in = 0;
		int out = 0;
		for(int i = 0; i < 6 * 4; i += 4) {
            in = 0;
            out = 0;
            for(int j = 0; (j < points.length) && (in == 0 || out == 0); j += 3) {
            	if((mFrustrumPlanes[i + 0] * points[j + 0] + mFrustrumPlanes[i + 1] * points[j + 1] + mFrustrumPlanes[i + 2] * points[j + 2] + mFrustrumPlanes[i + 3]) <= 0) {
            		out++;
            	}
            	else {
            		in++;
            	}
            }
            if(in == 0) {
            	return false;
            }
		}
	    return true;
	}
	
	protected static void setFrustrumPlane(float[] plane, int planeOffset, float a, float b, float c, float d) {
		final float l = FastMath.invsqrt(a * a + b * b + c * c);
		plane[planeOffset + 0] = a * l;
		plane[planeOffset + 1] = b * l;
		plane[planeOffset + 2] = c * l;
		plane[planeOffset + 3] = d * l;
	}
	
	protected static float[] mProjectionMatrice = new float[16];
	protected static float[] mViewMatrice = new float[16];
	protected static float[] mFrustrumMatrice = new float[16];
	protected static float[] mFrustrumPlanes = new float[24];
}
