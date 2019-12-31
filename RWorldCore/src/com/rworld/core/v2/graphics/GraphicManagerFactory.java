package com.rworld.core.v2.graphics;

import com.rworld.core.v2.GameActivity;
import com.rworld.core.v2.graphics.GLES11.GraphicManagerImpl;

public class GraphicManagerFactory {

	public static GraphicManager createInstance(GameActivity activity) {
		return new GraphicManagerImpl(activity);
	}	
}
