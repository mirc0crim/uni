package rt;

import java.util.Iterator;

import javax.vecmath.Vector3f;

public class RefractiveIntegrator implements IntegratorFactory {

	@Override
	public Spectrum integrate(Intersectable scene, LightList lights, Vector3f eye, Ray ray,
			int bounces) {
		HitRecord hit = scene.intersect(ray);
		Iterator<Light> it = lights.getLightList().iterator();
		Spectrum spectrum = new Spectrum(0.f, 0.f, 0.f);

		if (hit != null && hit.getT() > 0 && hit.getT() <= Float.POSITIVE_INFINITY) {
			int rec = 0;
			while (hit.getMaterial().type == "refractive" && rec <= bounces) {
				Ray refraction = refractionRay(scene, hit, ray);
				hit = scene.intersect(refraction);
				rec++;
			}
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
					spectrum.append(hit.getMaterial().shade(hit, eye, light));
			}
		}
		return spectrum;
	}

	private Ray refractionRay(Intersectable scene, HitRecord oldHit, Ray oldRay) {
		if (oldHit.getMaterial().type != "refractive")
			return oldRay;
		Vector3f v = new Vector3f(oldHit.getRayDir());
		v.negate();
		Vector3f normal = new Vector3f(oldHit.getNormal());
		float theta1 = normal.dot(v);
		float n1 = 1.00029f;
		float n2 = ((RefractiveMaterial) oldHit.getMaterial()).getRefractiveIndex();

		if (theta1 < 0) { // outgoing
			normal.negate();
			theta1 = normal.dot(v);
			float tmp = n1;
			n1 = n2;
			n2 = tmp;
		}

		float criticalAngle = n2 / n1;
		float theta2 = (float) (Math.asin(theta1 * (n1 / n2)));

		if (theta1 > criticalAngle) {
			Ray reflectedRay = reflectionRay(scene, oldHit, oldRay);
			return reflectedRay;
		}

		v = new Vector3f(oldHit.getRayDir());
		v.scale(n1 / n2);
		Vector3f r = new Vector3f(oldHit.getNormal());
		r.scale((n1 / n2) * (float) Math.cos(theta1) - (float) Math.cos(theta2));
		r.add(v);
		r.normalize();

		Vector3f pos = new Vector3f(oldHit.getIntersectionPoint());
		Vector3f bias = new Vector3f(oldHit.getRayDir());
		bias.scale(0.00001f);
		pos.add(bias);
		return new Ray(r, pos);
	}

	private Ray reflectionRay(Intersectable scene, HitRecord oldHit, Ray oldRay) {
		Vector3f normal = new Vector3f(oldHit.getNormal());
		float dDotN = oldRay.getDirection().dot(normal);
		Vector3f twodnn = new Vector3f(oldHit.getNormal());
		twodnn.scale(dDotN * 2);
		Vector3f refl = new Vector3f(oldRay.getDirection());
		refl.sub(twodnn);
		Vector3f bias = new Vector3f(oldHit.getRayDir());
		bias.negate();
		bias.scale(0.00001f);
		Vector3f intPoint = new Vector3f(oldHit.getIntersectionPoint());
		intPoint.add(bias);
		return new Ray(refl, intPoint);
	}
}