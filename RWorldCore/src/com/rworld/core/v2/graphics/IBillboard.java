package com.rworld.core.v2.graphics;

public interface IBillboard extends IDrawable {
	
	void release();
	
	void setTexture(ITexture texture);
	
	void ensureData(int count, float size);
}
