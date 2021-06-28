package control.selection;

import java.util.List;

import model.annotation.Annotation;

public abstract class SelectionMediaContainer<T> extends MediaContainer<T> {
	protected int mediaIndex;
	
	public SelectionMediaContainer() {
		super();
		this.mediaIndex = -1;
	}

	public SelectionMediaContainer(List<MediaReference<T>> medias, List<Annotation> annotations) {
		super(medias, annotations);
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
