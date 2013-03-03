package rt;

import java.util.LinkedList;

import javax.vecmath.Vector4f;

public class Mesh extends Aggregate {

	private int n;
	private int[] indices;
	private LinkedList<VertexElement> vertexElements;

	/**
	 * Vertex data semantic can be position, normal, or texture coordinates.
	 */
	public enum Semantic {
		POSITION, NORMAL, TEXCOORD, COLOR
	}

	public class VertexElement {

		public float[] getData() {
			return data;
		}

		public Semantic getSemantic() {
			return semantic;
		}

		public int getNumberOfComponents() {
			return nComponents;
		}

		private float[] data;
		private Semantic semantic;
		private int nComponents;
	}

	public Mesh(int n) {
		this.n = n;
		vertexElements = new LinkedList<VertexElement>();
	}

	public int getNumberOfVertices() {
		return n;
	}

	public void addElement(float f[], Semantic s, int i) {
		if (f.length == n * i) {
			VertexElement vertexElement = new VertexElement();
			vertexElement.data = f;
			vertexElement.semantic = s;
			vertexElement.nComponents = i;

			// Make sure POSITION is the last element in the list. This
			// guarantees
			// that rendering works as expected (i.e., vertex attributes are set
			// before the vertex is rendered).
			if (s == Semantic.POSITION)
				vertexElements.addLast(vertexElement);
			else
				vertexElements.addFirst(vertexElement);
		}
	}

	public void addIndices(int indices[]) {
		this.indices = indices;
	}

	public LinkedList<VertexElement> getElements() {
		return vertexElements;
	}

	public int[] getIndices() {
		return indices;
	}

	@Override
	public HitRecord intersect(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}

	public Vector4f getNormal() {
		return null;
	}

}