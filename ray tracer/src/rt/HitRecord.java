package rt;

import javax.vecmath.Vector3f;

public class HitRecord {

	private float t;
	private Vector3f intersectionPoint;
	private Vector3f normal;
	private Intersectable object;
	private Material material;
	private Vector3f rayDir;

	public HitRecord(float t, Vector3f intersectionPoint, Vector3f normal,
			Intersectable object, Material material, Vector3f rayDir) {
		setT(t);
		setIntersectionPoint(intersectionPoint);
		setNormal(normal);
		setObject(object);
		setMaterial(material);
		setRayDir(rayDir);
	}

	public Vector3f getIntersectionPoint() {
		return intersectionPoint;
	}

	public void setIntersectionPoint(Vector3f intersectionPt) {
		intersectionPoint = intersectionPt;
	}

	public float getT() {
		return t;
	}

	public void setT(float newt) {
		t = newt;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public void setNormal(Vector3f norm) {
		normal = norm;
	}

	public Intersectable getObject() {
		return object;
	}

	public void setObject(Intersectable obj) {
		object = obj;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material mat) {
		material = mat;
	}

	public Vector3f getRayDir() {
		return rayDir;
	}

	public void setRayDir(Vector3f rayDirection) {
		rayDir = rayDirection;
	}

}