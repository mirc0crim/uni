package rt;

import javax.vecmath.Vector3f;

public interface Intersectable {

	public HitRecord intersect(Ray ray);

	public Vector3f getNormal();

}
