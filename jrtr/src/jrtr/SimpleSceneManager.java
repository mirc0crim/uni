package jrtr;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Iterator;

/**
 * A simple scene manager that stores objects in a linked list.
 */
public class SimpleSceneManager implements SceneManagerInterface {

	private LinkedList<Shape> shapes;
	private Camera camera;
	private Frustum frustum;
	
	public SimpleSceneManager()
	{
		shapes = new LinkedList<Shape>();
		camera = new Camera();
		frustum = new Frustum();
	}
	
	public Camera getCamera()
	{
		return camera;
	}
	
	public Frustum getFrustum()
	{
		return frustum;
	}
	
	public void addShape(Shape shape)
	{
		shapes.add(shape);
	}
	
	public SceneManagerIterator iterator()
	{
		return new SimpleSceneManagerItr(this);
	}
	
	/**
	 * To be implemented in the "Textures and Shading" project.
	 */
	public Iterator<Light> lightIterator()
	{
		return null;
	}

	private class SimpleSceneManagerItr implements SceneManagerIterator {
		
		public SimpleSceneManagerItr(SimpleSceneManager sceneManager)
		{
			itr = sceneManager.shapes.listIterator(0);
		}
		
		public boolean hasNext()
		{
			return itr.hasNext();
		}
		
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
