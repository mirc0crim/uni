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

	@Override
	public float testIntersection(Vector3f ray, Vector3f eye) {
		Vector3f l = new Vector3f(ray.x, ray.y, ray.z);
		Vector3f p0 = new Vector3f(normal);
		p0.scale(distance);
		Vector3f l0 = new Vector3f(eye);
		Vector3f n = new Vector3f(normal);
		p0.sub(l0);
		float t = p0.dot(n) / l.dot(n);
		return -t;
	}

}
