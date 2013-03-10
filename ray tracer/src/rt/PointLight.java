package rt;

import javax.vecmath.Vector3f;

public class PointLight implements Light {

	private Vector3f position;
	private Spectrum cSrc;

	public PointLight(Vector3f pos, Spectrum strength) {
		position = pos;
		cSrc = strength;
	}

	public Spectrum getCl(Vector3f hitPoint) {
		Vector3f p = new Vector3f(position);
		p.sub(hitPoint);
		float len = (float) Math.pow(p.length(), 2);
		return new Spectrum(cSrc.divideBy(len));
	}

	public Vector3f getL(Vector3f hitPoint) {
		Vector3f L = new Vector3f(position);
		L.sub(hitPoint);
		L.normalize();
		return L;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f pos) {
		position = pos;
	}
}