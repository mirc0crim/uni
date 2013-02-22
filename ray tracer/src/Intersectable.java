import javax.vecmath.Vector3f;


public interface Intersectable {

	public boolean testIntersection(Vector3f ray, Vector3f eye);

}
