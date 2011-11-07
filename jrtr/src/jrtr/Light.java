package jrtr;

import javax.vecmath.Vector3f;


/**
 * Stores the properties of a light source. To be implemented for
 * the "Texturing and Shading" project.
 */
public class Light {

	public Type type;
	public Vector3f diffuse;
	public Vector3f specular;
	public Vector3f ambient;
	public Vector3f direction;
	public Vector3f position;

	public Light() {
		type = Type.Spot;
		diffuse = new Vector3f(1, 1, 1);
		specular = new Vector3f(1, 1, 1);
		ambient = new Vector3f(0, 0, 0);
		direction = new Vector3f(0, 0, 1);
		position = new Vector3f(0, 0, 1);
	}

	public enum Type {
		Point, Spot, Directional
	}

}
