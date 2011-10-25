/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * Solution for 1st Task of 3rd Exercise
 */

import javax.swing.JFrame;
import javax.vecmath.Matrix4f;

import jrtr.GLRenderPanel;
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
	static Shape torus;

	/**
	 * An extension of {@link GLRenderPanel} or {@link SWRenderPanel} to provide
	 * a call-back function for initialization.
	 */
	public final static class SimpleRenderPanel extends SWRenderPanel {
		@Override
		public void init(RenderContext r) {

			renderContext = r;
			renderContext.setSceneManager(sceneManager);
			Matrix4f t = torus.getTransformation();
			Matrix4f rotX = new Matrix4f();
			rotX.rotX(1);
			t.mul(rotX);
			torus.setTransformation(t);
		}
	}

	public static void main(String[] args) {

		int seg = 20;
		float mainRad = 2;
		float rad = 1;

		torus = makeTorus(seg, mainRad, rad, 0, 0, 0);

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

	private static float[] calcTorusVertex(int seg, float mainRadius, float radius, float x,
			float y, float z) {
		float[] circle = new float[seg * 3];
		float[] torVert = new float[3 * seg * seg];
		int i = 0;
		while (i < seg) {
			circle[3 * i] = (float) Math.cos(i * 2 * Math.PI / seg) * radius + mainRadius;
			circle[3 * i + 1] = (float) Math.sin(i * 2 * Math.PI / seg) * radius;
			circle[3 * i + 2] = 0;
			i++;
		}
		float cos, sin;
		i = 0;
		while (i < seg) {
			int k = 0;
			while (k < seg) {
				cos = (float) Math.cos(i * 2 * Math.PI / seg);
				sin = (float) Math.sin(i * 2 * Math.PI / seg);
				torVert[3 * k + 3 * i * seg] = circle[3 * k] * cos - circle[3 * i + 2] * sin + x;
				torVert[3 * k + 3 * i * seg + 1] = circle[3 * k + 1] + y;
				torVert[3 * k + 3 * i * seg + 2] = circle[3 * k] * sin + circle[3 * i + 2] * cos
				+ z;
				k++;
			}
			i++;
		}
		return torVert;
	}

	private static float[] calcTorusColors(int seg) {
		float[] torCol = new float[3 * seg * seg];
		int i = 0;
		while (i < seg * seg) {
			torCol[3 * i] = 1;
			if (i % 3 == 0) {
				torCol[3 * i + 1] = 1;
				torCol[3 * i + 2] = 0;
			} else if (i % 3 == 1) {
				torCol[3 * i + 1] = 0;
				torCol[3 * i + 2] = 1;
			} else {
				torCol[3 * i + 1] = 1;
				torCol[3 * i + 2] = 1;
			}
			i++;
		}
		return torCol;

	}

	private static int[] calcTorusFaces(int seg) {
		int[] torFac = new int[6 * seg * seg];
		int k = 0;
		int i = 0;
		int j = 0;
		while (k < seg - 1) {
			j = 0;
			i = 0;
			while (j < seg) { // side face one bottom two top
				torFac[3 * i + 3 * k * seg] = j + k * seg;
				torFac[3 * i + 1 + 3 * k * seg] = j + seg + k * seg;
				if (j != seg - 1)
					torFac[3 * i + 2 + 3 * k * seg] = j + 1 + seg + k * seg;
				else
					torFac[3 * i + 2 + 3 * k * seg] = seg + k * seg;
				i++;
				j++;
			}
			j = 0;
			while (j < seg - 1) { // side face one top two bottom
				torFac[3 * i + 3 * k * seg + 3 * seg * seg] = j + seg + 1 + k * seg;
				torFac[3 * i + 1 + 3 * k * seg + 3 * seg * seg] = j + k * seg;
				torFac[3 * i + 2 + 3 * k * seg + 3 * seg * seg] = j + 1 + k * seg;
				i++;
				j++;
			}
			torFac[3 * i + 3 * k * seg + 3 * seg * seg] = seg + k * seg;
			torFac[3 * i + 1 + 3 * k * seg + 3 * seg * seg] = 0 + k * seg;
			torFac[3 * i + 2 + 3 * k * seg + 3 * seg * seg] = seg - 1 + k * seg;
			k++;
			i++;
		}
		j = 0;
		i = 0;
		while (j < seg) { // last and first segment, one last and two first
			torFac[3 * i + 3 * k * seg] = j + k * seg;
			torFac[3 * i + 1 + 3 * k * seg] = j;
			if (j != seg - 1)
				torFac[3 * i + 2 + 3 * k * seg] = j + 1;
			else
				torFac[3 * i + 2 + 3 * k * seg] = 0;
			j++;
			i++;
		}
		j = 0;
		i = 0;
		k = 0;
		while (j < seg) { // last and first segment, one first and two last
			torFac[3 * i + 3 * k * seg + 3 * seg * seg] = j;
			torFac[3 * i + 1 + 3 * k * seg + 3 * seg * seg] = j + (seg - 1) * seg;
			if (j != 0)
				torFac[3 * i + 2 + 3 * k * seg + 3 * seg * seg] = j - 1 + (seg - 1) * seg;
			else
				torFac[3 * i + 2 + 3 * k * seg + 3 * seg * seg] = seg - 1 + (seg - 1) * seg;
			i++;
			j++;
		}
		return torFac;
	}

	public static Shape makeTorus(int resolution, float mainRad, float rad, float x, float y,
			float z) {
		float[] torusVertex;
		float[] torusColors;
		int[] torusFaces;

		torusVertex = calcTorusVertex(resolution, mainRad, rad, x, y, z);
		torusColors = calcTorusColors(resolution);
		torusFaces = calcTorusFaces(resolution);

		VertexData vertexData = new VertexData(resolution * resolution);
		vertexData.addElement(torusVertex, VertexData.Semantic.POSITION, 3);
		vertexData.addElement(torusColors, VertexData.Semantic.COLOR, 3);

		vertexData.addIndices(torusFaces);

		return new Shape(vertexData);
	}

}
