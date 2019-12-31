package com.rworld.core.v2.graphics;

public class Font {
	
	public ITexture texture;
	public int width;
	public int height;

	public Font(int width, int height, ITexture texture) {
		this.width = width;
		this.height = height;
		this.texture = texture;
	}
}
