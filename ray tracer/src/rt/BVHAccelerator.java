package rt;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public class BVHAccelerator implements Intersectable {

	protected BVHNode root;

	public BVHAccelerator(Mesh m) {
		int dept = 0;
		root = new BVHNode(m.getTriangles(), m.getBox());
		construct(m.getTriangles(), dept);
		BVHNode node = root;
		while (node.getChild1() != null) {
			System.out.println(node.getTriangles().size());
			node = node.getChild1();
		}
		System.out.println(node.getTriangles().size());
	}

	public void construct(ArrayList<Triangle> triList, int dept) {
		addChildren(triList, root, "x", dept);
	}

	private void addChildren(ArrayList<Triangle> triList, BVHNode node, String ax, int dept) {
		float midX;
		float midY;
		ArrayList<Float> mx = new ArrayList<Float>();
		ArrayList<Float> my = new ArrayList<Float>();
		for (Triangle t : triList) {
			mx.add(t.getV1().x);
			my.add(t.getV1().y);
		}
		int middlex = mx.size() / 2;
		int middley = my.size() / 2;
		if (mx.size() % 2 == 1) {
			midX = mx.get(middlex);
			midY = my.get(middley);
		} else {
			midX = (mx.get(middlex + 1) + mx.get(middlex)) / 2f;
			midY = (my.get(middley + 1) + my.get(middley)) / 2f;
		}
		Vector3f axis = new Vector3f(midX, midY, 0);

		String otherAxis;
		if (ax == "x")
			otherAxis = "y";
		else
			otherAxis = "x";

		BVHNode c1 = makeChild1(axis, triList, ax);
		BVHNode c2 = makeChild2(axis, triList, ax);
		node.addChild(c1, c2);

		dept++;

		if (dept > 7)
			return;

		if (c1.getTriangles().size() > 50)
			addChildren(c1.getTriangles(), c1, otherAxis, dept);
		if (c2.getTriangles().size() > 50)
			addChildren(c2.getTriangles(), c2, otherAxis, dept);
	}

	public ArrayList<Triangle> getTriangles(Ray ray, BVHNode node, Vector3f dirfrac) {
		ArrayList<Triangle> shorterList = new ArrayList<Triangle>();
		if (node.getChild1() == null && node.getChild2() == null) {
			if (intersectsBoundingbox(ray, node.getBox(), dirfrac))
				return node.getTriangles();
		} else {
			if (node.getChild1() != null)
				if (intersectsBoundingbox(ray, node.getChild1().getBox(), dirfrac))
					shorterList.addAll(getTriangles(ray, node.getChild1(), dirfrac));
			if (node.getChild2() != null)
				if (intersectsBoundingbox(ray, node.getChild2().getBox(), dirfrac))
					shorterList.addAll(getTriangles(ray, node.getChild2(), dirfrac));
		}
		return shorterList;
	}

	private BVHNode makeChild1(Vector3f axis, ArrayList<Triangle> triList, String ax) {
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		if (ax == "x")
			triangles = Triangle.splitAtXAxis(axis, true, triList);
		if (ax == "y")
			triangles = Triangle.splitAtYAxis(axis, true, triList);
		ArrayList<Boundingbox> box = new ArrayList<Boundingbox>();
		for (Triangle tri : triangles)
			box.add(tri.getBox());
		Boundingbox boundingbox = Boundingbox.combineBox(box);
		BVHNode child = new BVHNode(triangles, boundingbox);
		return child;
	}

	private BVHNode makeChild2(Vector3f axis, ArrayList<Triangle> triList, String ax) {
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		if (ax == "x")
			triangles = Triangle.splitAtXAxis(axis, false, triList);
		if (ax == "y")
			triangles = Triangle.splitAtYAxis(axis, false, triList);
		ArrayList<Boundingbox> box = new ArrayList<Boundingbox>();
		for (Triangle tri : triangles)
			box.add(tri.getBox());
		Boundingbox boundingbox = Boundingbox.combineBox(box);
		BVHNode child = new BVHNode(triangles, boundingbox);
		return child;
	}

	@Override
	public HitRecord intersect(Ray ray) {
		return null;
	}

	@Override
	public Boundingbox getBox() {
		return root.getBox();
	}

	public BVHNode getRoot() {
		return root;
	}

	public boolean intersectsBoundingbox(Ray ray, Boundingbox b, Vector3f dirfrac) {
		// lb is the corner of axis aligned bounding box AABB with minimal
		// coordinates - left bottom, rt is maximal corner
		Vector3f bl = new Vector3f(b.bottomFrontLeft);
		Vector3f tr = new Vector3f(b.topBackRight);
		float t1 = (bl.x - ray.origin.x) * dirfrac.x;
		float t2 = (tr.x - ray.origin.x) * dirfrac.x;
		float t3 = (bl.y - ray.origin.y) * dirfrac.y;
		float t4 = (tr.y - ray.origin.y) * dirfrac.y;
		float t5 = (bl.z - ray.origin.z) * dirfrac.z;
		float t6 = (tr.z - ray.origin.z) * dirfrac.z;

		float tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
		float tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));

		// if tmax < 0, ray is intersecting AABB, but whole AABB is behing us
		// if tmin > tmax, ray doesn't intersect AABB
		if (tmax < 0 || tmin > tmax)
			return false;

		return true;
	}

}
