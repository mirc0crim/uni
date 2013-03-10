package rt;

import javax.vecmath.Vector3f;

public class Plane implements Intersectable {

	Vector3f normal;
	float distance;
	private Material material;

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
	public HitRecord intersect(Ray ray) {
		Vector3f l = new Vector3f(ray.getDirection());
		Vector3f p0 = new Vector3f(normal);
		p0.scale(distance);
		Vector3f l0 = new Vector3f(ray.getOrigin());
		Vector3f n = new Vector3f(normal);
		p0.sub(l0);
		float t = p0.dot(n) / l.dot(n);
		if (Float.isNaN(t))
			return null;
		if (t > 0)
			return new HitRecord(t, ray.getHitPoint(t), normal, this, getMaterial(),
					ray.getDirection());
		else
			return null;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material mat) {
		material = mat;
	}

}
