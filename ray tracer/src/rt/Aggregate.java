package rt;

import java.util.ArrayList;

public abstract class Aggregate {

	protected ArrayList<Intersectable> intersectables;

	public Aggregate() {
		intersectables = new ArrayList<Intersectable>();
	}

	/**
	 * @return the intersectables
	 */
	public ArrayList<Intersectable> getIntersectables() {
		return intersectables;
	}

	/**
	 * @param intersectables
	 *            the intersectables to set
	 */
	public void setIntersectables(ArrayList<Intersectable> intersectables) {
		this.intersectables = intersectables;
	}

	public HitRecord intersect(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
}