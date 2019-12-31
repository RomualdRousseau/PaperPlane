package com.rworld.core.v2.graphics;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class MeshLoader extends MeshBuilder {

	public MeshLoader() {
		super(MeshBuilder.OPTIONS_NORMAL | MeshBuilder.OPTIONS_TEXTURE, MeshBuilder.OPTIONS_DEFAULT);
	}
	
	public abstract void onLoadFromStream(BufferedReader stream) throws IOException;
}
