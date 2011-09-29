package jrtr;

import javax.media.opengl.GL3;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.*;
import java.nio.*;

/**
 * Manages OpenGL textures. This class will be used in the
 * "Texturing and Shading" project.
 */
public class GLTexture implements Texture {
	
	private GL3 gl;			// The OpenGL context that stores the texture
	private IntBuffer id;	// Stores the OpenGL texture identifier
	private int w, h;		// Width and height
	
	public GLTexture(GL3 gl)
	{
		this.gl = gl;
		id = IntBuffer.allocate(1);	// Make the buffer that will store the texture identifier
	}

	/**
	 * Load the texture from an image file.
	 */
	public void load(String fileName) throws IOException
	{
		BufferedImage i;
		
		File f = new File(fileName);
		i = ImageIO.read(f);
	
		gl.glPixelStorei(GL3.GL_UNPACK_ALIGNMENT, 1);
		gl.glGenTextures(1, id);
		gl.glBindTexture(GL3.GL_TEXTURE_2D, id.get(0));

		w = i.getWidth();
		h = i.getHeight();
		
		IntBuffer buf = getData(i);
		gl.glTexImage2D(GL3.GL_TEXTURE_2D, 0, GL3.GL_RGBA, w, h, 0, GL3.GL_RGBA, GL3.GL_UNSIGNED_BYTE, buf);			
	}
	
	public int getId()
	{
		return id.get(0);
	}
	
	/**
	 * Copy the image data into a buffer that can be passed to OpenGL.
	 */
	IntBuffer getData(BufferedImage img)
	{
		IntBuffer buf = IntBuffer.allocate(img.getWidth()*img.getHeight());
		
		for(int i=0; i<img.getHeight(); i++)
		{
			for(int j=0; j<img.getWidth(); j++)
			{
				// We need to shuffle the RGB values to pass them correctly to OpenGL. 
				int in = img.getRGB(j,i);
				int out = ((in & 0x000000FF) << 16) | (in & 0x0000FF00) | ((in & 0x00FF0000) >> 16);
				buf.put((img.getHeight()-i-1)*img.getWidth()+j, out);
			}
		}
		return buf;
	}
}
