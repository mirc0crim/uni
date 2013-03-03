package rt;


public class SaneIntegrator extends IntegratorFactory {

	@Override
	public Spectrum integrate(Intersectable scene, LightList lights, Ray ray) {
		HitRecord hitRecord = scene.intersect(ray);
		if (hitRecord != null) {
			float c = hitRecord.getIntersectionPoint().z / 2 * -1;
			return new Spectrum(c, c, c);
		} else
			return new Spectrum(0, 0, 0);
	}
}