/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * Solution for 6th Exercise
 */

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import jrtr.Camera;
import jrtr.GLRenderPanel;
import jrtr.Light;
import jrtr.Light.Type;
import jrtr.Material;
import jrtr.RenderContext;
import jrtr.RenderPanel;
import jrtr.Shader;
import jrtr.Shape;
import jrtr.SimpleSceneManager;

public class simple61 {
	static RenderPanel renderPanel;
	static RenderContext renderContext;
	static SimpleSceneManager sceneManager;
	static float angle;
	static int task = 1;
	static Shape wine;
	static Shape wine2;
	static Shape table;
	static Shape bottle;
	static Shape backPlane;
	static Shape rightPlane;
	static Shape leftPlane;
	static Shape bottomPlane;
	static Shape plate;
	static Shape plate2;
	static Shape stick;
	static Shape stick2;
	static Shape stick3;
	static Shape stick4;

	public final static class SimpleRenderPanel extends GLRenderPanel {
		@Override
		public void init(RenderContext r) {
			renderContext = r;
			renderContext.setSceneManager(sceneManager);

			scene();

			Matrix4f wineTransform = wine.getTransformation();
			Matrix4f wine2Transform = wine2.getTransformation();
			Matrix4f tableTransform = table.getTransformation();
			Matrix4f bottleTransform = bottle.getTransformation();
			Matrix4f backPlaneTransform = backPlane.getTransformation();
			Matrix4f rightPlaneTransform = rightPlane.getTransformation();
			Matrix4f leftPlaneTransform = leftPlane.getTransformation();
			Matrix4f bottomPlaneTransform = bottomPlane.getTransformation();
			Matrix4f plateTransform = plate.getTransformation();
			Matrix4f plate2Transform = plate2.getTransformation();
			Matrix4f stickTransform = stick.getTransformation();
			Matrix4f stick2Transform = stick2.getTransformation();
			Matrix4f stick3Transform = stick3.getTransformation();
			Matrix4f stick4Transform = stick4.getTransformation();
			Matrix4f flip = new Matrix4f();
			Matrix4f backFlip = new Matrix4f();
			Matrix4f turnClockwise = new Matrix4f();
			Matrix4f turnCounterClockwise = new Matrix4f();
			flip.rotX((float) Math.PI / -2);
			backFlip.rotX((float) Math.PI / 2);
			turnClockwise.rotZ((float) Math.PI / -2);
			turnCounterClockwise.rotZ((float) Math.PI / 2);
			Matrix4f quarterSize = new Matrix4f(0.25f, 0, 0, 0, 0, 0.25f, 0, 0, 0, 0, 0.25f, 0, 0,
					0, 0, 1);
			Matrix4f halfSize = new Matrix4f(0.5f, 0, 0, 0, 0, 0.5f, 0, 0, 0, 0, 0.5f, 0, 0, 0, 0,
					1);
			Matrix4f moveWine = new Matrix4f(1, 0, 0, -0.5f, 0, 1, 0, 4, 0, 0, 1, 1, 0, 0, 0, 1);
			Matrix4f moveWine2 = new Matrix4f(1, 0, 0, 0.5f, 0, 1, 0, 4, 0, 0, 1, -1, 0, 0, 0, 1);
			Matrix4f moveTable = new Matrix4f(1, 0, 0, 0, 0, 1, 0, -4.25f, 0, 0, 1, 0, 0, 0, 0, 1);
			Matrix4f moveBottle = new Matrix4f(1, 0, 0, 0, 0, 1, 0, 3.5f, 0, 0, 1, -2.5f, 0, 0, 0,
					1);
			Matrix4f moveRightPlane = new Matrix4f(1, 0, 0, 4, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
			Matrix4f moveLeftPlane = new Matrix4f(1, 0, 0, -4, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
			Matrix4f moveBottomPlane = new Matrix4f(1, 0, 0, 0, 0, 1, 0, -3.5f, 0, 0, 1, 0, 0, 0,
					0, 1);
			Matrix4f biggerPlane = new Matrix4f(2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1);
			Matrix4f movePlate = new Matrix4f(1, 0, 0, -3, 0, 1, 0, 2.75f, 0, 0, 1, 0, 0, 0, 0, 1);
			Matrix4f movePlate2 = new Matrix4f(1, 0, 0, 3, 0, 1, 0, 2.75f, 0, 0, 1, 0, 0, 0, 0, 1);
			Matrix4f moveStick = new Matrix4f(1, 0, 0, -4.5f, 0, 1, 0, 2.75f, 0, 0, 1, 1.75f, 0, 0,
					0, 1);
			Matrix4f moveStick2 = new Matrix4f(1, 0, 0, -4.5f, 0, 1, 0, 2.75f, 0, 0, 1, 2, 0, 0, 0,
					1);
			Matrix4f moveStick3 = new Matrix4f(1, 0, 0, 4.5f, 0, 1, 0, 2.75f, 0, 0, 1, -1.75f, 0,
					0, 0, 1);
			Matrix4f moveStick4 = new Matrix4f(1, 0, 0, 4.5f, 0, 1, 0, 2.75f, 0, 0, 1, -2, 0, 0, 0,
					1);
			wineTransform.mul(moveWine);
			wineTransform.mul(quarterSize);
			wineTransform.mul(flip);
			wine2Transform.mul(moveWine2);
			wine2Transform.mul(quarterSize);
			wine2Transform.mul(flip);
			tableTransform.mul(moveTable);
			tableTransform.mul(flip);
			bottleTransform.mul(moveBottle);
			bottleTransform.mul(halfSize);
			bottleTransform.mul(flip);
			backPlaneTransform.mul(biggerPlane);
			backPlaneTransform.mul(backFlip);
			rightPlaneTransform.mul(moveRightPlane);
			rightPlaneTransform.mul(biggerPlane);
			rightPlaneTransform.mul(backFlip);
			rightPlaneTransform.mul(turnCounterClockwise);
			leftPlaneTransform.mul(moveLeftPlane);
			leftPlaneTransform.mul(biggerPlane);
			leftPlaneTransform.mul(backFlip);
			leftPlaneTransform.mul(turnClockwise);
			bottomPlaneTransform.mul(moveBottomPlane);
			bottomPlaneTransform.mul(biggerPlane);
			plateTransform.mul(movePlate);
			plateTransform.mul(halfSize);
			plateTransform.mul(flip);
			plate2Transform.mul(movePlate2);
			plate2Transform.mul(halfSize);
			plate2Transform.mul(flip);
			stickTransform.mul(moveStick);
			stickTransform.mul(quarterSize);
			stickTransform.mul(turnClockwise);
			stickTransform.mul(flip);
			stick2Transform.mul(moveStick2);
			stick2Transform.mul(quarterSize);
			stick2Transform.mul(turnClockwise);
			stick2Transform.mul(flip);
			stick3Transform.mul(moveStick3);
			stick3Transform.mul(quarterSize);
			stick3Transform.mul(turnCounterClockwise);
			stick3Transform.mul(flip);
			stick4Transform.mul(moveStick4);
			stick4Transform.mul(quarterSize);
			stick4Transform.mul(turnCounterClockwise);
			stick4Transform.mul(flip);
			wine.setTransformation(wineTransform);
			wine2.setTransformation(wine2Transform);
			table.setTransformation(tableTransform);
			bottle.setTransformation(bottleTransform);
			backPlane.setTransformation(backPlaneTransform);
			rightPlane.setTransformation(rightPlaneTransform);
			leftPlane.setTransformation(leftPlaneTransform);
			bottomPlane.setTransformation(bottomPlaneTransform);
			plate.setTransformation(plateTransform);
			plate2.setTransformation(plate2Transform);
			stick.setTransformation(stickTransform);
			stick2.setTransformation(stick2Transform);
			stick3.setTransformation(stick3Transform);
			stick4.setTransformation(stick4Transform);
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new AnimationTask(), 0, 10);
		}

	}

