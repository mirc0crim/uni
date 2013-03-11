package rt;

import javax.vecmath.Vector3f;

public class BlinnMaterial extends Material {

	public BlinnMaterial(Spectrum kd) {
		setDiffuse(kd);
	}

	public BlinnMaterial(Spectrum kd, Spectrum ks) {
		setDiffuse(kd);
		setSpecular(ks);
	}

	public BlinnMaterial(Spectrum kd, Spectrum ks, Spectrum ka) {
		setDiffuse(kd);
		setSpecular(ks);
		setAmbient(ka);
	}

	public BlinnMaterial(Spectrum kd, Spectrum ks, float shininess) {
		setDiffuse(kd);
		setSpecular(ks);
		setShininess(shininess);
	}

	public BlinnMaterial(Spectrum kd, Spectrum ks, Spectrum ka, int shininess) {
		setDiffuse(kd);
		setSpecular(ks);
		setAmbient(ka);
		setShininess(shininess);
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
			spectrum.append(cl.multipliedBy(diffuse).multipliedBy(nDotL));
		}

		// Specular reflectance term
		Vector3f e = new Vector3f(eye);
		e.sub(hitPoint);
		e.normalize();
		Vector3f h = new Vector3f(L);
		h.add(e);
		h.normalize();
		float hDotN = (float) Math.pow(h.dot(normal), shininess);
		spectrum.append(specular.multipliedBy(cl).multipliedBy(hDotN));

		// Ambient reflectance term
		cl = light.getCl(hitPoint);
		Spectrum amb = ambient.multipliedBy(cl);
		spectrum.append(amb);

		spectrum.clampMax(1.f);
		spectrum.clampMin(0.f);

		return spectrum;
	}
}