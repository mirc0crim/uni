package rt;

import javax.vecmath.Vector3f;

public class DirectionalLight implements Light {

	private Vector3f direction;
	private Spectrum cSrc;

	public DirectionalLight(Vector3f dir, Spectrum col) {
		direction = dir;
		cSrc = col;
	}

	@Override
	public Spectrum getCl(Vector3f hitPoint) {
		return new Spectrum(cSrc);
	}

	@Override
	public Vector3f getL(Vector3f hitPoint) {
		Vector3f L = new Vector3f(direction);
		L.negate();
		L.normalize();
		return L;
	}

	public Vector3f getDirection() {
		return direction;
	}

	public void setDirection(Vector3f dir) {
		direction = dir;
	}

	@Override
	public Vector3f getPosition() {
		return null;
	}
}