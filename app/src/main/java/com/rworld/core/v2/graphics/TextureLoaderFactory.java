package com.rworld.core.v2.graphics;

import java.io.IOException;

import com.rworld.core.v2.graphics.loaders.ImageTextureLoader;

import android.content.res.AssetManager;

public class TextureLoaderFactory {
	
	public static TextureLoader createInstance(AssetManager assets, String filename) throws IOException {
		ImageTextureLoader loader = new ImageTextureLoader();
		loader.onLoadFromStream(assets.open(filename));
		return loader;
	}
}
