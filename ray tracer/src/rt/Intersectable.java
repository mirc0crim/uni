package rt;

public interface Intersectable {

	public HitRecord intersect(Ray ray);

	public Boundingbox getBox();

}
