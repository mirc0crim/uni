package rt;

import javax.vecmath.Vector3f;

public class BSPNode {

	private Vector3f plane_pos;
	private String axis;
	private String tag;
	private BSPNode child;
	private Aggregate leaf;
	private Boundingbox box;

	public BSPNode(Boundingbox b) {
		box = b;
	}

	public Boundingbox getBox() {
		return box;
	}

	public void setBox(Boundingbox b) {
		box = b;
	}

}
