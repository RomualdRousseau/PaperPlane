package com.rworld.core.v2.math;

import java.nio.FloatBuffer;

public class BoundingBox {
	
	public static void zero(float[] result) {
		result[0] = 0;
		result[1] = 0;
		result[2] = 0;
		result[3] = 0;
		result[4] = 0;
		result[5] = 0;
	}

	public static void minimum(float[] result) {
		result[0] = Float.MAX_VALUE;
		result[1] = Float.MAX_VALUE;
		result[2] = Float.MAX_VALUE;
		result[3] = -Float.MAX_VALUE;
		result[4] = -Float.MAX_VALUE;
		result[5] = -Float.MAX_VALUE;
	}
	
	public static void maximum(float[] result) {
		result[0] = -Float.MAX_VALUE;
		result[1] = -Float.MAX_VALUE;
		result[2] = -Float.MAX_VALUE;
		result[3] = Float.MAX_VALUE;
		result[4] = Float.MAX_VALUE;
		result[5] = Float.MAX_VALUE;
	}
	
	public static void merge(float[] result, float[] bbox) {
		result[0] = (bbox[0] < result[0]) ? bbox[0] : result[0];
		result[1] = (bbox[1] < result[1]) ? bbox[1] : result[1];
		result[2] = (bbox[2] < result[2]) ? bbox[2] : result[2];
		result[3] = (bbox[3] > result[3]) ? bbox[3] : result[3];
		result[4] = (bbox[4] > result[4]) ? bbox[4] : result[4];
		result[5] = (bbox[5] > result[5]) ? bbox[5] : result[5];
	}
	
	public static void addPoint(float[] result, float[] point) {
		result[0] = (point[0] < result[0]) ? point[0] : result[0];
		result[1] = (point[1] < result[1]) ? point[1] : result[1];
		result[2] = (point[2] < result[2]) ? point[2] : result[2];
		result[3] = (point[0] > result[3]) ? point[0] : result[3];
		result[4] = (point[1] > result[4]) ? point[1] : result[4];
		result[5] = (point[2] > result[5]) ? point[2] : result[5];
	}
	
	public static void applyMatrix(float[] result, float[] matrix, float[] bbox) {
		// BoundingBox.getCorners(bbox, mBBoxCorners);
		mBBoxCorners[ 0] = bbox[3]; mBBoxCorners[ 1] = bbox[1]; mBBoxCorners[ 2] = bbox[2];
		mBBoxCorners[ 3] = bbox[3]; mBBoxCorners[ 4] = bbox[1]; mBBoxCorners[ 5] = bbox[5];
		mBBoxCorners[ 6] = bbox[0]; mBBoxCorners[ 7] = bbox[1]; mBBoxCorners[ 8] = bbox[5];
		mBBoxCorners[ 9] = bbox[0]; mBBoxCorners[10] = bbox[1]; mBBoxCorners[11] = bbox[2];
		mBBoxCorners[12] = bbox[3]; mBBoxCorners[13] = bbox[4]; mBBoxCorners[14] = bbox[2];
		mBBoxCorners[15] = bbox[3]; mBBoxCorners[16] = bbox[4]; mBBoxCorners[17] = bbox[5];
		mBBoxCorners[18] = bbox[0]; mBBoxCorners[19] = bbox[4]; mBBoxCorners[20] = bbox[5];
		mBBoxCorners[21] = bbox[0]; mBBoxCorners[22] = bbox[4]; mBBoxCorners[23] = bbox[2];
		// BoundingBox.minimum(result);
		result[0] = Float.MAX_VALUE;
		result[1] = Float.MAX_VALUE;
		result[2] = Float.MAX_VALUE;
		result[3] = -Float.MAX_VALUE;
		result[4] = -Float.MAX_VALUE;
		result[5] = -Float.MAX_VALUE;
		for(int i = 0; i < 8 * 3; i += 3) {
			// Matrix.multiplyMM(v, matrix, v);
			final float a = mBBoxCorners[i + 0] * matrix[0] + mBBoxCorners[i + 1] * matrix[4] + mBBoxCorners[i + 2] * matrix[ 8] + matrix[12];
			final float b = mBBoxCorners[i + 0] * matrix[1] + mBBoxCorners[i + 1] * matrix[5] + mBBoxCorners[i + 2] * matrix[ 9] + matrix[13];
			final float c = mBBoxCorners[i + 0] * matrix[2] + mBBoxCorners[i + 1] * matrix[6] + mBBoxCorners[i + 2] * matrix[10] + matrix[14];
			// BoundingBox.addPoint(result, v);
			result[0] = (a < result[0]) ? a : result[0];
			result[1] = (b < result[1]) ? b : result[1];
			result[2] = (c < result[2]) ? c : result[2];
			result[3] = (a > result[3]) ? a : result[3];
			result[4] = (b > result[4]) ? b : result[4];
			result[5] = (c > result[5]) ? c : result[5];
		}
	}

	public static void getCorners(float[] bbox, float[] corners) {
		corners[ 0] = bbox[3]; corners[ 1] = bbox[1]; corners[ 2] = bbox[2];
		corners[ 3] = bbox[3]; corners[ 4] = bbox[1]; corners[ 5] = bbox[5];
		corners[ 6] = bbox[0]; corners[ 7] = bbox[1]; corners[ 8] = bbox[5];
		corners[ 9] = bbox[0]; corners[10] = bbox[1]; corners[11] = bbox[2];
		corners[12] = bbox[3]; corners[13] = bbox[4]; corners[14] = bbox[2];
		corners[15] = bbox[3]; corners[16] = bbox[4]; corners[17] = bbox[5];
		corners[18] = bbox[0]; corners[19] = bbox[4]; corners[20] = bbox[5];
		corners[21] = bbox[0]; corners[22] = bbox[4]; corners[23] = bbox[2];
	}

	public static void getCorners(float[] bbox, FloatBuffer corners) {
		mBBoxCorners[ 0] = bbox[3]; mBBoxCorners[ 1] = bbox[1]; mBBoxCorners[ 2] = bbox[2];
		mBBoxCorners[ 3] = bbox[3]; mBBoxCorners[ 4] = bbox[1]; mBBoxCorners[ 5] = bbox[5];
		mBBoxCorners[ 6] = bbox[0]; mBBoxCorners[ 7] = bbox[1]; mBBoxCorners[ 8] = bbox[5];
		mBBoxCorners[ 9] = bbox[0]; mBBoxCorners[10] = bbox[1]; mBBoxCorners[11] = bbox[2];
		mBBoxCorners[12] = bbox[3]; mBBoxCorners[13] = bbox[4]; mBBoxCorners[14] = bbox[2];
		mBBoxCorners[15] = bbox[3]; mBBoxCorners[16] = bbox[4]; mBBoxCorners[17] = bbox[5];
		mBBoxCorners[18] = bbox[0]; mBBoxCorners[19] = bbox[4]; mBBoxCorners[20] = bbox[5];
		mBBoxCorners[21] = bbox[0]; mBBoxCorners[22] = bbox[4]; mBBoxCorners[23] = bbox[2];
		corners.clear();
		corners.put(mBBoxCorners);
	}

	protected static float[] mBBoxCorners = new float[24];
}
