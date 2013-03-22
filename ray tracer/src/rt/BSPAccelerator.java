package rt;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class BSPAccelerator implements Intersectable {

	protected BSPNode root;

	public BSPAccelerator(Mesh m) {
		root = new BSPNode(m.getTriangles(), m.getBox());
		construct(m);
	}

	public void construct(Mesh m) {
		float midX = (root.getBox().bottomFrontLeft.x + root.getBox().topBackRight.x) / 2;

		ArrayList<Triangle> t1 = Triangle.splitAtXAxis(new Vector3f(midX, 0, 0), true,
				m.getTriangles());
		ArrayList<Boundingbox> b1 = new ArrayList<Boundingbox>();
		for (Triangle t : t1)
			b1.add(t.getBox());
		Boundingbox bb1 = Boundingbox.combineBox(b1);
		BSPNode c1 = new BSPNode(t1, bb1);

		ArrayList<Triangle> t2 = Triangle.splitAtXAxis(new Vector3f(midX, 0, 0), false,
				m.getTriangles());
		ArrayList<Boundingbox> b2 = new ArrayList<Boundingbox>();
		for (Triangle t : t2)
			b2.add(t.getBox());
		Boundingbox bb2 = Boundingbox.combineBox(b2);
		BSPNode c2 = new BSPNode(t2, bb2);

		root.addChild(c1, c2);
	}

	@Override
	public HitRecord intersect(Ray ray) {
		return null;
	}

	@Override
	public Boundingbox getBox() {
		return root.getBox();
	}

	public BSPNode getRoot() {
		return root;
	}

	public boolean intersectsBoundingbox(Ray ray, Boundingbox b) {
		Vector3f dirfrac = new Vector3f();
		dirfrac.x = 1.0f / ray.direction.x;
		dirfrac.y = 1.0f / ray.direction.y;
		dirfrac.z = 1.0f / ray.direction.z;
		// lb is the corner of axis aligned bounding box AABB with minimal
		// coordinates - left bottom, rt is maximal corner
		Vector3f lb = b.bottomFrontLeft;
		Vector3f rt = b.topBackRight;
		float t1 = (lb.x - ray.origin.x) * dirfrac.x;
		float t2 = (rt.x - ray.origin.x) * dirfrac.x;
		float t3 = (lb.y - ray.origin.y) * dirfrac.y;
		float t4 = (rt.y - ray.origin.y) * dirfrac.y;
		float t5 = (lb.z - ray.origin.z) * dirfrac.z;
		float t6 = (rt.z - ray.origin.z) * dirfrac.z;

		float tmin = max(max(min(t1, t2), min(t3, t4)), min(t5, t6));
		float tmax = min(min(max(t1, t2), max(t3, t4)), max(t5, t6));

		// if tmax < 0, ray is intersecting AABB, but whole AABB is behing us
		if (tmax < 0)
			return false;

		// if tmin > tmax, ray doesn't intersect AABB
		if (tmin > tmax)
			return false;

		return true;
	}

	private float min(float v1, float v2) {
		if (v1 < v2)
			return v1;
		else
			return v2;
	}

	private float max(float v1, float v2) {
		if (v1 > v2)
			return v1;
		else
			return v2;
	}


}
