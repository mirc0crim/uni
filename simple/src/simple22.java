/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * Solution for 2nd Task of 2nd Exercise
 */

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

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

	public final static class SimpleRenderPanel extends GLRenderPanel {
		@Override
		public void init(RenderContext r) {

			renderContext = r;
			renderContext.setSceneManager(sceneManager);

			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new AnimationTask(), 0, 10);
		}
	}

	public static class AnimationTask extends TimerTask {
		@Override
		public void run() {
			renderPanel.getCanvas().repaint();
		}
	}

	public static class SimpleMouseListener implements MouseListener {
		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}
	}

	public static void main(String[] args) {

		VertexData vertexTeapot = null;

		try {
			vertexTeapot = ObjReader.read("../simple/teapot.obj", 1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		sceneManager = new SimpleSceneManager();
		Shape teapot = new Shape(vertexTeapot);
		sceneManager.addShape(teapot);

		renderPanel = new SimpleRenderPanel();

		JFrame jframe = new JFrame("simple");
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().add(renderPanel.getCanvas());
		jframe.addMouseListener(new SimpleMouseListener());

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}

}
