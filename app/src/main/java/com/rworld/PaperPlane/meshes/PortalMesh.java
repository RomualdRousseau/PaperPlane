package com.rworld.PaperPlane.meshes;

import android.opengl.GLES11;

import com.rworld.PaperPlane.Camera2;
import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.IMesh;
import com.rworld.core.v2.math.Frustrum;

public class PortalMesh {

    public PortalMesh(GraphicManager graphicManager) {
        mGraphicManager = graphicManager;
        mMesh = mGraphicManager.meshManager.get("meshes/room.obj");
        mMesh.enableNormals(true);

        for (int i = 0; i < 6; i++) {
            mPortals[i] = new Portal();
        }

        mPortals[0].center[0] = -2.56f;
        mPortals[0].center[1] = 0.0f;
        mPortals[0].center[2] = 0.0f;
        mPortals[0].portal = mPortals[1];

        mPortals[1].center[0] = 2.56f;
        mPortals[1].center[1] = 0.0f;
        mPortals[1].center[2] = 0.0f;
        mPortals[1].portal = mPortals[0];

        mPortals[2].center[0] = 0.0f;
        mPortals[2].center[1] = -2.56f;
        mPortals[2].center[2] = 0.0f;
        mPortals[2].portal = mPortals[3];

        mPortals[3].center[0] = 0.0f;
        mPortals[3].center[1] = 2.56f;
        mPortals[3].center[2] = 0.0f;
        mPortals[3].portal = mPortals[2];

        mPortals[4].center[0] = 0.0f;
        mPortals[4].center[1] = 0.0f;
        mPortals[4].center[2] = -2.56f;
        mPortals[4].portal = mPortals[5];

        mPortals[5].center[0] = 0.0f;
        mPortals[5].center[1] = 0.0f;
        mPortals[5].center[2] = 2.56f;
        mPortals[5].portal = mPortals[4];
    }

    public void dispose() {
        mMesh.release();
    }

    public void update(float time) {
    }

    public void render(Camera2 camera) {
        v0[0] = (float) Math.floor((camera.position[0] + 2.56f) / 5.12f) * 5.12f;
        v0[1] = (float) Math.floor((camera.position[1] + 2.56f) / 5.12f) * 5.12f;
        v0[2] = (float) Math.floor((camera.position[2] + 2.56f) / 5.12f) * 5.12f;

        GLES11.glPushMatrix();
        GLES11.glTranslatef(v0[0], v0[1], v0[2]);
        mMesh.draw(GameEntity.NULL, false);

        for (int i = 0; i < 6; i++) {
            v1[0] = mPortals[i].center[0] + v0[0];
            v1[1] = mPortals[i].center[1] + v0[1];
            v1[2] = mPortals[i].center[2] + v0[2];
            if (Frustrum.isSphereVisible(v1, 0.2f)) {
                v1[0] = mPortals[i].center[0] - mPortals[i].portal.center[0];
                v1[1] = mPortals[i].center[1] - mPortals[i].portal.center[1];
                v1[2] = mPortals[i].center[2] - mPortals[i].portal.center[2];

                GLES11.glPushMatrix();
                GLES11.glTranslatef(v1[0], v1[1], v1[2]);
                mMesh.draw(GameEntity.NULL, false);
                GLES11.glPopMatrix();
            }
        }

        GLES11.glPopMatrix();
    }

    protected GraphicManager mGraphicManager = null;
    protected IMesh mMesh = null;
    protected Portal[] mPortals = new Portal[6];
    protected float[] v0 = {0.0f, 0.0f, 0.0f};
    protected float[] v1 = {0.0f, 0.0f, 0.0f};

    protected class Portal {
        public float[] center = {0.0f, 0.0f, 0.0f};
        public Portal portal = null;
    }
}
