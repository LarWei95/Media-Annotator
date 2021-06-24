package control.selection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Annotation;
import view.annotation.media.IMediaAnnotator;

public class MediaContainer<T> {
	private ArrayList<MediaReference<T>> medias;
	private ArrayList<Annotation> annotations;
	
	private final IMediaAnnotator<T> annotator;
	private int mediaIndex;

	public MediaContainer (IMediaAnnotator<T> annotator) {
		this.medias = new ArrayList<MediaReference<T>> ();
		this.annotations = new ArrayList<Annotation>();
		
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
