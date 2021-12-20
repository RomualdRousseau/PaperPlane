package com.rworld.PaperPlane.physics;

import com.rworld.core.v2.physics.PhysicEntity;
import com.rworld.core.v2.physics.PhysicSystem;

public class StarFieldSystem extends PhysicSystem {

    public StarFieldSystem() {
        super(1000, 1000, 30, new PhysicEntity(null, new float[]{0, 0, -4}));
    }

    @Override
    public PhysicEntity allocateEntity(PhysicSystem parent) {
        return new StarFieldEntity(this);
    }
}
