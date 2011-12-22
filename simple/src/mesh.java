import java.io.IOException;

import javax.vecmath.Vector3f;

import jrtr.ObjReader;
import jrtr.Shape;
import jrtr.VertexData;

public class mesh {

	public static Shape makeCube() {
		float cubeVertex[] = { -1, -1, 1, 1, -1, 1, 1, 1, 1, -1, 1, 1, -1, -1, -1, -1, -1, 1, -1,
				1, 1, -1, 1, -1, 1, -1, -1, -1, -1, -1, -1, 1, -1, 1, 1, -1, 1, -1, 1, 1, -1, -1,
				1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, 1, -1, -1, 1, 1, -1, -1, 1, -1, -1, -1,
				1, -1, -1, 1, -1, 1 };

		float cubeColors[] = { 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1,
				0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1,
				0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1 };

		float normals[] = { 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1,
				0, 0, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0,
				0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0 };

		int cubeFaces[] = { 0, 2, 3, 0, 1, 2, 4, 6, 7, 4, 5, 6, 8, 10, 11, 8, 9, 10, 12, 14, 15,
				12, 13, 14, 16, 18, 19, 16, 17, 18, 20, 22, 23, 20, 21, 22 };

		VertexData cubeData = new VertexData(24);
		cubeData.addElement(cubeColors, VertexData.Semantic.COLOR, 3);
		cubeData.addElement(cubeVertex, VertexData.Semantic.POSITION, 3);
		cubeData.addElement(normals, VertexData.Semantic.NORMAL, 3);
		cubeData.addIndices(cubeFaces);
		Shape cube = new Shape(cubeData);

		return cube;
	}

	public static Shape makeHouse() {
		float vertices[] = { -4, -4, 4, 4, -4, 4, 4, 4, 4, -4, 4, 4, -4, -4, -4, -4, -4, 4, -4, 4,
				4, -4, 4, -4, 4, -4, -4, -4, -4, -4, -4, 4, -4, 4, 4, -4, 4, -4, 4, 4, -4, -4, 4,
				4, -4, 4, 4, 4, 4, 4, 4, 4, 4, -4, -4, 4, -4, -4, 4, 4, -4, -4, 4, -4, -4, -4, 4,
				-4, -4, 4, -4, 4, -20, -4, 20, 20, -4, 20, 20, -4, -20, -20, -4, -20, -4, 4, 4, 4,
				4, 4, 0, 8, 4, 4, 4, 4, 4, 4, -4, 0, 8, -4, 0, 8, 4, -4, 4, 4, 0, 8, 4, 0, 8, -4,
				-4, 4, -4, 4, 4, -4, -4, 4, -4, 0, 8, -4 };

		float normals[] = { 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1,
				0, 0, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0,
				0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, -1, 0, 0, 1,
				0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0.707f, 0.707f, 0, 0.707f,
				0.707f, 0, 0.707f, 0.707f, 0, 0.707f, 0.707f, 0, -0.707f, 0.707f, 0, -0.707f,
				0.707f, 0, -0.707f, 0.707f, 0, -0.707f, 0.707f, 0, 0, 0, -1, 0, 0, -1, 0, 0, -1 };

		float colors[] = { 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0,
				1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0,
				0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0.5f, 0, 0, 0.5f, 0,
				0, 0.5f, 0, 0, 0.5f, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0,
				0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, };

		int indices[] = { 0, 2, 3, 0, 1, 2, 4, 6, 7, 4, 5, 6, 8, 10, 11, 8, 9, 10, 12, 14, 15, 12,
				13, 14, 16, 18, 19, 16, 17, 18, 20, 22, 23, 20, 21, 22, 24, 26, 27, 24, 25, 26, 28,
				29, 30, 31, 33, 34, 31, 32, 33, 35, 37, 38, 35, 36, 37, 39, 40, 41 };

		VertexData vertexData = new VertexData(42);
		vertexData.addElement(vertices, VertexData.Semantic.POSITION, 3);
		vertexData.addElement(colors, VertexData.Semantic.COLOR, 3);
		vertexData.addElement(normals, VertexData.Semantic.NORMAL, 3);
		vertexData.addIndices(indices);

		return new Shape(vertexData);
	}

	public static Shape makeSquare() {
		float squareVertex[] = { -5, -5, 0, 5, -5, 0, 5, 5, 0, -5, 5, 0 };
		float squareColors[] = { 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 };
		int squareFaces[] = { 0, 2, 1, 0, 2, 3 };
		float squareTexture[] = { 0, 0, 1, 0, 1, 1, 0, 1 };

		VertexData squareData = new VertexData(4);
		squareData.addElement(squareColors, VertexData.Semantic.COLOR, 3);
		squareData.addElement(squareVertex, VertexData.Semantic.POSITION, 3);
		squareData.addElement(squareTexture, VertexData.Semantic.TEXCOORD, 2);
		squareData.addIndices(squareFaces);

		return new Shape(squareData);
	}

