/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * Solution for 1st Task of 3rd Exercise
 */

import java.io.IOException;

import javax.swing.JFrame;
import javax.vecmath.Vector3f;

import jrtr.Camera;
import jrtr.Material;
import jrtr.RenderContext;
import jrtr.RenderPanel;
import jrtr.SWRenderPanel;
import jrtr.Shape;
import jrtr.SimpleSceneManager;

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

		int sh = 0;
		if (sh == 0)
			shape = simple21.makeTorus(100, 2, 1, 0, 0, 0);
		if (sh == 1)
			shape = simple21.makeHouse();
		if (sh == 2)
			shape = simple21.makeZylinder(10);
		if (sh == 3)
			shape = simple21.makeSquare();
		if (sh == 4)
			shape = simple21.makePlane();

		Camera.setCenterOfProjection(new Vector3f(5, 10, -10));

		sceneManager = new SimpleSceneManager();
		sceneManager.addShape(shape);
		renderPanel = new SimpleRenderPanel();

		Material mat = new Material();
		mat.setTexture(renderContext.makeTexture());
		try {
			mat.getTexture().load("../simple/texture/texture.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		shape.setMaterial(mat);

		JFrame jframe = new JFrame("simple - SW Render");
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().add(renderPanel.getCanvas());

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}
}
