package rt;
import javax.vecmath.Vector4f;

public class Ray {

	Vector4f origin;
	Vector4f direction;

	public Ray(Vector4f dir, Vector4f orig) {
		origin = orig;
		direction = new Vector4f(dir);
		direction.sub(orig);
		direction.normalize();
	}

	public Vector4f getHitPoint(float t) {
		Vector4f hitPoint = new Vector4f(origin);
		Vector4f d = new Vector4f(direction);
		d.scale(t);
		hitPoint.add(d);
		return hitPoint;
	}

	public Vector4f getOrigin() {
		return origin;
	}

	public void setOrigin(Vector4f orig) {
		origin = orig;
	}

	public Vector4f getDirection() {
		return direction;
	}

	public void setDirection(Vector4f dir) {
		direction = dir;
	}

}
