package com.rworld.core.v2.graphics.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.rworld.core.v2.graphics.MeshBuilder;
import com.rworld.core.v2.graphics.MeshLoader;

public class ObjMeshLoader extends MeshLoader {

	@Override
	public void onLoadFromStream(BufferedReader stream) throws IOException {
		ArrayList<Float> vertice = new ArrayList<Float>();
		ArrayList<Float> normals = new ArrayList<Float>();
		ArrayList<Float> texCoords = new ArrayList<Float>();
		String line;
		String[] parts;
		String[] tokens;

		while ((line = stream.readLine()) != null) {
			tokens = line.split(" ");
			if (tokens[0].equals("v")) {
				vertice.add(Float.parseFloat(tokens[1]));
				vertice.add(Float.parseFloat(tokens[2]));
				vertice.add(Float.parseFloat(tokens[3]));
			} else if (tokens[0].equals("vn")) {
				normals.add(Float.parseFloat(tokens[1]));
				normals.add(Float.parseFloat(tokens[2]));
				normals.add(Float.parseFloat(tokens[3]));
			} else if (tokens[0].equals("vt")) {
				texCoords.add(Float.parseFloat(tokens[1]));
				texCoords.add(Float.parseFloat(tokens[2]));
			} else if (tokens[0].equals("f")) {
				for (int i = 1; i <= 3; i++) {
					parts = tokens[i].split("/", 3);
					MeshBuilder.Element newElement = new Element();
					
					final int vi = Integer.parseInt(parts[0]) - 1;
					newElement.vertex[0] = vertice.get(vi * 3 + 0);
					newElement.vertex[1] = vertice.get(vi * 3 + 1);
					newElement.vertex[2] = vertice.get(vi * 3 + 2);

					if (parts[1].length() > 0) {
						final int vti = Integer.parseInt(parts[1]) - 1;
						newElement.texCoord[0] = texCoords.get(vti * 2 + 0);
						newElement.texCoord[1] = 1.0f - texCoords.get(vti * 2 + 1);
					}
					
					if (parts[2].length() > 0) {
						final int vni = Integer.parseInt(parts[2]) - 1;
						newElement.normal[0] = normals.get(vni * 3 + 0);
						newElement.normal[1] = normals.get(vni * 3 + 1);
						newElement.normal[2] = normals.get(vni * 3 + 2);
					}

					addElement(newElement);
				}
			}
		}
	}
}
