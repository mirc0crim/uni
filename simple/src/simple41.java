/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * Solution for 4th Exercise
 */

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import jrtr.Camera;
import jrtr.GLRenderPanel;
import jrtr.Light;
import jrtr.Material;
import jrtr.ObjReader;
import jrtr.RenderContext;
import jrtr.RenderPanel;
import jrtr.Shader;
import jrtr.Shape;
import jrtr.SimpleSceneManager;
import jrtr.VertexData;

public class simple41 {
	static RenderPanel renderPanel;
	static RenderContext renderContext;
	static SimpleSceneManager sceneManager;
	static Shape tea1;
	static Shape tea2;
	static Shape tea3;
	static float angle;

	public final static class SimpleRenderPanel extends GLRenderPanel {
		@Override
		public void init(RenderContext r) {
			renderContext = r;
			renderContext.setSceneManager(sceneManager);

			scene();

			Timer timer = new Timer();
			angle = 0.01f;
			Matrix4f t1 = tea1.getTransformation();
			Matrix4f t2 = tea2.getTransformation();
			Matrix4f t3 = tea3.getTransformation();
			t1.setTranslation(new Vector3f(-1, 1, 0));
			t2.setTranslation(new Vector3f(1, -1, 0));
			t3.setScale(0.5f);
			tea1.setTransformation(t1);
			tea2.setTransformation(t2);
			tea3.setTransformation(t3);
			timer.scheduleAtFixedRate(new AnimationTask(), 0, 10);
		}
	}

	public static class AnimationTask extends TimerTask {
		@Override
		public void run() {
			Matrix4f t1 = tea1.getTransformation();
			Matrix4f t2 = tea2.getTransformation();
			Matrix4f t3 = tea3.getTransformation();
			Matrix4f rotX = new Matrix4f();
			Matrix4f rotY = new Matrix4f();

			rotX.rotX(angle);
			rotY.rotY(1.5f * angle);
			t1.mul(rotX);
			t2.mul(rotY);
			t3.mul(rotX);
			t3.mul(rotY);

			tea1.setTransformation(t1);
			tea2.setTransformation(t2);
			tea3.setTransformation(t3);

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

		Camera.setCenterOfProjection(new Vector3f(0, 0, 4));

		tea1 = new Shape(vertexTeapot);
		tea2 = new Shape(vertexTeapot);
		tea3 = new Shape(vertexTeapot);

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
		Shader toon = renderContext.makeShader();
		Shader diffuse = renderContext.makeShader();
		Shader phong = renderContext.makeShader();
		try {
			toon.load("../jrtr/shaders/toon.vert", "../jrtr/shaders/toon.frag");
			diffuse.load("../jrtr/shaders/diffuseBsp.vert", "../jrtr/shaders/diffuseBsp.frag");
			phong.load("../jrtr/shaders/texphong.vert", "../jrtr/shaders/texphong.frag");
		} catch (Exception e) {
			System.out.println("Problem loading shader");
		}


		Material mat1 = new Material();
		mat1.setMatColor(new Vector3f(1, 1, 1));
		mat1.setShader(diffuse);
		Material mat2 = new Material();
		mat2.setMatColor(new Vector3f(1, 0, 0));
		mat2.setShader(diffuse);
		Material mat3 = new Material();
		mat2.setMatColor(new Vector3f(0.5f, 0.5f, 0.5f));
		mat3.setShader(diffuse);
		tea1.setMaterial(mat1);
		tea2.setMaterial(mat2);
		tea3.setMaterial(mat3);
		sceneManager.addShape(tea1);
		sceneManager.addShape(tea2);
		sceneManager.addShape(tea3);
		Light l1 = new Light();
		l1.setDirection(new Vector3f(0, 1, 0));
		l1.setRadiance(new Vector3f(1, 0, 0));
		l1.setPosition(new Vector3f(0, 10, 0));
		Light l2 = new Light();
		l2.setDirection(new Vector3f(0, -1, 0));
		l2.setRadiance(new Vector3f(0, 1, 0));
		l2.setPosition(new Vector3f(0, -10, 0));
		Light l3 = new Light();
		l3.setDirection(new Vector3f(0, 0, 1));
		l3.setRadiance(new Vector3f(0, 0, 0.5f));
		l3.setPosition(new Vector3f(0, 0, 10));
		sceneManager.addLight(l1);
		sceneManager.addLight(l2);
		sceneManager.addLight(l3);
	}
}
