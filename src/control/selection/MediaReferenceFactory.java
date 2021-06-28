package control.selection;

import java.awt.image.BufferedImage;

public abstract class MediaReferenceFactory<T> {
	public abstract MediaReference<T> generateEmpty();
	
	@SuppressWarnings("unchecked")
	public static <T> MediaReferenceFactory<T> select (Class<T> cls) {
		if (cls == BufferedImage.class) {
			return (MediaReferenceFactory<T>)getImageInstance();
		} else {
			String errMsg = "There is no factory for the given class: "+String.valueOf(cls);
			throw new IllegalArgumentException(errMsg);
		}
	}
	
	
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
