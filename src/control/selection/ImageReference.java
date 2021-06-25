package control.selection;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class ImageReference extends MediaReference<BufferedImage>{

	public ImageReference(Path path, BufferedImage media) {
		super(path, media);
	}

	@Override
	public BufferedImage loadMedia() throws IOException {
		return ImageIO.read(this.getPath().toFile());
	}
}
