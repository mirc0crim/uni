package jrtr;

import javax.vecmath.Matrix4f;

public class TransformGroup extends Group {

	private Matrix4f transformationMat;

	public TransformGroup() {
		super();
	}

	@Override
	public Matrix4f getTransformationMat() {
		return transformationMat;
	}

	@Override
	public void setTransformationMat(Matrix4f transform) {
		transformationMat = transform;
	}
}
