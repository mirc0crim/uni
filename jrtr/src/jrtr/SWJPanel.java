package jrtr;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyListener;
import java.awt.event.HierarchyEvent;
import javax.swing.JPanel;

/** 
 * Shows the bitmap rendered by the the software renderer. This class also
 * redirects requests to redraw the image (via <code>paintComponent</code>)
 * to the software renderer {@link SWRenderContext}.
 */
public class SWJPanel extends JPanel {

	static final long serialVersionUID = 0;
	/**
	 * This class notifies the renderer when it needs to resize its
	 * rendered image.
	 */
	private class SWPanelHierarchyBoundsListener implements HierarchyBoundsListener {

		private SWRenderContext renderContext;
		
		public SWPanelHierarchyBoundsListener(SWRenderContext renderContext)
		{
			this.renderContext = renderContext;
		}
		
		public void ancestorMoved(HierarchyEvent e)
		{
		}
		
		public void ancestorResized(HierarchyEvent e)
		{
			Dimension d = e.getChanged().getSize();
			renderContext.setViewportSize(d.width, d.height);
		}
	}

	private class SWPanelHierarchyListener implements HierarchyListener {

		private SWRenderContext renderContext;
		
		public SWPanelHierarchyListener(SWRenderContext renderContext)
		{
			this.renderContext = renderContext;
		}
		
		public void hierarchyChanged(HierarchyEvent e)
		{
			if((e.getID() & HierarchyEvent.HIERARCHY_CHANGED)!=0 & e.getChangedParent()!=null)
			{
				Dimension d = e.getChangedParent().getPreferredSize();
				renderContext.setViewportSize(d.width, d.height);
			}
		}
	}

	private SWRenderContext renderContext;
		
	public SWJPanel(SWRenderContext renderContext)
	{
		this.renderContext = renderContext;
		this.addHierarchyBoundsListener(new SWPanelHierarchyBoundsListener(renderContext));
		this.addHierarchyListener(new SWPanelHierarchyListener(renderContext));
	}
	
	/**
	 * Redirect paint requests to the renderer.
	 */
	public void paintComponent(Graphics g)
	{
		renderContext.display();
		((Graphics2D)g).drawImage(renderContext.getColorBuffer(), null, 0, 0);
	}
	
	public void reshape()
	{
	}
}
