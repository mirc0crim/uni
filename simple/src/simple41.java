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

import jrtr.GLRenderPanel;
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
	static Shape shape;
	static float angle;

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
			Matrix4f t = shape.getTransformation();
			Matrix4f rotX = new Matrix4f();
			Matrix4f rotY = new Matrix4f();
			rotX.rotX(angle);
			rotY.rotY(1.5f * angle);
			t.mul(rotX);
			t.mul(rotY);
			shape.setTransformation(t);
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
			e.printStackTrace();
		}

		sceneManager = new SimpleSceneManager();
		shape = new Shape(vertexData);
		// shape = new Shape(vertexTeapot);
		sceneManager.addShape(shape);
		renderPanel = new SimpleRenderPanel();
		JFrame jframe = new JFrame("simple");
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().add(renderPanel.getCanvas());
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}
}
