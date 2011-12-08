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

	public final static class SimpleRenderPanel extends GLRenderPanel {
		@Override
		public void init(RenderContext r) {
			renderContext = r;
			renderContext.setSceneManager(sceneManager);

			scene();

			Matrix4f wineTransform = wine.getTransformation();
			Matrix4f wine2Transform = wine2.getTransformation();
			Matrix4f tableTransform = table.getTransformation();
			Matrix4f flip = new Matrix4f();
			flip.rotX((float) Math.PI / -2);
			Matrix4f makeSmaller = new Matrix4f(0.25f, 0, 0, 0, 0, 0.25f, 0, 0, 0, 0, 0.25f, 0, 0,
					0, 0, 1);
			Matrix4f moveWine = new Matrix4f(1, 0, 0, -2, 0, 1, 0, 5, 0, 0, 1, 0, 0, 0, 0, 1);
			Matrix4f moveWine2 = new Matrix4f(1, 0, 0, 2, 0, 1, 0, 5, 0, 0, 1, 0, 0, 0, 0, 1);
			Matrix4f moveTable = new Matrix4f(1, 0, 0, 0, 0, 1, 0, -3.25f, 0, 0, 1, 0, 0, 0, 0, 1);
			wineTransform.mul(moveWine);
			wineTransform.mul(flip);
			wineTransform.mul(makeSmaller);
			wine2Transform.mul(moveWine2);
			wine2Transform.mul(flip);
			wine2Transform.mul(makeSmaller);
			tableTransform.mul(moveTable);
			tableTransform.mul(flip);
			wine.setTransformation(wineTransform);
			wine2.setTransformation(wine2Transform);
			table.setTransformation(tableTransform);

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
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().add(renderPanel.getCanvas());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}

	public static void scene() {
		Shader diffuse = renderContext.makeShader();
		try {
			diffuse.load("../jrtr/shaders/diffuse.vert", "../jrtr/shaders/diffuse.frag");
		} catch (Exception e) {
			System.out.println("Problem loading shader");
		}
		Vector3f[] winePoints = new Vector3f[7];
		winePoints[0] = new Vector3f(3, 0, 7);
		winePoints[1] = new Vector3f(4, 0, 4);
		winePoints[2] = new Vector3f(3, 0, 2);
		winePoints[3] = new Vector3f(0.5f, 0, 0.5f);
		winePoints[4] = new Vector3f(0.5f, 0, -3);
		winePoints[5] = new Vector3f(0.5f, 0, -5);
		winePoints[6] = new Vector3f(2.5f, 0, -5);
		wine = mesh.makeBezier(50, winePoints);
		wine2 = mesh.makeBezier(50, winePoints);
		Vector3f[] tablePoints = new Vector3f[10];
		tablePoints[0] = new Vector3f(0, 0, 7);
		tablePoints[1] = new Vector3f(0, 0, 7);
		tablePoints[2] = new Vector3f(5, 0, 7);
		tablePoints[3] = new Vector3f(5, 0, 7);
		tablePoints[4] = new Vector3f(1, 0, 6);
		tablePoints[5] = new Vector3f(0.5f, 0, 2);
		tablePoints[6] = new Vector3f(0.5f, 0, 1);
		tablePoints[7] = new Vector3f(0.5f, 0, -3);
		tablePoints[8] = new Vector3f(1, 0, -5);
		tablePoints[9] = new Vector3f(2, 0, -5);
		table = mesh.makeBezier(50, tablePoints);
		Material wineMat = new Material();
		wineMat.setShader(diffuse);
		wineMat.setMatColor(new Vector3f(1, 1, 2));
		wine.setMaterial(wineMat);
		wine2.setMaterial(wineMat);
		Material tableMat = new Material();
		tableMat.setShader(diffuse);
		tableMat.setMatColor(new Vector3f(0.4f, 0.27f, 0.13f));
		table.setMaterial(tableMat);
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
	}
}
