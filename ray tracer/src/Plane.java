import javax.vecmath.Vector3f;


public class Plane implements Intersectable {

	Vector3f normal;

	public Plane(Vector3f n, float d) {
		normal = n;
	}

}
