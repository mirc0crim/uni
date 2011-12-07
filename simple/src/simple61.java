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

	public final static class SimpleRenderPanel extends GLRenderPanel {
		@Override
		public void init(RenderContext r) {
			renderContext = r;
			renderContext.setSceneManager(sceneManager);

			scene();

			Matrix4f t = wine.getTransformation();
			Matrix4f flip = new Matrix4f();
			flip.rotX((float) Math.PI / -2);
			t.mul(flip);
			wine.setTransformation(t);

			Timer timer = new Timer();
			angle = 0.01f;
			timer.scheduleAtFixedRate(new AnimationTask(), 0, 10);
		}

	}

	public static class AnimationTask extends TimerTask {
		@Override
		public void run() {
			Matrix4f rotX = new Matrix4f();
			Matrix4f rotY = new Matrix4f();
			Matrix4f rotZ = new Matrix4f();
			rotX.rotX(angle);
			rotY.rotY(1.5f * angle);
			rotZ.rotZ(2.5f * angle);

			Matrix4f t = wine.getTransformation();
			t.mul(rotX);
			t.mul(rotY);
			t.mul(rotZ);
			wine.setTransformation(t);

			renderPanel.getCanvas().repaint();
		}
	}

	public static void main(String[] args)
	{
		Camera.setCenterOfProjection(new Vector3f(0, 10, 20));
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
		Vector3f[] point = new Vector3f[7];
		point[0] = new Vector3f(3, 0, 7);
		point[1] = new Vector3f(4, 0, 4);
		point[2] = new Vector3f(3, 0, 2);
		point[3] = new Vector3f(0.5f, 0, 0.5f);
		point[4] = new Vector3f(0.5f, 0, -3);
		point[5] = new Vector3f(0.5f, 0, -5);
		point[6] = new Vector3f(2, 0, -5);
		wine = mesh.makeBezier(50, point);
		Material m = new Material();
		m.setShader(diffuse);
		m.setMatColor(new Vector3f(1, 1, 1));
		wine.setMaterial(m);
		Light l = new Light();
		l.setType(Type.Directional);
		l.setDirection(new Vector3f(-1, -1, -1));
		l.setRadiance(new Vector3f(0.3f, 0.3f, 1));
		sceneManager.addLight(l);
		sceneManager.addShape(wine);
	}
}
