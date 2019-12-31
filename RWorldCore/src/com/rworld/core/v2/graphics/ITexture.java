package com.rworld.core.v2.graphics;

public interface ITexture {

	void release();
	
	public void bind();
	
	public void ensureData(TextureBuilder builder);	
}
