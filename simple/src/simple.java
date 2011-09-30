import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.vecmath.Matrix4f;

import jrtr.GLRenderPanel;
import jrtr.RenderContext;
import jrtr.RenderPanel;
import jrtr.SWRenderPanel;
import jrtr.Shape;
import jrtr.SimpleSceneManager;
import jrtr.VertexData;

/**
 * Implements a simple application that opens a 3D rendering window and shows a
 * rotating cube.
 */
public class simple {
	static RenderPanel renderPanel;
	static RenderContext renderContext;
	static SimpleSceneManager sceneManager;
	static Shape shape;
	static float angle;

	static float[] zylinderVertex;
	static float[] zylinderColors;
	static int[] zylinderFaces;

	static float[] torusVertex;
	static float[] torusColors;
	static int[] torusFaces;

	/**
	 * An extension of {@link GLRenderPanel} or {@link SWRenderPanel} to provide
	 * a call-back function for initialization.
	 */
	public final static class SimpleRenderPanel extends GLRenderPanel {
		/**
		 * Initialization call-back. We initialize our renderer here.
		 * 
		 * @param r
		 *            the render context that is associated with this render
		 *            panel
		 */
		@Override
		public void init(RenderContext r) {

			renderContext = r;
			renderContext.setSceneManager(sceneManager);

			// Register a timer task
			Timer timer = new Timer();
			angle = 0.01f;
			timer.scheduleAtFixedRate(new AnimationTask(), 0, 10);
		}
	}

	/**
	 * A timer task that generates an animation. This task triggers the
	 * redrawing of the 3D scene every time it is executed.
	 */
	public static class AnimationTask extends TimerTask {
		@Override
		public void run() {
			// Update transformation
			Matrix4f t = shape.getTransformation();
			Matrix4f rotX = new Matrix4f();
			rotX.rotX(angle);
			Matrix4f rotY = new Matrix4f();
			rotY.rotY(angle);
			t.mul(rotX);
			t.mul(rotY);
			shape.setTransformation(t);

			// Trigger redrawing of the render window
			renderPanel.getCanvas().repaint();
		}
	}

