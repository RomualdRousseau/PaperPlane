package com.rworld.core.v2.graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

import android.content.res.AssetManager;

import com.rworld.core.v2.graphics.loaders.ObjMeshLoader;

public class MeshLoaderFactory {
	
	public static void registerLoader(String typeOfLoader, MeshLoader loader) {
		MeshLoaderFactory.mLoaders.put(typeOfLoader, loader);
	}

	public static MeshLoader createInstance(AssetManager assets, String filename) throws NoSuchMethodException, IOException {
		MeshLoaderFactory.initialize();
		
		final String key = MeshLoaderFactory.buildKey(filename);
		if(!MeshLoaderFactory.mLoaders.containsKey(key)) {
			throw new UnsupportedOperationException("Could not find a suitable mesh loader for this object");
		}
		
		final MeshLoader loader = MeshLoaderFactory.mLoaders.get(key);
		final BufferedReader stream = new BufferedReader(new InputStreamReader(assets.open(filename)), 8192);
		loader.onLoadFromStream(stream);
		stream.close();
		return loader;
	}

	protected static void initialize() {
		if(mInitialized) {
			return;
		}
		MeshLoaderFactory.registerLoader(".obj", new ObjMeshLoader());
		mInitialized = true;
	}
	
	protected static String buildKey(String filename) {
		final int indexOfDot = filename.lastIndexOf(".");
		return ((indexOfDot != -1) ? filename.substring(indexOfDot, indexOfDot + 4) : filename).toLowerCase();
	}

	protected static boolean mInitialized = false;
	protected static Hashtable<String, MeshLoader> mLoaders = new Hashtable<String, MeshLoader>();
}
