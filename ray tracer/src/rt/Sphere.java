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
		if (!intersectsBoundingbox(ray))
			return null;
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

		Vector3f norm;

		// if t0 is less than zero, the intersection point is at t1
		if (t0 < 0) {
			norm = new Vector3f(ray.getHitPoint(t1));
			norm.sub(center);
			norm.scale(1 / radius);
			normal = norm;
			return new HitRecord(t1, ray.getHitPoint(t1), norm, this, material,
					ray.getDirection());
		} else {
			norm = new Vector3f(ray.getHitPoint(t0));
			norm.sub(center);
			norm.scale(1 / radius);// else the intersection point is at t0
			normal = norm;
			return new HitRecord(t0, ray.getHitPoint(t0), norm, this, material,
					ray.getDirection());
		}
	}

	@Override
	public Boundingbox getBox() {
		Vector3f bfl = new Vector3f(center.x - radius, center.y - radius, center.z - radius);
		Vector3f tbr = new Vector3f(center.x + radius, center.y + radius, center.z + radius);
		return new Boundingbox(bfl, tbr);
	}

	private boolean intersectsBoundingbox(Ray ray) {
		float txmin, txmax, tymin, tymax;
		float d_x = ray.getDirection().x;
		float d_y = ray.getDirection().y;
		float e_x = ray.getOrigin().x;
		float e_y = ray.getOrigin().y;
		float x_min = center.x - radius;
		float x_max = center.x + radius;
		float y_min = center.y - radius;
		float y_max = center.y + radius;
		if ( d_x >= 0 ) {
			txmin = (x_min - e_x) / d_x;
			txmax = (x_max - e_x) / d_x;
		} else {
			txmin = (x_max - e_x) / d_x;
			txmax = (x_min - e_x) / d_x;
		}
		if (d_y >= 0) {
			tymin = (y_min - e_y) / d_y;
			tymax = (y_max - e_y) / d_y;
		} else {
			tymin = (y_max - e_y) / d_y;
			tymax = (y_min - e_y) / d_y;
		}
		if ((txmin > tymax) || (tymin > txmax)) {
			return false;
		} else {
			return true;
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

	public Vector3f getNormal() {
		return normal;
	}
}
