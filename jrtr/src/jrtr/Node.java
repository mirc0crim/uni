package jrtr;

import java.util.List;

import javax.vecmath.Matrix4f;



public interface Node {

	public Shape getShape();

	public void setShape(Shape shape);

	public Light getLight();

	public void setLight(Light light);

	public List<Node> getChildren();

	public void addChild(Node child);

	public Matrix4f getTransformationMat();

	public void setTransformationMat(Matrix4f transform);
}
