package rt;

import javax.vecmath.Vector3f;

public class BinaryIntegrator implements IntegratorFactory {

	@Override
	public Spectrum integrate(Intersectable scene, LightList lights, Vector3f eye, Ray ray,
			int bounces) {
		HitRecord hit = scene.intersect(ray);
		if (hit != null && hit.getT() > 0 && hit.getT() < Float.POSITIVE_INFINITY)
			return new Spectrum(1, 1, 1); // white
		else
			return new Spectrum(0, 0, 0); // black
	}

}