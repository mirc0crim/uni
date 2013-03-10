package rt;

import java.util.Iterator;

import javax.vecmath.Vector3f;

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
			if (newhit != null) {
				if (hit == null)
					hit = newhit;
				else if (newhit.getT() < hit.getT())
					hit = newhit;
			}
		}
		return hit;
	}

	@Override
	public Vector3f getNormal() {
		// TODO Auto-generated method stub
		return null;
	}
}