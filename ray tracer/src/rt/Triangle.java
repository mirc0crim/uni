package rt;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class Triangle implements Intersectable {

	private Vector3f verticle1;
	private Vector3f verticle2;
	private Vector3f verticle3;
	private Vector4f u;
	private Vector4f v;
	private Vector4f normal;
	private Material material;

	public Triangle(Vector3f p1, Vector3f p2, Vector3f p3) {
		verticle1 = p1;
		verticle2 = p2;
		verticle3 = p3;

		Vector3f uTemp = new Vector3f(p1);
		uTemp.sub(p2);
		u = new Vector4f(uTemp.x, uTemp.y, uTemp.z, 0);

		Vector3f vTemp = new Vector3f(p1);
		vTemp.sub(p3);
		v = new Vector4f(vTemp.x, vTemp.y, vTemp.z, 0);

		Vector3f nTemp = new Vector3f(uTemp);
		nTemp.cross(uTemp, vTemp);
		normal = new Vector4f(nTemp.x, nTemp.y, nTemp.z, 0);
		normal.normalize();
	}

	@Override
	public HitRecord intersect(Ray ray) {
		float a = verticle1.x - verticle2.x, b = verticle1.x - verticle3.x, c = ray.getDirection().x, d = verticle1.x
				- ray.getOrigin().x;
		float e = verticle1.y - verticle2.y, f = verticle1.y - verticle3.y, g = ray.getDirection().y, h = verticle1.y
				- ray.getOrigin().y;
		float i = verticle1.z - verticle2.z, j = verticle1.z - verticle3.z, k = ray.getDirection().z, l = verticle1.z
				- ray.getOrigin().z;

		float m = f * k - g * j, n = h * k - g * l, p = f * l - h * j;
		float q = g * i - e * k, s = e * j - f * i;

		float inv_denom = 1.0f / (a * m + b * q + c * s);

		float e1 = d * m - b * n - c * p;
		float beta = e1 * inv_denom;

		if (beta < 0.0f)
			return null;

		float r = e * l - h * i;
		float e2 = a * n + d * q + c * r;
		float gamma = e2 * inv_denom;

		if (gamma < 0.0f)
			return null;
		if (beta + gamma > 1.0f)
			return null;

		float e3 = a * p - b * r + d * s;
		float t = e3 * inv_denom;
		if (t > 0)
			return new HitRecord(t, ray.getHitPoint(t), normal, this, material);
		return null;
	}

	@Override
	public Vector4f getNormal() {
		return normal;
	}

	public void setNormal(Vector4f norm) {
		normal = norm;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material mat) {
		material = mat;
	}

}
