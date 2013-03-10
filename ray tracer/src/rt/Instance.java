package rt;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class Instance implements Intersectable {

	private Intersectable objectRef;
	private Matrix4f t;
	private Matrix4f t_inverse;

	public Instance(Intersectable object, Matrix4f t) {
		objectRef = object;
		t = new Matrix4f(t);
		t_inverse = new Matrix4f(t);
		t_inverse.invert();
	}

	@Override
	public HitRecord intersect(Ray ray) {
		Ray rayObj = transformRayToObject(ray);
		HitRecord hit = objectRef.intersect(rayObj);
		if (hit != null) {
			transformHitToWorld(hit);
		}
		return hit;
	}

	private void transformHitToWorld(HitRecord hitObj) {
		Vector4f intersectionPoint = new Vector4f(hitObj.getIntersectionPoint());
		intersectionPoint.setW(1);
		t.transform(intersectionPoint);
		Vector3f intersectionPoint3 = new Vector3f(intersectionPoint.x, intersectionPoint.y,
				intersectionPoint.z);

		Vector4f normal = new Vector4f(hitObj.getNormal());
		Matrix4f x = new Matrix4f(t);
		x.transpose();
		x.transform(normal);
		Vector3f normal3 = new Vector3f(normal.x, normal.y, normal.z);
		normal.normalize();

		Vector4f rayDir = new Vector4f(hitObj.getRayDir());
		t.transform(rayDir);
		Vector3f rayDir3 = new Vector3f(rayDir.x, rayDir.y, rayDir.z);

		hitObj.setIntersectionPoint(intersectionPoint3);
		hitObj.setNormal(normal3);
		hitObj.setRayDir(rayDir3);
	}

	private Ray transformRayToObject(Ray ray) {
		Vector4f rayDir = new Vector4f(ray.getDirection());
		Vector4f rayOrigin = new Vector4f(ray.getOrigin());
		rayOrigin.setW(1);
		t_inverse.transform(rayDir);
		t_inverse.transform(rayOrigin);
		Vector3f rayDir3 = new Vector3f(rayDir.x, rayDir.y, rayDir.z);
		Vector3f rayOrigin3 = new Vector3f(rayOrigin.x, rayOrigin.y, rayOrigin.z);
		return new Ray(rayDir3, rayOrigin3);
	}

	public Intersectable getObject() {
		return this.objectRef;
	}

	public void setObject(Intersectable obj) {
		objectRef = obj;
	}

	public Matrix4f getT() {
		return t;
	}

	public void setT(Matrix4f newt) {
		t = newt;
	}

	public Matrix4f getT_inverse() {
		return t_inverse;
	}

	public void setT_inverse(Matrix4f t_inv) {
		t_inverse = t_inv;
	}
}