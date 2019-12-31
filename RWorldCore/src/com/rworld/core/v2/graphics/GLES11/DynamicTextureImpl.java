package com.rworld.core.v2.graphics.GLES11;

import com.rworld.core.v2.graphics.TextureBuilder;

public class DynamicTextureImpl extends StaticTextureImpl {

	@Override
	public void bind() {
		super.bind();
		mBuilder.updateTextureData();
	}
	
	@Override
	public void ensureData(TextureBuilder builder) {
		super.ensureData(builder);
		mBuilder = builder;
	}
	
	protected TextureBuilder mBuilder = null;
}
