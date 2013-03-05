package rt;

import javax.vecmath.Vector4f;

public class BlinnMaterial extends Material {

	public BlinnMaterial(Spectrum kd) {
		this.setDiffuse(kd);
	}

	public BlinnMaterial(Spectrum kd, Spectrum ks) {
		this.setDiffuse(kd);
		this.setSpecular(ks);
	}

	public BlinnMaterial(Spectrum kd, Spectrum ks, Spectrum ka) {
		this.setDiffuse(kd);
		this.setSpecular(ks);
		this.setAmbient(ka);
	}

	public BlinnMaterial(Spectrum kd, Spectrum ks, float shininess) {
		this.setDiffuse(kd);
		this.setSpecular(ks);
		this.setShininess(shininess);
	}

	public BlinnMaterial(Spectrum kd, Spectrum ks, Spectrum ka, int shininess) {
		this.setDiffuse(kd);
		this.setSpecular(ks);
		this.setAmbient(ka);
		this.setShininess(shininess);
	}

	public Spectrum shade(HitRecord hit, Light light) {
		Spectrum spectrum = new Spectrum(0.f, 0.f, 0.f);
		Vector4f hitPoint = hit.getIntersectionPoint();
		Vector4f normal = hit.getNormal();
		normal.normalize();
		Spectrum cl = light.getCl(hitPoint);

		// Diffuse reflectance term
		float nDotL = normal.dot(light.getL(hitPoint));
		if (nDotL >= 0) {
			spectrum.append(getDiffuse().multipliedBy(cl).multipliedBy(nDotL));
		}

		// Specular reflectance term
		Vector4f eye = new Vector4f(hitPoint);
		eye.negate();
		eye.normalize();
		Vector4f h = light.getL(hitPoint);
		h.add(eye);
		h.normalize();
		cl = light.getCl(hitPoint);
		float hDotE = (float) Math.pow((h.dot(normal)), getShininess());
		spectrum.append(getSpecular().multipliedBy(cl).multipliedBy(hDotE));

		// Ambient reflectance term
		cl = light.getCl(hitPoint);
		Spectrum ambient = getAmbient().multipliedBy(cl);
		spectrum.append(ambient);

		spectrum.clampMax(1.f);
		spectrum.clampMin(0.f);

		return spectrum;
	}
}