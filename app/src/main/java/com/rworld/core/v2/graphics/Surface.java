package com.rworld.core.v2.graphics;

public class Surface {
	
	public ITexture texture;
	public int x;
	public int y;
	public int width;
	public int height;
	
	public Surface(int x, int y, int width, int height, ITexture texture) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
	}
	
	public void release() {
		this.texture.release();
	}
}
