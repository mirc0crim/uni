package rt;

import java.util.ArrayList;

public class BVHNode {

	private BVHNode child1;
	private BVHNode child2;
	private Boundingbox box;
	private ArrayList<Triangle> triangles;

	public BVHNode(ArrayList<Triangle> tri, Boundingbox b) {
		box = b;
		triangles = tri;
	}

	public Boundingbox getBox() {
		return box;
	}

	public void setBox(Boundingbox b) {
		box = b;
	}

	public void addChild(BVHNode c1, BVHNode c2) {
		child1 = c1;
		child2 = c2;
	}

	public BVHNode getChild1() {
		return child1;
	}

	public BVHNode getChild2() {
		return child2;
	}

	public ArrayList<Triangle> getTriangles() {
		return triangles;
	}

}
