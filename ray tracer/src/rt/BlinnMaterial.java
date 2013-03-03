package rt;


public class BlinnMaterial extends Material {

	public BlinnMaterial(Spectrum kd) {
		this.setDiffuse(kd);
	}

	public BlinnMaterial(Spectrum kd, Spectrum ks, float shininess) {
		this.setDiffuse(kd);
		this.setSpecular(ks);
		this.setShininess(shininess);
	}
}