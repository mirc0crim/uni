/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * Solution for 4th Task of 2nd Exercise
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import jrtr.Camera;
import jrtr.GLRenderPanel;
import jrtr.RenderContext;
import jrtr.RenderPanel;
import jrtr.Shape;
import jrtr.SimpleSceneManager;

public class simple24 {
	static RenderPanel renderPanel;
	static RenderContext renderContext;
	static SimpleSceneManager sceneManager;

	public final static class SimpleRenderPanel extends GLRenderPanel {
		@Override
		public void init(RenderContext r) {
			renderContext = r;
			renderContext.setSceneManager(sceneManager);
		}
	}

	public static class SimpleMouseListener implements MouseMotionListener, MouseListener {

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseDragged(MouseEvent e) {
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

	public static class MyKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			Matrix4f cam = Camera.getCameraMatrix();
			Vector3f trans = new Vector3f();
			cam.get(trans);
			int shift = 1;
			switch(e.getKeyChar()) {
			case 'w': {
				trans.setY(trans.getY() - shift);
				break;
			}
			case 's': {
				trans.setY(trans.getY() + shift);
				break;
			}
			case 'd': {
				trans.setX(trans.getX() - shift);
				break;
			}
			case 'a': {
				trans.setX(trans.getX() + shift);
				break;
			}
			}
			cam.setTranslation(new Vector3f(trans));
			Camera.setCameraMatrix(cam);
			e.getComponent().repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

	public static void main(String[] args) {

		sceneManager = new SimpleSceneManager();
		Shape terrain = simple23.getTerrain();
		sceneManager.addShape(terrain);
		Camera.setCenterOfProjection(new Vector3f(0, 0, 50));

		renderPanel = new SimpleRenderPanel();

		JFrame jframe = new JFrame("simple 2-4 Camera");
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().add(renderPanel.getCanvas());

		renderPanel.getCanvas().addKeyListener(new MyKeyListener());

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}

}
