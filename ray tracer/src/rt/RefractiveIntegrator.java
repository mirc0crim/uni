package rt;

import java.util.Iterator;

import javax.vecmath.Vector3f;

public class RefractiveIntegrator implements IntegratorFactory {

	@Override
	public Spectrum integrate(Intersectable scene, LightList lights, Ray ray, int bounces) {
		HitRecord hit = scene.intersect(ray);
		Spectrum spectrum = new Spectrum(0.f, 0.f, 0.f);
		spectrum.append(doShading(scene, lights, hit, ray, bounces));
		return spectrum;
	}

	private Spectrum doShading(Intersectable scene, LightList lights, HitRecord hit, Ray ray, int bounces) {
		if (bounces <= 0 )
			return makeShadow(scene, lights, hit);
		if (hit != null && hit.getT() > 0 && hit.getT() <= Float.POSITIVE_INFINITY)
			if (hit.getMaterial().type == "refractive")
				return refractionSpec(scene, lights, hit, ray, --bounces);
			else if(hit.getMaterial().type == "mirror"){
				Ray reflectedRay = reflectionRay(hit, ray);
				HitRecord mirrorHit = scene.intersect(reflectedRay);
				return doShading(scene, lights, mirrorHit, reflectedRay, --bounces);
			} else
				return makeShadow(scene, lights, hit);
		return new Spectrum(0.f, 0.f, 0.f);
	}

	private Spectrum makeShadow(Intersectable scene, LightList lights, HitRecord hit) {
		Iterator<Light> it = lights.getLightList().iterator();
		Spectrum spectrum = new Spectrum(0.f, 0.f, 0.f);
		while (it.hasNext()) {
			Light light = it.next();
			Vector3f hitToLight = new Vector3f(light.getPosition());
			hitToLight.sub(hit.getIntersectionPoint());
			hitToLight.normalize();
			Vector3f pos = new Vector3f(hit.getIntersectionPoint());
			Vector3f bias = new Vector3f(hit.getRayDir());
			bias.negate();
			bias.scale(0.00001f);
			pos.add(bias);
			HitRecord lightHit = scene.intersect(new Ray(hitToLight, pos));
			Vector3f dist = new Vector3f();
			dist.sub(light.getPosition(), pos);
			if (lightHit == null || lightHit.getT() > dist.length())
				spectrum.append(hit.getMaterial().shade(hit, light));
		}
		return spectrum;
	}

	private Spectrum refractionSpec(Intersectable scene, LightList lights, HitRecord oldHit,
			Ray oldRay, int bounces) {
		Vector3f v = new Vector3f(reflectionRay(oldHit, oldRay).getDirection());
		Vector3f n = new Vector3f(oldHit.getNormal());
		float n1, n2;
		float cosTheta1 = n.dot(v);

		if (cosTheta1 < 0) { // outgoing
			n1 = ((RefractiveMaterial) oldHit.getMaterial()).getRefractiveIndex();
			n2 = 1.00029f;
			n.negate();
		} else { // incoming
			n1 = 1.00029f;
			n2 = ((RefractiveMaterial) oldHit.getMaterial()).getRefractiveIndex();
		}

		float thetaC = (float) Math.asin(n2 / n1);
		float theta1 = (float) Math.acos(n.dot(v));
		float theta2 = (float) Math.asin(Math.sin(theta1) * n1 / n2);

		Ray reflectedRay = reflectionRay(oldHit, oldRay);
		HitRecord reflectionHit = scene.intersect(reflectedRay);

		if (theta1 > thetaC)
			return doShading(scene, lights, reflectionHit, reflectedRay, --bounces);

		Vector3f v2 = new Vector3f(oldHit.getRayDir());
		v2.normalize();
		v2.scale(n1 / n2);
		n.scale((float) (n1 / n2 * Math.cos(theta1) - Math.cos(theta2)));
		Vector3f r = new Vector3f(v2);
		r.add(n);
		r.normalize();

		Vector3f pos = new Vector3f(oldHit.getIntersectionPoint());
		Vector3f bias = new Vector3f(oldHit.getRayDir());
		bias.scale(0.00001f);
		pos.add(bias);

		Ray refractedRay = new Ray(r, pos);
		HitRecord refractionHit = scene.intersect(refractedRay);

		// Fresnel equations
		float R1 = (float) Math.pow(
				(n1 * Math.cos(theta1) - n2 * Math.cos(theta2))
				/ (n1 * Math.cos(theta1) + n2 * Math.cos(theta2)), 2);
		float R2 = (float) Math.pow(
				(n2 * Math.cos(theta1) - n1 * Math.cos(theta2))
				/ (n2 * Math.cos(theta1) + n1 * Math.cos(theta2)), 2);
		float R = (R1 + R2) / 2f;
		float T = 1.f - R;

		int newDepth = --bounces;
		Spectrum reflectionSpectrum = doShading(scene, lights, reflectionHit, reflectedRay,
				newDepth).multipliedBy(R);
		Spectrum refractionSpectrum = doShading(scene, lights, refractionHit, refractedRay,
				newDepth).multipliedBy(T);
		Spectrum finalSpectrum = new Spectrum(reflectionSpectrum);
		finalSpectrum.append(refractionSpectrum);
		return finalSpectrum;
	}

	private Ray reflectionRay(HitRecord oldHit, Ray oldRay) {
		Vector3f normal = new Vector3f(oldHit.getNormal());
		float dDotN = oldRay.getDirection().dot(normal);
		Vector3f twodnn = new Vector3f(oldHit.getNormal());
		twodnn.scale(dDotN * 2);
		Vector3f refl = new Vector3f(oldRay.getDirection());
		refl.sub(twodnn);
		refl.normalize();
		Vector3f bias = new Vector3f(oldHit.getRayDir());
		bias.negate();
		bias.scale(0.00001f);
		Vector3f intPoint = new Vector3f(oldHit.getIntersectionPoint());
		intPoint.add(bias);
		return new Ray(refl, intPoint);
	}
}