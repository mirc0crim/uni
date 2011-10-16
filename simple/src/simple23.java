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

	public final static class SimpleRenderPanel extends GLRenderPanel {
		@Override
		public void init(RenderContext r) {
			renderContext = r;
			renderContext.setSceneManager(sceneManager);
		}
	}

	public static void main(String[] args) {
		int s = 3;
		int size = (1 << s) + 1; // = 2^s +1
		// create grid
		float frac[][][] = new float[size][size][3];
		for (int i = 0; i < frac.length; i++)
			for (int j=0;j<frac[i].length; j++) {
				frac[i][j][0]=i;
				frac[i][j][1]=j;
				frac[i][j][2] = rand.nextInt(2);
			}
		// save grid in vertex
		float vertex[] = new float[size * size * 3];
		int index = 0;
		for (float[][] point : frac)
			for (float[] element : point)
				for (int k=0; k<3; k++){
					vertex[index] = element[k];
					index++;
				}
		// create triangles
		int triangles[][] = new int[2 * (size - 1) * (size - 1)][3];
		index = 0;
		for (int i = 0; i < size - 1; i++)
			for (int j=0;j<size-1;j++) {
				triangles[index][0]=j+i*size;
				triangles[index][1]=j+1+i*size;
				triangles[index][2]=j+size+i*size;
				index++;
				triangles[index][0]=j +i*size+1;
				triangles[index][1]=j+size+1 +i*size;
				triangles[index][2]=j+size +i*size;
				index++;
			}
		// save triangles in faces
		int faces[] = new int[triangles.length * 3];
		index = 0;
		for (int i = 0; i < 2 * (size - 1) * (size - 1); i++)
			for (int j=0;j<3;j++) {
				faces[index]=triangles[i][j];
				index++;
			}
		// create colors
		float color[] = new float[vertex.length];
		index = 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				color[index] = rand.nextFloat();
				color[++index] = rand.nextFloat();
				color[++index] = rand.nextFloat();
				index++;
			}

		Camera.setCenterOfProjection(new Vector3f(10, 15, 15));

		VertexData vertexData = new VertexData(size * size);
		vertexData.addElement(color, VertexData.Semantic.COLOR, 3);
		vertexData.addElement(vertex, VertexData.Semantic.POSITION, 3);
		vertexData.addIndices(faces);
		Shape shape = new Shape(vertexData);
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
