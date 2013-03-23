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
		float midY = (root.getBox().bottomFrontLeft.y + root.getBox().topBackRight.y) / 2;
		Vector3f axis = new Vector3f(midX, midY, 0);

		BSPNode c1 = makeChild1(axis, m.getTriangles(), "x");
		BSPNode c2 = makeChild2(axis, m.getTriangles(), "x");
		root.addChild(c1, c2);

		BSPNode c11 = makeChild1(axis, c1.getTriangles(), "y");
		BSPNode c12 = makeChild2(axis, c1.getTriangles(), "y");
		c1.addChild(c11, c12);

		BSPNode c21 = makeChild1(axis, c2.getTriangles(), "y");
		BSPNode c22 = makeChild2(axis, c2.getTriangles(), "y");
		c2.addChild(c21, c22);
	}

	private BSPNode makeChild1(Vector3f axis, ArrayList<Triangle> triList, String ax) {
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		if (ax == "x")
			triangles = Triangle.splitAtXAxis(axis, true, triList);
		if (ax == "y")
			triangles = Triangle.splitAtYAxis(axis, true, triList);
		ArrayList<Boundingbox> box = new ArrayList<Boundingbox>();
		for (Triangle tri : triangles)
			box.add(tri.getBox());
		Boundingbox boundingbox = Boundingbox.combineBox(box);
		BSPNode child = new BSPNode(triangles, boundingbox);
		return child;
	}

	private BSPNode makeChild2(Vector3f axis, ArrayList<Triangle> triList, String ax) {
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		if (ax == "x")
			triangles = Triangle.splitAtXAxis(axis, false, triList);
		if (ax == "y")
			triangles = Triangle.splitAtYAxis(axis, false, triList);
		ArrayList<Boundingbox> box = new ArrayList<Boundingbox>();
		for (Triangle tri : triangles)
			box.add(tri.getBox());
		Boundingbox boundingbox = Boundingbox.combineBox(box);
		BSPNode child = new BSPNode(triangles, boundingbox);
		return child;
	}

	@Override
	public HitRecord intersect(Ray ray) {
		return null;
	}

	public ArrayList<Triangle> getTriangles(Ray ray, BSPNode node) {
		ArrayList<Triangle> shorterList = new ArrayList<Triangle>();
		if (intersectsBoundingbox(ray, node.getBox())) {
			if (node.getChild1() != null)
				shorterList.addAll(getTriangles(ray, node.getChild1()));
			if (node.getChild2() != null)
				shorterList.addAll(getTriangles(ray, node.getChild2()));
			if (node.getChild1() == null && node.getChild2() == null)
				shorterList.addAll(node.getTriangles());
		}
		return shorterList;
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
