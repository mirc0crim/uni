package rt;

import java.util.Iterator;

import javax.vecmath.Vector3f;

public class BlinnIntegrator extends IntegratorFactory {

	@Override
	public Spectrum integrate(Intersectable scene, LightList lights, Vector3f eye, Ray ray) {
		HitRecord hitRecord = scene.intersect(ray);
		Spectrum spectrum = new Spectrum(0, 0, 0);
		Iterator<Light> lightIterator = lights.getLightList().iterator();
		if (hitRecord != null && hitRecord.getT() > 0
				&& hitRecord.getT() < Float.POSITIVE_INFINITY) {
			while (lightIterator.hasNext()) {
				Light light = lightIterator.next();
				spectrum.append(hitRecord.getMaterial().shade(hitRecord, eye, light));
			}
			return spectrum;
		} else
			return spectrum;
	}
}