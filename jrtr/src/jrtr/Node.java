package jrtr;

import java.util.List;

import javax.vecmath.Matrix4f;



public interface Node {

	public Shape getShape();

	public void setShape(Shape shape);

	public List<Node> getChildren();

	public Matrix4f getTransformationMat();

	public void setTransformationMat(Matrix4f transform);
}
