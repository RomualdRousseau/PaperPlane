package com.rworld.PaperPlane.meshes;

import com.rworld.core.v2.graphics.MeshBuilder;

import java.nio.FloatBuffer;

public class TunnelBuilder extends MeshBuilder {

    public TunnelBuilder(int stacks, int slices) {
        super(MeshBuilder.OPTIONS_TEXTURE, MeshBuilder.OPTIONS_TEXTURE);
        build(stacks, slices);
    }

    @Override
    public void updateMeshData(FloatBuffer vertexBuffer, FloatBuffer normalBuffer, FloatBuffer texCoordBuffer, FloatBuffer colorBuffer) {
        final float ct = (float) Math.cos(mTime);
        final float st = (float) Math.sin(mTime);
        for (int i = 0; i < mVertexArray.length; i += 3) {
            mVertexArray[i + 0] += mVertexModifier[i + 1] * st + mVertexModifier[i + 0] * ct;
            mVertexArray[i + 1] += mVertexModifier[i + 0] * st - mVertexModifier[i + 1] * ct;
        }
        vertexBuffer.rewind();
        vertexBuffer.put(mVertexArray);

        for (int i = 0; i < mTextureArray.length; i += 2) {
            //mTextureArray[i + 0] = mTextureModifier[i + 0] + (mTime % 1.0f);
            mTextureArray[i + 1] = mTextureModifier[i + 1] + (mTime % 1.0f);
        }
        texCoordBuffer.rewind();
        texCoordBuffer.put(mTextureArray);

        mTime += 0.1f;
    }

    protected void build(int stacks, int slices) {
        for (int i = 0; i <= stacks; i++) {
            for (int j = 0; j < slices; j++) {
                final float fi = (float) i / stacks;
                final float fii = (float) (i + 1) / stacks;
                final float fj = (float) Math.PI * 2.0f * j / slices;
                final float fjj = (float) Math.PI * 2.0f * (j + 1) / slices;

                Element e1 = new Element();
                e1.vertex[0] = (float) Math.cos(fj) + i * (float) Math.sin(fi) / 4.0f;
                e1.vertex[1] = (float) Math.sin(fj) + i * (float) Math.cos(fi) / 4.0f;
                e1.vertex[2] = 4 - i * 0.5f;
                e1.texCoord[0] = 0.0f;
                e1.texCoord[1] = ((i % 2) == 0) ? 0.0f : 0.0f;
                addElement(e1);

                Element e6 = new Element();
                e6.vertex[0] = (float) Math.cos(fjj) + i * (float) Math.sin(fi) / 4.0f;
                e6.vertex[1] = (float) Math.sin(fjj) + i * (float) Math.cos(fi) / 4.0f;
                e6.vertex[2] = 4 - i * 0.5f;
                e6.texCoord[0] = 1.0f;
                e6.texCoord[1] = ((i % 2) == 0) ? 0.0f : 0.0f;
                addElement(e6);

                Element e3 = new Element();
                e3.vertex[0] = (float) Math.cos(fjj) + (i + 1) * (float) Math.sin(fii) / 4.0f;
                e3.vertex[1] = (float) Math.sin(fjj) + (i + 1) * (float) Math.cos(fii) / 4.0f;
                e3.vertex[2] = 4 - (i + 1) * 0.5f;
                e3.texCoord[0] = 1.0f;
                e3.texCoord[1] = ((i % 2) == 0) ? 1.0f : 1.0f;
                addElement(e3);

                Element e4 = new Element();
                e4.vertex[0] = (float) Math.cos(fj) + i * (float) Math.sin(fi) / 4.0f;
                e4.vertex[1] = (float) Math.sin(fj) + i * (float) Math.cos(fi) / 4.0f;
                e4.vertex[2] = 4 - i * 0.5f;
                e4.texCoord[0] = 0.0f;
                e4.texCoord[1] = ((i % 2) == 0) ? 0.0f : 0.0f;
                addElement(e4);

                Element e5 = new Element();
                e5.vertex[0] = (float) Math.cos(fjj) + (i + 1) * (float) Math.sin(fii) / 4.0f;
                e5.vertex[1] = (float) Math.sin(fjj) + (i + 1) * (float) Math.cos(fii) / 4.0f;
                e5.vertex[2] = 4 - (i + 1) * 0.5f;
                e5.texCoord[0] = 1.0f;
                e5.texCoord[1] = ((i % 2) == 0) ? 1.0f : 1.0f;
                addElement(e5);

                Element e2 = new Element();
                e2.vertex[0] = (float) Math.cos(fj) + (i + 1) * (float) Math.sin(fii) / 4.0f;
                e2.vertex[1] = (float) Math.sin(fj) + (i + 1) * (float) Math.cos(fii) / 4.0f;
                e2.vertex[2] = 4 - (i + 1) * 0.5f;
                e2.texCoord[0] = 0.0f;
                e2.texCoord[1] = ((i % 2) == 0) ? 1.0f : 1.0f;
                addElement(e2);
            }
        }

        mVertexArray = new float[getElementCount() * 3];
        mTextureArray = new float[getElementCount() * 2];
        fillDynamicArrays(mVertexArray, null, mTextureArray, null);

        mVertexModifier = new float[getElementCount() * 3];
        for (int i = 0; i < mVertexArray.length; i += 3) {
            mVertexModifier[i + 0] = 0.0025f * (mVertexArray[i + 2] - 4) * (float) Math.cos(mVertexArray[i + 2]);
            mVertexModifier[i + 1] = 0.0025f * (mVertexArray[i + 2] - 4) * (float) Math.sin(mVertexArray[i + 2]);
            mVertexModifier[i + 2] = mVertexArray[i + 2];
        }

        mTextureModifier = new float[getElementCount() * 2];
        for (int i = 0; i < mTextureArray.length; i += 2) {
            mTextureModifier[i + 0] = mTextureArray[i + 0];
            mTextureModifier[i + 1] = mTextureArray[i + 1];
        }
    }

    protected float[] mVertexArray = null;
    protected float[] mVertexModifier = null;
    protected float[] mTextureArray = null;
    protected float[] mTextureModifier = null;
    protected float mTime = 0;
}
