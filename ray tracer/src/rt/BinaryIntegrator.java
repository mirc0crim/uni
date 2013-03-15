package rt;


public class BinaryIntegrator implements IntegratorFactory {

	@Override
	public Spectrum integrate(Intersectable scene, LightList lights, Ray ray, int bounces) {
		HitRecord hit = scene.intersect(ray);
		if (hit != null && hit.getT() > 0 && hit.getT() < Float.POSITIVE_INFINITY)
			return new Spectrum(1, 1, 1); // white
		else
			return new Spectrum(0, 0, 0); // black
	}

}