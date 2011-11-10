package jrtr;

import javax.vecmath.Vector3f;

/**
 * Stores the properties of a material. You will implement this
 * class in the "Shading and Texturing" project.
 */
public class Material {
	private Texture texture;
	private Vector3f diffuse;
	private Vector3f specular;
	private Vector3f ambient;
	private float phong;
	private Shader shader;
	private Vector3f matColor;

	public Material() {
		diffuse = new Vector3f(1, 1, 1);
		specular = new Vector3f(1, 1, 1);
		ambient = new Vector3f(0.4f, 0.4f, 0.4f);
		matColor = new Vector3f(1, 1, 1);
		phong = 1;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture tex) {
		texture = tex;
	}

	public Vector3f getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(Vector3f dif) {
		diffuse = dif;
	}

	public Vector3f getSpecular() {
		return specular;
	}

	public void setSpecular(Vector3f spec) {
		specular = spec;
	}

	public Vector3f getAmbient() {
		return ambient;
	}

	public void setAmbient(Vector3f amb) {
		ambient = amb;
	}

	public float getPhong() {
		return phong;
	}

	public void setPhong(float ph) {
		phong = ph;
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shad) {
		shader = shad;
	}

	public Vector3f getMatColor() {
		return matColor;
	}

	public void setMatColor(Vector3f kd) {
		matColor = kd;
	}

}
