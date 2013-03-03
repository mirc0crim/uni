package rt;

import javax.vecmath.Vector4f;

public class HitRecord {

	private float t;
	private Vector4f intersectionPoint;
	private Vector4f normal;
	private Intersectable object;
	private Material material;

	public HitRecord(float t, Vector4f intersectionPoint, Vector4f normal, Intersectable object,
			Material material) {
		setT(t);
		setIntersectionPoint(intersectionPoint);
		setNormal(normal);
		setObject(object);
		setMaterial(material);
	}

	/**
	 * @return the intersectionPoint
	 */
	public Vector4f getIntersectionPoint() {
		return intersectionPoint;
	}

	/**
	 * @param intersectionPoint
	 *            the intersectionPoint to set
	 */
	public void setIntersectionPoint(Vector4f intersectionPoint) {
		this.intersectionPoint = intersectionPoint;
	}

	/**
	 * @return the t
	 */
	public float getT() {
		return t;
	}

	/**
	 * @param t
	 *            the t to set
	 */
	public void setT(float t) {
		this.t = t;
	}

	/**
	 * @return the normal
	 */
	public Vector4f getNormal() {
		return normal;
	}

	/**
	 * @param normal
	 *            the normal to set
	 */
	public void setNormal(Vector4f normal) {
		this.normal = normal;
	}

	/**
	 * @return the object
	 */
	public Intersectable getObject() {
		return object;
	}

	/**
	 * @param object
	 *            the object to set
	 */
	public void setObject(Intersectable object) {
		this.object = object;
	}

	/**
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * @param material
	 *            the material to set
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}

}