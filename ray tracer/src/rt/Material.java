package rt;

import javax.vecmath.Vector3f;

/**
 * Stores the properties of a material. You will implement this class in the
 * "Shading and Texturing" project.
 */
public class Material {

	protected Spectrum diffuse;
	protected Spectrum specular;
	protected Spectrum ambient;
	protected float shininess;
	protected String type;

	public Material() {
		setDiffuse(new Spectrum(1, 1, 1));
		setSpecular(new Spectrum(1, 1, 1));
		setAmbient(new Spectrum(.2f, .2f, .2f));
		setShininess(32);
		type = "";
	}

	public Spectrum shade(HitRecord hit, Vector3f eye, Light light) {
		return null;
	}

	public Spectrum getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(Spectrum kd) {
		diffuse = kd;
	}

	public Spectrum getSpecular() {
		return specular;
	}

	public void setSpecular(Spectrum specular) {
		this.specular = specular;
	}

	public Spectrum getAmbient() {
		return ambient;
	}

	public void setAmbient(Spectrum ambient) {
		this.ambient = ambient;
	}

	public float getShininess() {
		return shininess;
	}

	public void setShininess(float phong) {
		this.shininess = phong;
	}
}