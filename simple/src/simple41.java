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
import jrtr.Shape;
import jrtr.SimpleSceneManager;
import jrtr.VertexData;

public class simple41 {
	static RenderPanel renderPanel;
	static RenderContext renderContext;
	static SimpleSceneManager sceneManager;
	static Shape tea1;
	static Shape tea2;
	static Shape cube;
	static float angle;

	public final static class SimpleRenderPanel extends GLRenderPanel {
		@Override
		public void init(RenderContext r) {
			renderContext = r;
			renderContext.setSceneManager(sceneManager);
			Timer timer = new Timer();
			angle = 0.01f;
			Matrix4f t = tea1.getTransformation();
			Matrix4f t2 = tea2.getTransformation();
			Matrix4f t3 = cube.getTransformation();
			t.setTranslation(new Vector3f(-1.5f, 1, 0));
			t2.setTranslation(new Vector3f(1.5f, -1, 0));
			t3.setScale(0.5f);
			tea1.setTransformation(t);
			tea2.setTransformation(t2);
			cube.setTransformation(t3);
			timer.scheduleAtFixedRate(new AnimationTask(), 0, 10);
		}
	}

	public static class AnimationTask extends TimerTask {
		@Override
		public void run() {
			Matrix4f t = tea1.getTransformation();
			Matrix4f t2 = tea2.getTransformation();
			Matrix4f t3 = cube.getTransformation();
			Matrix4f rotX = new Matrix4f();
			Matrix4f rotY = new Matrix4f();

			rotX.rotX(angle);
			rotY.rotY(1.5f * angle);
			t.mul(rotX);
			t2.mul(rotY);
			t3.mul(rotX);
			t3.mul(rotY);

			tea1.setTransformation(t);
			tea2.setTransformation(t2);
			cube.setTransformation(t3);

			renderPanel.getCanvas().repaint();
		}
	}

	public static void main(String[] args)
	{
		// Make a simple geometric object: a cube

		float v[] = {-1,-1,1, 1,-1,1, 1,1,1, -1,1,1,		// front face
				-1,-1,-1, -1,-1,1, -1,1,1, -1,1,-1,	// left face
				1,-1,-1,-1,-1,-1, -1,1,-1, 1,1,-1,		// back face
				1,-1,1, 1,-1,-1, 1,1,-1, 1,1,1,		// right face
				1,1,1, 1,1,-1, -1,1,-1, -1,1,1,		// top face
				-1,-1,1, -1,-1,-1, 1,-1,-1, 1,-1,1};	// bottom face

		float c[] = {1,0,0, 1,0,0, 1,0,0, 1,0,0,
				0,1,0, 0,1,0, 0,1,0, 0,1,0,
				1,0,0, 1,0,0, 1,0,0, 1,0,0,
				0,1,0, 0,1,0, 0,1,0, 0,1,0,
				0,0,1, 0,0,1, 0,0,1, 0,0,1,
				0,0,1, 0,0,1, 0,0,1, 0,0,1};

		float n[] = {0,0,1, 0,0,1, 0,0,1, 0,0,1,
				-1,0,0, -1,0,0, -1,0,0, -1,0,0,
				0,0,-1, 0,0,-1, 0,0,-1, 0,0,-1,
				1,0,0, 1,0,0, 1,0,0, 1,0,0,
				0,1,0, 0,1,0, 0,1,0, 0,1,0,
				0,-1,0, 0,-1,0, 0,-1,0,  0,-1,0};

		float uv[] = {0,0, 1,0, 1,1, 0,1,
				0,0, 1,0, 1,1, 0,1,
				0,0, 1,0, 1,1, 0,1,
				0,0, 1,0, 1,1, 0,1,
				0,0, 1,0, 1,1, 0,1,
				0,0, 1,0, 1,1, 0,1};

		VertexData vertexData = new VertexData(24);
		vertexData.addElement(c, VertexData.Semantic.COLOR, 3);
		vertexData.addElement(v, VertexData.Semantic.POSITION, 3);
		vertexData.addElement(n, VertexData.Semantic.NORMAL, 3);
		vertexData.addElement(uv, VertexData.Semantic.TEXCOORD, 2);

		int indices[] = {0,2,3, 0,1,2,			// front face
				4,6,7, 4,5,6,			// left face
				8,10,11, 8,9,10,		// back face
				12,14,15, 12,13,14,	// right face
				16,18,19, 16,17,18,	// top face
				20,22,23, 20,21,22};	// bottom face

		vertexData.addIndices(indices);

		VertexData vertexTeapot = null;
		try {
			vertexTeapot = ObjReader.read("../simple/teapot_tex.obj", 1);
		} catch (IOException e) {
			System.out.println("ObjReader Problem");
		}

		Camera.setCenterOfProjection(new Vector3f(0, 0, 5));

		sceneManager = new SimpleSceneManager();
		tea1 = new Shape(vertexTeapot);
		tea2 = new Shape(vertexTeapot);
		cube = new Shape(vertexData);
		Material mat1 = new Material();
		mat1.setMatColor(new Vector3f(1, 1, 1));
		Material mat2 = new Material();
		mat2.setMatColor(new Vector3f(1, 0, 0));
		tea1.setMaterial(mat1);
		tea2.setMaterial(mat2);
		cube.setMaterial(new Material());
		sceneManager.addShape(tea1);
		sceneManager.addShape(tea2);
		sceneManager.addShape(cube);
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
		renderPanel = new SimpleRenderPanel();
		JFrame jframe = new JFrame("simple");
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().add(renderPanel.getCanvas());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}
}
