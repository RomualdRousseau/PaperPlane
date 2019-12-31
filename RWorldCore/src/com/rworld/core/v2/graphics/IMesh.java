package com.rworld.core.v2.graphics;

public interface IMesh extends IDrawable {
	
	void release();
	
	int countofElements();
	
	int countofFaces();
	
	float[] getBoundingBox();
	
	void resizeBoundingBox(float[] newBBox);
	
	void enableNormals(boolean normals);
	
	void enableTexture(ITexture texture);
	
	void enableColors(boolean colors);
	
	void ensureData(MeshBuilder builder);
}
