package control.selection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Annotation;

public class MediaContainer<T> {
	private ArrayList<MediaReference<T>> medias;
	private ArrayList<Annotation> annotations;

	public MediaContainer () {
		this.medias = new ArrayList<MediaReference<T>> ();
		this.annotations = new ArrayList<Annotation>();
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
