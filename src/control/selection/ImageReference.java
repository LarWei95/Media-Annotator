package control.selection;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class ImageReference extends MediaReference<BufferedImage>{


	public ImageReference(Path path, String checksum) {
		super(path, checksum);
	}

	public ImageReference(Path path) {
		super(path);
	}

	@Override
	public BufferedImage loadMedia() throws IOException {
		return ImageIO.read(this.getPath().toFile());
	}
}
