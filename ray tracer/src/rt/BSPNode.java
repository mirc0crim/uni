package rt;

import java.util.ArrayList;

public class BSPNode {

	private BSPNode child1;
	private BSPNode child2;
	private Boundingbox box;
	private ArrayList<Triangle> triangles;
	private Aggregate intersectables;
	private boolean isLeaf;
	private String axis;

	public BSPNode(ArrayList<Triangle> tri, Boundingbox b, String a) {
		box = b;
		triangles = tri;
		isLeaf = false;
		axis = a;
	}

	public BSPNode(Aggregate inters, Boundingbox b) {
		box = b;
		intersectables = inters;
		isLeaf = true;
	}

	public Boundingbox getBox() {
		return box;
	}

	public void setBox(Boundingbox b) {
		box = b;
	}

	public void addChild(BSPNode c1, BSPNode c2) {
		child1 = c1;
		child2 = c2;
	}

	public BSPNode getChild1() {
		return child1;
	}

	public BSPNode getChild2() {
		return child2;
	}

	public ArrayList<Triangle> getTriangles() {
		return triangles;
	}

}
