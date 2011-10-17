/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * Solution for 2nd Task of 2nd Exercise
 */

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import jrtr.Camera;
import jrtr.GLRenderPanel;
import jrtr.ObjReader;
import jrtr.RenderContext;
import jrtr.RenderPanel;
import jrtr.Shape;
import jrtr.SimpleSceneManager;
import jrtr.VertexData;

public class simple22 {
	static RenderPanel renderPanel;
	static RenderContext renderContext;
	static SimpleSceneManager sceneManager;
	static float startX = 0;
	static float startY = 0;
	static float startZ = 0;
	static float currX = 0;
	static float currY = 0;
	static float currZ = 0;
	static Vector3f currVec = new Vector3f();
	static Vector3f startVec = new Vector3f();
	static Shape teapot;

	public final static class SimpleRenderPanel extends GLRenderPanel {
		@Override
		public void init(RenderContext r) {
			renderContext = r;
			renderContext.setSceneManager(sceneManager);
		}
	}

	public static class AnimationTask extends TimerTask {
		@Override
		public void run() {
			renderPanel.getCanvas().repaint();
		}
	}

	public static class SimpleMouseListener implements MouseMotionListener, MouseListener {

		@Override
		public void mousePressed(MouseEvent e) {
			int winHeight = e.getComponent().getSize().height;
			int winWidth = e.getComponent().getSize().width;
			startX = 2 * e.getX() / winWidth - 1;
			startY = 1 - 2 * e.getY() / winHeight;
			double z2 = 1 - startX * startX - startY * startY;
			startZ = (float) (z2 > 0 ? Math.sqrt(z2) : 0);
			startVec.set(startX, startY, startZ);
			startVec.normalize();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			int winHeight = e.getComponent().getSize().height;
			int winWidth = e.getComponent().getSize().width;
			currX = 2 * e.getX() / winWidth - 1;
			currY = 1 - 2 * e.getY() / winHeight;
			double z2 = 1 - currX * currX - currY * currY;
			currZ = (float) (z2 > 0 ? Math.sqrt(z2) : 0);
			currVec.set(currX, currY, currZ);
			currVec.normalize();
			System.out.println(currVec.toString());
			System.out.println(startVec.toString());

			Vector3f axis = new Vector3f();
			axis.cross(startVec, currVec);
			axis.normalize();
			float theta = 0;
			theta = startVec.angle(currVec);

			Matrix4f tea = teapot.getTransformation();
			Matrix4f t = getRotMatrix(axis, theta);
			t.mul(tea);
			teapot.setTransformation(t);

			startVec.set(currVec);
			e.getComponent().repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	public static void main(String[] args) {

		VertexData vertexTeapot = null;
		Camera.setCenterOfProjection(new Vector3f(0, 0, 3));

		try {
			vertexTeapot = ObjReader.read("../simple/teapot.obj", 1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		sceneManager = new SimpleSceneManager();
		teapot = new Shape(vertexTeapot);
		sceneManager.addShape(teapot);

		renderPanel = new SimpleRenderPanel();

		JFrame jframe = new JFrame("simple 2-2 Trackball");
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null);
		renderPanel.getCanvas().addMouseListener(new SimpleMouseListener());
		renderPanel.getCanvas().addMouseMotionListener(new SimpleMouseListener());
		jframe.getContentPane().add(renderPanel.getCanvas());

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}

	public static Matrix4f getRotMatrix(Vector3f axis, float theta) {
		float[] m = new float[16];
		float x = axis.getX();
		float y = axis.getY();
		float z = axis.getZ();
		double cos = Math.cos(theta);
		double sin = Math.sin(theta);
		m[3] = m[7] = m[11] = m[12] = m[13] = m[14] = 0;
		m[15] = 1;

		m[0] = (float) (cos + x * x * (1 - cos));
		m[1] = (float) (x * y * (1 - cos) - z * sin);
		m[2] = (float) (x * z * (1 - cos) + y * sin);

		m[4] = (float) (x * y * (1 - cos) + z * sin);
		m[5] = (float) (cos + y * y * (1 - cos));
		m[6] = (float) (y * z * (1 - cos) - x * sin);

		m[8] = (float) (x * z * (1 - cos) - y * sin);
		m[9] = (float) (y * z * (1 - cos) + x * sin);
		m[10] = (float) (cos + z * z * (1 - cos));

		return new Matrix4f(m);
	}

}
