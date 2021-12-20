package com.rworld.core.v2;

import android.opengl.Matrix;

public class GameEntity {
	
	public static final GameEntity NULL = new NullEntity();

	public float[] position = {0, 0, 0};
	
	public float[] rotation = {0, 0, 0};
	
	public float[] color = {1, 1, 1, 1};
	
	public GameEntity() {
	}
	
	public GameEntity(float[] initialPosition) {
		position[0] = initialPosition[0];
		position[1] = initialPosition[1];
		position[2] = initialPosition[2];
	}
	
	public GameEntity(float[] initialPosition, float[] initialRotation) {
		position[0] = initialPosition[0];
		position[1] = initialPosition[1];
		position[2] = initialPosition[2];
		rotation[0] = initialRotation[0];
		rotation[1] = initialRotation[1];
		rotation[2] = initialRotation[2];
	}
	
	public GameEntity(float[] initialPosition, float[] initialRotation, float[] initialColor) {
		position[0] = initialPosition[0];
		position[1] = initialPosition[1];
		position[2] = initialPosition[2];
		rotation[0] = initialRotation[0];
		rotation[1] = initialRotation[1];
		rotation[2] = initialRotation[2];
		color[0] = initialColor[0];
		color[1] = initialColor[1];
		color[2] = initialColor[2];
		color[3] = initialColor[3];
	}
	
	public void setTransformMatrix(float[] matrix) {
		Matrix.setIdentityM(matrix, 0);
		Matrix.translateM(matrix, 0, position[0], position[1], position[2]);
		//TODO: Too much heap allocation there
		Matrix.rotateM(matrix, 0, rotation[2], 0, 0, 1);
		Matrix.rotateM(matrix, 0, rotation[1], 0, 1, 0);
		Matrix.rotateM(matrix, 0, rotation[0], 1, 0, 0);
	}
	
	protected static class NullEntity extends GameEntity {

		@Override
		public void setTransformMatrix(float[] matrix) {
			Matrix.setIdentityM(matrix, 0);
		}
	}
}
