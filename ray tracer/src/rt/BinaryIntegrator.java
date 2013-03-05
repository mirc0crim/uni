package rt;

public class BinaryIntegrator extends IntegratorFactory {

	@Override
	public Spectrum integrate(Intersectable scene, LightList lights, Ray ray) {
		if (scene.intersect(ray) != null)
			return new Spectrum(1, 1, 1); // white
		else
			return new Spectrum(0, 0, 0); // black
	}

}