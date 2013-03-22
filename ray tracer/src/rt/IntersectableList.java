package rt;

import java.util.ArrayList;
import java.util.Iterator;

public class IntersectableList extends Aggregate implements Intersectable {

	public IntersectableList() {
		super();
	}

	public void add(Intersectable object) {
		intersectables.add(object);
	}

	@Override
	public HitRecord intersect(Ray ray) {
		HitRecord hit = null;
		HitRecord newhit = null;
		Iterator<Intersectable> iter = intersectables.iterator();
		while (iter.hasNext()) {
			Intersectable object = iter.next();
			newhit = object.intersect(ray);
			if (newhit != null)
				if (hit == null)
					hit = newhit;
				else if (newhit.getT() < hit.getT())
					hit = newhit;
		}
		return hit;
	}

	@Override
	public Boundingbox getBox() {
		ArrayList<Boundingbox> bb = new ArrayList<Boundingbox>();
		for (Intersectable t : intersectables)
			bb.add(t.getBox());
		return Boundingbox.combineBox(bb);
	}
}