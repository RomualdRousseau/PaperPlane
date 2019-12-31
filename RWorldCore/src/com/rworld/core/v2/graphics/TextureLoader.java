package com.rworld.core.v2.graphics;

import java.io.InputStream;

public abstract class TextureLoader extends TextureBuilder {

	public abstract void onLoadFromStream(InputStream stream);
}
