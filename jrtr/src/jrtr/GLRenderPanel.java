package jrtr;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import java.awt.Component;

/**
 * An implementation of the {@link RenderPanel} interface using
 * OpenGL. Its purpose is to provide an AWT component that displays
 * the rendered image. The class {@link GLRenderContext} performs the actual
 * rendering. The user needs to extend this class and provide an 
 * implementation for the <code>init</code> call-back function.
 */
public abstract class GLRenderPanel implements RenderPanel {

	/**
	 * An event listener for the GLJPanel to which this context renders.
	 * The main purpose of this event listener is to redirect display 
	 * events to the renderer (the {@link GLRenderContext}).
	 */
	private class GLRenderContextEventListener implements GLEventListener
	{
		private GLRenderPanel renderPanel;
		private GLRenderContext renderContext;
		
		public GLRenderContextEventListener(GLRenderPanel renderPanel)
		{
			this.renderPanel = renderPanel;
		}
		
		/**
		 * Initialization call-back. Makes a render context (a renderer) using 
		 * the provided <code>GLAutoDrawable</code> and calls the user provided
		 * <code>init</code> of the render panel.
		 */
		public void init(GLAutoDrawable drawable)
		{
			renderContext = new GLRenderContext(drawable);
			// Invoke the user-provided call back function
			renderPanel.init(renderContext);	
		}
		
		/**
		 * Redirect the display event to the renderer.
		 */
		public void display(GLAutoDrawable drawable)
		{
			renderContext.display(drawable);
		}
		
		public void reshape(GLAutoDrawable drawable, int x, int y, int width,
		    int height)
		{
		}
		
		public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
		    boolean deviceChanged)
		{
		}
		
		public void dispose(GLAutoDrawable g)
		{
		}
	}

	/**
	 * Because of problems with the computers in the ExWi pool, we are using 
	 * <code>GLCanvas</code>, which is based on AWT, instead of 
	 * <code>GLJPanel</code>, which is based on Swing.
	 */
	private	GLCanvas canvas;

	public GLRenderPanel()
	{
	    canvas = new GLCanvas();
	    
		GLEventListener eventListener = new GLRenderContextEventListener(this);
		canvas.addGLEventListener(eventListener);
	}

	/**
	 * Return the AWT component that contains the rendered image. The user application
	 * needs to call this. The returned component is usually added to an application 
	 * window.
	 */
	public final Component getCanvas() 
	{
		return canvas;
	}

	/**
	 * This call-back function needs to be implemented by the user.
	 */
	abstract public void init(RenderContext renderContext);
}