	public static class AnimationTask extends TimerTask {
		@Override
		public void run() {
			renderPanel.getCanvas().repaint();
		}
	}

	public static void main(String[] args)
	{
		Camera.setCenterOfProjection(new Vector3f(0, 10, 15));
		sceneManager = new SimpleSceneManager();

		renderPanel = new SimpleRenderPanel();
		JFrame jframe = new JFrame("simple");
		jframe.setSize(750, 750);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().add(renderPanel.getCanvas());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}

	public static void scene() {
		Shader diffuse = renderContext.makeShader();
		Shader brick = renderContext.makeShader();
		try {
			diffuse.load("../jrtr/shaders/diffuse.vert", "../jrtr/shaders/diffuse.frag");
			brick.load("../jrtr/shaders/brick.vert", "../jrtr/shaders/brick.frag");
		} catch (Exception e) {
			System.out.println("Problem loading shader");
		}
		Vector3f[] winePoints = new Vector3f[10];
		winePoints[0] = new Vector3f(2, 0, 2);
		winePoints[1] = new Vector3f(3, 0, 7);
		winePoints[2] = new Vector3f(3, 0, 7);
		winePoints[3] = new Vector3f(3, 0, 7);
		winePoints[4] = new Vector3f(4, 0, 4);
		winePoints[5] = new Vector3f(3, 0, 2);
		winePoints[6] = new Vector3f(0.5f, 0, 0.5f);
		winePoints[7] = new Vector3f(0.5f, 0, -3);
		winePoints[8] = new Vector3f(0.5f, 0, -5);
		winePoints[9] = new Vector3f(2.5f, 0, -5);
		wine = mesh.makeBezier(50, winePoints);
		wine2 = mesh.makeBezier(50, winePoints);
		Vector3f[] tablePoints = new Vector3f[10];
		tablePoints[0] = new Vector3f(0, 0, 7);
		tablePoints[1] = new Vector3f(0, 0, 7);
		tablePoints[2] = new Vector3f(5.5f, 0, 7);
		tablePoints[3] = new Vector3f(5.5f, 0, 7);
		tablePoints[4] = new Vector3f(1, 0, 6);
		tablePoints[5] = new Vector3f(0.5f, 0, 2);
		tablePoints[6] = new Vector3f(0.5f, 0, 1);
		tablePoints[7] = new Vector3f(0.5f, 0, -3);
		tablePoints[8] = new Vector3f(1, 0, -4);
		tablePoints[9] = new Vector3f(3, 0, -5);
		table = mesh.makeBezier(50, tablePoints);
		Vector3f[] bottlePoints = new Vector3f[10];
		bottlePoints[0] = new Vector3f(0, 0, 8);
		bottlePoints[1] = new Vector3f(0.5f, 0, 8);
		bottlePoints[2] = new Vector3f(0.5f, 0, 8);
		bottlePoints[3] = new Vector3f(0.5f, 0, 6);
		bottlePoints[4] = new Vector3f(1.5f, 0, 5);
		bottlePoints[5] = new Vector3f(1.5f, 0, 5);
		bottlePoints[6] = new Vector3f(1.5f, 0, -2);
		bottlePoints[7] = new Vector3f(1.5f, 0, -2);
		bottlePoints[8] = new Vector3f(1.5f, 0, -2);
		bottlePoints[9] = new Vector3f(0, 0, -2);
		bottle = mesh.makeBezier(50, bottlePoints);
		backPlane = mesh.makePlane();
		rightPlane = mesh.makePlane();
		leftPlane = mesh.makePlane();
		bottomPlane = mesh.makePlane();
		Vector3f[] platePoints = new Vector3f[10];
		platePoints[0] = new Vector3f(0, 0, 0.3f);
		platePoints[1] = new Vector3f(1, 0, 0.3f);
		platePoints[2] = new Vector3f(2, 0, 0.3f);
		platePoints[3] = new Vector3f(2, 0, 0.7f);
		platePoints[4] = new Vector3f(2, 0, 0.8f);
		platePoints[5] = new Vector3f(4, 0, 1);
		platePoints[6] = new Vector3f(4, 0, 1);
		platePoints[7] = new Vector3f(3, 0, 0.9f);
		platePoints[8] = new Vector3f(2, 0, 0);
		platePoints[9] = new Vector3f(0, 0, 0);
		plate = mesh.makeBezier(50, platePoints);
		plate2 = mesh.makeBezier(50, platePoints);
		Vector3f[] stickPoints = new Vector3f[4];
		stickPoints[0] = new Vector3f(0.25f, 0, 15);
		stickPoints[1] = new Vector3f(0.25f, 0, 2);
		stickPoints[2] = new Vector3f(0.5f, 0, 1);
		stickPoints[3] = new Vector3f(0.5f, 0, 0);
		stick = mesh.makeBezier(4, stickPoints);
		stick2 = mesh.makeBezier(4, stickPoints);
		stick3 = mesh.makeBezier(4, stickPoints);
		stick4 = mesh.makeBezier(4, stickPoints);
		Material wineMat = new Material();
		wineMat.setShader(diffuse);
		wineMat.setMatColor(new Vector3f(1, 1, 2));
		wine.setMaterial(wineMat);
		wine2.setMaterial(wineMat);
		Material tableMat = new Material();
		tableMat.setShader(diffuse);
		tableMat.setMatColor(new Vector3f(0.4f, 0.27f, 0.13f));
		table.setMaterial(tableMat);
		Material bottleMat = new Material();
		bottleMat.setShader(diffuse);
		bottleMat.setMatColor(new Vector3f(0.3f, 0, 0));
		bottle.setMaterial(bottleMat);
		Material wallMat = new Material();
		wallMat.setShader(brick);
		wallMat.setMatColor(new Vector3f(0.8f, 0.8f, 0.8f));
		backPlane.setMaterial(wallMat);
		rightPlane.setMaterial(wallMat);
		leftPlane.setMaterial(wallMat);
		Material bottomMat = new Material();
		bottomMat.setShader(diffuse);
		bottomMat.setMatColor(new Vector3f(1, 0, 0));
		bottomPlane.setMaterial(bottomMat);
		Material plateMat = new Material();
		plateMat.setShader(diffuse);
		plateMat.setMatColor(new Vector3f(0.3f, 0.8f, 0.3f));
		plate.setMaterial(plateMat);
		plate2.setMaterial(plateMat);
		Material stickMat = new Material();
		stickMat.setShader(diffuse);
		stickMat.setMatColor(new Vector3f(0.4f, 0.27f, 0.13f));
		stick.setMaterial(stickMat);
		stick2.setMaterial(stickMat);
		stick3.setMaterial(stickMat);
		stick4.setMaterial(stickMat);
		Light l = new Light();
		l.setType(Type.Directional);
		l.setDirection(new Vector3f(-1, -1, -1));
		l.setRadiance(new Vector3f(1, 1, 1));
		Light l2 = new Light();
		l2.setType(Type.Directional);
		l2.setDirection(new Vector3f(1, -1, -1));
		l2.setRadiance(new Vector3f(1, 1, 1));
		sceneManager.addLight(l);
		sceneManager.addLight(l2);
		sceneManager.addShape(wine);
		sceneManager.addShape(wine2);
		sceneManager.addShape(table);
		sceneManager.addShape(bottle);
		sceneManager.addShape(backPlane);
		sceneManager.addShape(rightPlane);
		sceneManager.addShape(leftPlane);
		sceneManager.addShape(bottomPlane);
		sceneManager.addShape(plate);
		sceneManager.addShape(plate2);
		sceneManager.addShape(stick);
		sceneManager.addShape(stick2);
		sceneManager.addShape(stick3);
		sceneManager.addShape(stick4);
	}
}
