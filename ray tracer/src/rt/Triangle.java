package rt;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class Triangle implements Intersectable {

	private Vector3f verticle1, verticle2, verticle3;
	private Vector3f norm1, norm2, norm3;
	private Vector3f u;
	private Vector3f v;
	private Vector3f normal;
	private Material material;

	public Triangle(Vector3f p1, Vector3f p2, Vector3f p3) {
		verticle1 = p1;
		verticle2 = p2;
		verticle3 = p3;

		Vector3f uTemp = new Vector3f(p2);
		uTemp.sub(p1);
		u = new Vector3f(uTemp);

		Vector3f vTemp = new Vector3f(p3);
		vTemp.sub(p1);
		v = new Vector3f(vTemp);

		Vector3f nTemp = new Vector3f(uTemp);
		nTemp.cross(uTemp, vTemp);
		normal = new Vector3f(nTemp);
	}

	public Triangle(Vector3f p1, Vector3f p2, Vector3f p3, Vector3f n1, Vector3f n2,
			Vector3f n3) {
		verticle1 = p1;
		verticle2 = p2;
		verticle3 = p3;

		Vector3f uTemp = new Vector3f(p2);
		uTemp.sub(p1);
		u = new Vector3f(uTemp);

		Vector3f vTemp = new Vector3f(p3);
		vTemp.sub(p1);
		v = new Vector3f(vTemp);

		Vector3f nTemp = new Vector3f(uTemp);
		nTemp.cross(uTemp, vTemp);
		normal = new Vector3f(nTemp);

		norm1 = n1;
		norm2 = n2;
		norm3 = n3;
	}

	@Override
	public HitRecord intersect(Ray ray) {

		Vector3f rayDir = ray.getDirection();
		Vector3f rayOrigin = ray.getOrigin();

		if (normal.length() == 0)
			return null; // triangle is degenerate
		Vector3f w0 = new Vector3f();
		w0.sub(rayOrigin, verticle1);
		float a = -normal.dot(w0);
		float b = normal.dot(rayDir);

		// ray lies in triangle plane or is disjoint from plane
		if (Math.abs(b) < 0.0000001f)
			return null;

		float t = a / b;
		if (t < 0)
			return null;

		// check if hitPoint is inside the triangle
		Vector3f hitPoint = ray.getHitPoint(t);
		float uu = u.dot(u);
		float uv = u.dot(v);
		float vv = v.dot(v);
		Vector3f w = new Vector3f();
		w.sub(hitPoint, verticle1);
		float wu = w.dot(u);
		float wv = w.dot(v);
		float D = uv * uv - uu * vv;

		float beta = (uv * wv - vv * wu) / D;
		if (beta < 0 || beta > 1)
			return null;
		float gamma = (uv * wu - uu * wv) / D;
		if (gamma < 0 || beta + gamma > 1)
			return null;
		float alpha = 1 - beta - gamma;

		Vector3f nI = normal;
		if (norm1 != null && norm2 != null && norm3 != null) {
			Vector3f n1I = new Vector3f(norm1);
			Vector3f n2I = new Vector3f(norm2);
			Vector3f n3I = new Vector3f(norm3);
			n1I.scale(alpha);
			n2I.scale(beta);
			n3I.scale(gamma);
			nI = new Vector3f(n1I);
			nI.add(n2I);
			nI.add(n3I);
		}

		return new HitRecord(t, hitPoint, nI, this, material, ray.getDirection());
	}

	@Override
	public Boundingbox getBox() {
		float minX = getMin(new float[] { verticle1.x, verticle2.x, verticle3.x });
		float minY = getMin(new float[] { verticle1.y, verticle2.y, verticle3.y });
		float minZ = getMin(new float[] { verticle1.z, verticle2.z, verticle3.z });
		float maxX = getMax(new float[] { verticle1.x, verticle2.x, verticle3.x });
		float maxY = getMax(new float[] { verticle1.y, verticle2.y, verticle3.y });
		float maxZ = getMax(new float[] { verticle1.z, verticle2.z, verticle3.z });

		Vector3f bfl = new Vector3f(minX, minY, minZ);
		Vector3f tbr = new Vector3f(maxX, maxY, maxZ);
		return new Boundingbox(bfl, tbr);
	}

	public static ArrayList<Triangle> splitAtYAxis(Vector3f axis, boolean above,
			ArrayList<Triangle> tri) {
		ArrayList<Triangle> newTri = new ArrayList<Triangle>();
		for (Triangle t : tri)
			if (above){
				if (t.verticle1.y >= axis.y || t.verticle2.y >= axis.y || t.verticle3.y >= axis.y)
					newTri.add(t);
			} else if (t.verticle1.y <= axis.y || t.verticle2.y <= axis.y || t.verticle3.y <= axis.y)
				newTri.add(t);
		return newTri;
	}

	public static ArrayList<Triangle> splitAtXAxis(Vector3f axis, boolean left,
			ArrayList<Triangle> tri) {
		ArrayList<Triangle> newTri = new ArrayList<Triangle>();
		for (Triangle t : tri)
			if (left){
				if (t.verticle1.x <= axis.x || t.verticle2.x <= axis.x || t.verticle3.x <= axis.x)
					newTri.add(t);
			} else if (t.verticle1.x >= axis.x || t.verticle2.x >= axis.x || t.verticle3.x >= axis.x)
				newTri.add(t);
		return newTri;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public Vector3f getV1() {
		return verticle1;
	}

	public void setNormal(Vector3f norm) {
		normal = norm;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material mat) {
		material = mat;
	}

	private float getMin(float[] values) {
		float minVal = Float.MAX_VALUE;
		for (float v : values)
			if (v < minVal)
				minVal = v;
		return minVal;
	}

	private float getMax(float[] values) {
		float maxVal = Float.MIN_VALUE;
		for (float v : values)
			if (v > maxVal)
				maxVal = v;
		return maxVal;
	}

}
