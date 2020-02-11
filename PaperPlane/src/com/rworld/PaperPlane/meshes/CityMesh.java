package com.rworld.PaperPlane.meshes;

import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.IMesh;
import com.rworld.core.v2.math.BoundingBox;

public class CityMesh {

    public CityMesh(GraphicManager graphicManager) {
        mGraphicManager = graphicManager;
        mQuadTreeMeshes = new IMesh[countofCityNodes(mQuadTreeLevel)];
        allocateCityNodes(mQuadTreeLevel, 0);
    }

    public void dispose() {
        for (int i = 0; i < mQuadTreeMeshes.length; i++) {
            mQuadTreeMeshes[i].release();
        }
        mGraphicManager.textureManager.release("textures/city_skin.jpg");
    }

    public void render() {
        mQuadTreeMeshIndex[0] = 0;
        mQuadTreeMeshIndex[1] = 1;
        for (int level = 1; level > 0; ) {
            if (mQuadTreeMeshIndex[level] < 5 + mQuadTreeMeshIndex[level - 1] * 4) {
                if (mQuadTreeMeshes[mQuadTreeMeshIndex[level]].draw(GameEntity.NULL, true) && (level < mQuadTreeLevel)) {
                    level++;
                    mQuadTreeMeshIndex[level] = 1 + mQuadTreeMeshIndex[level - 1] * 4;
                    continue;
                }
            } else {
                level--;
            }
            mQuadTreeMeshIndex[level]++;
        }
    }

    public void ensureData() {
        generateCityNodes(mQuadTreeLevel, 0, new CityBuilder(), new float[]{-8, -8, 8, 8});
    }

    protected int countofCityNodes(int level) {
        return (level == 0) ? 1 : 1 + countofCityNodes(level - 1) + countofCityNodes(level - 1) + countofCityNodes(level - 1) + countofCityNodes(level - 1);
    }

    protected void allocateCityNodes(int level, int node) {
        if (level == 0) {
            mQuadTreeMeshes[node] = mGraphicManager.createStaticMesh();
            mQuadTreeMeshes[node].enableColors(true);
            mQuadTreeMeshes[node].enableTexture(mGraphicManager.textureManager.get("textures/city_skin.jpg"));
        } else {
            mQuadTreeMeshes[node] = mGraphicManager.createNullMesh();

            final int child = 1 + node * 4;
            allocateCityNodes(level - 1, child + 0);
            allocateCityNodes(level - 1, child + 1);
            allocateCityNodes(level - 1, child + 2);
            allocateCityNodes(level - 1, child + 3);
        }
    }

    protected void generateCityNodes(int level, int node, CityBuilder cityGenerator, float[] voxel) {
        if (level == 0) {
            cityGenerator.build(new float[]{(voxel[0] + voxel[2]) / 2, (voxel[1] + voxel[3]) / 2}, new float[]{Math.abs(voxel[2] - voxel[0]), Math.abs(voxel[3] - voxel[1])});
            mQuadTreeMeshes[node].ensureData(cityGenerator);
        } else {
            final float centerX = (voxel[0] + voxel[2]) / 2;
            final float centerY = (voxel[1] + voxel[3]) / 2;

            final int child = 1 + node * 4;
            generateCityNodes(level - 1, child + 0, cityGenerator, new float[]{voxel[0], voxel[1], centerX, centerY});
            generateCityNodes(level - 1, child + 1, cityGenerator, new float[]{centerX, voxel[1], voxel[2], centerY});
            generateCityNodes(level - 1, child + 2, cityGenerator, new float[]{voxel[0], centerY, centerX, voxel[3]});
            generateCityNodes(level - 1, child + 3, cityGenerator, new float[]{centerX, centerY, voxel[2], voxel[3]});

            float[] bbox = mQuadTreeMeshes[node].getBoundingBox();
            BoundingBox.minimum(bbox);
            BoundingBox.merge(bbox, mQuadTreeMeshes[child + 0].getBoundingBox());
            BoundingBox.merge(bbox, mQuadTreeMeshes[child + 1].getBoundingBox());
            BoundingBox.merge(bbox, mQuadTreeMeshes[child + 2].getBoundingBox());
            BoundingBox.merge(bbox, mQuadTreeMeshes[child + 3].getBoundingBox());
            mQuadTreeMeshes[node].resizeBoundingBox(bbox);
            mQuadTreeMeshes[node].ensureData(null);
        }
    }

    protected GraphicManager mGraphicManager = null;
    protected final int mQuadTreeLevel = 2;
    protected int[] mQuadTreeMeshIndex = new int[mQuadTreeLevel + 1];
    protected IMesh[] mQuadTreeMeshes = null;
}
