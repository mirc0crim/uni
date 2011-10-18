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
	static Shape terrain;
	static int startX;
	static int startY;

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
			startX = e.getX();
			startY = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			Vector3f lap = Camera.getLookAtPoint();
			Vector3f cop = Camera.getCenterOfProjection();
			float x = lap.getX();
			float y = lap.getY();
			float z = lap.getZ();
			float spin = 0.01f;
			if (e.getX() < startX) {
				lap.setX((float) (cop.getX() + Math.cos(-spin) * (x - cop.getX()) - Math.sin(-spin)
						* (z - cop.getZ())));
				lap.setZ((float) (cop.getZ() + Math.sin(-spin) * (x - cop.getX()) + Math.cos(-spin)
						* (z - cop.getZ())));
			}
			if (e.getX() > startX) {
				lap.setX((float) (cop.getX() + Math.cos(spin) * (x - cop.getX()) - Math.sin(spin)
						* (z - cop.getZ())));
				lap.setZ((float) (cop.getZ() + Math.sin(spin) * (x - cop.getX()) + Math.cos(spin)
						* (z - cop.getZ())));
			}
			if (e.getY() < startY) {
				lap.setY((float) (cop.getY() + Math.cos(spin) * (y - cop.getY()) - Math.sin(spin)
						* (z - cop.getZ())));
				lap.setZ((float) (cop.getZ() + Math.sin(spin) * (y - cop.getY()) + Math.cos(spin)
						* (z - cop.getZ())));
			}
			if (e.getY() > startY) {
				lap.setY((float) (cop.getY() + Math.cos(-spin) * (y - cop.getY()) - Math.sin(-spin)
						* (z - cop.getZ())));
				lap.setZ((float) (cop.getZ() + Math.sin(-spin) * (y - cop.getY()) + Math.cos(-spin)
						* (z - cop.getZ())));
			}
			startX = e.getX();
			startY = e.getY();
			Camera.setLookAtPoint(lap);
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

	public static class MyKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			Matrix4f cam = Camera.getCameraMatrix();
			Vector3f trans = new Vector3f();
			Vector3f cop = Camera.getCenterOfProjection();
			Vector3f lap = Camera.getLookAtPoint();
			Vector3f uv = Camera.getUpVector();
			Vector3f look = new Vector3f();
			look.set(lap);
			look.sub(cop);
			cam.get(trans);
			int shift = 1;
			switch(e.getKeyChar()) {
			case 'w': {
				cop.setY(cop.getY() + shift);
				lap.setY(lap.getY() + shift);
				break;
			}
			case 's': {
				cop.setY(cop.getY() - shift);
				lap.setY(lap.getY() - shift);
				break;
			}
			case 'd': {
				cop.setX(cop.getX() + shift);
				lap.setX(lap.getX() + shift);
				break;
			}
			case 'a': {
				cop.setX(cop.getX() - shift);
				lap.setX(lap.getX() - shift);
				break;
			}
			}
			Camera.setCenterOfProjection(cop);
			Camera.setUpVector(uv);
			Camera.setLookAtPoint(lap);
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
		terrain = simple23.getTerrain();
		sceneManager.addShape(terrain);
		Camera.setCenterOfProjection(new Vector3f(30, 20, 50));
		Camera.setLookAtPoint(new Vector3f(40, 20, 0));

		renderPanel = new SimpleRenderPanel();

		JFrame jframe = new JFrame("simple 2-4 Camera");
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().add(renderPanel.getCanvas());

		renderPanel.getCanvas().addKeyListener(new MyKeyListener());
		renderPanel.getCanvas().addMouseMotionListener(new SimpleMouseListener());
		renderPanel.getCanvas().addMouseListener(new SimpleMouseListener());

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}

}
