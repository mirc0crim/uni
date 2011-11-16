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
import jrtr.Light.Type;
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
	static int task = 4;

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
		Shader neon = renderContext.makeShader();
		Shader brick = renderContext.makeShader();
		Shader diffuse = renderContext.makeShader();
		Shader phong = renderContext.makeShader();
		Shader texphong = renderContext.makeShader();
		try {
			toon.load("../jrtr/shaders/toon.vert", "../jrtr/shaders/toon.frag");
			neon.load("../jrtr/shaders/neon.vert", "../jrtr/shaders/neon.frag");
			brick.load("../jrtr/shaders/brick.vert", "../jrtr/shaders/brick.frag");
			diffuse.load("../jrtr/shaders/diffuse.vert", "../jrtr/shaders/diffuse.frag");
			phong.load("../jrtr/shaders/phong.vert", "../jrtr/shaders/phong.frag");
			texphong.load("../jrtr/shaders/texphong.vert", "../jrtr/shaders/texphong.frag");
		} catch (Exception e) {
			System.out.println("Problem loading shader");
		}
		Material mat1 = new Material();
		Material mat2 = new Material();
		Material mat3 = new Material();
		Light l1 = new Light();
		Light l2 = new Light();
		Light l3 = new Light();
		if (task == 1) {
			mat1.setMatColor(new Vector3f(1, 1, 1));
			mat1.setShader(diffuse);
			mat2.setMatColor(new Vector3f(1, 0, 0));
			mat2.setShader(diffuse);
			mat3.setMatColor(new Vector3f(0.5f, 0.5f, 0.5f));
			mat3.setShader(diffuse);

			tea1.setMaterial(mat1);
			tea2.setMaterial(mat2);
			tea3.setMaterial(mat3);

			l1.setType(Type.Directional);
			l1.setDirection(new Vector3f(0, 1, 0));
			l1.setRadiance(new Vector3f(1, 0, 0));
			l2.setType(Type.Directional);
			l2.setDirection(new Vector3f(0, -1, 0));
			l2.setRadiance(new Vector3f(0, 1, 0));
			l3.setType(Type.Directional);
			l3.setDirection(new Vector3f(0, 0, 1));
			l3.setRadiance(new Vector3f(0, 0, 0.5f));
		}
		if (task == 2) {
			mat1.setMatColor(new Vector3f(1, 1, 1));
			mat1.setShader(phong);
			mat2.setMatColor(new Vector3f(1, 1, 1));
			mat2.setShader(phong);
			mat3.setMatColor(new Vector3f(1, 1, 1));
			mat3.setShader(phong);

			tea1.setMaterial(mat1);
			tea2.setMaterial(mat2);
			tea3.setMaterial(mat3);

			l1.setType(Type.Point);
			l1.setPosition(new Vector3f(0, 10, 0));
			l1.setRadiance(new Vector3f(1, 0, 0));
			l2.setType(Type.Point);
			l2.setPosition(new Vector3f(0, -10, 0));
			l2.setRadiance(new Vector3f(0, 1, 0));
			l3.setType(Type.Point);
			l3.setPosition(new Vector3f(0, 0, 10));
			l3.setRadiance(new Vector3f(0, 0, 0.5f));
		}
		if (task == 3) {
			mat1.setMatColor(new Vector3f(1, 1, 1));
			mat1.setShader(texphong);
			mat2.setMatColor(new Vector3f(1, 1, 1));
			mat2.setShader(texphong);
			mat3.setMatColor(new Vector3f(1, 1, 1));
			mat3.setShader(texphong);

			tea1.setMaterial(mat1);
			tea2.setMaterial(mat2);
			tea3.setMaterial(mat3);

			l1.setType(Type.Point);
			l1.setPosition(new Vector3f(0, 10, 0));
			l1.setRadiance(new Vector3f(1, 0, 0));
			l2.setType(Type.Point);
			l2.setPosition(new Vector3f(0, -10, 0));
			l2.setRadiance(new Vector3f(0, 1, 0));
			l3.setType(Type.Point);
			l3.setPosition(new Vector3f(0, 0, 10));
			l3.setRadiance(new Vector3f(0, 0, 0.5f));
		}
		if (task == 4) {
			mat1.setMatColor(new Vector3f(1, 1, 1));
			mat1.setShader(toon);
			mat2.setMatColor(new Vector3f(0.5f, 0.5f, 0.5f));
			mat2.setShader(brick);
			mat3.setMatColor(new Vector3f(1, 1, 1));
			mat3.setShader(neon);

			tea1.setMaterial(mat1);
			tea2.setMaterial(mat2);
			tea3.setMaterial(mat3);

			l1.setType(Type.Point);
			l1.setPosition(new Vector3f(0, 10, 0));
			l1.setRadiance(new Vector3f(1, 0, 0));
			l2.setType(Type.Point);
			l2.setPosition(new Vector3f(0, -10, 0));
			l2.setRadiance(new Vector3f(0, 1, 0));
			l3.setType(Type.Point);
			l3.setPosition(new Vector3f(0, 0, 10));
			l3.setRadiance(new Vector3f(0, 0, 0.5f));
		}
		sceneManager.addShape(tea1);
		sceneManager.addShape(tea2);
		sceneManager.addShape(tea3);
		sceneManager.addLight(l1);
		sceneManager.addLight(l2);
		sceneManager.addLight(l3);
	}
}
