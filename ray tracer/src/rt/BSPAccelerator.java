package rt;

import java.util.ArrayList;
import java.util.Random;

import javax.vecmath.Vector3f;

public class BSPAccelerator implements Intersectable {

	protected BSPNode root;

	public BSPAccelerator(Mesh m) {
		int dept = 0;
		root = new BSPNode(m.getTriangles(), m.getBox());
		construct(m.getTriangles(), dept);
		BSPNode node = root;
		while (node.getChild1() != null) {
			System.out.println(node.getTriangles().size());
			node = node.getChild1();
		}
		System.out.println(node.getTriangles().size());
	}

	public void construct(ArrayList<Triangle> triList, int dept) {
		addChildren(triList, root, "x", dept);
	}

	private void addChildren(ArrayList<Triangle> triList, BSPNode node, String ax, int dept) {
		float midX = (node.getBox().bottomFrontLeft.x + node.getBox().topBackRight.x) / 2f
				* (new Random().nextFloat() / 10f + 0.95f);
		float midY = (node.getBox().bottomFrontLeft.y + node.getBox().topBackRight.y) / 2f
				* (new Random().nextFloat() / 10f + 0.95f);
		Vector3f axis = new Vector3f(midX, midY, 0);

		BSPNode c1 = makeChild1(axis, triList, ax);
		BSPNode c2 = makeChild2(axis, triList, ax);
		node.addChild(c1, c2);

		dept++;

		if (dept > 8 + 1.3 * Math.log(root.getTriangles().size()))
			return;

		String otherAxis;
		if (ax == "x")
			otherAxis = "y";
		else
			otherAxis = "x";
		if (c1.getTriangles().size() > 100)
			addChildren(c1.getTriangles(), c1, otherAxis, dept);
		if (c2.getTriangles().size() > 100)
			addChildren(c2.getTriangles(), c2, otherAxis, dept);
	}

	public ArrayList<Triangle> getTriangles(Ray ray, BSPNode node, Vector3f dirfrac) {
		ArrayList<Triangle> shorterList = new ArrayList<Triangle>();
		if (intersectsBoundingbox(ray, node.getBox(), dirfrac)) {
			if (node.getChild1() != null)
				shorterList.addAll(getTriangles(ray, node.getChild1(), dirfrac));
			if (node.getChild2() != null)
				shorterList.addAll(getTriangles(ray, node.getChild2(), dirfrac));
			if (node.getChild1() == null && node.getChild2() == null)
				shorterList = node.getTriangles();
		}
		return shorterList;
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

	@Override
	public Boundingbox getBox() {
		return root.getBox();
	}

	public BSPNode getRoot() {
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
