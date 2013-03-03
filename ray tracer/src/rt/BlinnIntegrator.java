package rt;

import java.util.Iterator;

public class BlinnIntegrator extends IntegratorFactory {

	@Override
	public Spectrum integrate(Intersectable scene, LightList lights, Ray ray) {
		HitRecord hitRecord = scene.intersect(ray);
		Spectrum spectrum = new Spectrum(0, 0, 0);
		Iterator<Light> lightIterator = lights.getLightList().iterator();
		if (hitRecord != null) {
			while (lightIterator.hasNext()) {

			}
			return spectrum;
		} else
			return spectrum;
	}
}