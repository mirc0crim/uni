package jrtr;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class GraphSceneManager implements SceneManagerInterface {

	private Camera camera;
	private Frustum frustum;
	private Node root;

	public GraphSceneManager() {
		camera = new Camera();
		frustum = new Frustum();
	}

	@Override
	public Camera getCamera() {
		return camera;
	}

	@Override
	public Frustum getFrustum() {
		return frustum;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node r) {
		root = r;
	}

	@Override
	public SceneManagerIterator iterator() {
		return new GraphSceneManagerItr(this);
	}

	private class GraphSceneManagerItr implements SceneManagerIterator {

		private Node root;
		private Stack<RenderItem> stack;
		private List<Light> lights;

		public GraphSceneManagerItr(GraphSceneManager sceneManager) {
			stack = new Stack<RenderItem>();
			root = sceneManager.getRoot();
			lights = new LinkedList<Light>();
			makeTree(root);
		}

		private boolean shapeNotNull(Node node) {
			boolean bol = true;
			try {
				node.getShape();
			} catch (NullPointerException e) {
				bol = false;
			}
			return bol && node.getShape() != null;
		}

		private boolean childrenNotNull(Node node) {
			boolean bol = true;
			try {
				node.getChildren();
			} catch (NullPointerException e) {
				bol = false;
			}
			return bol && node.getChildren() != null;
		}

		private void makeTree(Node node) {
			if (node instanceof ShapeNode) {
				if (shapeNotNull(node))
					stack.push(new RenderItem(node.getShape(), node.getTransformationMat()));
			} else if (node instanceof LightNode) {
				Vector3f pos = node.getLight().getPosition();
				Vector4f pos4 = new Vector4f(pos.x, pos.y, pos.z, 0);
				Matrix4f trans = node.getTransformationMat();
				trans.transform(pos4);
				node.getLight().setPosition(new Vector3f(pos4.x, pos4.y, pos4.z));
				lights.add(node.getLight());
			}
			if (childrenNotNull(node)) {
				List<Node> children = node.getChildren();
				for (Node child : children) {
					Matrix4f t = child.getTransformationMat();
					t.mul(node.getTransformationMat());
					child.setTransformationMat(t);
					makeTree(child);
				}
			}
		}

		@Override
		public boolean hasNext() {
			return !stack.empty();
		}

		@Override
		public RenderItem next() {
			return stack.pop();
		}

		public List<Light> getLights() {
			return lights;
		}
	}

	@Override
	public Iterator<Light> lightIterator() {
		return new GraphSceneManagerItr(this).getLights().iterator();
	}
}
