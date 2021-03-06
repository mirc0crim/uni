package jrtr;


public class ShapeNode extends Leaf {
	private Shape shapeNode;

	public ShapeNode() {
		super();
	}

	@Override
	public void setShape(Shape shape) {
		shapeNode = shape;
	}

	@Override
	public Shape getShape() {
		return shapeNode;
	}

	@Override
	public Light getLight() {
		return null;
	}

	@Override
	public void setLight(Light light) {
	}

}
