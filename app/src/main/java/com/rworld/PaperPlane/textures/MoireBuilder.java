package com.rworld.PaperPlane.textures;

import android.opengl.GLES11;

import com.rworld.core.v2.graphics.TextureBuilder;
import com.rworld.core.v2.math.FixedMath;

import java.nio.IntBuffer;

public class MoireBuilder extends TextureBuilder {

    public MoireBuilder() {
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 256; j++) {
                int x = j - 128;
                int y = i - 128;
                mMoireTable[i * 256 + j] = ((float) Math.sin((float) Math.sqrt(x * x + y * y)) > 0) ? (byte) 1 : (byte) 0;
            }
        }

        mColor[0] = (0 << 0) + (127 << 8) + (255 << 16) + (255 << 24);
        mColor[1] = (0 << 0) + (255 << 8) + (255 << 16) + (255 << 24);
    }


    @Override
    public void createTextureData() {
        build();
        GLES11.glTexImage2D(GLES11.GL_TEXTURE_2D, 0, GLES11.GL_RGBA, 128, 128, 0, GLES11.GL_RGBA, GLES11.GL_UNSIGNED_BYTE, IntBuffer.wrap(mBitmap));
    }

    @Override
    public void updateTextureData() {
        build();
        GLES11.glTexSubImage2D(GLES11.GL_TEXTURE_2D, 0, 0, 0, 128, 128, GLES11.GL_RGBA, GLES11.GL_UNSIGNED_BYTE, IntBuffer.wrap(mBitmap));
    }

    protected void build() {
        final int a = FixedMath.sin((mTime << 1) + 256);
        final int b = FixedMath.sin(mTime);
        final int c = 64 + (a >> 5);
        final int d = 64 - (a >> 5);

        int x1 = c;
        int x2 = d;
        int y1 = 64 + (b >> 5);
        int y2 = 64 - (b >> 5);

        int off1 = (y1 & 255) << 8;
        int off2 = (y2 & 255) << 8;
        int off3 = 0;

        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 128; j++) {
                mBitmap[off3++] = mColor[mMoireTable[off1 + (x1 & 255)] ^ mMoireTable[off2 + (x2 & 255)]];

                x1++;
                x2++;
            }

            off1 = (++y1 & 255) << 8;
            off2 = (++y2 & 255) << 8;
            x1 = c;
            x2 = d;
        }

        mTime += 25;
    }

    protected int[] mBitmap = new int[128 * 128];
    protected byte[] mMoireTable = new byte[256 * 256];
    protected int[] mColor = new int[2];
    protected int mTime = 0;
}
