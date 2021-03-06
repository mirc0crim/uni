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

	@Override
	public HitRecord intersect(Ray ray) {
		Vector3f p0 = new Vector3f(normal);
		p0.negate();
		p0.scale(distance);
		Vector3f l0 = new Vector3f(ray.getOrigin());
		p0.sub(l0);
		float t = p0.dot(normal) / ray.getDirection().dot(normal);
		if (Float.isNaN(t))
			return null;
		if (t > 0)
			return new HitRecord(t, ray.getHitPoint(t), normal, this, getMaterial(),
					ray.getDirection());
		else
			return null;
	}

	@Override
	public Boundingbox getBox() {
		Vector3f bfl = new Vector3f(10, 10, 10);
		Vector3f tbr = new Vector3f(10, 10, 10);
		return new Boundingbox(bfl, tbr);
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

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material mat) {
		material = mat;
	}

}
