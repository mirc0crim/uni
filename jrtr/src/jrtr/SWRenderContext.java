package jrtr;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.vecmath.Matrix3f;
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
	private List<Vector4f> edges;
	private int[][] zBuffer;

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
		colorBuffer = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
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
		VertexData vertDat = renderItem.getShape().getVertexData();
		edges = new ArrayList<Vector4f>();
		zBuffer = new int[vWidth][vHeight];
		for (int i = 0; i < vHeight; i++)
			for (int j = 0; j < vWidth; j++)
				zBuffer[i][j] = Integer.MAX_VALUE;

		projection(vertDat);
		raster(vertDat);
	}

	private void raster(VertexData dat) {
		for (int i = 0; i < edges.size(); i++) {
			Vector4f a = edges.get(i++);
			Vector4f b = edges.get(i++);
			Vector4f c = edges.get(i++);

			// edge function
			Matrix3f edge = new Matrix3f();
			boolean invertable = true;
			edge.setM00(a.getX());
			edge.setM01(a.getY());
			edge.setM02(a.getW());
			edge.setM10(b.getX());
			edge.setM11(b.getY());
			edge.setM12(b.getW());
			edge.setM20(c.getX());
			edge.setM21(c.getY());
			edge.setM22(c.getW());
			try {
				edge.invert();
			} catch (Exception e) {
				invertable = false;
			}

			if (invertable)
				if (pos(a) && pos(b) && pos(c)){
					// do bounding box
					int leftx = min(a.getX(), b.getX(), c.getX());
					int rightx = max(a.getX(), b.getX(), c.getX());
					int bottomy = min(a.getY(), b.getY(), c.getY());
					int topy = max(a.getY(), b.getY(), c.getY());
					drawInBox(edge, leftx, bottomy, rightx, topy, a, b, c);
				} else
					drawInBox(edge, 0, 0 , vWidth, vHeight, a, b, c);

		}
	}


	private void drawInBox(Matrix3f edge, int leftx, int bottomy, int rightx, int topy,
			Vector4f a, Vector4f b, Vector4f c) {
		for (int i = leftx; i < rightx; i++)
			for (int j = topy; j > bottomy; j--) {
				// alpha beta gamma functions
			}

	}

	private boolean pos(Vector4f a) {
		return a.getX() > 0 && a.getY() > 0;
	}

	private int min(float a, float b, float c) {
		if (a < b && a < c)
			return (int) a;
		if (b < a && b < c)
			return (int) b;
		return (int) c;
	}

	private int max(float a, float b, float c) {
		if (a > b && a > c)
			return (int) a;
		if (b > a && b > c)
			return (int) b;
		return (int) c;
	}


	private void projection(VertexData vertexData) {
		LinkedList<VertexElement> vertexEle = vertexData.getElements();
		int[] index = vertexData.getIndices();
		if (index == null)
			return;
		Matrix4f temp = new Matrix4f();
		temp.set(viewMat);
		temp.mul(Frustum.getProjectionMatrix());
		temp.mul(Camera.getCameraMatrix());
		Vector4f vec;
		for (int k : index) {
			ListIterator<VertexData.VertexElement> itr = vertexEle.listIterator(0);
			while (itr.hasNext()) {
				VertexData.VertexElement e = itr.next();
				if (e.getSemantic() == VertexData.Semantic.POSITION) {
					float x = e.getData()[k * 3];
					float y = e.getData()[k * 3 + 1];
					float z = e.getData()[k * 3 + 2];
					vec = new Vector4f(x, y, z, 1);
					temp.transform(vec);

					edges.add(vec);

					vec.setX(vec.getX() / vec.getW());
					vec.setY(vec.getY() / vec.getW());
					if (vec.getX() > 0 && vec.getY() > 0 && vec.getX() < vWidth
							&& vec.getY() < vHeight)
						colorBuffer.setRGB((int) vec.getX(), vHeight - (int) vec.getY(),
								Color.white.getRGB());
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
