package com.rworld.core.v2.physics;

import java.util.ArrayList;
import java.util.LinkedList;

import com.rworld.core.v2.GameEntity;

public abstract class PhysicSystem {

	public GameEntity emitter = null;
	
	public final ArrayList<PhysicEntity> entities = new ArrayList<PhysicEntity>();
	
	public abstract PhysicEntity allocateEntity(PhysicSystem parent);
	
	public PhysicSystem(int maxFrames, int maxEntities, int maxLifetime, GameEntity emitter) {
		mMaxFrames = maxFrames;
		mMaxEntities = maxEntities;
		mMaxLifetime = maxLifetime;
		this.emitter = emitter;
	}
	
	public int getBufferSize() {
		return mMaxLifetime * mMaxFrames / mMaxEntities;
	}
	
	public boolean isCompleted() {
		synchronized(entities) {
			return (mCurrentFrame >= mMaxFrames) && entities.isEmpty();
		}
	}
	
	public void restart(boolean killAll) {
		if(killAll) {
			synchronized(entities) {
				final int s = entities.size();
				for(int i = 0; i < s; i++) {
					mNotUsedEntities.addFirst(entities.get(i));
				}
				for(int i = 0; i < s; i++) {
					entities.remove(entities.size() - 1);
				}
			}
		}
		mCurrentFrame = 0;
	}
	
	public void update(float time) {
		synchronized(entities) {
			if(mCurrentFrame < mMaxFrames) {
				final int n = mMaxEntities / mMaxFrames;
				for(int i = 0; i < n; i++) {
					spawnEntity();
				}
				mCurrentFrame++;
			}
			final int s = entities.size();
			int n = s;
			for(int i = 0; i < n;) {
				final PhysicEntity entity = entities.get(i);
				if(entity.lifetime > 0.0f) {
					entity.update(time);
					entity.lifetime--;
					i++;
				}
				else {
					mNotUsedEntities.addFirst(entity);
					entities.set(i, entities.get(--n));
				}
			}
			for(int i = s - 1; i >= n; i--) {
				entities.remove(i);
			}
		}
	}
	
	protected PhysicEntity spawnEntity() {
		PhysicEntity entity = mNotUsedEntities.isEmpty() ? allocateEntity(this) : mNotUsedEntities.removeFirst();
		entity.init(emitter, mMaxLifetime);
		entities.add(entity);
		return entity;
	}
	
	protected int mMaxFrames = 1;
	protected int mCurrentFrame = 0;
	protected int mMaxEntities = 1000;
	protected int mMaxLifetime = 100;
	protected LinkedList<PhysicEntity> mNotUsedEntities = new LinkedList<PhysicEntity>();
}
