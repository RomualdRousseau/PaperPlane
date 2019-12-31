package com.rworld.PaperPlane.physics;

import android.util.FloatMath;

import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.physics.PhysicEntity;
import com.rworld.core.v2.physics.PhysicSystem;

public class PaperPlaneSystem extends PhysicSystem {

	public GameEntity gravityCenter = new GameEntity();
	
	public PaperPlaneSystem() {
		super(1, 10, 10000, GameEntity.NULL);
	}

	@Override
	public PhysicEntity allocateEntity(PhysicSystem parent) {
		return new PaperPlaneEntity(parent);
	}
	
	@Override
	public void update(float time) {
		super.update(time);
		mStepX -= 0.8f * 0.3f * time;
		mStepY -= 1.0f * 0.3f * time;
		emitter.position[0] = mBoundingBox[0] * FloatMath.sin(mStepX);
		emitter.position[1] = mBoundingBox[1] * FloatMath.sin(mStepY) + 0.2f;
		emitter.position[2] = mBoundingBox[2] * FloatMath.sin(mStepX + mStepY);
		
		gravityCenter.position[0] = 0.0f;
		gravityCenter.position[1] = 0.0f;
		gravityCenter.position[2] = 0.0f;
		int n = entities.size();
		for(int i = 0; i < n; i++) {
			PhysicEntity e = entities.get(i);
			gravityCenter.position[0] += e.position[0];
			gravityCenter.position[1] += e.position[1];
			gravityCenter.position[2] += e.position[2];
		}
		gravityCenter.position[0] /= n;
		gravityCenter.position[1] /= n;
		gravityCenter.position[2] /= n;
	}

	protected final float[] mBoundingBox = {3, 2, 3};
	protected float mStepX = 0;
	protected float mStepY = 0.16f;
}
