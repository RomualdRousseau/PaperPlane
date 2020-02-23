package com.rworld.core.v2.graphics;

public interface ITexture {

	void release();
	
	void bind();
	
	void ensureData(TextureBuilder builder);
}
