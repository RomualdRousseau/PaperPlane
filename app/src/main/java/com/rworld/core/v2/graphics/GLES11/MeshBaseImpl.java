package com.rworld.core.v2.graphics.GLES11;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES10;
import android.opengl.GLES11;

import com.rworld.core.v2.graphics.MeshBase;
import com.rworld.core.v2.math.BoundingBox;

public abstract class MeshBaseImpl extends MeshBase {
	
	@Override
	protected void drawBBox() {
		BoundingBox.getCorners(mTransformBBox, mBBoxVertexBufferData);

		mBBoxVertexBufferData.rewind();
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mBBoxVertexBufferID);
		GLES11.glBufferSubData(GLES11.GL_ARRAY_BUFFER, 0, 8 * 3 * Float.SIZE, mBBoxVertexBufferData);
		GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
		GLES11.glVertexPointer(3, GLES10.GL_FLOAT, 0, 0);
		GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, mBBoxIndexBufferID);
		
		boolean fog = GLES11.glIsEnabled(GLES10.GL_FOG);
		if(fog) {
			GLES10.glDisable(GLES10.GL_FOG);
		}
		GLES10.glDisable(GLES10.GL_CULL_FACE);
		GLES10.glDisable(GLES10.GL_TEXTURE_2D);
		GLES10.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
		
		GLES11.glDrawElements(GLES10.GL_LINES, 12 * 2, GLES10.GL_UNSIGNED_SHORT, 0);
		
		GLES10.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GLES10.glEnable(GLES10.GL_TEXTURE_2D);
		GLES10.glEnable(GLES10.GL_CULL_FACE);
		if(fog) {
			GLES10.glEnable(GLES10.GL_FOG);
		}
		
		GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, 0);
		GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, 0);
	}
	
	@Override
	protected void ensureBBoxData() {		
		ByteBuffer vbb = ByteBuffer.allocateDirect(8 * 3 * Float.SIZE);
		vbb.order(ByteOrder.nativeOrder());
		mBBoxVertexBufferData = vbb.asFloatBuffer();
		
		ByteBuffer ibb = ByteBuffer.allocateDirect(12 * 2 * Short.SIZE);
		ibb.order(ByteOrder.nativeOrder());
		ShortBuffer indexBufferData = ibb.asShortBuffer();
		
		BoundingBox.getCorners(mBBox, mBBoxVertexBufferData);
		indexBufferData.clear();
		indexBufferData.put(new short[]{
				0, 1, 1, 2, 2, 3, 3, 0,
				4, 7, 7, 6, 6, 5, 5, 4,
				0, 4, 1, 5, 2, 6, 3, 7});
		
		int[] buffers = new int[2];
		GLES11.glGenBuffers(2, buffers, 0);

		mBBoxVertexBufferID = buffers[0];
		mBBoxVertexBufferData.rewind();
        GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mBBoxVertexBufferID);
        GLES11.glBufferData(GLES11.GL_ARRAY_BUFFER, 8 * 3 * Float.SIZE, mBBoxVertexBufferData, GLES11.GL_DYNAMIC_DRAW);
        
        mBBoxIndexBufferID = buffers[1];
        indexBufferData.rewind();
        GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, mBBoxIndexBufferID);
        GLES11.glBufferData(GLES11.GL_ELEMENT_ARRAY_BUFFER, 12 * 2 * Short.SIZE, indexBufferData, GLES11.GL_STATIC_DRAW);
	}
	
	@Override
	protected void releaseBBoxData() {
		GLES11.glDeleteBuffers(2, new int[]{mBBoxVertexBufferID, mBBoxIndexBufferID}, 0);
	}
	
	protected int mBBoxVertexBufferID = 0;
	protected int mBBoxIndexBufferID = 0;
	protected FloatBuffer mBBoxVertexBufferData = null;
}
