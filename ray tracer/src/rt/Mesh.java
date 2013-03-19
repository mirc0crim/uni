package rt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.vecmath.Vector3f;

/**
 * Represents a triangle mesh which consists of several triangles (the triangles
 * are aggregated to a mesh).
 */
public class Mesh extends Aggregate implements Intersectable {

	ArrayList<Triangle> triangles;
	String name;
	private Material material;
	Boundingbox box;
	

	public Mesh() {
		triangles = new ArrayList<Triangle>();
	}

	public void loadObjFile(File f, float scale) throws IOException {

		ArrayList<float[]> vertices = new ArrayList<float[]>();
		ArrayList<float[]> texCoords = new ArrayList<float[]>();
		ArrayList<float[]> normals = new ArrayList<float[]>();
		ArrayList<int[][]> faces = new ArrayList<int[][]>();
		ObjReader.read(f.getAbsolutePath(), scale, vertices, texCoords, normals, faces);

		// loop through faces in order to create triangle instances
		for (int[][] face : faces) {
			float[] point;
			point = vertices.get(face[0][0] - 1);
			Vector3f p1 = new Vector3f(point[0], point[1], point[2]);
			point = vertices.get(face[1][0] - 1);
			Vector3f p2 = new Vector3f(point[0], point[1], point[2]);
			point = vertices.get(face[2][0] - 1);
			Vector3f p3 = new Vector3f(point[0], point[1], point[2]);

			if (normals.size() > 0) {
				float[] normal;
				normal = normals.get(face[0][2] - 1);
				Vector3f n0 = new Vector3f(normal[0], normal[1], normal[2]);
				normal = normals.get(face[1][2] - 1);
				Vector3f n1 = new Vector3f(normal[0], normal[1], normal[2]);
				normal = normals.get(face[2][2] - 1);
				Vector3f n2 = new Vector3f(normal[0], normal[1], normal[2]);
				triangles.add(new Triangle(p1, p2, p3, n0, n1, n2));
			} else
				triangles.add(new Triangle(p1, p2, p3));
		}

		ArrayList<Boundingbox> bb = new ArrayList<Boundingbox>();
		for (Triangle t : triangles)
			bb.add(t.getBox());
		box = Boundingbox.combineBox(bb);
	}

	@Override
	public HitRecord intersect(Ray ray) {
		if (!intersectsBoundingbox(ray))
			return null;
		HitRecord tempHit = null;
		HitRecord hit = null;
		Iterator<Triangle> it = triangles.iterator();
		while (it.hasNext()) {
			Triangle triangle = it.next();
			tempHit = triangle.intersect(ray);
			if (tempHit != null) {
				if (hit == null)
					hit = tempHit;
				else if (tempHit.getT() < hit.getT())
					hit = tempHit;
			}
		}
		if (hit != null && this.material != null)
			hit.setMaterial(material);
		else if (hit != null && this.material == null)
			hit.setMaterial(new BlinnMaterial(new Spectrum(.6f, .6f, .6f)));
		return hit;
	}

	@Override
	public Boundingbox getBox() {
		return box;
	}

	private boolean intersectsBoundingbox(Ray ray) {
		float txmin, txmax, tymin, tymax;
		float d_x = ray.getDirection().x;
		float d_y = ray.getDirection().y;
		float e_x = ray.getOrigin().x;
		float e_y = ray.getOrigin().y;
		float x_min = getBox().bottomFrontLeft.x;
		float x_max = getBox().topBackRight.x;
		float y_min = getBox().bottomFrontLeft.y;
		float y_max = getBox().topBackRight.y;
		if (d_x >= 0) {
			txmin = (x_min - e_x) / d_x;
			txmax = (x_max - e_x) / d_x;
		} else {
			txmin = (x_max - e_x) / d_x;
			txmax = (x_min - e_x) / d_x;
		}
		if (d_y >= 0) {
			tymin = (y_min - e_y) / d_y;
			tymax = (y_max - e_y) / d_y;
		} else {
			tymin = (y_max - e_y) / d_y;
			tymax = (y_min - e_y) / d_y;
		}
		if ((txmin > tymax) || (tymin > txmax)) {
			return false;
		} else {
			return true;
		}
	}

	public ArrayList<Triangle> getTriangles() {
		return triangles;
	}

	public void setTriangles(ArrayList<Triangle> triang) {
		triangles = triang;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material mat) {
		material = mat;
	}
}