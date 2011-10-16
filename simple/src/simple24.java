/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * Solution for 4th Task of 2nd Exercise
 */

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.TimerTask;

import javax.swing.JFrame;

import jrtr.GLRenderPanel;
import jrtr.RenderContext;
import jrtr.RenderPanel;
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

	public static class AnimationTask extends TimerTask {
		@Override
		public void run() {
			renderPanel.getCanvas().repaint();
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

	public static void main(String[] args) {

		sceneManager = new SimpleSceneManager();
		// sceneManager.addShape(object);

		renderPanel = new SimpleRenderPanel();

		JFrame jframe = new JFrame("simple 2-4 Camera");
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().add(renderPanel.getCanvas());

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}

}
