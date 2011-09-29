package jrtr;

import javax.swing.JPanel;

/**
 * Implements a panel for a software renderer. It uses a {@link SWJPanel}to
 * display a bitmap rendered by the software renderer ({@link SWRenderContext}). 
 * This class and the class {@link SWRenderContext} are used in project 3.
 * <p> 
 * The user needs to extend this class and provide an implementation
 * for the <code>init</code> call-back function. To use the software 
 * renderer instead of the OpenGL renderer in your application, you will 
 * simply derive your render panel from {@link SWRenderPanel} instead of
 * {@link GLRenderPanel}.
 */
public abstract class SWRenderPanel implements RenderPanel {

	private SWJPanel canvas;
	private SWRenderContext renderContext;
	
	public SWRenderPanel()
	{
		renderContext = new SWRenderContext();
		canvas = new SWJPanel(renderContext);
		
		// Invoke user provided init call-back
		init(renderContext);
	}
	
	/**
	 * Return the AWT component that contains the rendered image. The user application
	 * needs to call this. The returned component is usually added to an application 
	 * window.
	 */
	public JPanel getCanvas() 
	{
		return canvas;
	}

	/**
	 * This needs to be implemented by the derived class.
	 */
	public abstract void init(RenderContext renderContext);
}
