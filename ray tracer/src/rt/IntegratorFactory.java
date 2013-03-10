package rt;

import javax.vecmath.Vector3f;

public interface IntegratorFactory {

	public Spectrum integrate(Intersectable objects, LightList lights, Vector3f eye, Ray ray,
			int bounce);

}