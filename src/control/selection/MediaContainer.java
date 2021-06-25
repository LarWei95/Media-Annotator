package control.selection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.annotation.Annotation;

public class MediaContainer<T> {
	protected final ArrayList<MediaReference<T>> medias;
	protected final ArrayList<Annotation> annotations;
	
	public MediaContainer () {
		this(new ArrayList<MediaReference<T>> (), new ArrayList<Annotation>());
	}
	
	public MediaContainer (List<MediaReference<T>> medias, List<Annotation> annotations) {
		this.medias = new ArrayList<MediaReference<T>>(medias);
		this.annotations = new ArrayList<Annotation>(annotations);
	}
	
	public MediaContainer (MediaContainer<T> mediaContainer) {
		this(mediaContainer.medias, mediaContainer.annotations);
	}
	
	public void setContainer (ArrayList<MediaReference<T>> medias, ArrayList<Annotation> annotations) {
		if (medias.size() == annotations.size()) {
			this.medias.clear();
			this.annotations.clear();
			
			this.medias.addAll(medias);
			this.annotations.addAll(annotations);
		} else {
			String errMsg = "The given media container contents are not of the same size.\n"+medias.size()+"\n"+annotations.size();
			throw new IllegalArgumentException(errMsg);
		}
	}
	
	public final void loadAll () throws IOException{
		for (MediaReference<T> ref: this.medias) {
			ref.load();
		}
	}
	
	public final void unloadAll () {
		for (MediaReference<T> ref: this.medias) {
			ref.unload();
		}
	}
	
	public ArrayList<MediaReference<T>> getMedias () {
		return this.medias;
	}
	
	public ArrayList<Annotation> getAnnotations () {
		return this.annotations;
	}
	
	public void setAnnotationAt (Annotation annotation, int i) {
		this.annotations.set(i, annotation);
	}
	
	public void addBlankMedia (MediaReference<T> media) {
		this.medias.add(media);
		this.annotations.add(null);
	}
	
	public void addBlankMedias (MediaReference<T>[] medias) {
		for (MediaReference<T> ref: medias) {
			this.addBlankMedia(ref);
		}
	}
	
	public void addBlankMedias (List<MediaReference<T>> medias) {
		for (MediaReference<T> ref: medias) {
			this.addBlankMedia(ref);
		}
	}
}
