package rt;

import javax.vecmath.Vector3f;

public class PointLight implements Light {

	private Vector3f position;
	private Spectrum cSrc;

	public PointLight(Vector3f position, Spectrum strength) {
		this.position = position;
		cSrc = strength;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f pos) {
		position = pos;
	}

	public Spectrum getCl(Vector3f hitPoint) {
		Vector3f p = new Vector3f(position);
		p.sub(hitPoint);
		float length = p.length() * p.length();
		return cSrc.divideBy(length);
	}

	public Vector3f getL(Vector3f hitPoint) {
		Vector3f L = new Vector3f();
		L.set(position);
		L.sub(hitPoint);
		L.normalize();
		return L;
	}
}