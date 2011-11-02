/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * param = 0 for 1st Exercise
 * param = 1 or 2 for 1st Task of 2nd Exercise
 */

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import jrtr.Camera;
import jrtr.Frustum;
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
public class simple21 {
	static RenderPanel renderPanel;
	static RenderContext renderContext;
	static SimpleSceneManager sceneManager;
	static Shape torusFront;
	static Shape torusBack;
	static Shape zylinder;
	static Shape cubeSeat;
	static Shape cubeSteering;
	static Shape plane;
	static float angle;
	static float currentAngle;
	static int param = 2; // 0=animation, 1=param1, 2=param2

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
			Matrix4f p = plane.getTransformation();
			Matrix4f z = zylinder.getTransformation();
			Matrix4f tF = torusFront.getTransformation();
			Matrix4f tB = torusBack.getTransformation();
			Matrix4f cSeat = cubeSeat.getTransformation();
			Matrix4f cSteering = cubeSteering.getTransformation();
			Matrix4f transAway = new Matrix4f(1, 0, 0, 0, 0, 1, 0, -5, 0, 0, 1, -25, 0, 0, 0, 1);
			Matrix4f transZ = new Matrix4f(1, 0, 0, 0, 0, 1, 0, 2, 0, 0, 1, -10, 0, 0, 0, 1);
			Matrix4f transP = new Matrix4f(1, 0, 0, 0, 0, 1, 0, -2, 0, 0, 1, 0, 0, 0, 0, 1);
			Matrix4f transCSeat = new Matrix4f(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, -10, 0, 0, 0, 1);
			Matrix4f transCSteering = new Matrix4f(1, 0, 0, -3, 0, 1, 0, 1, 0, 0, 1, -10, 0, 0, 0,
					1);
			Matrix4f scaleP = new Matrix4f(3, 0, 0, 0, 0, 1, 0, 0, 0, 0, 3, 0, 0, 0, 0, 1);
			Matrix4f scaleCSeat = new Matrix4f(4, 0, 0, 0, 0, (float) 1.5, 0, 0, 0, 0, 1, 0, 0, 0,
					0, 1);
			Matrix4f scaleDriver = new Matrix4f((float) 3 / 2, 0, 0, 0, 0, 3, 0, 0, 0, 0,
					(float) 3 / 2, 0, 0, 0, 0, 1);
			Matrix4f scaleCSteering = new Matrix4f(1, 0, 0, 0, 0, 3, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
			Matrix4f transTF = new Matrix4f(1, 0, 0, -4, 0, 1, 0, -2, 0, 0, 1, -10, 0, 0, 0, 1);
			Matrix4f transTB = new Matrix4f(1, 0, 0, 4, 0, 1, 0, -2, 0, 0, 1, -10, 0, 0, 0, 1);
			Matrix4f rotX = new Matrix4f();
			rotX.rotX((float) Math.PI / 2);
			Matrix4f rotZ = new Matrix4f();
			rotZ.rotZ((float) Math.PI / 4);
			Matrix4f rotZi = new Matrix4f();
			rotZi.rotZ((float) Math.PI / -4);
			p.mul(transAway);
			p.mul(transP);
			p.mul(scaleP);
			cSeat.mul(transAway);
			cSeat.mul(transCSeat);
			cSeat.mul(scaleCSeat);
			cSteering.mul(transAway);
			cSteering.mul(transCSteering);
			cSteering.mul(scaleCSteering);
			z.mul(transAway);
			z.mul(transZ);
			z.mul(rotZ);
			z.mul(scaleDriver);
			tF.mul(transAway);
			tF.mul(transTF);
			tF.mul(rotX);
			tB.mul(transAway);
			tB.mul(transTB);
			tB.mul(rotX);
			plane.setTransformation(p);
			torusFront.setTransformation(tF);
			torusBack.setTransformation(tB);
			cubeSeat.setTransformation(cSeat);
			cubeSteering.setTransformation(cSteering);
			zylinder.setTransformation(z);
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
			Matrix4f z = zylinder.getTransformation();
			Matrix4f tF = torusFront.getTransformation();
			Matrix4f tB = torusBack.getTransformation();
			Matrix4f cSeat = cubeSeat.getTransformation();
			Matrix4f cSteering = cubeSteering.getTransformation();
			Matrix4f rotY = new Matrix4f();
			Matrix4f rotYi = new Matrix4f();
			Matrix4f rotX = new Matrix4f();
			Matrix4f rotXi = new Matrix4f();
			Matrix4f rotZ = new Matrix4f();
			Matrix4f rotZ2 = new Matrix4f();
			Matrix4f rotZi = new Matrix4f();
			Matrix4f transZ = new Matrix4f(1, 0, 0, 0, 0, 1, 0, -2, 0, 0, 1, 10, 0, 0, 0, 1);
			Matrix4f transZi = new Matrix4f(1, 0, 0, 0, 0, 1, 0, 2, 0, 0, 1, -10, 0, 0, 0, 1);
			Matrix4f transCSeat = new Matrix4f(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 10, 0, 0, 0, 1);
			Matrix4f transCSeati = new Matrix4f(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, -10, 0, 0, 0, 1);
			Matrix4f transCSteering = new Matrix4f(1, 0, 0, 3, 0, 1, 0, 0, 0, 0, 1, 10, 0, 0, 0, 1);
			Matrix4f transCSteeringi = new Matrix4f(1, 0, 0, -3, 0, 1, 0, 0, 0, 0, 1, -10, 0, 0, 0,
					1);
			Matrix4f transTF = new Matrix4f(1, 0, 0, -4, 0, 1, 0, -2, 0, 0, 1, -10, 0, 0, 0, 1);
			Matrix4f transTFi = new Matrix4f(1, 0, 0, 4, 0, 1, 0, 2, 0, 0, 1, 10, 0, 0, 0, 1);
			Matrix4f transTB = new Matrix4f(1, 0, 0, 4, 0, 1, 0, -2, 0, 0, 1, -10, 0, 0, 0, 1);
			Matrix4f transTBi = new Matrix4f(1, 0, 0, -4, 0, 1, 0, 2, 0, 0, 1, 10, 0, 0, 0, 1);
			Matrix4f scaleCSeat = new Matrix4f((float) 1 / 4, 0, 0, 0, 0, (float) 2 / 3, 0, 0, 0,
					0, 1, 0, 0, 0, 0, 1);
			Matrix4f scaleCSeati = new Matrix4f(4, 0, 0, 0, 0, (float) 1.5, 0, 0, 0, 0, 1, 0, 0, 0,
					0, 1);
			Matrix4f scaleCSteering = new Matrix4f(1, 0, 0, 0, 0, (float) 1 / 3, 0, 0, 0, 0, 1, 0,
					0, 0, 0, 1);
			Matrix4f scaleCSteeringi = new Matrix4f(1, 0, 0, 0, 0, 3, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
			Matrix4f scaleDriver = new Matrix4f((float) 2 / 3, 0, 0, 0, 0, (float) 1 / 3, 0, 0, 0,
					0, (float) 2 / 3, 0, 0, 0, 0, 1);
			Matrix4f scaleDriveri = new Matrix4f((float) 3 / 2, 0, 0, 0, 0, 3, 0, 0, 0, 0,
					(float) 3 / 2, 0, 0, 0, 0, 1);
			rotY.rotY(angle);
			rotYi.rotY(-angle);
			rotX.rotX((float) (Math.PI / -2));
			rotXi.rotX((float) (Math.PI / 2));
			rotZ.rotZ((float) Math.PI / -4);
			rotZ2.rotZ(-angle);
			rotZi.rotZ((float) Math.PI / 4);
			Matrix4f rotBack = new Matrix4f();
			Matrix4f rot = new Matrix4f();
			rotBack.rotY(-currentAngle);
			rot.rotY(currentAngle + angle * 3);


			cSeat.mul(scaleCSeat);
			cSeat.mul(transCSeat);
			cSeat.mul(rotY);
			cSeat.mul(transCSeati);
			cSeat.mul(scaleCSeati);

			cSteering.mul(scaleCSteering);
			cSteering.mul(transCSteering);
			cSteering.mul(rotY);
			cSteering.mul(transCSteeringi);
			cSteering.mul(scaleCSteeringi);

			tF.mul(rotBack);
			tF.mul(rotX);
			tF.mul(transTFi);
			tF.mul(rotY);
			tF.mul(transTF);
			tF.mul(rotXi);
			tF.mul(rot);

			tB.mul(rotBack);
			tB.mul(rotX);
			tB.mul(transTBi);
			tB.mul(rotY);
			tB.mul(transTB);
			tB.mul(rotXi);
			tB.mul(rot);

			z.mul(scaleDriver);
			z.mul(rotZ);
			z.mul(transZ);
			z.mul(rotY);
			z.mul(transZi);
			z.mul(rotZi);
			z.mul(scaleDriveri);

			torusFront.setTransformation(tF);
			torusBack.setTransformation(tB);
			zylinder.setTransformation(z);
			cubeSeat.setTransformation(cSeat);
			cubeSteering.setTransformation(cSteering);

			// Trigger redrawing of the render window
			renderPanel.getCanvas().repaint();
			currentAngle += angle * 3;

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

		int seg = 20;
		float mainRad = 2;
		float rad = 1;

		Shape zylinder1 = makeZylinder(seg);
		Shape torusFront1 = makeTorus(seg, mainRad, rad, 0, 0, 0);
		Shape torusBack1 = makeTorus(seg, mainRad, rad, 0, 0, 0);
		Shape house = makeHouse();
		Shape plane1 = makePlane();

		zylinder = zylinder1;
		torusFront = torusFront1;
		torusBack = torusBack1;
		plane = plane1;

		if (param == 1) { // Parameter für Bild 1
			Camera.setCenterOfProjection(new Vector3f(0, 0, 40));
			Camera.setUpVector(new Vector3f(0, 1, 0));
			Camera.setLookAtPoint(new Vector3f(0, 0, 0));
			Frustum.setAspectRatio(1);
			Frustum.setVerticalFOV(60);
			Frustum.setNearPlane(1);
			Frustum.setFarPlane(100);
		}
		if (param == 2) { // Parameter für Bild 2
			Camera.setCenterOfProjection(new Vector3f(-10, 40, 40));
			Camera.setUpVector(new Vector3f(0, 1, 0));
			Camera.setLookAtPoint(new Vector3f(-5, 0, 0));
			Frustum.setAspectRatio(1);
			Frustum.setVerticalFOV(60);
			Frustum.setNearPlane(1);
			Frustum.setFarPlane(100);
		}

		// Make a simple geometric object: a cube
		// The vertex positions of the cube
		float cubeVertex[] = { -1, -1, 1, 1, -1, 1, 1, 1, 1, -1, 1, 1, // front
				// face
				-1, -1, -1, -1, -1, 1, -1, 1, 1, -1, 1, -1, // left face
				1, -1, -1, -1, -1, -1, -1, 1, -1, 1, 1, -1, // back face
				1, -1, 1, 1, -1, -1, 1, 1, -1, 1, 1, 1, // right face
				1, 1, 1, 1, 1, -1, -1, 1, -1, -1, 1, 1, // top face
				-1, -1, 1, -1, -1, -1, 1, -1, -1, 1, -1, 1 }; // bottom face

		// The vertex colors
		float cubeColors[] = { 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1,
				0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1,
				0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1 };


		// Construct a data structure that stores the vertices, their
		// attributes, and the triangle mesh connectivity

		VertexData cubeData = new VertexData(24);
		cubeData.addElement(cubeColors, VertexData.Semantic.COLOR, 3);
		cubeData.addElement(cubeVertex, VertexData.Semantic.POSITION, 3);

		// The triangles (three vertex indices for each triangle)
		int cubeFaces[] = { 0, 2, 3, 0, 1, 2, // front face
				4, 6, 7, 4, 5, 6, // left face
				8, 10, 11, 8, 9, 10, // back face
				12, 14, 15, 12, 13, 14, // right face
				16, 18, 19, 16, 17, 18, // top face
				20, 22, 23, 20, 21, 22 }; // bottom face

		cubeData.addIndices(cubeFaces);

		// Make a scene manager and add the object
		sceneManager = new SimpleSceneManager();
		cubeSeat = new Shape(cubeData);
		cubeSteering = new Shape(cubeData);

		if (param == 0) {
			sceneManager.addShape(zylinder);
			sceneManager.addShape(torusFront);
			sceneManager.addShape(torusBack);
			sceneManager.addShape(cubeSeat);
			sceneManager.addShape(cubeSteering);
			sceneManager.addShape(plane);
		} else
			sceneManager.addShape(house);

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

	private static float[] calcTorusVertex(int seg, float mainRadius, float radius, float x,
			float y, float z) {
		float[] circle = new float[seg * 3];
		float[] torVert = new float[3 * seg * seg];
		int i = 0;
		while (i < seg) {
			circle[3 * i] = (float) Math.cos(i * 2 * Math.PI / seg) * radius + mainRadius;
			circle[3 * i + 1] = (float) Math.sin(i * 2 * Math.PI / seg) * radius;
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
				torVert[3 * k + 3 * i * seg] = circle[3 * k] * cos - circle[3 * i + 2] * sin + x;
				torVert[3 * k + 3 * i * seg + 1] = circle[3 * k + 1] + y;
				torVert[3 * k + 3 * i * seg + 2] = circle[3 * k] * sin + circle[3 * i + 2] * cos
						+ z;
				k++;
			}
			i++;
		}
		return torVert;
	}

	private static float[] calcTorusColors(int seg) {
		float[] torCol = new float[3 * seg * seg];
		int i = 0;
		while (i < seg * seg) {
			torCol[3 * i] = 1;
			if (i % 3 == 0) {
				torCol[3 * i + 1] = 1;
				torCol[3 * i + 2] = 0;
			} else if (i % 3 == 1) {
				torCol[3 * i + 1] = 0;
				torCol[3 * i + 2] = 1;
			} else {
				torCol[3 * i + 1] = 1;
				torCol[3 * i + 2] = 1;
			}
			i++;
		}
		return torCol;

	}

	private static int[] calcTorusFaces(int seg) {
		int[] torFac = new int[6 * seg * seg];
		int k = 0;
		int i = 0;
		int j = 0;
		while (k < seg - 1) {
			j = 0;
			i = 0;
			while (j < seg) { // side face one bottom two top
				torFac[3 * i + 3 * k * seg] = j + k * seg;
				torFac[3 * i + 1 + 3 * k * seg] = j + seg + k * seg;
				if (j != seg - 1)
					torFac[3 * i + 2 + 3 * k * seg] = j + 1 + seg + k * seg;
				else
					torFac[3 * i + 2 + 3 * k * seg] = seg + k * seg;
				i++;
				j++;
			}
			j = 0;
			while (j < seg - 1) { // side face one top two bottom
				torFac[3 * i + 3 * k * seg + 3 * seg * seg] = j + seg + 1 + k * seg;
				torFac[3 * i + 1 + 3 * k * seg + 3 * seg * seg] = j + k * seg;
				torFac[3 * i + 2 + 3 * k * seg + 3 * seg * seg] = j + 1 + k * seg;
				i++;
				j++;
			}
			torFac[3 * i + 3 * k * seg + 3 * seg * seg] = seg + k * seg;
			torFac[3 * i + 1 + 3 * k * seg + 3 * seg * seg] = 0 + k * seg;
			torFac[3 * i + 2 + 3 * k * seg + 3 * seg * seg] = seg - 1 + k * seg;
			k++;
			i++;
		}
		j = 0;
		i = 0;
		while (j < seg) { // last and first segment, one last and two first
			torFac[3 * i + 3 * k * seg] = j + k * seg;
			torFac[3 * i + 1 + 3 * k * seg] = j;
			if (j != seg - 1)
				torFac[3 * i + 2 + 3 * k * seg] = j + 1;
			else
				torFac[3 * i + 2 + 3 * k * seg] = 0;
			j++;
			i++;
		}
		j = 0;
		i = 0;
		k = 0;
		while (j < seg) { // last and first segment, one first and two last
			torFac[3 * i + 3 * k * seg + 3 * seg * seg] = j;
			torFac[3 * i + 1 + 3 * k * seg + 3 * seg * seg] = j + (seg - 1) * seg;
			if (j != 0)
				torFac[3 * i + 2 + 3 * k * seg + 3 * seg * seg] = j - 1 + (seg - 1) * seg;
			else
				torFac[3 * i + 2 + 3 * k * seg + 3 * seg * seg] = seg - 1 + (seg - 1) * seg;
			i++;
			j++;
		}
		return torFac;
	}

	private static float[] calcZylinderVertex(int Segments) {
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
		return zylVert;
	}

	private static float[] calcZylinderColors(int Segments) {
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
		return zylCol;
	}

	private static int[] calcZylinderFaces(int Segments) {
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
		return zylFac;
	}

	public static Shape makeZylinder(int resolution) {
		float[] zylinderVertex;
		float[] zylinderColors;
		int[] zylinderFaces;

		zylinderVertex = calcZylinderVertex(resolution);
		zylinderColors = calcZylinderColors(resolution);
		zylinderFaces = calcZylinderFaces(resolution);

		VertexData vertexData = new VertexData(2 * resolution);
		vertexData.addElement(zylinderVertex, VertexData.Semantic.POSITION, 3);
		vertexData.addElement(zylinderColors, VertexData.Semantic.COLOR, 3);

		vertexData.addIndices(zylinderFaces);

		return new Shape(vertexData);
	}

	public static Shape makeTorus(int resolution, float mainRad, float rad, float x, float y,
			float z) {
		float[] torusVertex;
		float[] torusColors;
		int[] torusFaces;

		torusVertex = calcTorusVertex(resolution, mainRad, rad, x, y, z);
		torusColors = calcTorusColors(resolution);
		torusFaces = calcTorusFaces(resolution);
		float torusTexture[] = { 0, 0, 1, 0, 1, 1, 0, 1 };

		VertexData vertexData = new VertexData(resolution * resolution);
		vertexData.addElement(torusVertex, VertexData.Semantic.POSITION, 3);
		vertexData.addElement(torusColors, VertexData.Semantic.COLOR, 3);
		vertexData.addElement(torusTexture, VertexData.Semantic.TEXCOORD, 2);

		vertexData.addIndices(torusFaces);

		return new Shape(vertexData);
	}

	public static Shape makePlane() {
		float planeVertex[] = { -5, -3, 5, -5, -3, -5, 5, -3, -5, 5, -3, 5 };
		float planeColors[] = { 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 };
		int planeFaces[] = { 0, 1, 2, 0, 3, 2 };

		VertexData planeData = new VertexData(4);
		planeData.addElement(planeColors, VertexData.Semantic.COLOR, 3);
		planeData.addElement(planeVertex, VertexData.Semantic.POSITION, 3);

		planeData.addIndices(planeFaces);
		return new Shape(planeData);

	}

	public static Shape makeSquare() {
		float squareVertex[] = { -5, -5, 0, 5, -5, 0, 5, 5, 0, -5, 5, 0 };
		float squareColors[] = { 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 };
		int squareFaces[] = { 0, 2, 1, 0, 2, 3 };
		float squareTexture[] = { 0, 0, 1, 0, 1, 1, 0, 1 };

		VertexData squareData = new VertexData(4);
		squareData.addElement(squareColors, VertexData.Semantic.COLOR, 3);
		squareData.addElement(squareVertex, VertexData.Semantic.POSITION, 3);
		squareData.addElement(squareTexture, VertexData.Semantic.TEXCOORD, 2);

		squareData.addIndices(squareFaces);
		return new Shape(squareData);
	}

	public static Shape makeHouse()
	{
		// A house
		float vertices[] = {-4,-4,4, 4,-4,4, 4,4,4, -4,4,4,		// front face
				-4,-4,-4, -4,-4,4, -4,4,4, -4,4,-4, // left face
				4,-4,-4,-4,-4,-4, -4,4,-4, 4,4,-4,  // back face
				4,-4,4, 4,-4,-4, 4,4,-4, 4,4,4,		// right face
				4,4,4, 4,4,-4, -4,4,-4, -4,4,4,		// top face
				-4,-4,4, -4,-4,-4, 4,-4,-4, 4,-4,4, // bottom face

				-20,-4,20, 20,-4,20, 20,-4,-20, -20,-4,-20, // ground floor
				-4,4,4, 4,4,4, 0,8,4,				// the roof
				4,4,4, 4,4,-4, 0,8,-4, 0,8,4,
				-4,4,4, 0,8,4, 0,8,-4, -4,4,-4,
				4,4,-4, -4,4,-4, 0,8,-4};

		float normals[] = {0,0,1,  0,0,1,  0,0,1,  0,0,1,		// front face
				-1,0,0, -1,0,0, -1,0,0, -1,0,0,		// left face
				0,0,-1, 0,0,-1, 0,0,-1, 0,0,-1,		// back face
				1,0,0,  1,0,0,  1,0,0,  1,0,0,		// right face
				0,1,0,  0,1,0,  0,1,0,  0,1,0,		// top face
				0,-1,0, 0,-1,0, 0,-1,0, 0,-1,0,		// bottom face

				0,1,0,  0,1,0,  0,1,0,  0,1,0,		// ground floor
				0,0,1,  0,0,1,  0,0,1,				// front roof
				0.707f,0.707f,0, 0.707f,0.707f,0, 0.707f,0.707f,0, 0.707f,0.707f,0, // right roof
				-0.707f,0.707f,0, -0.707f,0.707f,0, -0.707f,0.707f,0, -0.707f,0.707f,0, // left roof
				0,0,-1, 0,0,-1, 0,0,-1};				// back roof

		float colors[] = {1,0,0, 1,0,0, 1,0,0, 1,0,0,
				0,1,0, 0,1,0, 0,1,0, 0,1,0,
				1,0,0, 1,0,0, 1,0,0, 1,0,0,
				0,1,0, 0,1,0, 0,1,0, 0,1,0,
				0,0,1, 0,0,1, 0,0,1, 0,0,1,
				0,0,1, 0,0,1, 0,0,1, 0,0,1,

				0,0.5f,0, 0,0.5f,0, 0,0.5f,0, 0,0.5f,0,			// ground floor
				0,0,1, 0,0,1, 0,0,1,							// roof
				1,0,0, 1,0,0, 1,0,0, 1,0,0,
				0,1,0, 0,1,0, 0,1,0, 0,1,0,
				0,0,1, 0,0,1, 0,0,1,};

		// Set up the vertex data
		VertexData vertexData = new VertexData(42);

		// Specify the elements of the vertex data:
		// - one element for vertex positions
		vertexData.addElement(vertices, VertexData.Semantic.POSITION, 3);
		// - one element for vertex colors
		vertexData.addElement(colors, VertexData.Semantic.COLOR, 3);
		// - one element for vertex normals
		vertexData.addElement(normals, VertexData.Semantic.NORMAL, 3);

		// The index data that stores the connectivity of the triangles
		int indices[] = {0,2,3, 0,1,2,			// front face
				4,6,7, 4,5,6,			// left face
				8,10,11, 8,9,10,		// back face
				12,14,15, 12,13,14,	// right face
				16,18,19, 16,17,18,	// top face
				20,22,23, 20,21,22,	// bottom face

				24,26,27, 24,25,26,	// ground floor
				28,29,30,				// roof
				31,33,34, 31,32,33,
				35,37,38, 35,36,37,
				39,40,41};

		vertexData.addIndices(indices);

		Shape house = new Shape(vertexData);

		return house;
	}

}
