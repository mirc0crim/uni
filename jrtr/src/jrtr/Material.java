package jrtr;

import javax.vecmath.Vector3f;

/**
 * Stores the properties of a material. You will implement this
 * class in the "Shading and Texturing" project.
 */
public class Material {
	private Texture texture;
	public Vector3f diffuse;
	public Vector3f specular;
	public Vector3f ambient;
	public float phong;

	public Material() {
		diffuse = new Vector3f(1, 1, 1);
		specular = new Vector3f(1, 1, 1);
		ambient = new Vector3f(1, 1, 1);
		phong = 1;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture tex) {
		texture = tex;
	}

}
