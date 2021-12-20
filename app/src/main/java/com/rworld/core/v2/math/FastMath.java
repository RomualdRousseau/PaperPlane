package com.rworld.core.v2.math;

public class FastMath {
	
	public static final float PI = (float) Math.PI;
	
	public static float sqrt(float value) {
	    return 1.0f / invsqrt(value);
	}
	
	public static float invsqrt(float value) {
		double x = value;
		double xhalf = 0.5f * x; 
	    long i = Double.doubleToRawLongBits(x);
	    i = 0x5FE6EB50C7B537AAL - (i>>1); 
	    x = Double.longBitsToDouble(i);
	    x = x * (1.5f - xhalf * x * x); 
	    return (float) x; 
	}
}
