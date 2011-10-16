/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * Solution for 3rd Task of 2nd Exercise
 */

import javax.swing.JFrame;

import jrtr.GLRenderPanel;
import jrtr.RenderContext;
import jrtr.RenderPanel;
import jrtr.Shape;
import jrtr.SimpleSceneManager;
import jrtr.VertexData;

public class simple23 {
	static RenderPanel renderPanel;
	static RenderContext renderContext;
	static SimpleSceneManager sceneManager;
	static Shape shape;

	public final static class SimpleRenderPanel extends GLRenderPanel {
		@Override
		public void init(RenderContext r) {
			renderContext = r;
			renderContext.setSceneManager(sceneManager);
		}
	}

	public static void main(String[] args) {

		VertexData vertexData = new VertexData(size * size);
		vertexData.addElement(color, VertexData.Semantic.COLOR, 3);
		vertexData.addElement(vertex, VertexData.Semantic.POSITION, 3);
		vertexData.addIndices(faces);
		shape = new Shape(vertexData);
		sceneManager = new SimpleSceneManager();
		sceneManager.addShape(shape);

		renderPanel = new SimpleRenderPanel();

		JFrame jframe = new JFrame("simple 2-3 Fraktal");
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().add(renderPanel.getCanvas());

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}

}
