package jrtr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Manages textures for the software renderer. Not implemented here.
 */
public class SWTexture implements Texture {

	private BufferedImage texture;

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage tex) {
		texture = tex;
	}

	@Override
	public void load(String fileName) throws IOException {
		File f = new File(fileName);
		BufferedImage bi = ImageIO.read(f);
		texture = bi;
	}

}
