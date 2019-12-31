package com.rworld.core.v2.graphics.builders;

import com.rworld.core.v2.graphics.MeshBuilder;

public class PlaneBuilder extends MeshBuilder {

	public PlaneBuilder() {
		super(MeshBuilder.OPTIONS_NORMAL | MeshBuilder.OPTIONS_TEXTURE, MeshBuilder.OPTIONS_DEFAULT);
	}
	
	public void build(float[] position, float[] size, float[] uvcoord) {
		addElement(new float[]{position[0] + size[0], position[2], position[2] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{uvcoord[2], uvcoord[3]});
		addElement(new float[]{position[0] - size[0], position[2], position[2] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{uvcoord[0], uvcoord[3]});
		addElement(new float[]{position[0] - size[0], position[2], position[2] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{uvcoord[0], uvcoord[1]});
		addElement(new float[]{position[0] + size[0], position[2], position[2] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{uvcoord[2], uvcoord[3]});
		addElement(new float[]{position[0] - size[0], position[2], position[2] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{uvcoord[0], uvcoord[1]});
		addElement(new float[]{position[0] + size[0], position[2], position[2] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{uvcoord[2], uvcoord[1]});
	}
}
