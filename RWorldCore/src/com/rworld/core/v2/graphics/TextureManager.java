package com.rworld.core.v2.graphics;

import java.util.Hashtable;
import java.util.Map.Entry;

import com.rworld.core.v2.Log;

public class TextureManager {
	
	public TextureManager(GraphicManager graphicManager) {
		mGraphicManager = graphicManager;
	}
	
	public ITexture get(String filename) {		
		if(!mTextureCache.containsKey(filename)) {
			ITexture texture = mGraphicManager.createStaticTexture();
			mTextureCache.put(filename, texture);
			return texture;
		}
		else {
			return mTextureCache.get(filename);
		}
	}
	
	public void release(String filename) {
		if(mTextureCache.containsKey(filename)) {
			ITexture texture = mTextureCache.remove(filename);
			texture.release();
		}
	}
	
	public void ensureTextures() {
		for(Entry<String, ITexture> e: mTextureCache.entrySet()) {
			ensureTexture(e.getKey(), e.getValue());
		}
	}
	
	protected void ensureTexture(String filename, ITexture texture) {
		try {
			Log.d("Game is loading texture '" + filename + "'...");
			TextureBuilder builder = TextureLoaderFactory.createInstance(mGraphicManager.mActivity.getAssets(), filename);
			texture.ensureData(builder);
		} catch (Exception e) {
			Log.e("", e);
		}
	}
	
	protected GraphicManager mGraphicManager = null;
	protected Hashtable<String, ITexture> mTextureCache = new Hashtable<String, ITexture>();
}
