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
	protected BVHAccelerator tree;


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
		HitRecord tempHit = null;
		HitRecord hit = null;
		Vector3f dirfrac = new Vector3f();
		dirfrac.x = 1.0f / ray.direction.x;
		dirfrac.y = 1.0f / ray.direction.y;
		dirfrac.z = 1.0f / ray.direction.z;
		ArrayList<Triangle> shorterList = tree.getTriangles(ray, tree.getRoot(), dirfrac);
		Iterator<Triangle> it = shorterList.iterator();
		if (shorterList.size() > tree.getRoot().getTriangles().size()) {
			it = tree.getRoot().getTriangles().iterator();
		}
		while (it.hasNext()) {
			Triangle triangle = it.next();
			tempHit = triangle.intersect(ray);
			if (tempHit != null)
				if (hit == null)
					hit = tempHit;
				else if (tempHit.getT() < hit.getT())
					hit = tempHit;
		}
		if (hit != null && material != null)
			hit.setMaterial(material);
		else if (hit != null && material == null)
			hit.setMaterial(new BlinnMaterial(new Spectrum(.6f, .6f, .6f)));
		return hit;
	}

	@Override
	public Boundingbox getBox() {
		return box;
	}

	public ArrayList<Triangle> getTriangles() {
		return triangles;
	}

	public void setTriangles(ArrayList<Triangle> triang) {
		triangles = triang;
	}

	public void setTree(BVHAccelerator t) {
		tree = t;
	}

	public BVHAccelerator getTree() {
		return tree;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material mat) {
		material = mat;
	}
}