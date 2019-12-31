package com.rworld.PaperPlane.physics;

import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.physics.PhysicEntity;
import com.rworld.core.v2.physics.PhysicSystem;

public class JetEngineSystem extends PhysicSystem {

	public JetEngineSystem(GameEntity emitter) {
		super(2000, 2000, 40, emitter);
	}

	@Override
	public PhysicEntity allocateEntity(PhysicSystem parent) {
		return new JetEngineEntity(parent);
	}
}
