package com.rworld.PaperPlane.meshes;

import com.rworld.PaperPlane.MainActivity;
import com.rworld.core.v2.graphics.MeshBuilder;

public class CityBuilder extends MeshBuilder {

	public CityBuilder() {
		super(MeshBuilder.OPTIONS_NORMAL | MeshBuilder.OPTIONS_TEXTURE | MeshBuilder.OPTIONS_COLOR, MeshBuilder.OPTIONS_DEFAULT);
	}
	
	public void build(float[] center, float[] size) {
		for(int y = 0; y < size[1]; y++) {
			for(int x = 0; x < size[0]; x++) {
				final float[] position = {center[0] + x - size[0] * 0.5f, center[1] + y - size[1] * 0.5f};
				if((x % 2) == 0 && (y % 2) == 0) {
					final float tall = MainActivity.random.nextFloat() * 3.0f + 1.0f;
					switch(MainActivity.random.nextInt(4)) {
						case 0:
							generateBuilding1(position, new float[]{0.5f, tall, 0.5f}, new float[]{1.0f, 0.5f, 0.0f, 1.0f});
							break;
							
						case 1:
							generateBuilding1(position, new float[]{0.5f, tall, 0.5f}, new float[]{1.0f, 0.0f, 0.0f, 1.0f});
							break;
							
						case 2:
							generateBuilding2(position, new float[]{0.5f, tall, 0.5f}, new float[]{1.0f, 0.5f, 0.0f, 1.0f});
							break;
							
						case 3:
							generateBuilding2(position, new float[]{0.5f, tall, 0.5f}, new float[]{1.0f, 0.0f, 0.0f, 1.0f});
							break;
					}
				}
				else if((x % 2) == 0 && (y % 2) == 1) {
					generateStreetX(position, new float[]{0.5f, 0.0f, 0.5f});
				}
				else if((x % 2) == 1 && (y % 2) == 0) {
					generateStreetY(position, new float[]{0.5f, 0.0f, 0.5f});
				}
				else { // if((x % 2) == 1 && (y % 2) == 1) {
					generateStreetXY(position, new float[]{0.5f, 0.0f, 0.5f});
				}
			}
		}
	}
	
