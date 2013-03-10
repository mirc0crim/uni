package rt;

import javax.vecmath.Vector3f;

public class SpotLight implements Light {

	private Vector3f position;
	private Vector3f direction;
	private float angle;
	private int exp;
	private Spectrum cSrc;

	public SpotLight(Vector3f pos, Vector3f dir, Spectrum newcSrc, float newangle, int newexp) {
		position = pos;
		direction = dir;
		setcSrc(newcSrc);
		angle = (float) Math.cos(newangle / (2 * Math.PI));
		setExp(newexp);
	}

	public Spectrum getCl(Vector3f hitPoint) {
		Vector3f L = new Vector3f(position);
		L.sub(hitPoint);
		L.normalize();
		L.negate();
		float LdotD = L.dot(direction);
		if (LdotD <= angle)
			return new Spectrum(0.f, 0.f, 0.f);
		else
			return cSrc.multipliedBy((float) Math.pow(LdotD, exp));
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

	public Vector3f getDirection() {
		return direction;
	}

	public void setDirection(Vector3f dir) {
		direction = dir;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float newangle) {
		angle = newangle;
	}

	public Spectrum getcSrc() {
		return cSrc;
	}

	public void setcSrc(Spectrum newcSrc) {
		cSrc = newcSrc;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int newexp) {
		exp = newexp;
	}
}