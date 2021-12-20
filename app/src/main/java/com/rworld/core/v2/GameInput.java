package com.rworld.core.v2;

public class GameInput {
	
	public boolean isScreenTouched = false;
	
	public boolean isBackButtonTouched = false;
	
	public boolean isMenuButtonTouched = false;
	
	public int[] screenTouchCoord = {0, 0};
	
	public float[] sensorAcceleration = {0.0f, 0.0f, 0.0f};
}
