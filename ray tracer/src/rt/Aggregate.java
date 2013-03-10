package rt;

import java.util.ArrayList;

public abstract class Aggregate {

	protected ArrayList<Intersectable> intersectables;

	public Aggregate() {
		intersectables = new ArrayList<Intersectable>();
	}

	public ArrayList<Intersectable> getIntersectables() {
		return intersectables;
	}

	public void setIntersectables(ArrayList<Intersectable> intersects) {
		intersectables = intersects;
	}

	public HitRecord intersect(Ray ray) {
		return null;
	}
}