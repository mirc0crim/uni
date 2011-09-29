package jrtr;

/**
 * Declares the functionality to manage shaders.
 */
public interface Shader {

	public void load(String vertexFileName, String fragmentFileName) throws Exception;
	public void use();
	public void disable();
}
