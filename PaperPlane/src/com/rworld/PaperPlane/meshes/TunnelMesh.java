package com.rworld.PaperPlane.meshes;

import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.IMesh;

public class TunnelMesh {

    public GameEntity entity = new GameEntity();

    public TunnelMesh(GraphicManager graphicManager) {
        mGraphicManager = graphicManager;
        mMesh = mGraphicManager.createDynamicMesh();
        mMesh.enableTexture(mGraphicManager.textureManager.get("textures/tunnel.png"));
    }

    public void dispose() {
        mMesh.release();
        mGraphicManager.textureManager.release("textures/tunnel.png");
    }

    public void update(float time) {
        entity.rotation[2] -= 5.0f * (float) Math.sin(mVelocity);
        mVelocity += time;
    }

    public void render() {
        mMesh.draw(entity, false);
    }

    public void ensureData() {
        mMesh.ensureData(new TunnelBuilder(8, 16));
    }

    protected GraphicManager mGraphicManager = null;
    protected IMesh mMesh = null;
    protected float mVelocity = 0.0f;
}
