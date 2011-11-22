package jrtr;

import java.util.List;

import javax.vecmath.Matrix4f;


public abstract class Leaf implements Node {
	private Matrix4f transformation;

	@Override
	public List<Node> getChildren() {
		return null;
	}

	@Override
	public void addChild(Node child) {
	}

	@Override
	public Matrix4f getTransformationMat() {
		return transformation;
	}

	@Override
	public void setTransformationMat(Matrix4f transform) {
		transformation = transform;
	}

}
