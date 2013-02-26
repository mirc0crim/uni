import javax.vecmath.Vector3f;

public class Triangle implements Intersectable {

	Vector3f verticle1;
	Vector3f verticle2;
	Vector3f verticle3;

	public Triangle(Vector3f p1, Vector3f p2, Vector3f p3) {
		verticle1 = p1;
		verticle2 = p2;
		verticle3 = p3;
	}

	@Override
	public float testIntersection(Vector3f ray, Vector3f eye) {

		Vector3f e1 = new Vector3f();
		e1.sub(verticle2, verticle1);
		Vector3f e2 = new Vector3f();
		e2.sub(verticle3, verticle1);

		Vector3f h = new Vector3f();
		h.cross(ray, e2);
		float a = e1.dot(h);

		if (a > -0.00001 && a < 0.00001)
			return 0;

		float f = 1 / a;
		Vector3f s = new Vector3f();
		s.sub(eye, verticle1);
		float u = f * s.dot(h);

		if (u < 0.0 || u > 1.0)
			return 0;

		Vector3f q = new Vector3f();
		q.cross(s, verticle2);
		float v = f * ray.dot(q);

		if (v < 0.0 || u + v > 1.0)
			return 0;

		// at this stage we can compute t to find out where
		// the intersection point is on the line
		float t = f * verticle3.dot(q);

		if (t < 0.00001) // ray intersection
			return t;

		else
			// this means that there is a line intersection
			// but not a ray intersection
			return 0;

	}

}
