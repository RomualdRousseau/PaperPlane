package com.rworld.core.v2.graphics.GLES11;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.List;

import android.opengl.GLES10;
import android.opengl.GLES11;

import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.graphics.MeshBuilder;

public class StaticMeshImpl extends MeshBaseImpl {

	@Override
	public void release() {
		super.release();
		GLES11.glDeleteBuffers(5, new int[]{mVertexBufferID, mNormalBufferID, mTextureBufferID, mColorBufferID, mIndexBufferID}, 0);
	}
	
	@Override
	public int countofElements() {
		return mElementCount;
	}
	
	@Override
	public int countofFaces() {
		return mIndexCount / 3;
	}
	
	@Override
	public boolean draw(GameEntity entity, boolean checkVisibility) {
		if(!super.draw(entity, checkVisibility)) {
			return false;
		}

		GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mVertexBufferID);
		GLES11.glVertexPointer(3, GLES10.GL_FLOAT, 0, 0);
		GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, mIndexBufferID);
		
		if (mNormals && (mNormalBufferID > 0)) {
			GLES10.glEnableClientState(GLES10.GL_NORMAL_ARRAY);
			GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mNormalBufferID);
			GLES11.glNormalPointer(GLES10.GL_FLOAT, 0, 0);
		}
		
		if ((mTexture != null) && (mTextureBufferID > 0)) {
			mTexture.bind();
			GLES10.glEnableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
			GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mTextureBufferID);
			GLES11.glTexCoordPointer(2, GLES10.GL_FLOAT, 0, 0);
		}
		else {
			GLES10.glDisable(GLES10.GL_TEXTURE_2D);
		}
		
		if (mColors && (mColorBufferID > 0)) {
			GLES10.glEnableClientState(GLES10.GL_COLOR_ARRAY);
			GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mColorBufferID);
			GLES11.glColorPointer(4, GLES10.GL_FLOAT, 0, 0);
		}
		
		if(entity != GameEntity.NULL) {
			GLES10.glPushMatrix();
			GLES10.glMultMatrixf(mTransformMatrix, 0);
			GLES11.glDrawElements(GLES10.GL_TRIANGLES, mIndexCount, GLES10.GL_UNSIGNED_SHORT, 0);
			GLES10.glPopMatrix();
		}
		else {
			GLES11.glDrawElements(GLES10.GL_TRIANGLES, mIndexCount, GLES10.GL_UNSIGNED_SHORT, 0);
		}

		if (mColors && (mColorBufferID > 0)) {
			GLES10.glDisableClientState(GLES10.GL_COLOR_ARRAY);
			GLES10.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		}
		
		if ((mTexture != null) && (mTextureBufferID > 0)) {
			GLES10.glDisableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
		}
		else {
			GLES10.glEnable(GLES10.GL_TEXTURE_2D);
		}
		
		if (mNormals && (mNormalBufferID > 0)) {
			GLES10.glDisableClientState(GLES10.GL_NORMAL_ARRAY);
		}

		GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
		GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, 0);
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, 0);
		
		return true;
	}
	
	@Override
	public boolean draw(List<? extends GameEntity> entities, boolean checkVisibility) {
		if(!super.draw(entities, checkVisibility)) {
			return false;
		}
		
		GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mVertexBufferID);
		GLES11.glVertexPointer(3, GLES10.GL_FLOAT, 0, 0);
		GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, mIndexBufferID);
		
		if (mNormals && (mNormalBufferID > 0)) {
			GLES10.glEnableClientState(GLES10.GL_NORMAL_ARRAY);
			GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mNormalBufferID);
			GLES11.glNormalPointer(GLES10.GL_FLOAT, 0, 0);
		}
		
		if ((mTexture != null) && (mTextureBufferID > 0)) {
			mTexture.bind();
			GLES10.glEnableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
			GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mTextureBufferID);
			GLES11.glTexCoordPointer(2, GLES10.GL_FLOAT, 0, 0);
		}
		else {
			GLES10.glDisable(GLES10.GL_TEXTURE_2D);
		}
		
		if (mColors && (mColorBufferID > 0)) {
			GLES10.glEnableClientState(GLES10.GL_COLOR_ARRAY);
			GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mColorBufferID);
			GLES11.glColorPointer(4, GLES10.GL_FLOAT, 0, 0);
		}
		
		synchronized(entities) {
			for(GameEntity entity : entities) {
				if(!isBBoxVisible(entity, checkVisibility, false)) {
					continue;
				}
				if(entity != GameEntity.NULL) {
					GLES10.glPushMatrix();
					GLES10.glMultMatrixf(mTransformMatrix, 0);
					GLES11.glDrawElements(GLES10.GL_TRIANGLES, mIndexCount, GLES10.GL_UNSIGNED_SHORT, 0);
					GLES10.glPopMatrix();
				}
				else {
					GLES11.glDrawElements(GLES10.GL_TRIANGLES, mIndexCount, GLES10.GL_UNSIGNED_SHORT, 0);
				}
			}
		}
		
		if (mColors && (mColorBufferID > 0)) {
			GLES10.glDisableClientState(GLES10.GL_COLOR_ARRAY);
			GLES10.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		}
		
		if ((mTexture != null) && (mTextureBufferID > 0)) {
			GLES10.glDisableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
		}
		else {
			GLES10.glEnable(GLES10.GL_TEXTURE_2D);
		}
		
		if (mNormals && (mNormalBufferID > 0)) {
			GLES10.glDisableClientState(GLES10.GL_NORMAL_ARRAY);
		}

		GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, 0);
		GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, 0);
		
		return true;
	}

	@Override
	public void ensureData(MeshBuilder builder) {
		super.ensureData(builder);
		
		mIndexCount = builder.getIndexCount();
		mElementCount = builder.getElementCount();
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(mElementCount * 3 * Float.SIZE);
		vbb.order(ByteOrder.nativeOrder());
		FloatBuffer vertexBufferData = vbb.asFloatBuffer();
		
		ByteBuffer ibb = ByteBuffer.allocateDirect(mIndexCount * Short.SIZE);
		ibb.order(ByteOrder.nativeOrder());
		ShortBuffer indexBufferData = ibb.asShortBuffer();
		
		FloatBuffer normalBufferData = null;
		if((builder.createOptions & MeshBuilder.OPTIONS_NORMAL) > 0) {
			ByteBuffer nbb = ByteBuffer.allocateDirect(mElementCount * 3 * Float.SIZE);
			nbb.order(ByteOrder.nativeOrder());
			normalBufferData = nbb.asFloatBuffer();
		}

		FloatBuffer texCoordBufferData = null;
		if((builder.createOptions & MeshBuilder.OPTIONS_TEXTURE) > 0) {
			ByteBuffer tbb = ByteBuffer.allocateDirect(mElementCount * 2 * Float.SIZE);
			tbb.order(ByteOrder.nativeOrder());
			texCoordBufferData = tbb.asFloatBuffer();
		}
		
		FloatBuffer colorBufferData = null;
		if((builder.createOptions & MeshBuilder.OPTIONS_COLOR) > 0) {
			ByteBuffer cbb = ByteBuffer.allocateDirect(mElementCount * 4 * Float.SIZE);
			cbb.order(ByteOrder.nativeOrder());
			colorBufferData = cbb.asFloatBuffer();
		}

		builder.createMeshData(vertexBufferData, normalBufferData, texCoordBufferData, colorBufferData, indexBufferData);
		builder.recycle();
		
		int[] buffers = new int[2];
		GLES11.glGenBuffers(2, buffers, 0);

        mVertexBufferID = buffers[0];
        vertexBufferData.rewind();
        GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mVertexBufferID);
        GLES11.glBufferData(GLES11.GL_ARRAY_BUFFER, mElementCount * 3 * Float.SIZE, vertexBufferData, GLES11.GL_STATIC_DRAW);
		
        mIndexBufferID = buffers[1];
        indexBufferData.rewind();
        GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, mIndexBufferID);
        GLES11.glBufferData(GLES11.GL_ELEMENT_ARRAY_BUFFER, mIndexCount * Short.SIZE, indexBufferData, GLES11.GL_STATIC_DRAW);
        
        if((builder.createOptions & MeshBuilder.OPTIONS_NORMAL) > 0) {
        	int[] buffer = new int[1];
    		GLES11.glGenBuffers(1, buffer, 0);
        	mNormalBufferID = buffer[0];
        	normalBufferData.rewind();
        	GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mNormalBufferID);
        	GLES11.glBufferData(GLES11.GL_ARRAY_BUFFER, mElementCount * 3 * Float.SIZE, normalBufferData, GLES11.GL_STATIC_DRAW);
        }
        
        if((builder.createOptions & MeshBuilder.OPTIONS_TEXTURE) > 0) {
        	int[] buffer = new int[1];
    		GLES11.glGenBuffers(1, buffer, 0);
        	mTextureBufferID = buffer[0];
        	texCoordBufferData.rewind();
        	GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mTextureBufferID);
        	GLES11.glBufferData(GLES11.GL_ARRAY_BUFFER, mElementCount * 2 * Float.SIZE, texCoordBufferData, GLES11.GL_STATIC_DRAW);
        }
        
        if((builder.createOptions & MeshBuilder.OPTIONS_COLOR) > 0) {
        	int[] buffer = new int[1];
    		GLES11.glGenBuffers(1, buffer, 0);
        	mColorBufferID = buffer[0];
        	colorBufferData.rewind();
        	GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mColorBufferID);
        	GLES11.glBufferData(GLES11.GL_ARRAY_BUFFER, mElementCount * 4 * Float.SIZE, colorBufferData, GLES11.GL_STATIC_DRAW);
        }
        
        GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, 0);
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, 0);
	}
	
	protected int mElementCount = 0;
	protected int mIndexCount = 0;
	protected int mVertexBufferID = 0;
	protected int mNormalBufferID = 0;
	protected int mTextureBufferID = 0;
	protected int mColorBufferID = 0;
	protected int mIndexBufferID = 0;
}
