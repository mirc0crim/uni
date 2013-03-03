package rt;


/**
 * Stores the properties of a material. You will implement this class in the
 * "Shading and Texturing" project.
 */
public class Material {

	private Spectrum diffuse;
	private Spectrum specular;
	private Spectrum ambient;
	private float phong;

	public Material() {
		setDiffuse(new Spectrum(1, 1, 1));
		setSpecular(new Spectrum(1, 1, 1));
		setAmbient(new Spectrum(.2f, .2f, .2f));
		setShininess(32);
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

	public float getPhong() {
		return phong;
	}

	public void setShininess(float phong) {
		this.phong = phong;
	}
}