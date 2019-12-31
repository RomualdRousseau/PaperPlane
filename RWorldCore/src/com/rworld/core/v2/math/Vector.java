package com.rworld.core.v2.math;

public class Vector {
	
	public static void zero(float[] result) {
		result[0] = 0;
		result[1] = 0;
		result[2] = 0;
	}
	
	public static void neg(float[] result) {
		result[0] = -result[0];
		result[1] = -result[1];
		result[2] = -result[2];
	}
	
	public static void set(float[] result, float[] src) {
		result[0] = src[0];
		result[1] = src[1];
		result[2] = src[2];
	}
	
	public static void set(float[] result, float[] src, float[] dst) {
		result[0] = dst[0] - src[0];
		result[1] = dst[1] - src[1];
		result[2] = dst[2] - src[2];
	}
	
	public static void add(float[] result, float[] vector) {
		result[0] += vector[0];
		result[1] += vector[1];
		result[2] += vector[2];
	}
	
	public static void sub(float[] result, float[] vector) {
		result[0] -= vector[0];
		result[1] -= vector[1];
		result[2] -= vector[2];
	}
	
	public static void mul(float[] result, float scalar) {
		result[0] *= scalar;
		result[1] *= scalar;
		result[2] *= scalar;
	}
	
	public static void addmul(float[] result, float[] vector, float scalar) {
		result[0] += vector[0] * scalar;
		result[1] += vector[1] * scalar;
		result[2] += vector[2] * scalar;
	}
	
	public static float lenght(float[] vector) {
		return FastMath.sqrt(vector[0] * vector[0] + vector[1] * vector[1] + vector[2] * vector[2]);
	}
	
	public static float lenght2(float[] vector) {
		return vector[0] * vector[0] + vector[1] * vector[1] + vector[2] * vector[2];
	}
	
	public static void normalize(float[] vector) {
		final float n = FastMath.invsqrt(vector[0] * vector[0] + vector[1] * vector[1] + vector[2] *vector[2]);
		vector[0] *= n;
		vector[1] *= n;
		vector[2] *= n;
	}
	
	public static void limit(float[] vector, float limit) {
		final float n = limit * FastMath.invsqrt(vector[0] * vector[0] + vector[1] * vector[1] + vector[2] *vector[2]);
		if(n < 1.0f) {
			vector[0] *= n;
			vector[1] *= n;
			vector[2] *= n;
		}
	}
	
	public static void lerp(float[] result, float[] src, float[] dst, float time) {
		final float oneminustime = (1.0f - time);
		result[0] = src[0] * oneminustime + dst[0] * time;
		result[1] = src[1] * oneminustime + dst[1] * time;
		result[2] = src[2] * oneminustime + dst[2] * time;
	}
}
