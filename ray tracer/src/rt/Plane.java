package rt;
import javax.vecmath.Vector4f;

public class Plane implements Intersectable {

	Vector4f normal;
	float distance;
	private Material material;

	public Plane(Vector4f n, float d) {
		normal = n;
		distance = d;
	}

	@Override
	public Vector4f getNormal() {
		return normal;
	}

	public void setNormal(Vector4f n) {
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
		Vector4f l = new Vector4f(ray.getDirection());
		Vector4f p0 = new Vector4f(normal);
		p0.scale(distance);
		Vector4f l0 = new Vector4f(ray.getOrigin());
		Vector4f n = new Vector4f(normal);
		p0.sub(l0);
		float t = p0.dot(n) / l.dot(n);
		if (Float.isNaN(t))
			return null;
		if (t > 0)
			return new HitRecord(t, ray.getHitPoint(t), normal, this, getMaterial());
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
