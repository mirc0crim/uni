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

		Shape zylinder1 = mesh.makeZylinder(seg);
		Shape torusFront1 = mesh.makeTorus(seg, mainRad, rad, 0, 0, 0);
		Shape torusBack1 = mesh.makeTorus(seg, mainRad, rad, 0, 0, 0);
		Shape house = mesh.makeHouse();
		Shape plane1 = mesh.makePlane();
		Shape cubeSeat1 = mesh.makeCube();
		Shape cubeSteering1 = mesh.makeCube();

		zylinder = zylinder1;
		torusFront = torusFront1;
		torusBack = torusBack1;
		plane = plane1;
		cubeSeat = cubeSeat1;
		cubeSteering = cubeSteering1;

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



		// Make a scene manager and add the object
		sceneManager = new SimpleSceneManager();

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
}
