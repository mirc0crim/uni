package jrtr;

import java.util.List;
import java.util.Stack;

import javax.vecmath.Matrix4f;

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

		public GraphSceneManagerItr(GraphSceneManager sceneManager) {
			stack = new Stack<RenderItem>();
			root = sceneManager.getRoot();
			makeTree(root);
		}

		private void makeTree(Node node) {
			stack.push(new RenderItem(node.getShape(), node.getTransformationMat()));
			if (hasNext()) {
				List<Node> children = node.getChildren();
				for (Node child : children) {
					Matrix4f t = node.getTransformationMat();
					t.mul(child.getTransformationMat());
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
	}
}
