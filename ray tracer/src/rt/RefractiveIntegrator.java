package rt;

import java.util.Iterator;

import javax.vecmath.Vector3f;

public class RefractiveIntegrator implements IntegratorFactory {

	@Override
	public Spectrum integrate(Intersectable scene, LightList lights, Ray ray, int bounces) {
		HitRecord hit = scene.intersect(ray);
		Iterator<Light> it = lights.getLightList().iterator();
		Spectrum spectrum = new Spectrum(0.f, 0.f, 0.f);

		if (hit != null && hit.getT() > 0 && hit.getT() <= Float.POSITIVE_INFINITY) {
			int rec = 0;
			while ((hit.getMaterial().type == "refractive" || hit.getMaterial().type == "mirror")
					&& rec <= bounces) {
				Ray newRay;
				if (hit.getMaterial().type == "refractive")
					newRay = refractionRay(scene, hit, ray);
				else
					newRay = reflectionRay(scene, hit, ray);
				hit = scene.intersect(newRay);
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
					spectrum.append(hit.getMaterial().shade(hit, light));
			}
		}
		return spectrum;
	}

	private Ray refractionRay(Intersectable scene, HitRecord oldHit, Ray oldRay) {
		Vector3f v = new Vector3f(reflectionRay(scene, oldHit, oldRay).getDirection());
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

		if (theta1 > thetaC)
			return reflectionRay(scene, oldHit, oldRay);

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