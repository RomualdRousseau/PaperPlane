package com.rworld.PaperPlane.meshes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES10;
import android.opengl.GLES11;

import com.rworld.PaperPlane.physics.StarFieldSystem;
import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.graphics.GraphicManager;

public class StarFieldHalo {

	public StarFieldHalo(GraphicManager graphicManager) {
		mGraphicManager = graphicManager;
	}
	
	public void dispose() {
		int[] buffer = {mVertexBufferID};
		GLES11.glDeleteBuffers(1, buffer, 0);
	}
	
	public void render(StarFieldSystem starFieldSystem) {
		float[] line = {0.0f, 0.f, 0.0f, 0.0f, 0.0f, 0.0f};
		int count = 0;

		synchronized(starFieldSystem.entities) {
			count = Math.min(starFieldSystem.entities.size(), mMaxCount / 2);
			mVertexBufferData.clear();
			for(int i = 0; i < count; i++) {
				GameEntity entity = starFieldSystem.entities.get(i);
				line[0] = entity.position[0];
				line[1] = entity.position[1];
				line[2] = entity.position[2] + 0.1f;
				line[3] = entity.position[0];
				line[4] = entity.position[1];
				line[5] = entity.position[2] - 0.1f;
				mVertexBufferData.put(line);
			}
			count *= 2;
		}
		
		GLES10.glDisable(GLES10.GL_TEXTURE_2D);
		GLES10.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		
		mVertexBufferData.rewind();
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mVertexBufferID);
		GLES11.glBufferSubData(GLES11.GL_ARRAY_BUFFER, 0, count * 3 * Float.SIZE, mVertexBufferData);
		GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
		GLES11.glVertexPointer(3, GLES10.GL_FLOAT, 0, 0);
		
		GLES10.glDrawArrays(GLES10.GL_LINES, 0, count);
		
		GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, 0);
		
		GLES10.glEnable(GLES10.GL_TEXTURE_2D);
	}

	public void ensureData(StarFieldSystem starFieldSystem) {
		int[] buffer = new int[1];
		
		mMaxCount = starFieldSystem.getBufferSize() * 2;
		
		if(mVertexBufferData == null) {
			ByteBuffer vbb = ByteBuffer.allocateDirect(mMaxCount * 3 * Float.SIZE);
			vbb.order(ByteOrder.nativeOrder());
			mVertexBufferData = vbb.asFloatBuffer();
		}

		GLES11.glGenBuffers(1, buffer, 0);
		mVertexBufferID = buffer[0];
		mVertexBufferData.rewind();
	    GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mVertexBufferID);
	    GLES11.glBufferData(GLES11.GL_ARRAY_BUFFER, mMaxCount * 3 * Float.SIZE, mVertexBufferData, GLES11.GL_DYNAMIC_DRAW);
	}
	
	protected GraphicManager mGraphicManager = null;
	protected int mMaxCount = 0;
	protected int mVertexBufferID = 0;
	protected FloatBuffer mVertexBufferData = null;
}
