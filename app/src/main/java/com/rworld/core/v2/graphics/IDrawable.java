package com.rworld.core.v2.graphics;

import java.util.List;

import com.rworld.core.v2.GameEntity;

public interface IDrawable {
	
	void enableAlphaMask(boolean alpha);
	
	boolean draw(GameEntity entity, boolean checkVisibility);
	
	boolean draw(List<? extends GameEntity> entities, boolean checkVisibility);
}

