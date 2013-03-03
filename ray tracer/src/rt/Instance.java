package rt;

import javax.vecmath.Matrix4f;

public class Instance {

	private Mesh mesh;
	private Matrix4f t;

	public Instance(Mesh mesh, Matrix4f t) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the mesh
	 */
	public Mesh getMesh() {
		return mesh;
	}

	/**
	 * @param mesh
	 *            the mesh to set
	 */
	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}

	/**
	 * @return the t
	 */
	public Matrix4f getT() {
		return t;
	}

	/**
	 * @param t
	 *            the t to set
	 */
	public void setT(Matrix4f t) {
		this.t = t;
	}

}