	/**
	 * A mouse listener for the main window of this application. This can be
	 * used to process mouse events.
	 */
	public static class SimpleMouseListener implements MouseListener {
		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}
	}

	/**
	 * The main function opens a 3D rendering window, constructs a simple 3D
	 * scene, and starts a timer task to generate an animation.
	 */
	public static void main(String[] args) {

		int seg = 8;
		int mainRad = 1;
		int rad = 1;
		calcZylinderVertex(seg);
		calcZylinderColors(seg);
		calcZylinderFaces(seg);

		calcTorusVertex(seg, mainRad, rad);
		calcTorusColors(seg);
		calcTorusFaces(seg);

		// Make a simple geometric object: a cube
		// The vertex positions of the cube
		float cubeVertex[] = { -1, -1, 1, 1, -1, 1, 1, 1, 1, -1, 1, 1, // front face
				-1, -1, -1, -1, -1, 1, -1, 1, 1, -1, 1, -1, // left face
				1, -1, -1, -1, -1, -1, -1, 1, -1, 1, 1, -1, // back face
				1, -1, 1, 1, -1, -1, 1, 1, -1, 1, 1, 1, // right face
				1, 1, 1, 1, 1, -1, -1, 1, -1, -1, 1, 1, // top face
				-1, -1, 1, -1, -1, -1, 1, -1, -1, 1, -1, 1 }; // bottom face

		// The vertex colors
		float cubeColors[] = { 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0,
				1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0,
				1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0,
				0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1 };


		// Construct a data structure that stores the vertices, their
		// attributes, and the triangle mesh connectivity
		// cube = 24
		// zylinder = 2 * seg
		// torus = seg * seg

		VertexData vertexData = new VertexData(seg * seg);
		vertexData.addElement(torusColors, VertexData.Semantic.COLOR, 3);
		vertexData.addElement(torusVertex, VertexData.Semantic.POSITION, 3);

		// The triangles (three vertex indices for each triangle)
		int cubeFaces[] = { 0, 2, 3, 0, 1, 2, // front face
				4, 6, 7, 4, 5, 6, // left face
				8, 10, 11, 8, 9, 10, // back face
				12, 14, 15, 12, 13, 14, // right face
				16, 18, 19, 16, 17, 18, // top face
				20, 22, 23, 20, 21, 22 }; // bottom face


		vertexData.addIndices(torusFaces);

		// Make a scene manager and add the object
		sceneManager = new SimpleSceneManager();
		shape = new Shape(vertexData);
		sceneManager.addShape(shape);

		// Make a render panel. The init function of the renderPanel
		// (see above) will be called back for initialization.
		renderPanel = new SimpleRenderPanel();

		// Make the main window of this application and add the renderer to it
		JFrame jframe = new JFrame("simple");
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null); // center of screen
		jframe.getContentPane().add(renderPanel.getCanvas());// put the canvas
		// into a JFrame window

		// Add a mouse listener
		jframe.addMouseListener(new SimpleMouseListener());

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true); // show window
	}

	private static void calcTorusVertex(int seg, int mainRadius, int radius) {
		float[] circle = new float[seg*3];
		float[] torVert = new float[3 * seg * seg];
		int i = 0;
		while (i < seg) {
			circle[3 * i] = (float) Math.cos(i * 2 * Math.PI / seg) * radius
			+ mainRadius;
			circle[3 * i + 1] = (float) Math.sin(i * 2 * Math.PI / seg)
			* radius;
			circle[3 * i + 2] = 0;
			i++;
		}
		float cos, sin;
		i = 0;
		while (i < seg) {
			int k = 0;
			while (k < seg) {
				cos = (float) Math.cos(i * 2 * Math.PI / seg);
				sin = (float) Math.sin(i * 2 * Math.PI / seg);
				torVert[3 * k + 3 * i * seg] = circle[3 * k] * cos
				- circle[3 * i + 2] * sin;
				torVert[3 * k + 3 * i * seg + 1] = circle[3 * k + 1];
				torVert[3 * k + 3 * i * seg + 2] = circle[3 * k] * sin
				+ circle[3 * i + 2] * cos;
				k++;
			}
			i++;
		}
		for (int j = 0; j < 3 * seg * seg; j = j + 3)
			System.out.println(torVert[j] + " " + torVert[j + 1] + " "
					+ torVert[j + 2]);
		torusVertex = torVert;
	}

	private static void calcTorusColors(int seg) {
		float[] torCol = new float[3 * seg * seg];
		int i = 0;
		while (i < seg * seg) {
			torCol[3 * i] = 1;
			if (i % 2 == 0) {
				torCol[3 * i + 1] = 1;
				torCol[3 * i + 2] = 0;
			} else {
				torCol[3 * i + 1] = 0;
				torCol[3 * i + 2] = 1;
			}
			i++;
		}
		torusColors = torCol;

		for (int j = 0; j < 3 * seg * seg; j = j + 3)
			System.out.println(torCol[j] + " " + torCol[j + 1] + " "
					+ torCol[j + 2]);

	}

	private static void calcTorusFaces(int seg) {
		int[] torFac = new int[3];
		torFac[0] = 0;
		torFac[1] = 1;
		torFac[2] = 2;
		torusFaces = torFac;
	}

	private static void calcZylinderVertex(int Segments) {
		float[] zylVert = new float[6 * Segments];
		int i = 0;
		while (i < Segments) { // top circle
			zylVert[3 * i] = (float) Math.sin(i * 2 * Math.PI / Segments);
			zylVert[3 * i + 1] = 1;
			zylVert[3 * i + 2] = (float) Math.cos(i * 2 * Math.PI / Segments);
			i++;
		}
		int j = Segments;
		while (j < Segments * 2) { // bottom circle
			zylVert[3 * j] = (float) Math.sin(j * 2 * Math.PI / Segments);
			zylVert[3 * j + 1] = -1;
			zylVert[3 * j + 2] = (float) Math.cos(j * 2 * Math.PI / Segments);
			j++;
		}

		/*for (int k = 0; k < 6 * Segments; k = k + 3)
			System.out.println(zylVert[k] + " " + zylVert[k + 1] + " "
					+ zylVert[k + 2]);
		 */
		zylinderVertex = zylVert;
	}

	private static void calcZylinderColors(int Segments) {
		float[] zylCol = new float[6 * Segments];
		int i = 0;
		while (i < Segments * 2) {
			zylCol[3 * i] = 0;
			if (i % 3 == 0) {
				zylCol[3 * i + 1] = 0;
				zylCol[3 * i + 2] = 1;
			} else if (i % 3 == 1) {
				zylCol[3 * i + 1] = 1;
				zylCol[3 * i + 2] = 0;
			} else {
				zylCol[3 * i + 1] = 1;
				zylCol[3 * i + 2] = 1;
			}
			i++;
		}
		zylinderColors = zylCol;
		/*for (int j = 0; j < 6 * Segments; j = j + 3)
			System.out.println(zylCol[j] + " " + zylCol[j + 1] + " "
					+ zylCol[j + 2]);
		 */
	}

	private static void calcZylinderFaces(int Segments) {
		int[] zylFac = new int[12 * Segments - 12];
		int i = 0;
		while (i < Segments - 2) { // Top face
			zylFac[3 * i] = 0;
			zylFac[3 * i + 1] = i + 1;
			zylFac[3 * i + 2] = i + 2;
			i++;
		}
		while (i < 2 * Segments - 4) { // bottom face
			zylFac[3 * i] = Segments;
			zylFac[3 * i + 1] = i + 3;
			zylFac[3 * i + 2] = i + 4;
			i++;
		}
		int j = 0;
		while (j < Segments - 1) { // side face one bottom two top
			zylFac[3 * i] = j;
			zylFac[3 * i + 1] = j + Segments;
			zylFac[3 * i + 2] = j + 1 + Segments;
			i++;
			j++;
		}
		zylFac[3 * i] = j;
		zylFac[3 * i + 1] = j + Segments;
		zylFac[3 * i + 2] = Segments;
		i++;
		j = 0;
		while (j < Segments - 1) { // side face one top two bottom
			zylFac[3 * i] = j + Segments + 1;
			zylFac[3 * i + 1] = j;
			zylFac[3 * i + 2] = j + 1;
			i++;
			j++;
		}
		zylFac[3 * i] = Segments;
		zylFac[3 * i + 1] = 0;
		zylFac[3 * i + 2] = Segments - 1;

		/*for (int k = 0; k < 12 * Segments - 12; k = k + 3)
			System.out.println(zylFac[k] + " " + zylFac[k + 1] + " "
					+ zylFac[k + 2]);
		 */
		zylinderFaces = zylFac;
	}

}
