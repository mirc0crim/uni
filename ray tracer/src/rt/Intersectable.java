package rt;
import javax.vecmath.Vector4f;

public interface Intersectable {

	public HitRecord intersect(Ray ray);

	public Vector4f getNormal();

}
