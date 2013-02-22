import javax.vecmath.Vector3f;


public class Plane implements Intersectable {

	Vector3f normal;
	float distance;

	public Plane(Vector3f n, float d) {
		normal = n;
		distance = d;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public void setNormal(Vector3f n) {
		normal = n;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float d) {
		distance = d;
	}

}
