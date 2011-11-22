package jrtr;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import jrtr.Light.Type;

/**
 * This class implements a {@link RenderContext} (a renderer) using OpenGL version 3 (or later).
 */
public class GLRenderContext implements RenderContext {

	private SceneManagerInterface sceneManager;
	private GL3 gl;
	private GLShader activeShader;

	/**
	 * This constructor is called by {@link GLRenderPanel}.
	 * 
	 * @param drawable 	the OpenGL rendering context. All OpenGL calls are
	 * 					directed to this object.
	 */
	/*public GLRenderContext(GLAutoDrawable drawable)
	{
		gl = drawable.getGL().getGL3();
		gl.glEnable(GL3.GL_DEPTH_TEST);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// Load and use default shader
		GLShader defaultShader = new GLShader(gl);
		try {
			//defaultShader.load("../jrtr/shaders/normal.vert", "../jrtr/shaders/normal.frag");
			defaultShader.load("../jrtr/shaders/default.vert", "../jrtr/shaders/default.frag");
		} catch(Exception e) {
			System.out.print("Problem with shader:\n");
			System.out.print(e.getMessage());
		}
		defaultShader.use();
		activeShader = defaultShader;
	}*/

	public GLRenderContext(GLAutoDrawable drawable)
	{
		gl = drawable.getGL().getGL3();
		gl.glEnable(GL3.GL_DEPTH_TEST);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// Load and use default shader
		GLShader defaultShader = new GLShader(gl);
		try {
			defaultShader.load("../jrtr/shaders/texphong.vert", "../jrtr/shaders/texphong.frag");
		} catch (Exception e) {
			System.out.print("Problem with shader:\n");
			System.out.print(e.getMessage());
		}
		defaultShader.use();
		activeShader = defaultShader;

		try {
			gl.glActiveTexture(GL3.GL_TEXTURE0);
			GLTexture plant = new GLTexture(gl);
			plant.load("../jrtr/textures/plant.jpg");
			gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
			gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
			int id1 = gl.glGetUniformLocation(activeShader.programId(), "plant");
			gl.glUniform1i(id1, 0);

			gl.glActiveTexture(GL3.GL_TEXTURE1);
			GLTexture gloss = new GLTexture(gl);
			gloss.load("../jrtr/textures/gloss.jpg");
			gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
			gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
			int id2 = gl.glGetUniformLocation(activeShader.programId(), "gloss");
			gl.glUniform1i(id2, 1);
		} catch (Exception e) {
			System.out.print("Could not load texture\n");
		}
	}


	/**
	 * Set the scene manager. The scene manager contains the 3D
	 * scene that will be rendered. The scene includes geometry
	 * as well as the camera and viewing frustum.
	 */
	@Override
	public void setSceneManager(SceneManagerInterface sceneManager)
	{
		this.sceneManager = sceneManager;
	}

	/**
	 * This method is called by the GLRenderPanel to redraw the 3D scene.
	 * The method traverses the scene using the scene manager and passes
	 * each object to the rendering method.
	 */
	public void display(GLAutoDrawable drawable)
	{
		gl = drawable.getGL().getGL3();

		beginFrame();

		SceneManagerIterator iterator = sceneManager.iterator();
		while(iterator.hasNext())
		{
			RenderItem r = iterator.next();
			if(r.getShape()!=null) draw(r);
		}

		endFrame();
	}

