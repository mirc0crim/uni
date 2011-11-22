/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * Solution for 5th Exercise
 */

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import jrtr.Camera;
import jrtr.GLRenderPanel;
import jrtr.GraphSceneManager;
import jrtr.Light;
import jrtr.LightNode;
import jrtr.Node;
import jrtr.RenderContext;
import jrtr.RenderPanel;
import jrtr.Shape;
import jrtr.ShapeNode;
import jrtr.TransformGroup;

public class simple51 {
	static RenderPanel renderPanel;
	static RenderContext renderContext;
	static GraphSceneManager sceneManager;
	static float angle;
	static Node root;
	static ShapeNode tori;
	static LightNode sun;
	static int task = 1;

	public final static class SimpleRenderPanel extends GLRenderPanel {
		@Override
		public void init(RenderContext r) {
			renderContext = r;
			renderContext.setSceneManager(sceneManager);

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

		Camera.setCenterOfProjection(new Vector3f(0, 0, 10));
		sceneManager = new GraphSceneManager();

		Matrix4f id = new Matrix4f();
		id.setIdentity();
		Shape torus = simple21.makeTorus(20, 2, 1, 0, 0, 0);
		root = new TransformGroup();
		root.setTransformationMat(id);
		tori = new ShapeNode();
		tori.setShape(torus);
		tori.setTransformationMat(id);
		root.addChild(tori);

		Light l = new Light();
		sun = new LightNode();
		sun.setLight(l);
		sun.setTransformationMat(id);
		root.addChild(sun);

		sceneManager.setRoot(root);

		renderPanel = new SimpleRenderPanel();
		JFrame jframe = new JFrame("simple");
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().add(renderPanel.getCanvas());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}
}
