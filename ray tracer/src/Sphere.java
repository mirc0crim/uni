import javax.vecmath.Vector3f;

public class Sphere implements Intersectable {

	Vector3f center;
	float radius;

	public Sphere(Vector3f c, float r) {
		center = c;
		radius = r;
	}

	@Override
	public float testIntersection(Vector3f ray, Vector3f eye) {
		Vector3f ec = new Vector3f(eye);
		ec.sub(center);
		float a = ray.dot(ray);
		float b = 2 * ray.dot(ec);
		float c = ec.dot(ec) - (radius * radius);

		// Find discriminant
		float disc = b * b - 4 * a * c;

		// if discriminant is negative there are no real roots, so return
		// false as ray misses sphere
		if (disc < 0)
			return 0;

		float x1 = (float) ((-b + Math.sqrt(disc)) / 2 * a);
		float x2 = (float) ((-b - Math.sqrt(disc)) / 2 * a);

		if (x1 < x2)
			return x2;
		else
			return x1;
	}

}