	public static Shape makePlane() {
		float planeVertex[] = { -5, -3, 5, -5, -3, -5, 5, -3, -5, 5, -3, 5 };
		float planeColors[] = { 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 };
		float planeNormal[] = { 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0 };
		int planeFaces[] = { 0, 1, 2, 0, 3, 2 };

		VertexData planeData = new VertexData(4);
		planeData.addElement(planeColors, VertexData.Semantic.COLOR, 3);
		planeData.addElement(planeVertex, VertexData.Semantic.POSITION, 3);
		planeData.addElement(planeNormal, VertexData.Semantic.NORMAL, 3);
		planeData.addIndices(planeFaces);

		return new Shape(planeData);
	}

	private static float[] calcZylinderVertex(int Segments) {
		float[] zylVert = new float[6 * Segments];
		int i = 0;
		while (i < Segments) { // top circle
			zylVert[3 * i] = (float) Math.sin(i * 2 * Math.PI / Segments);
			zylVert[3 * i + 1] = 1;
			zylVert[3 * i + 2] = (float) Math.cos(i * 2 * Math.PI / Segments);
			i++;
		}
		int j = Segments;
		while (j < Segments * 2) { // bottom circle
			zylVert[3 * j] = (float) Math.sin(j * 2 * Math.PI / Segments);
			zylVert[3 * j + 1] = -1;
			zylVert[3 * j + 2] = (float) Math.cos(j * 2 * Math.PI / Segments);
			j++;
		}
		return zylVert;
	}

	private static float[] calcZylinderColors(int Segments) {
		float[] zylCol = new float[6 * Segments];
		int i = 0;
		while (i < Segments * 2) {
			zylCol[3 * i] = 0;
			if (i % 3 == 0) {
				zylCol[3 * i + 1] = 0;
				zylCol[3 * i + 2] = 1;
			} else if (i % 3 == 1) {
				zylCol[3 * i + 1] = 1;
				zylCol[3 * i + 2] = 0;
			} else {
				zylCol[3 * i + 1] = 1;
				zylCol[3 * i + 2] = 1;
			}
			i++;
		}
		return zylCol;
	}

	private static int[] calcZylinderFaces(int Segments) {
		int[] zylFac = new int[12 * Segments - 12];
		int i = 0;
		while (i < Segments - 2) { // Top face
			zylFac[3 * i] = 0;
			zylFac[3 * i + 1] = i + 1;
			zylFac[3 * i + 2] = i + 2;
			i++;
		}
		while (i < 2 * Segments - 4) { // bottom face
			zylFac[3 * i] = Segments;
			zylFac[3 * i + 1] = i + 3;
			zylFac[3 * i + 2] = i + 4;
			i++;
		}
		int j = 0;
		while (j < Segments - 1) { // side face one bottom two top
			zylFac[3 * i] = j;
			zylFac[3 * i + 1] = j + Segments;
			zylFac[3 * i + 2] = j + 1 + Segments;
			i++;
			j++;
		}
		zylFac[3 * i] = j;
		zylFac[3 * i + 1] = j + Segments;
		zylFac[3 * i + 2] = Segments;
		i++;
		j = 0;
		while (j < Segments - 1) { // side face one top two bottom
			zylFac[3 * i] = j + Segments + 1;
			zylFac[3 * i + 1] = j;
			zylFac[3 * i + 2] = j + 1;
			i++;
			j++;
		}
		zylFac[3 * i] = Segments;
		zylFac[3 * i + 1] = 0;
		zylFac[3 * i + 2] = Segments - 1;
		return zylFac;
	}

	public static Shape makeZylinder(int resolution) {
		float[] zylinderVertex = calcZylinderVertex(resolution);
		float[] zylinderColors = calcZylinderColors(resolution);
		int[] zylinderFaces = calcZylinderFaces(resolution);

		VertexData vertexData = new VertexData(2 * resolution);
		vertexData.addElement(zylinderVertex, VertexData.Semantic.POSITION, 3);
		vertexData.addElement(zylinderColors, VertexData.Semantic.COLOR, 3);
		vertexData.addElement(zylinderVertex, VertexData.Semantic.NORMAL, 3); // fake normals
		vertexData.addIndices(zylinderFaces);

		return new Shape(vertexData);
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
		float[] torusVertex = calcTorusVertex(resolution, mainRad, rad, x, y, z);
		float[] torusColors = calcTorusColors(resolution);
		int[] torusFaces = calcTorusFaces(resolution);
		float torusTexture[] = { 0, 0, 1, 0, 1, 1, 0, 1 };

		VertexData vertexData = new VertexData(resolution * resolution);
		vertexData.addElement(torusVertex, VertexData.Semantic.POSITION, 3);
		vertexData.addElement(torusColors, VertexData.Semantic.COLOR, 3);
		vertexData.addElement(torusTexture, VertexData.Semantic.TEXCOORD, 2);
		vertexData.addElement(torusVertex, VertexData.Semantic.NORMAL, 3); // fake normals
		vertexData.addIndices(torusFaces);

		return new Shape(vertexData);
	}

