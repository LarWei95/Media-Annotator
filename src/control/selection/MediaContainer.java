package control.selection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.annotation.Annotation;
import view.annotation.media.MediaAnnotationPanel;

public class MediaContainer<T> {
	private ArrayList<MediaReference<T>> medias;
	private ArrayList<Annotation> annotations;
	
	private final MediaAnnotationPanel<T> annotator;
	private int mediaIndex;

	public MediaContainer (MediaAnnotationPanel<T> annotator) {
		this(annotator, new ArrayList<MediaReference<T>> (), new ArrayList<Annotation>());
	}
	
	public MediaContainer (MediaAnnotationPanel<T> annotator, List<MediaReference<T>> medias, List<Annotation> annotations) {
		this.medias = new ArrayList<MediaReference<T>>(medias);
		this.annotations = new ArrayList<Annotation>(annotations);
		
		this.annotator = annotator;
		this.mediaIndex = -1;
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
	
	public void updateCurrentAnnotation () {
		if (this.mediaIndex != -1) {
			this.annotations.set(this.mediaIndex, this.annotator.getAnnotation());
		}
	}
	
	public void setSelectedMedia (int index) {
		this.updateCurrentAnnotation();
		
		this.annotator.clear();
		
		if (this.mediaIndex != -1) {
			this.annotator.setAnnotation(this.annotations.get(index));
		}
		
		this.annotator.setMedia(this.medias.get(index).getMedia());
		this.mediaIndex = index;
	}
}
