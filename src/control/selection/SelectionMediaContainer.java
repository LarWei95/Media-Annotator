package control.selection;

import java.util.List;

import model.MediaType;
import model.annotation.Annotation;

public abstract class SelectionMediaContainer<T> extends MediaContainer<T> {
	protected int mediaIndex;
	
	public SelectionMediaContainer(MediaType mediaType) {
		super(mediaType);
		this.mediaIndex = -1;
	}

	public SelectionMediaContainer(MediaType mediaType, List<MediaReference<T>> medias, List<Annotation> annotations) {
		super(mediaType, medias, annotations);
		this.mediaIndex = -1;
	}

	public SelectionMediaContainer(MediaContainer<T> mediaContainer) {
		super(mediaContainer);
		this.mediaIndex = -1;
	}

	public void setSelectedMedia (int index) {
		this.mediaIndex = index;
	}
	
	public int getMediaIndex () {
		return this.mediaIndex;
	}
}
