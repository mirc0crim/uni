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

	public final static class SimpleRenderPanel extends GLRenderPanel {
		@Override
		public void init(RenderContext r) {
			renderContext = r;
			renderContext.setSceneManager(sceneManager);

			scene();

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
			rotX.rotX(angle);
			rotY.rotY(1.5f * angle);

			renderPanel.getCanvas().repaint();
		}
	}

	public static void main(String[] args)
	{
		Camera.setCenterOfProjection(new Vector3f(5, 5, 15));
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
			diffuse.load("../jrtr/shaders/default.vert", "../jrtr/shaders/default.frag");
		} catch (Exception e) {
			System.out.println("Problem loading shader");
		}
		Vector3f[] point = new Vector3f[4];
		point[0] = new Vector3f(3, 0, 0);
		point[1] = new Vector3f(2, 0, 0);
		point[2] = new Vector3f(2, 1, 0);
		point[3] = new Vector3f(3, 3, 0);
		Shape bla = mesh.makeBezier(4, point);
	}
}
