package rt;


public interface IntegratorFactory {

	public Spectrum integrate(Intersectable objects, LightList lights, Ray ray, int bounce);

}