package rt;

import javax.vecmath.Point4f;
import javax.vecmath.Vector4f;

public class PointLight implements Light {

	private Vector4f position;
	private Spectrum cSrc;

	public PointLight(Vector4f position, Spectrum strength) {
		this.position = position;
		cSrc = strength;
	}

	public Vector4f getPosition() {
		return position;
	}

	public void setPosition(Vector4f pos) {
		position = pos;
	}

	public Spectrum getCl(Point4f hitPoint) {
		Point4f p = new Point4f(position);
		float length = p.distanceSquared(hitPoint);
		return cSrc.divideBy(length);
	}

	public Vector4f getL(Vector4f hitPoint) {
		Vector4f L = new Vector4f();
		L.set(position);
		L.sub(hitPoint);
		L.normalize();
		return L;
	}
}