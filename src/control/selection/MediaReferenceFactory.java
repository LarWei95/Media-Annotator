package control.selection;

import java.awt.image.BufferedImage;

public abstract class MediaReferenceFactory<T> {
	public abstract MediaReference<T> generateEmpty();
	
	public static MediaReferenceFactory<BufferedImage> getImageInstance () {
		return new ImageReferenceFactory();
	}
	
}

class ImageReferenceFactory extends MediaReferenceFactory<BufferedImage> {


	@Override
	public MediaReference<BufferedImage> generateEmpty() {
		return new ImageReference();
	}
	
}