	public static Shape makeBall(int resolution) {
		if (resolution < 30)
			resolution = 30;
		if (resolution % 2 == 0)
			resolution++;
		float color[], ball[];
		int faces[];
		double phi = Math.PI * 2 / resolution;
		double theta = Math.PI / 2 * resolution;
		ball = new float[2 * (resolution - 2) * 3 * resolution + 3 * resolution + 6];
		int a = -1;
		for (int i = 0; i < ball.length / 3; i++) { // top
			ball[++a] = (float) (Math.cos(i * phi) * Math.sin(i * theta));
			ball[++a] = (float) (Math.sin(i * phi) * Math.sin(i * theta));
			ball[++a] = (float) Math.cos(i * theta);
		}
		color = new float[2 * (resolution - 2) * 3 * resolution + 3 * resolution + 6];
		a = -1;
		for (int i = 0; i < resolution; i++) { // fancy color
			color[++a] = 1;
			if (i % 3 == 0)
				color[++a] = 1;
			else
				color[++a] = 0;
			if (i % 3 == 1)
				color[++a] = 1;
			else
				color[++a] = 0;

			if (i % 3 == 0)
				color[++a] = 0;
			else
				color[++a] = 1;
			if (i % 3 == 1)
				color[++a] = 0;
			else
				color[++a] = 1;
			color[++a] = 0;
		}
		// top & bottom: 2*3*(resolution-2)
		// sides: 6*resolution
		faces = new int[12 * resolution - 12];
		a = -1;
		for (int i = 0; i < resolution - 2; i++) { // top
			faces[++a] = 0;
			faces[++a] = i + 1;
			faces[++a] = i + 2;
		}
		for (int i = resolution; i < 2 * resolution - 2; i++) { // bottom
			faces[++a] = resolution;
			faces[++a] = i + 2;
			faces[++a] = i + 1;
		}
		for (int i = 0; i < resolution - 1; i++) { // sides
			faces[++a] = i;
			faces[++a] = resolution + i;
			faces[++a] = resolution + i + 1;
			faces[++a] = i;
			faces[++a] = resolution + i + 1;
			faces[++a] = (i + 1) % resolution;
		}
		// last side
		faces[++a] = resolution - 1;
		faces[++a] = 2 * resolution - 1;
		faces[++a] = resolution;
		faces[++a] = resolution - 1;
		faces[++a] = resolution;
		faces[++a] = 0;

		VertexData ballData = new VertexData(ball.length / 3);
		ballData.addElement(ball, VertexData.Semantic.POSITION, 3);
		ballData.addElement(color, VertexData.Semantic.COLOR, 3);
		ballData.addElement(ball, VertexData.Semantic.NORMAL, 3); // fake normals
		ballData.addIndices(faces);

		return new Shape(ballData);
	}

	public static Shape makeTeapot() {

		VertexData vertexTeapot = null;
		try {
			vertexTeapot = ObjReader.read("../simple/teapot_tex.obj", 1);
		} catch (IOException e) {
			System.out.println("ObjReader Problem");
		}

		return new Shape(vertexTeapot);
	}

	public static float[] calcBezierColor(int seg) {
		float[] bezierColor = new float[seg];
		int i = 0;
		while (i < seg / 3) {
			bezierColor[3 * i] = 0;
			if (i % 3 == 0) {
				bezierColor[3 * i + 1] = 0;
				bezierColor[3 * i + 2] = 1;
			} else if (i % 3 == 1) {
				bezierColor[3 * i + 1] = 1;
				bezierColor[3 * i + 2] = 0;
			} else {
				bezierColor[3 * i + 1] = 1;
				bezierColor[3 * i + 2] = 1;
			}
			i++;
		}
		return bezierColor;
	}

