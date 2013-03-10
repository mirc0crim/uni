package rt;

import javax.vecmath.Vector3f;

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

	@Override
	public Spectrum shade(HitRecord hit, Vector3f eye, Light light) {
		Spectrum spectrum = new Spectrum(0.f, 0.f, 0.f);
		Vector3f hitPoint = hit.getIntersectionPoint();
		Vector3f normal = hit.getNormal();
		normal.normalize();
		Spectrum cl = light.getCl(hitPoint);
		Vector3f L = light.getL(hitPoint);

		// Diffuse reflectance term
		float nDotL = normal.dot(L);
		if (nDotL >= 0) {
			spectrum.append(getDiffuse().multipliedBy(cl).multipliedBy(nDotL));
		}

		// Specular reflectance term
		Vector3f e = new Vector3f(eye);
		e.negate();
		e.normalize();
		Vector3f h = light.getL(hitPoint);
		h.add(e);
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