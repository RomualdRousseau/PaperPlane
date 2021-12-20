package com.rworld.core.v2.math;

public class FixedMath {
	
	public static final int PRECISION = 10;
	
	public static final int ZERO = 0;
	public static final int ONE = (1 << PRECISION);
	public static final int PI = (int) (3.14f * FixedMath.ONE);
	
	public static int[] COSTABLE;
	public static int[] SINTABLE;
	
	public static int mul(int value1, int value2) {
		final long z = (long) value1 * (long) value2;
		return (int) (z >> PRECISION);
	}
	
	public static int div(int value1, int value2) {
		final long z = value1;
		return (int) ((z << PRECISION) / value2);
	}

	public static int sqrt(int value) {
		return(int) (FastMath.sqrt((float)value / FixedMath.ONE) * FixedMath.ONE);
	}
	
	public static int cos(int angle) {
		final int a = ((angle << FixedMath.PRECISION) / FixedMath.PI) & 2047;
		return COSTABLE[a];
	}
	
	public static int sin(int angle) {
		final int a = ((angle << FixedMath.PRECISION) / FixedMath.PI) & 2047;
		return SINTABLE[a];
	}
	
	public static int tan(int angle) {
		final int a = ((angle << FixedMath.PRECISION) / FixedMath.PI) & 2047;
		return SINTABLE[a] / COSTABLE[a];
	}
	
	public static int fromFloat(float value) {
		return (int) (FixedMath.ONE * value);
	}
	
	public static float toFloat(int value) {
		return (float)value / FixedMath.ONE;
	}
	
	
	public static int toDegrees(int angle) {
		long z = (long) angle * 180;
		return (int) ((z << PRECISION) / FixedMath.PI);
	}
	
	public static int toRadians(int angle) {
		long z = (long) angle * (long) FixedMath.PI / 180;
		return (int) (z >> PRECISION);
	}

	static {
		FixedMath.COSTABLE = new int[2048];
		FixedMath.SINTABLE = new int[2048];
		for(int i = 0; i < 2048; i++) {
			FixedMath.COSTABLE[i] = FixedMath.fromFloat((float) Math.cos(i * 3.14f / 1024.0f));
			FixedMath.SINTABLE[i] = FixedMath.fromFloat((float) Math.sin(i * 3.14f / 1024.0f));
		}
	}
}
