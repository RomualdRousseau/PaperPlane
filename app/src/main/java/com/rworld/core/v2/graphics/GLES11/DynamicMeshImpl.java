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

public class DynamicMeshImpl extends MeshBaseImpl {

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
		
		mBuilder.updateMeshData(mVertexBufferData, mNormalBufferData, mTexCoordBufferData, mColorBufferData);

		GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mVertexBufferID);
		mVertexBufferData.rewind();
		GLES11.glBufferSubData(GLES11.GL_ARRAY_BUFFER, 0, mElementCount * 3 * Float.SIZE, mVertexBufferData);
		GLES11.glVertexPointer(3, GLES10.GL_FLOAT, 0, 0);
		GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, mIndexBufferID);
		
		if (mNormals && (mNormalBufferID > 0)) {
			GLES10.glEnableClientState(GLES10.GL_NORMAL_ARRAY);
			GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mNormalBufferID);
			if(mNormalBufferData != null) {
				mNormalBufferData.rewind();
				GLES11.glBufferSubData(GLES11.GL_ARRAY_BUFFER, 0, mElementCount * 3 * Float.SIZE, mNormalBufferData);
			}
			GLES11.glNormalPointer(GLES10.GL_FLOAT, 0, 0);
		}
		
		if ((mTexture != null) && (mTextureBufferID > 0)) {
			mTexture.bind();
			GLES10.glEnableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
			GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mTextureBufferID);
			if(mTexCoordBufferData != null) {
				mTexCoordBufferData.rewind();
				GLES11.glBufferSubData(GLES11.GL_ARRAY_BUFFER, 0, mElementCount * 2 * Float.SIZE, mTexCoordBufferData);
			}
			GLES11.glTexCoordPointer(2, GLES10.GL_FLOAT, 0, 0);
		}
		
		if (mColors && (mColorBufferID > 0)) {
			GLES10.glEnableClientState(GLES10.GL_COLOR_ARRAY);
			GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mColorBufferID);
			if(mColorBufferData != null) {
				mColorBufferData.rewind();
				GLES11.glBufferSubData(GLES11.GL_ARRAY_BUFFER, 0, mElementCount * 4 * Float.SIZE, mColorBufferData);
			}
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
		
		if (mColors) {
			GLES10.glDisableClientState(GLES10.GL_COLOR_ARRAY);
		}
		
		if (mTexture != null) {
			GLES10.glDisableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
		}
		
		if (mNormals) {
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
		
		mBuilder.updateMeshData(mVertexBufferData, mNormalBufferData, mTexCoordBufferData, mColorBufferData);

		mVertexBufferData.rewind();
		GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mVertexBufferID);
		GLES11.glBufferSubData(GLES11.GL_ARRAY_BUFFER, 0, mElementCount * 3 * Float.SIZE, mVertexBufferData);
		GLES11.glVertexPointer(3, GLES10.GL_FLOAT, 0, 0);
		GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, mIndexBufferID);
		
		if (mNormals && (mNormalBufferID > 0)) {
			GLES10.glEnableClientState(GLES10.GL_NORMAL_ARRAY);
			GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mNormalBufferID);
			if(mNormalBufferData != null) {
				mNormalBufferData.rewind();
				GLES11.glBufferSubData(GLES11.GL_ARRAY_BUFFER, 0, mElementCount * 3 * Float.SIZE, mNormalBufferData);
			}
			GLES11.glNormalPointer(GLES10.GL_FLOAT, 0, 0);
		}
		
		if ((mTexture != null) && (mTextureBufferID > 0)) {
			mTexture.bind();
			GLES10.glEnableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
			GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mTextureBufferID);
			if(mTexCoordBufferData != null) {
				mTexCoordBufferData.rewind();
				GLES11.glBufferSubData(GLES11.GL_ARRAY_BUFFER, 0, mElementCount * 2 * Float.SIZE, mTexCoordBufferData);
			}
			GLES11.glTexCoordPointer(2, GLES10.GL_FLOAT, 0, 0);
		}
		
		if (mColors && (mColorBufferID > 0)) {
			GLES10.glEnableClientState(GLES10.GL_COLOR_ARRAY);
			GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mColorBufferID);
			if(mColorBufferData != null) {
				mColorBufferData.rewind();
				GLES11.glBufferSubData(GLES11.GL_ARRAY_BUFFER, 0, mElementCount * 4 * Float.SIZE, mColorBufferData);
			}
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
		
		if (mColors) {
			GLES10.glDisableClientState(GLES10.GL_COLOR_ARRAY);
		}
		
		if (mTexture != null) {
			GLES10.glDisableClientState(GLES10.GL_TEXTURE_COORD_ARRAY);
		}
		
		if (mNormals) {
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
		
		mBuilder = builder;
		mIndexCount = mBuilder.getIndexCount();
		mElementCount = mBuilder.getElementCount();
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(mElementCount * 3 * Float.SIZE);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBufferData = vbb.asFloatBuffer();
		
		ByteBuffer ibb = ByteBuffer.allocateDirect(mIndexCount * Short.SIZE);
		ibb.order(ByteOrder.nativeOrder());
		ShortBuffer indexBufferData = ibb.asShortBuffer();
		
		if((mBuilder.createOptions & MeshBuilder.OPTIONS_NORMAL) > 0) {
			ByteBuffer nbb = ByteBuffer.allocateDirect(mElementCount * 3 * Float.SIZE);
			nbb.order(ByteOrder.nativeOrder());
			mNormalBufferData = nbb.asFloatBuffer();
		}

		if((mBuilder.createOptions & MeshBuilder.OPTIONS_TEXTURE) > 0) {
			ByteBuffer tbb = ByteBuffer.allocateDirect(mElementCount * 2 * Float.SIZE);
			tbb.order(ByteOrder.nativeOrder());
			mTexCoordBufferData = tbb.asFloatBuffer();
		}
		
		if((mBuilder.createOptions & MeshBuilder.OPTIONS_COLOR) > 0) {
			ByteBuffer cbb = ByteBuffer.allocateDirect(mElementCount * 4 * Float.SIZE);
			cbb.order(ByteOrder.nativeOrder());
			mColorBufferData = cbb.asFloatBuffer();
		}

		mBuilder.createMeshData(mVertexBufferData, mNormalBufferData, mTexCoordBufferData, mColorBufferData, indexBufferData);
		mBuilder.recycle();
		
		int[] buffers = new int[2];
		GLES11.glGenBuffers(2, buffers, 0);

        mVertexBufferID = buffers[0];
        mVertexBufferData.rewind();
        GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mVertexBufferID);
        GLES11.glBufferData(GLES11.GL_ARRAY_BUFFER, mElementCount * 3 * Float.SIZE, mVertexBufferData, GLES11.GL_DYNAMIC_DRAW);
		
        mIndexBufferID = buffers[1];
        indexBufferData.rewind();
        GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, mIndexBufferID);
        GLES11.glBufferData(GLES11.GL_ELEMENT_ARRAY_BUFFER, mIndexCount * Short.SIZE, indexBufferData, GLES11.GL_STATIC_DRAW);
        
        if((builder.createOptions & MeshBuilder.OPTIONS_NORMAL) > 0) {
        	int[] buffer = new int[1];
    		GLES11.glGenBuffers(1, buffer, 0);
        	mNormalBufferID = buffer[0];
        	mNormalBufferData.rewind();
        	GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mNormalBufferID);
        	if((builder.createOptions & MeshBuilder.OPTIONS_NORMAL) > 0) {
        		GLES11.glBufferData(GLES11.GL_ARRAY_BUFFER, mElementCount * 3 * Float.SIZE, mNormalBufferData, GLES11.GL_DYNAMIC_DRAW);
        	}
        	else {
        		GLES11.glBufferData(GLES11.GL_ARRAY_BUFFER, mElementCount * 3 * Float.SIZE, mNormalBufferData, GLES11.GL_STATIC_DRAW);
        		mNormalBufferData = null;
        	}
        }
        
        if((builder.createOptions & MeshBuilder.OPTIONS_TEXTURE) > 0) {
        	int[] buffer = new int[1];
    		GLES11.glGenBuffers(1, buffer, 0);
        	mTextureBufferID = buffer[0];
        	mTexCoordBufferData.rewind();
        	GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mTextureBufferID);
        	if((builder.createOptions & MeshBuilder.OPTIONS_TEXTURE) > 0) {
        		GLES11.glBufferData(GLES11.GL_ARRAY_BUFFER, mElementCount * 2 * Float.SIZE, mTexCoordBufferData, GLES11.GL_DYNAMIC_DRAW);
        	}
        	else {
        		GLES11.glBufferData(GLES11.GL_ARRAY_BUFFER, mElementCount * 2 * Float.SIZE, mTexCoordBufferData, GLES11.GL_STATIC_DRAW);
        		mTexCoordBufferData = null;
        	}
        }
        
        if((builder.createOptions & MeshBuilder.OPTIONS_COLOR) > 0) {
        	int[] buffer = new int[1];
    		GLES11.glGenBuffers(1, buffer, 0);
        	mColorBufferID = buffer[0];
        	mColorBufferData.rewind();
        	GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mColorBufferID);
        	if((builder.createOptions & MeshBuilder.OPTIONS_COLOR) > 0) {
        		GLES11.glBufferData(GLES11.GL_ARRAY_BUFFER, mElementCount * 4 * Float.SIZE, mColorBufferData, GLES11.GL_DYNAMIC_DRAW);
        	}
        	else {
        		GLES11.glBufferData(GLES11.GL_ARRAY_BUFFER, mElementCount * 4 * Float.SIZE, mColorBufferData, GLES11.GL_STATIC_DRAW);
        		mColorBufferData = null;
        	}
        }
        
        GLES11.glBindBuffer(GLES11.GL_ELEMENT_ARRAY_BUFFER, 0);
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, 0);
	}
	
	protected MeshBuilder mBuilder = null;
	protected int mElementCount = 0;
	protected int mIndexCount = 0;
	protected int mVertexBufferID = 0;
	protected int mNormalBufferID = 0;
	protected int mTextureBufferID = 0;
	protected int mColorBufferID = 0;
	protected int mIndexBufferID = 0;
	protected FloatBuffer mVertexBufferData = null;
	protected FloatBuffer mNormalBufferData = null;
	protected FloatBuffer mTexCoordBufferData = null;
	protected FloatBuffer mColorBufferData = null;
}
