package rt;

import javax.vecmath.Vector3f;

public class DiffuseMaterial extends Material {

	public DiffuseMaterial(Spectrum kd) {
		setDiffuse(kd);
		setSpecular(new Spectrum(0.f, 0.f, 0.f));
	}

	public DiffuseMaterial(Spectrum kd, Spectrum ka) {
		setDiffuse(kd);
		setSpecular(new Spectrum(0.f, 0.f, 0.f));
		setAmbient(ka);
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

		// Ambient reflectance term
		cl = light.getCl(hitPoint);
		Spectrum ambient = getAmbient().multipliedBy(cl);
		spectrum.append(ambient);

		spectrum.clampMax(1.f);
		spectrum.clampMin(0.f);

		return spectrum;
	}
}
