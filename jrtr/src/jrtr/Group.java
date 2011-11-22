package jrtr;

import java.util.LinkedList;
import java.util.List;

public abstract class Group implements Node {

	private List<Node> children;

	public Group() {
		super();
		children = new LinkedList<Node>();
	}

	@Override
	public Shape getShape() {
		return null;
	}

	public void addChild(Node child) {
		children.add(child);
	}

	public void removeChild(Node child) {
		children.remove(child);
	}

	@Override
	public void setShape(Shape shape) {
	}

	@Override
	public List<Node> getChildren() {
		return children;
	}

}
