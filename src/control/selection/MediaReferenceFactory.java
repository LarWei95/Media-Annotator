package control.selection;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Marking;
import model.MediaType;
import model.annotation.Annotation;

public abstract class MediaReferenceFactory<T> {	
	public abstract MediaReference<T> generateByPath (Path path);
	
	public abstract MediaReference<T> generateByPath (Path path, String hash);
	
	public abstract MediaReference<T> generateByPath (Path path, String hash, Marking marking);
	
	public abstract MediaType getMediaType ();
	
	public MediaContainer<T> ofCollections (Map<Path, String> hashes, Map<Path, Annotation> annotations, List<Path> order,
			List<Marking> markings) {
		MediaType mediaType = this.getMediaType();
		
		ArrayList<MediaReference<T>> finalMedias = new ArrayList<MediaReference<T>>(annotations.size());
		ArrayList<Annotation> finalAnnotations = new ArrayList<Annotation>(hashes.size());
		
		Path annotationPath;
		Annotation currentAnnotation;
		String currentHash;
		Marking currentMarking;
		
		for (int i = 0; i < order.size(); i++) {
			annotationPath = order.get(i);
			
			currentAnnotation = annotations.get(annotationPath);
			currentHash = hashes.getOrDefault(annotationPath, null);
			currentMarking = markings.get(i);
			
			finalMedias.add(this.generateByPath(annotationPath, currentHash, currentMarking));
			finalAnnotations.add(currentAnnotation);
		}
		
		return new MediaContainer<T>(mediaType, finalMedias, finalAnnotations);
	}
	
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
	public MediaReference<BufferedImage> generateByPath(Path path)  {
		return new ImageReference(path);
	}

	@Override
	public MediaReference<BufferedImage> generateByPath(Path path, String hash) {
		return new ImageReference(path, hash);
	}

	@Override
	public MediaType getMediaType() {
		return MediaType.IMAGE;
	}

	@Override
	public MediaReference<BufferedImage> generateByPath(Path path, String hash, Marking marking) {
		return new ImageReference(path, hash, marking);
	}
	
}
