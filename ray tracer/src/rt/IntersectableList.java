package rt;

import javax.vecmath.Vector4f;

public class IntersectableList extends Aggregate implements Intersectable {

	private float t = Float.MAX_VALUE;

	public IntersectableList() {
		super();
	}

	public void add(Intersectable object) {
		intersectables.add(object);
	}

	@Override
	public HitRecord intersect(Ray ray) {
		HitRecord hit = null;
		while (intersectables.iterator().hasNext()) {
			Intersectable object = intersectables.iterator().next();
			hit = object.intersect(ray);
			if (hit != null) {
				float temp = hit.getT();
				if (temp < t)
					t = temp;
			}
		}
		return hit;
	}

	public void add(Instance instance) {
		// TODO Auto-generated method stub

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

	@Override
	public Vector4f getNormal() {
		// TODO Auto-generated method stub
		return null;
	}
}