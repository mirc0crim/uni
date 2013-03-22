package rt;

import java.util.ArrayList;

public abstract class Aggregate {

	protected ArrayList<Intersectable> intersectables;
	protected Boundingbox box;

	public Aggregate() {
		intersectables = new ArrayList<Intersectable>();
		ArrayList<Boundingbox> bb = new ArrayList<Boundingbox>();
		for (Intersectable t : intersectables)
			bb.add(t.getBox());
		box = Boundingbox.combineBox(bb);
	}

	public Boundingbox getBox() {
		return box;
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