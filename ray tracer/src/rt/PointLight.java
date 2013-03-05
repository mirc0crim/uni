package rt;

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

	public Spectrum getCl(Vector4f hitPoint) {
		Vector4f p = new Vector4f(position);
		p.sub(hitPoint);
		float length = p.length() * p.length();
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