package jrtr;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector4f;

import jrtr.VertexData.VertexElement;

/**
 * A skeleton for a software renderer. It works in combination with
 * {@link SWRenderPanel}, which displays the output image. In project 3 you will
 * implement your own rasterizer in this class.
 * <p>
 * To use the software renderer, you will simply replace {@link GLRenderPanel}
 * with {@link SWRenderPanel} in the user application.
 */
public class SWRenderContext implements RenderContext {

	private SceneManagerInterface sceneManager;
	private BufferedImage colorBuffer;
	private Matrix4f viewMat;
	private int vHeight, vWidth;

	@Override
	public void setSceneManager(SceneManagerInterface sceneManager) {
		this.sceneManager = sceneManager;
	}

	/**
	 * This is called by the SWRenderPanel to render the scene to the software
	 * frame buffer.
	 */
	public void display() {
		if (sceneManager == null)
			return;

		beginFrame();

		SceneManagerIterator iterator = sceneManager.iterator();
		while (iterator.hasNext())
			draw(iterator.next());

		endFrame();
	}

	/**
	 * This is called by the {@link SWJPanel} to obtain the color buffer that
	 * will be displayed.
	 */
	public BufferedImage getColorBuffer() {
		return colorBuffer;
	}

	/**
	 * Set a new viewport size. The render context will also need to store a
	 * viewport matrix, which you need to reset here.
	 */
	public void setViewportSize(int width, int height) {
		vWidth = width;
		vHeight = height;
		viewMat = new Matrix4f();
		viewMat.setIdentity();
		viewMat.setM00(width / 2);
		viewMat.setM11(height / 2);
		viewMat.setM22(1 / 2);
		viewMat.setM03((width - 1) / 2);
		viewMat.setM13((height - 1) / 2);
		viewMat.setM23(1 / 2);
		colorBuffer = new BufferedImage(width, height,
				BufferedImage.TYPE_3BYTE_BGR);
	}

	/**
	 * Clear the framebuffer here.
	 */
	private void beginFrame() {
	}

	private void endFrame() {
	}

	/**
	 * The main rendering method. You will need to implement this to draw 3D
	 * objects.
	 */
	private void draw(RenderItem renderItem) {
		LinkedList<VertexElement> vertexEle = renderItem.getShape()
				.getVertexData().getElements();
		int[] index = renderItem.getShape().getVertexData().getIndices();
		if (index == null)
			return;
		Matrix4f temp = new Matrix4f();
		temp.set(viewMat);
		temp.mul(Frustum.getProjectionMatrix());
		temp.mul(Camera.getCameraMatrix());
		Vector4f vec;
		for (int k : index) {
			ListIterator<VertexData.VertexElement> itr = vertexEle
					.listIterator(0);
			while (itr.hasNext()) {
				VertexData.VertexElement e = itr.next();
				if (e.getSemantic() == VertexData.Semantic.POSITION) {
					float x = e.getData()[k * 3];
					float y = e.getData()[k * 3 + 1];
					float z = e.getData()[k * 3 + 2];
					vec = new Vector4f(x, y, z, 1);
					temp.transform(vec);

					vec.setX(vec.getX() / vec.getW());
					vec.setY(vec.getY() / vec.getW());
					if (vec.getX() > 0 && vec.getY() > 0 && vec.getX() < vWidth
							&& vec.getY() < vHeight)
						colorBuffer.setRGB((int) vec.getX(), vHeight
								- (int) vec.getY(), Color.white.getRGB());
				}
			}
		}
	}

	/**
	 * Does nothing. We will not implement shaders for the software renderer.
	 */
	@Override
	public Shader makeShader() {
		return new SWShader();
	}

	/**
	 * Does nothing. We will not implement textures for the software renderer.
	 */
	@Override
	public Texture makeTexture() {
		return new SWTexture();
	}
}
