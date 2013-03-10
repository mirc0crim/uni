package rt;

import javax.vecmath.Vector3f;

public class Sphere implements Intersectable {

	Vector3f center;
	float radius;
	private Material material;
	private Vector3f normal;

	public Sphere(Vector3f c, float r) {
		center = c;
		radius = r;
		normal = new Vector3f();
	}

	@Override
	public HitRecord intersect(Ray ray) {
		// Compute A, B and C coefficients
		float a = ray.getDirection().dot(ray.getDirection());
		Vector3f origin = new Vector3f(ray.getOrigin());
		origin.sub(center);
		float b = 2 * ray.getDirection().dot(origin);
		float c = origin.dot(origin) - radius * radius;

		// Find discriminant
		float disc = b * b - 4 * a * c;

		// if discriminant is negative there are no real roots, so return
		// false as ray misses sphere
		if (disc < 0)
			return null;

		// compute q as described above
		float distSqrt = (float) Math.sqrt(disc);
		float q;
		if (b < 0)
			q = (-b + distSqrt) / 2.0f;
		else
			q = (-b - distSqrt) / 2.0f;

		// compute t0 and t1
		float t0 = q / a;
		float t1 = c / q;

		// make sure t0 is smaller than t1
		if (t0 > t1) {
			// if t0 is bigger than t1 swap them around
			float temp = t0;
			t0 = t1;
			t1 = temp;
		}

		// if t1 is less than zero, the object is in the ray's negative
		// direction
		// and consequently the ray misses the sphere
		if (t1 < 0)
			return null;

		Vector3f normal;

		// if t0 is less than zero, the intersection point is at t1
		if (t0 < 0) {
			normal = new Vector3f(ray.getHitPoint(t1));
			normal.sub(center);
			normal.scale(1 / radius);
			this.normal = normal;
			return new HitRecord(t1, ray.getHitPoint(t1), normal, this, material);
		} else {
			normal = new Vector3f(ray.getHitPoint(t0));
			normal.sub(center);
			normal.scale(1 / radius);// else the intersection point is at t0
			this.normal = normal;
			return new HitRecord(t0, ray.getHitPoint(t0), normal, this, material);
		}
	}

	public Vector3f getCenter() {
		return center;
	}

	public void setCenter(Vector3f c) {
		center = c;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float r) {
		radius = r;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material mat) {
		material = mat;
	}

	@Override
	public Vector3f getNormal() {
		return normal;
	}
}
