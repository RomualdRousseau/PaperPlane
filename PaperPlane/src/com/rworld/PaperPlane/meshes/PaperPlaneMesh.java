package com.rworld.PaperPlane.meshes;

import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.IMesh;
import com.rworld.core.v2.physics.PhysicSystem;

public class PaperPlaneMesh {

	public GameEntity entity = GameEntity.NULL;
	
	public PaperPlaneMesh(GraphicManager graphicManager) {
		mGraphicManager = graphicManager;
		mMesh = mGraphicManager.meshManager.get("meshes/paperplane.obj");
		mMesh.enableTexture(mGraphicManager.textureManager.get("textures/paperplane_skin.jpg"));
	}
	
	public void dispose() {
		mGraphicManager.meshManager.release("meshes/paperplane.obj");
		mGraphicManager.textureManager.release("textures/paperplane_skin.jpg");
	}
	
	public void render() {
		mMesh.draw(entity, true);
	}

	public void render(boolean checkVisibility) {
		mMesh.draw(entity, checkVisibility);
	}
	
	public void render(PhysicSystem physicSystem) {
		mMesh.draw(physicSystem.entities, false);
	}
	
	private GraphicManager mGraphicManager = null;
	private IMesh mMesh = null;
}
