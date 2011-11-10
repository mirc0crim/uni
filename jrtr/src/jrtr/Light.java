package jrtr;

import javax.vecmath.Vector3f;


/**
 * Stores the properties of a light source. To be implemented for
 * the "Texturing and Shading" project.
 */
public class Light {

	private Type type;
	private Vector3f diffuse;
	private Vector3f specular;
	private Vector3f ambient;
	private Vector3f direction;
	private Vector3f position;
	private Vector3f radiance;

	public Light() {
		type = Type.Spot;
		diffuse = new Vector3f(1, 1, 1);
		specular = new Vector3f(1, 1, 1);
		ambient = new Vector3f(0.3f, 0.3f, 0.3f);
		direction = new Vector3f(0, 0, 1);
		position = new Vector3f(0, 0, 1);
		radiance = new Vector3f(0, 0, 1);
	}

	public enum Type {
		Point, Spot, Directional
	}

	public Type getType() {
		return type;
	}

	public void setType(Type typ) {
		type = typ;
	}

	public Vector3f getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(Vector3f diff) {
		diffuse = diff;
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

	public Vector3f getDirection() {
		return direction;
	}

	public void setDirection(Vector3f dir) {
		direction = dir;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f pos) {
		position = pos;
	}

	public Vector3f getRadiance() {
		return radiance;
	}

	public void setRadiance(Vector3f radi) {
		radiance = radi;
	}

}
