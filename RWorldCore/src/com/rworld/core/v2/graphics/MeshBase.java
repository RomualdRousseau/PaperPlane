package com.rworld.core.v2.graphics;

import java.util.List;

import com.rworld.core.v2.GameActivity;
import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.math.BoundingBox;
import com.rworld.core.v2.math.Frustrum;

public abstract class MeshBase implements IMesh {
	
	@Override
	public void release() {
		if(GameActivity.RenderMeshBoundaries) {
			releaseBBoxData();
		}
	}
	
	@Override
	public abstract int countofFaces();
	
	@Override
	public float[] getBoundingBox() {
		return mBBox;
	}
	
	@Override
	public void resizeBoundingBox(float[] newBBox) {
		mBBox = newBBox;
	}
	
	@Override
	public void enableNormals(boolean normals) {
		mNormals = normals;
	}
	
	@Override
	public void enableTexture(ITexture texture) {
		mTexture = texture;
	}
	
	@Override
	public void enableColors(boolean colors) {
		mColors = colors;
	}
	
	@Override
	public void enableAlphaMask(boolean alphaMask) {
	}
	
	@Override
	public boolean draw(GameEntity entity, boolean checkVisibility) {
		if(!isBBoxVisible(entity, checkVisibility, GameActivity.RenderMeshBoundaries)) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean draw(List<? extends GameEntity> entities, boolean checkVisibility) {
		if(GameActivity.RenderMeshBoundaries) {
			synchronized(entities) {
				boolean visible = false;
				for(GameEntity entity : entities) {
					if(!isBBoxVisible(entity, checkVisibility, true)) {
						continue;
					}
					visible = true;
				}
				if(!visible) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void ensureData(MeshBuilder builder) {
		if(builder != null) {
			builder.computeBoundingBox(mBBox);
		}
		if(GameActivity.RenderMeshBoundaries) {
        	ensureBBoxData();
        }
	}
	
	protected boolean isBBoxVisible(GameEntity entity, boolean checkVisibility, boolean renderBoundaries) {
		if(checkVisibility) {
			entity.setTransformMatrix(mTransformMatrix);
			BoundingBox.applyMatrix(mTransformBBox, mTransformMatrix, mBBox);
			if(!Frustrum.isBoundingBoxVisible(mTransformBBox)) {
				return false;
			}
			if(renderBoundaries) {
				drawBBox();
			}
		}
		else if(renderBoundaries) {
			entity.setTransformMatrix(mTransformMatrix);
			BoundingBox.applyMatrix(mTransformBBox, mTransformMatrix, mBBox);
			drawBBox();
		}
		else if(entity != GameEntity.NULL) {
			entity.setTransformMatrix(mTransformMatrix);
		}
		return true;
	}

	protected abstract void drawBBox();
	
	protected abstract void ensureBBoxData();
	
	protected abstract void releaseBBoxData();

	protected boolean mNormals = false;
	protected ITexture mTexture = null;
	protected boolean mColors = false;
	protected float[] mBBox = new float[6];
	protected float[] mTransformBBox = new float[6];
	protected float[] mTransformMatrix = new float[16];
}