	public static Shape makeBezier(int resolution, Vector3f[] controlPoints) {
		if (controlPoints.length < 4 ||
				controlPoints.length > 4 && (controlPoints.length - 4) % 3 != 0) {
			System.out.println("Too much or not enough control points");
			return null;
		}
		if (resolution < 2)
			resolution = 2;

		int segments = controlPoints.length == 4 ? 1 : (controlPoints.length - 4) / 3 + 1;
		float[] bezierVertex = new float[3 * segments * resolution * resolution];
		float[] bezierNormal = new float[bezierVertex.length];
		int[] bezierFace = new int[6 * (resolution * (segments * (resolution - 1) + 1) - 2)];

		float interpolatePoint = 0;
		int i = 0;
		bezierVertex[i++] = controlPoints[0].x;
		bezierVertex[i++] = controlPoints[0].y;
		bezierVertex[i++] = controlPoints[0].z;
		for (int j = 0; j < segments; j++) { // interpolate
			for (int k = 0; k < resolution - 1; k++) {
				interpolatePoint += 1f / (resolution - 1);
				Vector3f q1 = new Vector3f(controlPoints[0 + 3 * j]);
				q1.interpolate(controlPoints[1 + 3 * j], interpolatePoint);
				Vector3f q2 = new Vector3f(controlPoints[1 + 3 * j]);
				q2.interpolate(controlPoints[2 + 3 * j], interpolatePoint);
				Vector3f q3 = new Vector3f(controlPoints[2 + 3 * j]);
				q3.interpolate(controlPoints[3 + 3 * j], interpolatePoint);

				Vector3f r1 = new Vector3f(q1);
				r1.interpolate(q2, interpolatePoint);
				Vector3f r2 = new Vector3f(q2);
				r2.interpolate(q3, interpolatePoint);

				Vector3f x = new Vector3f(r1);
				x.interpolate(r2, interpolatePoint);

				Vector3f normal = new Vector3f(r2);
				normal.sub(r1);
				normal.cross(normal, new Vector3f(0, 1, 0));
				normal.normalize();

				bezierVertex[i] = x.getX();
				bezierNormal[i++] = normal.getX();
				bezierVertex[i] = x.getY();
				bezierNormal[i++] = 0;
				bezierVertex[i] = x.getZ();
				bezierNormal[i++] = -normal.getZ();

			}
			interpolatePoint = 0;
		}

		double a = 2 * Math.PI / resolution;
		int resPoint = 0;
		for (int j = 1; j < resolution; j++) // rotation
			for (int k = 0; k < (resolution - 1) * segments + 1; k++) {
				float x = bezierVertex[resPoint];
				float normalX = bezierNormal[resPoint];
				float y = bezierVertex[resPoint + 1];
				float normalY = bezierNormal[resPoint + 1];
				float z = bezierVertex[k * 3 + 2];
				float normalZ = bezierNormal[3 * k + 2];
				float radius = (float) Math.sqrt(x * x + y * y);
				bezierVertex[i] = (float) Math.cos(j * a) * radius;
				bezierNormal[i++] = (float) Math.cos(j * a) * normalX;
				bezierVertex[i] = (float) Math.sin(j * a) * radius;
				bezierNormal[i++] = (float) Math.sin(j * a) * normalY;
				bezierVertex[i] = z;
				bezierNormal[i++] = normalZ;
				resPoint += 3;
				resPoint %= 3 * ((resolution - 1) * segments + 1);
			}

		int row = (resolution - 1) * segments + 1;
		i = 0; // faces
		for (int j = 1; j < resolution - 1; j++) {
			bezierFace[i++] = 0;
			bezierFace[i++] = j * row;
			bezierFace[i++] = (j + 1) * row;
		}
		int init = row - 1;
		for (int j = 1; j < resolution - 1; j++) {
			bezierFace[i++] = init;
			bezierFace[i++] = init + j * row;
			bezierFace[i++] = init + (j + 1) * row;
		}
		for (int j = 0; j < row - 1; j++)
			for (int k = 0; k < resolution; k++) {
				bezierFace[i++] = j + k * row;
				bezierFace[i++] = j + k * row + 1;
				bezierFace[i++] = (j + 1 + (k + 1) * row) % (resolution * row);
				bezierFace[i++] = j + k * row;
				bezierFace[i++] = (j + 1 + (k + 1) * row) % (resolution * row);
				bezierFace[i++] = (j + 1 + (k + 1) * row) % (resolution * row) - 1;
			}

		float[] bezierColor = calcBezierColor(bezierVertex.length);

		VertexData vertexData = new VertexData(bezierVertex.length / 3);
		vertexData.addElement(bezierVertex, VertexData.Semantic.POSITION, 3);
		vertexData.addElement(bezierColor, VertexData.Semantic.COLOR, 3);
		vertexData.addElement(bezierNormal, VertexData.Semantic.NORMAL, 3);

		vertexData.addIndices(bezierFace);

		return new Shape(vertexData);
	}

}
