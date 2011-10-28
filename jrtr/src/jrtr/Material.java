package jrtr;

/**
 * Stores the properties of a material. You will implement this
 * class in the "Shading and Texturing" project.
 */
public class Material {
	private Texture texture;

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture tex) {
		texture = tex;
	}

}
