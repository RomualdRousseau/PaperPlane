package com.rworld.core.v2.graphics;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Hashtable;

import com.rworld.core.v2.math.BoundingBox;

public abstract class MeshBuilder {
	
	public static final int OPTIONS_DEFAULT	= 0;
	public static final int OPTIONS_NORMAL	= 1;
	public static final int OPTIONS_TEXTURE = 2;
	public static final int OPTIONS_COLOR	= 4;
	
	public int createOptions = MeshBuilder.OPTIONS_DEFAULT;
	public int updateOptions = MeshBuilder.OPTIONS_DEFAULT;
	
	public MeshBuilder(int createOptions, int updateOptions) {
		this.createOptions = createOptions;
		this.updateOptions = updateOptions;
	}
	
	public void recycle() {
		mLUTofElements.clear();
		mIndexes.clear();
		mElements.clear();
		System.gc();
	}
	
	public void createMeshData(FloatBuffer vertexBuffer, FloatBuffer normalBuffer, FloatBuffer texCoordBuffer, FloatBuffer colorBuffer, ShortBuffer indexBuffer) {
		for(Element e : mElements) {
			vertexBuffer.put(e.vertex);
			if(normalBuffer != null) normalBuffer.put(e.normal);
			if(texCoordBuffer != null) texCoordBuffer.put(e.texCoord);
			if(colorBuffer != null) colorBuffer.put(e.color);
		}
		for (Short s : mIndexes) {
			indexBuffer.put(s.shortValue());
		}
	}
	
	public void updateMeshData(FloatBuffer vertexBuffer, FloatBuffer normalBuffer, FloatBuffer texCoordBuffer, FloatBuffer colorBuffer) {
	}

	public int getElementCount() {
		return mElements.size();
	}

	public int getIndexCount() {
		return mIndexes.size();
	}
	
	public void addElement(float[] vertex) {
		Element newElement = new Element();
		newElement.vertex[0] = vertex[0];
		newElement.vertex[1] = vertex[1];
		newElement.vertex[2] = vertex[2];
		addElement(newElement);
	}
	
	public void addElement(float[] vertex, float[] normal) {
		Element newElement = new Element();
		newElement.vertex[0] = vertex[0];
		newElement.vertex[1] = vertex[1];
		newElement.vertex[2] = vertex[2];
		newElement.normal[0] = normal[0];
		newElement.normal[1] = normal[1];
		newElement.normal[2] = normal[2];
		addElement(newElement);
	}
	
	public void addElement(float[] vertex, float[] normal, float[] texCoord) {
		Element newElement = new Element();
		newElement.vertex[0] = vertex[0];
		newElement.vertex[1] = vertex[1];
		newElement.vertex[2] = vertex[2];
		newElement.normal[0] = normal[0];
		newElement.normal[1] = normal[1];
		newElement.normal[2] = normal[2];
		newElement.texCoord[0] = texCoord[0];
		newElement.texCoord[1] = texCoord[1];
		addElement(newElement);
	}
	
	public void addElement(float[] vertex, float[] normal, float[] texCoord, float[] color) {
		Element newElement = new Element();
		newElement.vertex[0] = vertex[0];
		newElement.vertex[1] = vertex[1];
		newElement.vertex[2] = vertex[2];
		newElement.normal[0] = normal[0];
		newElement.normal[1] = normal[1];
		newElement.normal[2] = normal[2];
		newElement.texCoord[0] = texCoord[0];
		newElement.texCoord[1] = texCoord[1];
		newElement.color[0] = color[0];
		newElement.color[1] = color[1];
		newElement.color[2] = color[2];
		newElement.color[3] = color[3];
		addElement(newElement);
	}

	public void addElement(Element newElement) {
		Short k = mLUTofElements.get(newElement);
		if (k == null) {
			k = (short) mElements.size();
			if(mElements.add(newElement)) {
				mLUTofElements.put(newElement, k);
			}
		}
		mIndexes.add(k.shortValue());
	}
	
	public void computeBoundingBox(float[] bbox) {
		BoundingBox.minimum(bbox);
		for(Element e : mElements) {
			BoundingBox.addPoint(bbox, e.vertex);
		}
	}
	
	public void fillDynamicArrays(float[] vertexArray, float[] normalArray, float[] texCoordArray, float[] colorArray) {
		int i = 0;
		for(Element e : mElements) {
			vertexArray[i * 3 + 0] = e.vertex[0];
			vertexArray[i * 3 + 1] = e.vertex[1];
			vertexArray[i * 3 + 2] = e.vertex[2];
			if(normalArray != null) {
				normalArray[i * 3 + 0] = e.normal[0];
				normalArray[i * 3 + 1] = e.normal[1];
				normalArray[i * 3 + 2] = e.normal[2];
			}
			if(texCoordArray != null) {
				texCoordArray[i * 2 + 0] = e.texCoord[0];
				texCoordArray[i * 2 + 1] = e.texCoord[1];
			}
			if(colorArray != null) {
				colorArray[i * 4 + 0] = e.color[0];
				colorArray[i * 4 + 1] = e.color[1];
				colorArray[i * 4 + 2] = e.color[2];
				colorArray[i * 4 + 3] = e.color[3];
			}
			i++;
		}
	}

	public class Element  {
		
		public float[] vertex = {0.0f, 0.0f, 0.0f};
		public float[] normal = {0.0f, 0.0f, 0.0f};
		public float[] texCoord = {0.0f, 0.0f};
		public float[] color = {1.0f, 1.0f, 1.0f, 1.0f};
		
		public Element() {
		}
		
		public boolean equals(Element o) {
			return
				(vertex[0] == o.vertex[0]) && (vertex[1] == o.vertex[1]) && (vertex[2] == o.vertex[2]) &&
				(normal[0] == o.normal[0]) && (normal[1] == o.normal[1]) && (normal[2] == o.normal[2]) &&
				(texCoord[0] == o.texCoord[0]) && (texCoord[1] == o.texCoord[1]) &&
				(color[0] == o.color[0]) && (color[1] == o.color[1]) && (color[2] == o.color[2]) && (color[3] == o.color[3]);
		}
		
		@Override
		public boolean equals(Object o) {
			return (o instanceof Element) && equals((Element) o);
		}

		@Override
		public int hashCode() {
			int hash = 0;
	        hash = (hash << 8) + (((int) (vertex[0] * 1024)) >> 24);
	        hash = (hash << 8) + (((int) (vertex[1] * 1024)) >> 24);
	        hash = (hash << 8) + (((int) (vertex[2] * 1024)) >> 24);
	        /*
	        hash = (hash << 4) + (((int) (normal[0] * 1024)) >> 28);
	        hash = (hash << 4) + (((int) (normal[1] * 1024)) >> 28);
	        hash = (hash << 4) + (((int) (normal[2] * 1024)) >> 28);
	        hash = (hash << 4) + (((int) (texCoord[0] * 1024)) >> 28);
	        hash = (hash << 4) + (((int) (texCoord[1] * 1024)) >> 28);
	        */
	        return hash;
		}
	}

	protected ArrayList<Element> mElements = new ArrayList<Element>();
	protected ArrayList<Short> mIndexes = new ArrayList<Short>();
	protected Hashtable<Element, Short> mLUTofElements = new Hashtable<Element, Short>();
}
