/*
 *      Camera1.java
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

import android.opengl.GLES11;
import android.opengl.Matrix;

import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.math.Frustrum;

public class Camera1 {

    public Camera1(GraphicManager graphicManager) {
    }

    public void dispose() {
    }

    public void render() {
        Matrix.setLookAtM(mCameraMatrix, 0, 0.0f, 0.0f, 5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        GLES11.glLoadMatrixf(mCameraMatrix, 0);
        Frustrum.update();
    }

    protected float[] mCameraMatrix = new float[16];
}
