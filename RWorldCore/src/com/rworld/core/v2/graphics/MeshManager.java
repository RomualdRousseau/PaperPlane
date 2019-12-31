package com.rworld.core.v2.graphics;

import java.util.Hashtable;
import java.util.Map.Entry;

import com.rworld.core.v2.Log;

public class MeshManager {

	public MeshManager(GraphicManager graphicManager) {
		mGraphicManager = graphicManager;
	}
	
	public IMesh get(String filename) {		
		if(!mMeshCache.containsKey(filename)) {
			IMesh mesh = mGraphicManager.createStaticMesh();
			mMeshCache.put(filename, mesh);
			return mesh;
		}
		else {
			return mMeshCache.get(filename);
		}
	}
	
	public void release(String filename) {
		if(mMeshCache.containsKey(filename)) {
			IMesh mesh = mMeshCache.remove(filename);
			mesh.release();
		}
	}
	
	public void ensureMeshes() {
		for(Entry<String, IMesh> e: mMeshCache.entrySet()) {
			ensureMesh(e.getKey(), e.getValue());
		}
	}
	
	protected void ensureMesh(String filename, IMesh mesh) {
		try {
			Log.d("Game is loading mesh '" + filename + "'...");
			MeshBuilder builder = MeshLoaderFactory.createInstance(mGraphicManager.mActivity.getAssets(), filename);
			mesh.ensureData(builder);
			builder.recycle();
		} catch (Exception e) {
			Log.e("", e);
		}
	}
	
	protected GraphicManager mGraphicManager = null;
	protected Hashtable<String, IMesh> mMeshCache = new Hashtable<String, IMesh>();
}