	/**
	 * This method is called at the beginning of each frame, i.e., before
	 * scene drawing starts.
	 */
	private void beginFrame()
	{
		setLights();

		gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL3.GL_DEPTH_BUFFER_BIT);
	}

	/**
	 * This method is called at the end of each frame, i.e., after
	 * scene drawing is complete.
	 */
	private void endFrame()
	{
		gl.glFlush();
	}

	/**
	 * Convert a Matrix4f to a float array in column major ordering,
	 * as used by OpenGL.
	 */
	private float[] matrix4fToFloat16(Matrix4f m)
	{
		float[] f = new float[16];
		for(int i=0; i<4; i++)
			for(int j=0; j<4; j++)
				f[j*4+i] = m.getElement(i,j);
		return f;
	}

	/**
	 * The main rendering method.
	 * 
	 * @param renderItem	the object that needs to be drawn
	 */
	private void draw(RenderItem renderItem)
	{
		VertexData vertexData = renderItem.getShape().getVertexData();
		LinkedList<VertexData.VertexElement> vertexElements = vertexData.getElements();
		int indices[] = vertexData.getIndices();

		// Don't draw if there are no indices
		if(indices == null) return;

		// Set the material
		setMaterial(renderItem.getShape().getMaterial());

		// Compute the modelview matrix by multiplying the camera matrix and the
		// transformation matrix of the object
		Matrix4f t = new Matrix4f();
		t.set(sceneManager.getCamera().getCameraMatrix());
		t.mul(renderItem.getT());

		// Set modelview and projection matrices in shader
		gl.glUniformMatrix4fv(gl.glGetUniformLocation(activeShader.programId(), "modelview"), 1, false, matrix4fToFloat16(t), 0);
		gl.glUniformMatrix4fv(gl.glGetUniformLocation(activeShader.programId(), "projection"), 1, false, matrix4fToFloat16(sceneManager.getFrustum().getProjectionMatrix()), 0);

		// Steps to pass vertex data to OpenGL:
		// 1. For all vertex attributes (position, normal, etc.)
		// Copy vertex data into float buffers that can be passed to OpenGL
		// Make/bind vertex buffer objects
		// Tell OpenGL which "in" variable in the shader corresponds to the current attribute
		// 2. Render vertex buffer objects
		// 3. Clean up
		// Note: Of course it would be more efficient to store the vertex buffer objects (VBOs) in a
		// vertex array object (VAO), and simply bind ("load") the VAO each time to render. But this
		// requires a bit more logic in the rendering engine, so we render every time "from scratch".

		// 1. For all vertex attributes, make vertex buffer objects
		IntBuffer vboBuffer = IntBuffer.allocate(vertexElements.size());
		gl.glGenBuffers(vertexElements.size(), vboBuffer);
		ListIterator<VertexData.VertexElement> itr = vertexElements.listIterator(0);
		while(itr.hasNext())
		{
			// Copy vertex data into float buffer
			VertexData.VertexElement e = itr.next();
			int dim = e.getNumberOfComponents();

			FloatBuffer varr = FloatBuffer.allocate(indices.length * dim);
			for (int indice : indices)
				for (int j = 0; j < dim; j++)
					varr.put(e.getData()[dim * indice + j]);

			// Make vertex buffer object
			gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vboBuffer.get());
			// Upload vertex data
			gl.glBufferData(GL3.GL_ARRAY_BUFFER, varr.array().length * 4, FloatBuffer.wrap(varr.array()), GL3.GL_DYNAMIC_DRAW);

			// Tell OpenGL which "in" variable in the vertex shader corresponds to the current vertex buffer object
			// We use our own convention to name the variables, i.e., "position", "normal", "color", "texcoord", or others if necessary
			int attribIndex = -1;
			if(e.getSemantic() == VertexData.Semantic.POSITION)
				attribIndex = gl.glGetAttribLocation(activeShader.programId(), "position");
			else if(e.getSemantic() == VertexData.Semantic.NORMAL)
				attribIndex = gl.glGetAttribLocation(activeShader.programId(), "normal");
			else if(e.getSemantic() == VertexData.Semantic.COLOR)
				attribIndex  = gl.glGetAttribLocation(activeShader.programId(), "color");
			else if(e.getSemantic() == VertexData.Semantic.TEXCOORD)
				attribIndex = gl.glGetAttribLocation(activeShader.programId(), "texcoord");
			gl.glVertexAttribPointer(attribIndex, dim, GL3.GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(attribIndex);
		}

		// 3. Render the vertex buffer objects
		gl.glDrawArrays(GL3.GL_TRIANGLES, 0, indices.length);

		// 4. Clean up
		gl.glDeleteBuffers(vboBuffer.array().length, vboBuffer.array(), 0);

		cleanMaterial(renderItem.getShape().getMaterial());
	}

	/**
	 * Pass the material properties to OpenGL, including textures and shaders.
	 * 
	 * To be implemented in the "Textures and Shading" project.
	 */
	private void setMaterial(Material m) {
		if (m == null)
			return;

		Vector3f kd = m.getMatColor();
		int idKD = gl.glGetUniformLocation(activeShader.programId(), "kd");
		gl.glUniform3f(idKD, kd.getX(), kd.getY(), kd.getZ());

		Vector3f ka = m.getAmbient();
		int idKA = gl.glGetUniformLocation(activeShader.programId(), "ka");
		gl.glUniform3f(idKA, ka.getX(), ka.getY(), ka.getZ());

		Vector3f ks = m.getSpecular();
		int idKS = gl.glGetUniformLocation(activeShader.programId(), "ks");
		gl.glUniform3f(idKS, ks.getX(), ks.getY(), ks.getZ());

		float phong = m.getPhong();
		int idPhong = gl.glGetUniformLocation(activeShader.programId(), "phong");
		gl.glUniform1f(idPhong, phong);

		if (m.getShader() != null) {
			m.getShader().use();
			activeShader = (GLShader) m.getShader();
			setLights();
		}
	}

	/**
	 * Pass the light properties to OpenGL. This assumes the list of lights in
	 * the scene manager is accessible via a method Iterator<Light> lightIterator().
	 * 
	 * To be implemented in the "Textures and Shading" project.
	 */
	void setLights() {
		Iterator<Light> itr = sceneManager.lightIterator();
		int i = 0;
		float[] dirArray = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		float[] posiArray = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		float[] radiArray = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int[] typeArray = { 0, 0, 0, 0, 0 };
		while (itr.hasNext() && i < 5) {
			Light l = itr.next();
			Vector3f ca = l.getAmbient();
			int idCA = gl.glGetUniformLocation(activeShader.programId(), "ca");
			gl.glUniform3f(idCA, ca.getX(), ca.getY(), ca.getZ());

			Vector3f rad = l.getRadiance();
			radiArray[4 * i] = rad.getX();
			radiArray[4 * i + 1] = rad.getY();
			radiArray[4 * i + 2] = rad.getZ();
			radiArray[4 * i + 3] = 0;

			if (l.getType() == Type.Directional) {
				Vector3f dir = l.getDirection();
				dirArray[4 * i] = dir.getX();
				dirArray[4 * i + 1] = dir.getY();
				dirArray[4 * i + 2] = dir.getZ();
				dirArray[4 * i + 3] = 0;
				typeArray[i] = 1;
			}

			if (l.getType() == Type.Point) {
				Vector3f pos = l.getPosition();
				posiArray[4 * i] = pos.getX();
				posiArray[4 * i + 1] = pos.getY();
				posiArray[4 * i + 2] = pos.getZ();
				posiArray[4 * i + 3] = 0;
				typeArray[i] = 2;
			}

			i++;
		}
		int numOfElements = 5;

		int idDir = gl.glGetUniformLocation(activeShader.programId(), "lightDirection");
		gl.glUniform4fv(idDir, numOfElements, dirArray, 0);

		int idRad = gl.glGetUniformLocation(activeShader.programId(), "radiance");
		gl.glUniform4fv(idRad, numOfElements, radiArray, 0);

		int idPos = gl.glGetUniformLocation(activeShader.programId(), "posLight");
		gl.glUniform4fv(idPos, numOfElements, posiArray, 0);

		int idTyp = gl.glGetUniformLocation(activeShader.programId(), "type");
		gl.glUniform1iv(idTyp, numOfElements, typeArray, 0);
	}

	/**
	 * Disable a material.
	 * 
	 * To be implemented in the "Textures and Shading" project.
	 */
	private void cleanMaterial(Material m)
	{
	}

	@Override
	public Shader makeShader()
	{
		return new GLShader(gl);
	}

	@Override
	public Texture makeTexture()
	{
		return new GLTexture(gl);
	}

}
