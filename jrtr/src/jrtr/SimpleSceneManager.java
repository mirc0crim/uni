package jrtr;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * A simple scene manager that stores objects in a linked list.
 */
public class SimpleSceneManager implements SceneManagerInterface {

	private LinkedList<Shape> shapes;
	private LinkedList<Light> lights;
	private Camera camera;
	private Frustum frustum;
	private final int MAX_LIGHTS = 5;

	public SimpleSceneManager()
	{
		shapes = new LinkedList<Shape>();
		lights = new LinkedList<Light>();
		camera = new Camera();
		frustum = new Frustum();
	}

	@Override
	public Camera getCamera()
	{
		return camera;
	}

	@Override
	public Frustum getFrustum()
	{
		return frustum;
	}

	public void addShape(Shape shape)
	{
		shapes.add(shape);
	}

	public void addLight(Light light) {
		if (lights.size() < MAX_LIGHTS)
			lights.add(light);
		else
			System.out.println("Not more than " + MAX_LIGHTS + " Lights allowed!");
	}

	@Override
	public SceneManagerIterator iterator()
	{
		return new SimpleSceneManagerItr(this);
	}

	/**
	 * To be implemented in the "Textures and Shading" project.
	 */
	@Override
	public Iterator<Light> lightIterator() {
		return lights.iterator();
	}

	private class SimpleSceneManagerItr implements SceneManagerIterator {

		public SimpleSceneManagerItr(SimpleSceneManager sceneManager)
		{
			itr = sceneManager.shapes.listIterator(0);
		}

		@Override
		public boolean hasNext()
		{
			return itr.hasNext();
		}

		@Override
		public RenderItem next()
		{
			Shape shape = itr.next();
			// Here the transformation in the RenderItem is simply the
			// transformation matrix of the shape. More sophisticated
			// scene managers will set the transformation for the
			// RenderItem differently.
			return new RenderItem(shape, shape.getTransformation());
		}

		ListIterator<Shape> itr;
	}
}
