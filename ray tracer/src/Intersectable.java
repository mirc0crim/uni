import javax.vecmath.Vector3f;

public interface Intersectable {

	public float testIntersection(Vector3f ray, Vector3f eye);

}
