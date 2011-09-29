package jrtr;

/**
 * Declares the functionality of a render context, or a "renderer". It is
 * currently implemented by {@link GLRenderContext} and {@link SWRenderContext}. 
 */
public interface RenderContext {

	/**
	 * Set a scene manager that will be rendered.
	 */
	void setSceneManager(SceneManagerInterface sceneManager);
	
	/**
	 * Make a shader.
	 * 
	 * @return the shader
	 */
	Shader makeShader();
	
	/**
	 * Make a texture.
	 * 
	 * @return the texture
	 */
	Texture makeTexture();

}
