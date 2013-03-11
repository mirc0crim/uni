package rt;

import java.util.Iterator;

import javax.vecmath.Vector3f;

public class ShadowIntegrator implements IntegratorFactory {

	@Override
	public Spectrum integrate(Intersectable scene, LightList lights, Vector3f eye, Ray ray,
			int bounces) {
		HitRecord hit = scene.intersect(ray);
		Iterator<Light> it = lights.getLightList().iterator();
		Spectrum spectrum = new Spectrum(0.f, 0.f, 0.f);

		if (hit != null && hit.getT() > 0 && hit.getT() < Float.POSITIVE_INFINITY) {
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
}