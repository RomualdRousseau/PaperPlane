package com.rworld.PaperPlane.physics;

import com.rworld.PaperPlane.MainActivity;
import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.math.Vector;
import com.rworld.core.v2.physics.PhysicEntity;
import com.rworld.core.v2.physics.PhysicSystem;

public class StarFieldEntity extends PhysicEntity {

    public StarFieldEntity(PhysicSystem parent) {
        super(parent);
    }

    @Override
    public void init(GameEntity emitter, int maxLifetime) {
        super.init(emitter, maxLifetime);

        final float[] randomPosition = {
                emitter.position[0] + 4.0f - 8.0f * MainActivity.random.nextFloat(),
                emitter.position[1] + 4.0f - 8.0f * MainActivity.random.nextFloat(),
                emitter.position[2]
        };
        Vector.set(position, randomPosition);

        final float[] randomVelocity = {
                0,
                0,
                10.0f + 5.0f * MainActivity.random.nextFloat()
        };
        Vector.set(velocity, randomVelocity);
    }

    @Override
    public void update(float time) {
        Vector.addmul(position, velocity, time);
    }
}
