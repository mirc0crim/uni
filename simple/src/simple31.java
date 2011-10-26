/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * Solution for 1st Task of 3rd Exercise
 */

import javax.swing.JFrame;

import jrtr.RenderContext;
import jrtr.RenderPanel;
import jrtr.SWRenderPanel;
import jrtr.Shape;
import jrtr.SimpleSceneManager;

public class simple31 {
	static RenderPanel renderPanel;
	static RenderContext renderContext;
	static SimpleSceneManager sceneManager;
	static Shape torus;

	public final static class SimpleRenderPanel extends SWRenderPanel {
		@Override
		public void init(RenderContext r) {
			renderContext = r;
			renderContext.setSceneManager(sceneManager);
		}
	}

	public static void main(String[] args) {

		torus = simple21.makeTorus(20, 2, 1, 0, 0, 0);

		sceneManager = new SimpleSceneManager();
		sceneManager.addShape(torus);

		renderPanel = new SimpleRenderPanel();

		JFrame jframe = new JFrame("simple - SW Render");
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().add(renderPanel.getCanvas());

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}
}
