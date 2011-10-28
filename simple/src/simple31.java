/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * Solution for 1st Task of 3rd Exercise
 */

import javax.swing.JFrame;
import javax.vecmath.Vector3f;

import jrtr.Camera;
import jrtr.Material;
import jrtr.RenderContext;
import jrtr.RenderPanel;
import jrtr.SWRenderPanel;
import jrtr.Shape;
import jrtr.SimpleSceneManager;
import jrtr.VertexData;

public class simple31 {
	static RenderPanel renderPanel;
	static RenderContext renderContext;
	static SimpleSceneManager sceneManager;
	static Shape shape;

	public final static class SimpleRenderPanel extends SWRenderPanel {
		@Override
		public void init(RenderContext r) {
			renderContext = r;
			renderContext.setSceneManager(sceneManager);
		}
	}

	public static void main(String[] args) {

		int sh = 3;
		if (sh == 0)
			shape = simple21.makeTorus(100, 2, 1, 0, 0, 0);
		if (sh == 1)
			shape = simple21.makeHouse();
		if (sh == 2)
			shape = simple21.makeZylinder(10);
		if (sh == 3) {
			float squareVertex[] = { -5, -5, 0, 5, -5, 0, 5, 5, 0, -5, 5, 0 };
			float squareColors[] = { 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 };
			int squareFaces[] = { 0, 2, 1, 0, 2, 3 };
			float squareTexture[] = { 0, 0, 1, 0, 1, 1, 0, 1 };

			VertexData squareData = new VertexData(4);
			squareData.addElement(squareColors, VertexData.Semantic.COLOR, 3);
			squareData.addElement(squareVertex, VertexData.Semantic.POSITION, 3);
			squareData.addElement(squareTexture, VertexData.Semantic.TEXCOORD, 2);

			squareData.addIndices(squareFaces);
			shape = new Shape(squareData);
			Material mat = new Material();
			shape.setMaterial(mat);
		}
		if (sh == 4)
			shape = simple21.makePlane();

		Camera.setCenterOfProjection(new Vector3f(0, 0, -30));

		sceneManager = new SimpleSceneManager();
		sceneManager.addShape(shape);

		renderPanel = new SimpleRenderPanel();

		JFrame jframe = new JFrame("simple - SW Render");
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().add(renderPanel.getCanvas());

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}
}
