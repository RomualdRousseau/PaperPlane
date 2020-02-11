package com.rworld.PaperPlane.physics;

import com.rworld.PaperPlane.MainActivity;
import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.math.FastMath;
import com.rworld.core.v2.math.Vector;
import com.rworld.core.v2.physics.PhysicEntity;
import com.rworld.core.v2.physics.PhysicSystem;

public class JetEngineEntity extends PhysicEntity {

    public JetEngineEntity(PhysicSystem parent) {
        super(parent);
    }

    @Override
    public void init(GameEntity emitter, int maxLifetime) {
        super.init(emitter, maxLifetime);
        mMaxLifetime = maxLifetime;

        Vector.set(position, emitter.position);
        position[2] += 0.05f;

        final float[] randomVelocity = {
                (float) Math.cos(MainActivity.random.nextFloat() * FastMath.PI * 2.0f),
                (float) Math.sin(MainActivity.random.nextFloat() * FastMath.PI * 2.0f),
                2.0f
        };
        Vector.normalize(randomVelocity);
        Vector.mul(randomVelocity, 0.4f);
        Vector.set(velocity, randomVelocity);
    }

    @Override
    public void update(float time) {
        color[3] = (float) lifetime / (float) mMaxLifetime;
        Vector.addmul(velocity, mGravity, mMass * time);
        Vector.addmul(position, velocity, time);
    }

    protected final float mMass = -0.02f;
    protected final float[] mGravity = {0.0f, -9.81f, 0.0f};
    protected int mMaxLifetime = 0;
}
