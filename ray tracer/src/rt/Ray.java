package rt;

import javax.vecmath.Vector3f;

public class Ray {

	Vector3f origin;
	Vector3f direction;

	public Ray(Vector3f dir, Vector3f orig) {
		origin = orig;
		direction = new Vector3f(dir);
	}

	public Vector3f getHitPoint(float t) {
		Vector3f hitPoint = new Vector3f(origin);
		Vector3f d = new Vector3f(direction);
		d.scale(t);
		hitPoint.add(d);
		return hitPoint;
	}

	public Vector3f getOrigin() {
		return origin;
	}

	public void setOrigin(Vector3f orig) {
		origin = orig;
	}

	public Vector3f getDirection() {
		return direction;
	}

	public void setDirection(Vector3f dir) {
		direction = dir;
	}

}