	protected void generateStreetXY(float[] groundPosition, float[] size) {
		addElement(new float[]{groundPosition[0] + size[0], 0.0f, groundPosition[1] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{0.0f, 0.5f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
		addElement(new float[]{groundPosition[0] - size[0], 0.0f, groundPosition[1] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
		addElement(new float[]{groundPosition[0] - size[0], 0.0f, groundPosition[1] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{0.5f, 0.0f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
		addElement(new float[]{groundPosition[0] + size[0], 0.0f, groundPosition[1] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{0.0f, 0.5f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
		addElement(new float[]{groundPosition[0] - size[0], 0.0f, groundPosition[1] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{0.5f, 0.0f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
		addElement(new float[]{groundPosition[0] + size[0], 0.0f, groundPosition[1] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{0.5f, 0.5f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
	}
	
	public void generateStreetX(float[] groundPosition, float[] size) {
		addElement(new float[]{groundPosition[0] + size[0], 0.0f, groundPosition[1] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{0.5f, 0.5f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
		addElement(new float[]{groundPosition[0] - size[0], 0.0f, groundPosition[1] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{0.5f, 0.0f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
		addElement(new float[]{groundPosition[0] - size[0], 0.0f, groundPosition[1] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 0.0f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
		addElement(new float[]{groundPosition[0] + size[0], 0.0f, groundPosition[1] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{0.5f, 0.5f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
		addElement(new float[]{groundPosition[0] - size[0], 0.0f, groundPosition[1] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 0.0f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
		addElement(new float[]{groundPosition[0] + size[0], 0.0f, groundPosition[1] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 0.5f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
	}
	
	protected void generateStreetY(float[] groundPosition, float[] size) {
		addElement(new float[]{groundPosition[0] + size[0], 0.0f, groundPosition[1] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{0.5f, 0.0f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
		addElement(new float[]{groundPosition[0] - size[0], 0.0f, groundPosition[1] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 0.0f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
		addElement(new float[]{groundPosition[0] - size[0], 0.0f, groundPosition[1] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 0.5f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
		addElement(new float[]{groundPosition[0] + size[0], 0.0f, groundPosition[1] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{0.5f, 0.0f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
		addElement(new float[]{groundPosition[0] - size[0], 0.0f, groundPosition[1] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 0.5f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
		addElement(new float[]{groundPosition[0] + size[0], 0.0f, groundPosition[1] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{0.5f, 0.5f}, new float[]{0.0f, 0.0f, 1.0f, 1.0f});
	}

	protected void generateBuilding1(float[] groundPosition, float[] size, float[] color) {
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 1.0f}, color);
		
		addElement(new float[]{groundPosition[0] + size[0], 0.0f   , groundPosition[1] - size[2]}, new float[]{1.0f, 0.0f, 0.0f}, new float[]{0.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] - size[2]}, new float[]{1.0f, 0.0f, 0.0f}, new float[]{0.0f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] + size[2]}, new float[]{1.0f, 0.0f, 0.0f}, new float[]{0.5f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] + size[0], 0.0f   , groundPosition[1] - size[2]}, new float[]{1.0f, 0.0f, 0.0f}, new float[]{0.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] + size[2]}, new float[]{1.0f, 0.0f, 0.0f}, new float[]{0.5f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] + size[0], 0.0f   , groundPosition[1] + size[2]}, new float[]{1.0f, 0.0f, 0.0f}, new float[]{0.5f, 1.0f}, color);
		
		addElement(new float[]{groundPosition[0] + size[0], 0.0f   , groundPosition[1] + size[2]}, new float[]{0.0f, 0.0f, 1.0f}, new float[]{0.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] + size[2]}, new float[]{0.0f, 0.0f, 1.0f}, new float[]{0.0f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] + size[2]}, new float[]{0.0f, 0.0f, 1.0f}, new float[]{0.5f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] + size[0], 0.0f   , groundPosition[1] + size[2]}, new float[]{0.0f, 0.0f, 1.0f}, new float[]{0.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] + size[2]}, new float[]{0.0f, 0.0f, 1.0f}, new float[]{0.5f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] - size[0], 0.0f   , groundPosition[1] + size[2]}, new float[]{0.0f, 0.0f, 1.0f}, new float[]{0.5f, 1.0f}, color);
		
		addElement(new float[]{groundPosition[0] - size[0], 0.0f   , groundPosition[1] + size[2]}, new float[]{-1.0f, 0.0f, 0.0f}, new float[]{0.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] + size[2]}, new float[]{-1.0f, 0.0f, 0.0f}, new float[]{0.0f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] - size[2]}, new float[]{-1.0f, 0.0f, 0.0f}, new float[]{0.5f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] - size[0], 0.0f   , groundPosition[1] + size[2]}, new float[]{-1.0f, 0.0f, 0.0f}, new float[]{0.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] - size[2]}, new float[]{-1.0f, 0.0f, 0.0f}, new float[]{0.5f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] - size[0], 0.0f   , groundPosition[1] - size[2]}, new float[]{-1.0f, 0.0f, 0.0f}, new float[]{0.5f, 1.0f}, color);
		
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] - size[2]}, new float[]{0.0f, 0.0f, -1.0f}, new float[]{0.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] + size[0], 0.0f   , groundPosition[1] - size[2]}, new float[]{0.0f, 0.0f, -1.0f}, new float[]{0.0f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] - size[0], 0.0f   , groundPosition[1] - size[2]}, new float[]{0.0f, 0.0f, -1.0f}, new float[]{0.5f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] - size[2]}, new float[]{0.0f, 0.0f, -1.0f}, new float[]{0.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] - size[0], 0.0f   , groundPosition[1] - size[2]}, new float[]{0.0f, 0.0f, -1.0f}, new float[]{0.5f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] - size[2]}, new float[]{0.0f, 0.0f, -1.0f}, new float[]{0.5f, 1.0f}, color);
	}
	
	protected void generateBuilding2(float[] groundPosition, float[] size, float[] color) {
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] - size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] + size[2]}, new float[]{0.0f, 1.0f, 0.0f}, new float[]{1.0f, 1.0f}, color);
		
		addElement(new float[]{groundPosition[0] + size[0], 0.0f   , groundPosition[1] - size[2]}, new float[]{1.0f, 0.0f, 0.0f}, new float[]{0.0f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] - size[2]}, new float[]{1.0f, 0.0f, 0.0f}, new float[]{0.5f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] + size[2]}, new float[]{1.0f, 0.0f, 0.0f}, new float[]{0.5f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] + size[0], 0.0f   , groundPosition[1] - size[2]}, new float[]{1.0f, 0.0f, 0.0f}, new float[]{0.0f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] + size[2]}, new float[]{1.0f, 0.0f, 0.0f}, new float[]{0.5f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] + size[0], 0.0f   , groundPosition[1] + size[2]}, new float[]{1.0f, 0.0f, 0.0f}, new float[]{0.0f, 1.0f}, color);
		
		addElement(new float[]{groundPosition[0] + size[0], 0.0f   , groundPosition[1] + size[2]}, new float[]{0.0f, 0.0f, 1.0f}, new float[]{0.0f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] + size[2]}, new float[]{0.0f, 0.0f, 1.0f}, new float[]{0.5f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] + size[2]}, new float[]{0.0f, 0.0f, 1.0f}, new float[]{0.5f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] + size[0], 0.0f   , groundPosition[1] + size[2]}, new float[]{0.0f, 0.0f, 1.0f}, new float[]{0.0f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] + size[2]}, new float[]{0.0f, 0.0f, 1.0f}, new float[]{0.5f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] - size[0], 0.0f   , groundPosition[1] + size[2]}, new float[]{0.0f, 0.0f, 1.0f}, new float[]{0.0f, 1.0f}, color);
		
		addElement(new float[]{groundPosition[0] - size[0], 0.0f   , groundPosition[1] + size[2]}, new float[]{-1.0f, 0.0f, 0.0f}, new float[]{0.0f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] + size[2]}, new float[]{-1.0f, 0.0f, 0.0f}, new float[]{0.5f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] - size[2]}, new float[]{-1.0f, 0.0f, 0.0f}, new float[]{0.5f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] - size[0], 0.0f   , groundPosition[1] + size[2]}, new float[]{-1.0f, 0.0f, 0.0f}, new float[]{0.0f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] - size[2]}, new float[]{-1.0f, 0.0f, 0.0f}, new float[]{0.5f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] - size[0], 0.0f   , groundPosition[1] - size[2]}, new float[]{-1.0f, 0.0f, 0.0f}, new float[]{0.0f, 1.0f}, color);
		
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] - size[2]}, new float[]{0.0f, 0.0f, -1.0f}, new float[]{0.0f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] + size[0], 0.0f   , groundPosition[1] - size[2]}, new float[]{0.0f, 0.0f, -1.0f}, new float[]{0.5f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] - size[0], 0.0f   , groundPosition[1] - size[2]}, new float[]{0.0f, 0.0f, -1.0f}, new float[]{0.5f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] + size[0], size[1], groundPosition[1] - size[2]}, new float[]{0.0f, 0.0f, -1.0f}, new float[]{0.0f, 0.5f}, color);
		addElement(new float[]{groundPosition[0] - size[0], 0.0f   , groundPosition[1] - size[2]}, new float[]{0.0f, 0.0f, -1.0f}, new float[]{0.5f, 1.0f}, color);
		addElement(new float[]{groundPosition[0] - size[0], size[1], groundPosition[1] - size[2]}, new float[]{0.0f, 0.0f, -1.0f}, new float[]{0.0f, 1.0f}, color);
	}
}
