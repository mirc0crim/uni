package rt;

import javax.vecmath.Vector3f;

public class MirrorMaterial extends Material {

	private float ks;

	public MirrorMaterial(float newks) {
		ks = newks;
	}

	public MirrorMaterial(Spectrum newks) {
		ks = (newks.blue + newks.red + newks.green) / 3f;
	}

	@Override
	public Spectrum shade(HitRecord hit, Vector3f eye, Light light) {
		Spectrum spectrum = new Spectrum(0.f, 0.f, 0.f);
		Vector3f hitPoint = hit.getIntersectionPoint();
		Vector3f n = hit.getNormal();
		n.normalize();
		Spectrum cl = light.getCl(hitPoint);
		Vector3f L = light.getL(hitPoint);

		// Diffuse reflectance term
		float nDotL = n.dot(L);
		if (nDotL >= 0) {
			spectrum.append(cl.multipliedBy(getDiffuse()).multipliedBy(nDotL));
		}

		// Specular reflectance term
		Vector3f e = new Vector3f(eye);
		e.sub(hitPoint);
		e.normalize();
		Vector3f h = new Vector3f(L);
		h.add(e);
		h.normalize();
		float hDotN = (float) Math.pow(h.dot(n), getShininess());
		spectrum.append(getSpecular().multipliedBy(cl).multipliedBy(hDotN));

		// Ambient reflectance term
		// cl = light.getCl(hitPoint);
		// Spectrum ambient = this.ambient.multipliedBy(cl);
		// spectrum.append(ambient);

		spectrum.clampMax(1.f);
		spectrum.clampMin(0.f);

		return spectrum;
	}

	public float getKs() {
		return ks;
	}

	public void setKs(float newks) {
		ks = newks;
	}
}