/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * Solution for 2nd Task of 2nd Exercise
 */

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.TimerTask;

import javax.swing.JFrame;
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
	static float theta = 0;
	static Vector3f currVec = new Vector3f();
	static Vector3f startVec = new Vector3f();
	static Vector3f axis = new Vector3f();

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

	public static class SimpleMouseListener implements MouseMotionListener {

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

			axis.cross(startVec, currVec);
			theta = startVec.angle(currVec);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

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
		Shape teapot = new Shape(vertexTeapot);
		sceneManager.addShape(teapot);

		renderPanel = new SimpleRenderPanel();

		JFrame jframe = new JFrame("simple 2-2 Trackball");
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().add(renderPanel.getCanvas());

		jframe.addMouseMotionListener(new SimpleMouseListener());

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}

}
