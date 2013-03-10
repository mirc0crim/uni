package rt;

import javax.vecmath.Vector3f;

public abstract class IntegratorFactory {

	public Spectrum integrate(Intersectable objects, LightList lights, Vector3f eye, Ray ray) {
		return null;
	}
}