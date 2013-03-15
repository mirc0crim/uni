package rt;

import javax.vecmath.Vector3f;

public class RefractiveMaterial extends Material {

	private float refractiveIndex;

	public RefractiveMaterial(float refractIndex) {
		refractiveIndex = refractIndex;
		type = "refractive";
	}

	public float getRefractiveIndex() {
		return refractiveIndex;
	}

	public void setRefractiveIndex(float refractIndex) {
		refractiveIndex = refractIndex;
	}

	@Override
	public Spectrum shade(HitRecord hit, Light light) {
		Spectrum spectrum = new Spectrum(0.f, 0.f, 0.f);
		Vector3f hitPoint = hit.getIntersectionPoint();
		Vector3f normal = hit.getNormal();
		normal.normalize();
		Spectrum cl = light.getCl(hitPoint);
		Vector3f L = light.getL(hitPoint);
		Vector3f rayDir = hit.getRayDir();
		rayDir.normalize();

		// Diffuse reflectance term
		float nDotL = normal.dot(L);
		if (nDotL >= 0) {
			spectrum.append(cl.multipliedBy(diffuse).multipliedBy(nDotL));

			// Specular reflectance term
			Vector3f h = new Vector3f(L);
			h.sub(rayDir);
			h.normalize();
			float hDotN = (float) Math.pow(h.dot(normal), shininess);
			spectrum.append(specular.multipliedBy(cl).multipliedBy(hDotN));
		}

		// Ambient reflectance term
		cl = light.getCl(hitPoint);
		Spectrum amb = ambient.multipliedBy(cl);
		spectrum.append(amb);

		spectrum.clampMax(1.f);
		spectrum.clampMin(0.f);

		return spectrum;
	}

}