/*
 * Author: Mirco Kocher
 * Matrikelno: 09-113-739
 * Solution for 3rd Task of 2nd Exercise
 */

import java.util.Random;

import javax.swing.JFrame;
import javax.vecmath.Vector3f;

import jrtr.Camera;
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
	static Random rand = new Random();
	static float[][] area;
	static int division;
	static int hight;
	static Shape terrain;

	public final static class SimpleRenderPanel extends GLRenderPanel {
		@Override
		public void init(RenderContext r) {
			renderContext = r;
			renderContext.setSceneManager(sceneManager);
		}
	}

	public static void main(String[] args) {
		Camera.setCenterOfProjection(new Vector3f(5, 0, 40));
		Camera.setLookAtPoint(new Vector3f(30, 30, 0));

		sceneManager = new SimpleSceneManager();
		sceneManager.addShape(getTerrain());

		renderPanel = new SimpleRenderPanel();

		JFrame jframe = new JFrame("simple 2-3 Fraktal");
		jframe.setSize(500, 500);
		jframe.setLocationRelativeTo(null);
		jframe.getContentPane().add(renderPanel.getCanvas());

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	}

	private static void createArea(int s, float rough) {
		division = 1 << s;
		area = new float[division + 1][division + 1];
		area[0][0] = 0;
		area[0][division] = rh();
		area[division][0] = rh();
		area[division][division] = 10;
		for (int i = 0; i < s; ++i) {
			int r = 1 << s - i, m = r >> 1;
			for (int j = 0; j < division; j += r)
				for (int k = 0; k < division; k += r)
					diamond(j, k, r, rough);
			if (m > 0)
				for (int j = 0; j <= division; j += m)
					for (int k = (j + m) % r; k <= division; k += r)
						square(j - m, k - m, r, rough);
			rough -= 0.1f * rough;
		}
	}

	private static void square(int x, int y, int side, float rough) {
		int half = side / 2;
		float avg = 0, sum = 0;
		if (x >= 0) {
			avg += area[x][y + half];
			sum += 1.0;
		}
		if (x + side <= division) {
			avg += area[x + side][y + half];
			sum += 1.0;
		}
		if (y >= 0) {
			avg += area[x + half][y];
			sum += 1.0;
		}
		if (y + side <= division) {
			avg += area[x + half][y + side];
			sum += 1.0;
		}
		area[x + half][y + half] = avg / sum + rand.nextFloat() * rough - rough / 2;
	}

	private static void diamond(int x, int y, int side, float rough) {
		if (side <= 1)
			return;
		float avg = area[x][y] + area[x][y + side] + area[x + side][y] + area[x + side][y + side];
		int half = side / 2;
		area[x + half][y + half] = avg / 4 + rand.nextFloat() * rough - rough / 2;
	}

	private static float getHeight(int i, int j) {
		return area[i][j];
	}

	private static float getColorR(int i, int j) {
		float red = getHeight(i, j) / 10;
		if (red < 0.3 || red > 0.7)
			red = 0;
		return red;
	}

	private static float getColorG(int i, int j) {
		float green = getHeight(i, j) / 10;
		if (green < 0.3)
			green = 0;
		return green;
	}

	private static float getColorB(int i, int j) {
		float blue = 1 - getHeight(i, j) / 10;
		if (blue < 0.3)
			blue = 0;
		return blue;
	}

	private static float rh() {
		return rand.nextFloat() * hight;
	}

	public static Shape getTerrain() {
		int s = 6;
		int size = (1 << s) + 1; // = 2^s +1
		hight = 10;
		float r = 2f; // best between 1 and 3
		createArea(s, r);
		// create grid
		float frac[][][] = new float[size][size][3];
		for (int i = 0; i < frac.length; i++)
			for (int j = 0; j < frac[i].length; j++) {
				frac[i][j][0] = i;
				frac[i][j][1] = j;
				frac[i][j][2] = getHeight(i, j);
			}
		// save grid in vertex
		float vertex[] = new float[size * size * 3];
		int index = 0;
		for (float[][] point : frac)
			for (float[] element : point)
				for (int k = 0; k < 3; k++) {
					vertex[index] = element[k];
					index++;
				}
		// create triangles
		int triangles[][] = new int[2 * (size - 1) * (size - 1)][3];
		index = 0;
		for (int i = 0; i < size - 1; i++)
			for (int j = 0; j < size - 1; j++) {
				triangles[index][0] = j + i * size;
				triangles[index][1] = j + 1 + i * size;
				triangles[index][2] = j + size + i * size;
				index++;
				triangles[index][0] = j + i * size + 1;
				triangles[index][1] = j + size + 1 + i * size;
				triangles[index][2] = j + size + i * size;
				index++;
			}
		// save triangles in faces
		int faces[] = new int[triangles.length * 3];
		index = 0;
		for (int i = 0; i < 2 * (size - 1) * (size - 1); i++)
			for (int j = 0; j < 3; j++) {
				faces[index] = triangles[i][j];
				index++;
			}
		// create colors
		float color[] = new float[vertex.length];
		index = 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				color[index] = getColorR(i, j);
				color[++index] = getColorG(i, j);
				color[++index] = getColorB(i, j);
				index++;
			}

		VertexData vertexData = new VertexData(size * size);
		vertexData.addElement(color, VertexData.Semantic.COLOR, 3);
		vertexData.addElement(vertex, VertexData.Semantic.POSITION, 3);
		vertexData.addIndices(faces);
		return terrain = new Shape(vertexData);
	}

}
