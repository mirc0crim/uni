/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * Solution for 5th Exercise
 */

import java.io.IOException;
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
import jrtr.Material;
import jrtr.Node;
import jrtr.ObjReader;
import jrtr.RenderContext;
import jrtr.RenderPanel;
import jrtr.Shader;
import jrtr.Shape;
import jrtr.ShapeNode;
import jrtr.TransformGroup;
import jrtr.VertexData;

public class simple51 {
	static RenderPanel renderPanel;
	static RenderContext renderContext;
	static GraphSceneManager sceneManager;
	static float angle;
	static Shape tea;
	static Node root;
	static ShapeNode tori;
	static LightNode sun;
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
		VertexData vertexTeapot = null;
		try {
			vertexTeapot = ObjReader.read("../simple/teapot_tex.obj", 1);
		} catch (IOException e) {
			System.out.println("ObjReader Problem");
		}

		tea = new Shape(vertexTeapot);

		Camera.setCenterOfProjection(new Vector3f(0, 0, 5));
		sceneManager = new GraphSceneManager();

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

		Matrix4f id = new Matrix4f();
		id.setIdentity();

		Material mat = new Material();
		mat.setShader(diffuse);
		tea.setMaterial(mat);
		root = new TransformGroup();
		root.setTransformationMat(id);
		tori = new ShapeNode();
		tori.setShape(tea);
		tori.setTransformationMat(id);
		root.addChild(tori);

		Light l = new Light();
		l.setPosition(new Vector3f(0, 0, 10));
		sun = new LightNode();
		sun.setLight(l);
		sun.setTransformationMat(id);
		root.addChild(sun);

		sceneManager.setRoot(root);
	}
}
