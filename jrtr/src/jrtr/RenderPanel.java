package jrtr;

import java.awt.Component;

/**
 * An interface to display images that are rendered
 * by a render context (a "renderer"). Currently, this
 * interface is implemented by {@link GLRenderPanel} and 
 * {@link SWRenderPanel}. 
 */
public interface RenderPanel {

	/**
	 * This is a call-back that needs to be implemented by the user
	 * to initialize the renderContext.
	 */
	void init(RenderContext renderContext);
	
	/** 
	 * Obtain a <code>Component</code> that contains the rendered 
	 * image.
	 */ 
	Component getCanvas();
}
