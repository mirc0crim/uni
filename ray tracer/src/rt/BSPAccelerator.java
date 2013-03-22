package rt;

public class BSPAccelerator implements Intersectable {

	private BSPNode root;

	public void construct(Mesh m) {
		root = new BSPNode(m.getBox());
	}

	@Override
	public HitRecord intersect(Ray ray) {
		return null;
	}

	@Override
	public Boundingbox getBox() {
		return root.getBox();
	}

}
