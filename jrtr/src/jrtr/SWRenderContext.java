package jrtr;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.vecmath.Color3f;
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
	private List<Color3f> colors;
	private float[][] zBuffer;

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
		viewMat.setM03((width - 1) / 2);
		viewMat.setM13((height - 1) / 2);
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
		colors = new ArrayList<Color3f>();
		zBuffer = new float[vWidth][vHeight];
		for (int i = 0; i < vHeight; i++)
			for (int j = 0; j < vWidth; j++)
				zBuffer[i][j] = Float.MAX_VALUE;

		projection(vertDat);
		raster();

		System.out.println("finished");
	}

	private void raster() {
		for (int i = 0; i < edges.size(); i++) {
			Color3f aCol = colors.get(i);
			Vector4f a = edges.get(i++);
			Color3f bCol = colors.get(i);
			Vector4f b = edges.get(i++);
			Color3f cCol = colors.get(i);
			Vector4f c = edges.get(i);

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
				if (pos(a) && pos(b) && pos(c)) {
					// do bounding box
					float leftx = min(a.getX(), b.getX(), c.getX());
					float rightx = max(a.getX(), b.getX(), c.getX());
					float bottomy = min(a.getY(), b.getY(), c.getY());
					float topy = max(a.getY(), b.getY(), c.getY());
					// System.out.println("Left: " + leftx + " Right: " +
					// rightx);
					// System.out.println("Top: " + topy + " Bottom: " +
					// bottomy);
					drawInBox(edge, leftx, bottomy, rightx, topy, a, b, c,
							aCol);
				} else {
					drawInBox(edge, 0, 0, vWidth, vHeight, a, b, c, aCol);
					System.out.println("a b c not pos");
				}
		}
	}

	private void drawInBox(Matrix3f edge, float leftx, float bottomy, float rightx,
			float topy, Vector4f a, Vector4f b, Vector4f c, Color3f col) {
		for (float x = leftx; x < rightx; x++)
			for (float y = bottomy; y < topy; y++) {
				float zSlope = (1 / b.w - 1 / a.w) / (b.x - a.x);
				float z = a.w + (x - a.x) * zSlope;
				float alpha = edge.m00 * x / z + edge.m10 * y / z + edge.m20;
				float beta = edge.m01 * x / z + edge.m11 * y / z + edge.m21;
				float gamma = edge.m02 * x / z + edge.m12 * y / z + edge.m22;

				if (alpha > 0 && beta > 0 && gamma > 0)
					drawPixel((int) x, (int) y, 1 / z, col);
			}

	}

	private void drawPixel(int x, int y, float z, Color3f col) {
		if (x < 0 || y < 0 || x > vWidth - 1 || y > vHeight - 1)
			return;
		if (zBuffer[x][y] < z)
			return;
		zBuffer[x][y] = z;
		try {
			int color = (int) (255 * col.x) << 16 | (int) (255 * col.y) << 8
					| (int) (255 * col.z);
			colorBuffer.setRGB(x, vHeight - y, color);
		} catch (ArrayIndexOutOfBoundsException exc) {
			System.out.println("Out of Bounds: x=" + x + " y=" + y + " z=" + z);
		}

	}

	private boolean pos(Vector4f a) {
		return a.getX() > 0 && a.getY() > 0;
	}

	private float min(float a, float b, float c) {
		if (a < b && a < c)
			return a;
		if (b < a && b < c)
			return b;
		return c;
	}

	private float max(float a, float b, float c) {
		if (a > b && a > c)
			return a;
		if (b > a && b > c)
			return b;
		return c;
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
				} else if (e.getSemantic() == VertexData.Semantic.COLOR) {
					Color3f col = new Color3f(e.getData()[k * 3], e.getData()[k * 3 + 1],
							e.getData()[k * 3 + 2]);
					colors.add(col);
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
