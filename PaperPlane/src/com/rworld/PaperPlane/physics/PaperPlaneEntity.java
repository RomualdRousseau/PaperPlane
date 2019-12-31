package com.rworld.PaperPlane.physics;

import com.rworld.PaperPlane.MainActivity;
import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.math.FastMath;
import com.rworld.core.v2.math.Vector;
import com.rworld.core.v2.physics.PhysicEntity;
import com.rworld.core.v2.physics.PhysicSystem;

public class PaperPlaneEntity extends PhysicEntity {

	public PaperPlaneEntity(PhysicSystem parent) {
		super(parent);
	}

	@Override
	public void init(GameEntity emitter, int maxLifetime) {
		super.init(emitter, maxLifetime);
		
		final float[] randomPosition = {
				4.8f + 1.0f - 2.0f * MainActivity.random.nextFloat(),
				4.8f + 1.0f - 2.0f * MainActivity.random.nextFloat(),
				4.8f + 1.0f - 2.0f * MainActivity.random.nextFloat()
				};
		Vector.set(position, randomPosition);
		
		Vector.zero(velocity);

		mTurnThink = MainActivity.random.nextInt(5);
		mTurn = mTurnThink;
	}

	@Override
	public void update(float time) {
		if(mTurn == mTurnThink) {
			Vector.zero(mAcceleration);
			think(mAcceleration, mInfluences);
		}
		
		//Vector.addmul(velocity, mAcceleration, time);
		velocity[0] += mAcceleration[0] * time;
		velocity[1] += mAcceleration[1] * time;
		velocity[2] += mAcceleration[2] * time;
		Vector.limit(velocity, 1.0f);
		//Vector.addmul(position, velocity, time);
		position[0] += velocity[0] * time;
		position[1] += velocity[1] * time;
		position[2] += velocity[2] * time;
		
		if(mTurn == mTurnThink) {
			look(mHeading);
		}
		Vector.lerp(rotation, rotation, mHeading, time);
		
		mTurn = (mTurn + 1) % 5;
	}
	
	protected void think(float[] acceleration, float[] influences) {
		final int l = mParent.entities.size();
		final float n = 1.0f / (l - 1);
		float[] d = {0, 0, 0};
		float[] cohesion = {0, 0, 0};
		float[] separation = {0, 0, 0};
		float[] alignment = {0, 0, 0};

		for(int i = 0; i < l; i++) {
			PhysicEntity entity = mParent.entities.get(i);
			if(entity != this) {
				//Vector.add(cohesion, entity.position);
				cohesion[0] += entity.position[0];
				cohesion[1] += entity.position[1];
				cohesion[2] += entity.position[2];
				//Vector.set(d, position, entity.position);
				d[0] = entity.position[0] - position[0];
				d[1] = entity.position[1] - position[1];
				d[2] = entity.position[2] - position[2];
				// if(Vector.lenght2(d) < 0.8f * 0.8f) {
				if(d[0] * d[0] + d[1] * d[1] + d[2] * d[2] < 0.8f * 0.8f) {
					// Vector.sub(separation, d);
					separation[0] -= d[0];
					separation[1] -= d[1];
					separation[2] -= d[2];
					
				}
				//Vector.add(alignment, entity.velocity);
				alignment[0] += entity.velocity[0];
				alignment[1] += entity.velocity[1];
				alignment[2] += entity.velocity[2];
			}
		}
		//Vector.mul(cohesion, n);
		//Vector.sub(cohesion, position);
		//Vector.addmul(acceleration, cohesion, influences[0]);
		acceleration[0] += (cohesion[0] * n - position[0]) * influences[0];
		acceleration[1] += (cohesion[1] * n - position[1]) * influences[0];
		acceleration[2] += (cohesion[2] * n - position[2]) * influences[0];
		//Vector.addmul(acceleration, separation, influences[1]);
		acceleration[0] += separation[0] * influences[1];
		acceleration[1] += separation[1] * influences[1];
		acceleration[2] += separation[2] * influences[1];
		//Vector.mul(alignment, n);
		//Vector.sub(alignment, velocity);
		//Vector.addmul(acceleration, alignment, influences[2]);
		acceleration[0] += (alignment[0] * n - velocity[0]) * influences[2];
		acceleration[1] += (alignment[1] * n - velocity[1]) * influences[2];
		acceleration[2] += (alignment[2] * n - velocity[2]) * influences[2];
		//Vector.set(direction, position, mParent.emitter.position);
		//Vector.addmul(acceleration, direction, influences[3]);
		acceleration[0] += (mParent.emitter.position[0] - position[0]) * influences[3];
		acceleration[1] += (mParent.emitter.position[1] - position[1]) * influences[3];
		acceleration[2] += (mParent.emitter.position[2] - position[2]) * influences[3];
	}
	
	protected void look(float[] heading) {
		heading[0] = 0;
		heading[1] = (float) (RAGTODEG * (Math.atan2(velocity[0], velocity[2]) + Math.PI));
		heading[2] = (float) (RAGTODEG * Math.atan(velocity[1] / velocity[2]));
	}
	
	protected static final double RAGTODEG = 180.0f / FastMath.PI;
	protected final float[] mInfluences = {1, 10, 1, 1};
	protected float[] mAcceleration = {0, 0, 0};
	protected float[] mHeading = {0, 0, 0};
	protected int mTurnThink = 0;
	protected int mTurn = 0;
	
}
