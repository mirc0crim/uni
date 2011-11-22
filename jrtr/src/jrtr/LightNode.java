package jrtr;


public class LightNode extends Leaf {
	private Light lightNode;

	public LightNode() {
		super();
	}

	@Override
	public void setShape(Shape shape) {
	}

	@Override
	public Shape getShape() {
		return null;
	}

	@Override
	public Light getLight() {
		return lightNode;
	}

	@Override
	public void setLight(Light light) {
		lightNode = light;
	}

}
