package com.rworld.core.v2.graphics.GLES11;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

import android.opengl.GLES10;
import android.opengl.GLES11;

import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.graphics.IBillboard;
import com.rworld.core.v2.graphics.ITexture;

public class BillboardImpl implements IBillboard {

	@Override
	public void setTexture(ITexture texture) {
		mTexture = texture;
	}
	
	@Override
	public void enableAlphaMask(boolean alpha) {
		mAlpha = alpha;
	}
	
	@Override
	public boolean draw(GameEntity entity, boolean checkVisibility) {
		mVertexBufferData.clear();
		mVertexBufferData.put(entity.position);
		mVertexBufferData.rewind();
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mVertexBufferID);
		GLES11.glBufferSubData(GLES11.GL_ARRAY_BUFFER, 0, 3 * Float.SIZE, mVertexBufferData);
		GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
		GLES11.glVertexPointer(3, GLES10.GL_FLOAT, 0, 0);
		
		if(mAlpha) {
			mColorBufferData.clear();
			mColorBufferData.put(entity.color);
			mColorBufferData.rewind();
			GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mColorBufferID);
			GLES11.glBufferSubData(GLES11.GL_ARRAY_BUFFER, 0, 4 * Float.SIZE, mColorBufferData);
			GLES10.glEnableClientState(GLES10.GL_COLOR_ARRAY);
			GLES11.glColorPointer(4, GLES10.GL_FLOAT, 0, 0);
		}
		
		if (mTexture != null) {
			mTexture.bind();
			GLES10.glEnable(GLES11.GL_POINT_SPRITE_OES);
			GLES11.glTexEnvi(GLES11.GL_POINT_SPRITE_OES, GLES11.GL_COORD_REPLACE_OES, GLES10.GL_TRUE); 
		}
		
		GLES10.glPointSize(mSize);
		GLES10.glDrawArrays(GLES10.GL_POINTS, 0, 1);
		
		if (mTexture != null) {
			GLES10.glDisable(GLES11.GL_POINT_SPRITE_OES);
		}

		if(mAlpha) {
			GLES10.glDisableClientState(GLES10.GL_COLOR_ARRAY);
		}
		
		GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, 0);
		
		return true;
	}
	
	@Override
	public boolean draw(List<? extends GameEntity> entities, boolean checkVisibility) {
		int count = 0;

		synchronized(entities) {
			count = Math.min(entities.size(), mMaxCount);
			mVertexBufferData.clear();
			if(mAlpha) {
				mColorBufferData.clear();
				for(int i = 0; i < count; i++) {
					GameEntity entity = entities.get(i);
					mVertexBufferData.put(entity.position);
					mColorBufferData.put(entity.color);
				}
			}
			else {
				for(int i = 0; i < count; i++) {
					GameEntity entity = entities.get(i);
					mVertexBufferData.put(entity.position);
				}
			}
		}
		
		mVertexBufferData.rewind();
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mVertexBufferID);
		GLES11.glBufferSubData(GLES11.GL_ARRAY_BUFFER, 0, count * 3 * Float.SIZE, mVertexBufferData);
		GLES10.glEnableClientState(GLES10.GL_VERTEX_ARRAY);
		GLES11.glVertexPointer(3, GLES10.GL_FLOAT, 0, 0);
		
		if(mAlpha) {
			mColorBufferData.rewind();
			GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mColorBufferID);
			GLES11.glBufferSubData(GLES11.GL_ARRAY_BUFFER, 0, count * 4 * Float.SIZE, mColorBufferData);
			GLES10.glEnableClientState(GLES10.GL_COLOR_ARRAY);
			GLES11.glColorPointer(4, GLES10.GL_FLOAT, 0, 0);
		}
		
		if (mTexture != null) {
			mTexture.bind();
			GLES10.glEnable(GLES11.GL_POINT_SPRITE_OES);
			GLES11.glTexEnvi(GLES11.GL_POINT_SPRITE_OES, GLES11.GL_COORD_REPLACE_OES, GLES10.GL_TRUE); 
		}
		
		GLES10.glPointSize(mSize);
		GLES10.glDrawArrays(GLES10.GL_POINTS, 0, count);
		
		if (mTexture != null) {
			GLES10.glDisable(GLES11.GL_POINT_SPRITE_OES);
		}

		if(mAlpha) {
			GLES10.glDisableClientState(GLES10.GL_COLOR_ARRAY);
		}
		
		GLES10.glDisableClientState(GLES10.GL_VERTEX_ARRAY);
		GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, 0);
		
		return true;
	}
	
	@Override
	public void ensureData(int maxCount, float size) {
		int[] buffer = {0};
		
		mMaxCount = maxCount;
		mSize = size;
		
		if(mVertexBufferData == null) {
			ByteBuffer vbb = ByteBuffer.allocateDirect(mMaxCount * 3 * Float.SIZE);
			vbb.order(ByteOrder.nativeOrder());
			mVertexBufferData = vbb.asFloatBuffer();
		}
		
		if(mAlpha && (mColorBufferData == null)) {
			ByteBuffer vbb = ByteBuffer.allocateDirect(mMaxCount * 4 * Float.SIZE);
			vbb.order(ByteOrder.nativeOrder());
			mColorBufferData = vbb.asFloatBuffer();
		}
		
		GLES11.glGenBuffers(1, buffer, 0);
		mVertexBufferID = buffer[0];
		mVertexBufferData.rewind();
        GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mVertexBufferID);
        GLES11.glBufferData(GLES11.GL_ARRAY_BUFFER, mMaxCount * 3 * Float.SIZE, mVertexBufferData, GLES11.GL_DYNAMIC_DRAW);
        
        if(mAlpha) {
        	GLES11.glGenBuffers(1, buffer, 0);
        	mColorBufferID = buffer[0];
        	mColorBufferData.rewind();
        	GLES11.glBindBuffer(GLES11.GL_ARRAY_BUFFER, mColorBufferID);
        	GLES11.glBufferData(GLES11.GL_ARRAY_BUFFER, mMaxCount * 4 * Float.SIZE, mColorBufferData, GLES11.GL_DYNAMIC_DRAW);
        }
	}
	
	@Override
	public void release() {
		int[] buffer = {0};
		
		buffer[0] = mVertexBufferID;
		GLES11.glDeleteBuffers(1, buffer, 0);
		
		if(mAlpha) {
			buffer[0] = mColorBufferID;
			GLES11.glDeleteBuffers(1, buffer, 0);
		}
	}
	
	protected ITexture mTexture = null;
	protected boolean mAlpha = false;
	protected float mSize = 1.0f;
	protected int mMaxCount = 0;
	protected int mVertexBufferID = 0;
	protected int mColorBufferID = 0;
	protected FloatBuffer mVertexBufferData = null;
	protected FloatBuffer mColorBufferData = null;
}
