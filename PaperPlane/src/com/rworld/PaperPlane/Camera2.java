/*
 *      Camera2.java
 *
 *      Copyright 2011 Romuald Rousseau <romualdrousseau@gmail.com>
 *
 *      This program is free software; you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 2 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program; if not, write to the Free Software
 *      Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *      MA 02110-1301, USA.
 */

package com.rworld.PaperPlane;

import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.math.Frustrum;

import android.opengl.GLES11;
import android.opengl.Matrix;
import android.util.FloatMath;

public class Camera2 {

	public float[] position = {0.0f, 0.0f, 0.0f};
	
	public Camera2(GraphicManager graphicManager) {
	}
	
	public void dispose() {
	}
	
	public void update(float time) {
		position[0] = -2.56f + 2.56f * FloatMath.sin(0.62f * mVelocity);
		position[2] = -mVelocity;
		mDirection[0] = position[0] + 2.56f * 0.62f * FloatMath.cos(0.62f * mVelocity);
		mDirection[2] = position[2] - 1.0f;
		mUp[0] = FloatMath.sin(0.62f * mVelocity);
		mUp[1] = FloatMath.cos(0.62f * mVelocity);
		mVelocity += 0.5f * time;
	}
	
	public void render() {
		Matrix.setLookAtM(mMatrix, 0, position[0], position[1], position[2], mDirection[0], mDirection[1], mDirection[2], mUp[0], mUp[1], mUp[2]);
		GLES11.glLoadMatrixf(mMatrix, 0);
		Frustrum.update();
	}
	
	protected float[] mMatrix = new float[16];
	protected float[] mDirection = {0.0f, 0.0f, 0.0f};
	protected float[] mUp = {0.0f, 1.0f, 0.0f};
	protected float mVelocity = 0.0f;
}
