package com.rworld.core.v2.physics;

import com.rworld.core.v2.GameEntity;

public class PhysicEntity extends GameEntity {
	
	public int lifetime = 0;
	
	public float[] velocity = {0, 0, 0};

	public PhysicEntity(PhysicSystem parent) {
		super();
		mParent = parent;
	}
	
	public PhysicEntity(PhysicSystem parent, float[] initialPosition) {
		super(initialPosition);
		mParent = parent;
	}
	
	public void init(GameEntity emitter, int lifetime) {
		this.lifetime = lifetime;
	}
	
	public void update(float time) {
	}
	
	protected PhysicSystem mParent = null;
}
