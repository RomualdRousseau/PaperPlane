package com.rworld.PaperPlane.meshes;

import com.rworld.core.v2.GameEntity;
import com.rworld.core.v2.graphics.Font;
import com.rworld.core.v2.graphics.GraphicManager;
import com.rworld.core.v2.graphics.IMesh;
import com.rworld.core.v2.graphics.builders.CubeBuilder;

public class CubeTextMesh {
	
	public CubeTextMesh(GraphicManager graphicManager, Font font, int fontScale, String message) {
		mGraphicManager = graphicManager;
		mFont = font;
		mFontScale = fontScale;
		mMessage = message;
		mMeshes = new IMesh[message.length()];
		mEntities = new GameEntity[message.length()];
		
		for(int i = 0; i < message.length(); i++) {
			mMeshes[i] = mGraphicManager.createStaticMesh();
			mMeshes[i].enableTexture(font.texture);
			mEntities[i] = new GameEntity();
			mEntities[i].position[0] = 10.0f + i * 0.9f;
			mEntities[i].position[2] = -10.0f - i * 0.9f;
		}
	}
	
	public void dispose() {
		for(int i = 0; i < mMeshes.length; i++) {
			mMeshes[i].release();
		}
	}
	
	public void update(float time) {
		for(int i = 0; i < mEntities.length; i++) {
			mEntities[i].position[0] -= 2.1f * time;
			mEntities[i].position[2] += 2.1f * time;
			mEntities[i].rotation[1] += 2.1f * time;
		}
	}
	
	public void render() {
		for(int i = 0; i < mMeshes.length; i++) {
			mMeshes[i].draw(mEntities[i], true);
		}
	}
	
	public void ensureData() {
		final float[] position = {0.0f, 0.0f, 0.0f};
		final float [] size = {0.4f, 0.4f, 0.4f};
		float[] uvcoord = {0.0f, 0.0f, 0.0f, 0.0f};
		CubeBuilder cubeGenerator = new CubeBuilder();
		for(int i = 0; i < mMeshes.length; i++) {
			int c = mMessage.codePointAt(i) - 32;
			uvcoord[0] = (c & 15) * mFont.width;
			uvcoord[1] = ((c >> 4) + 1) * mFont.height;
			uvcoord[2] = uvcoord[0] + mFont.width - 1;
			uvcoord[3] = uvcoord[1] - mFont.height + 1;
			uvcoord[0] /= 512.0f;
			uvcoord[1] /= 256.0f;
			uvcoord[2] /= 512.0f;
			uvcoord[3] /= 256.0f;
			cubeGenerator.build(position, size, uvcoord);
			mMeshes[i].ensureData(cubeGenerator);
		}
	}

	protected GraphicManager mGraphicManager = null;
	protected Font mFont = null;
	protected int mFontScale = 0;
	protected String mMessage = "";
	protected IMesh[] mMeshes = null;
	protected GameEntity[] mEntities = null;
}
