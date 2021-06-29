package control.selection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.MediaType;
import model.annotation.Annotation;

public class MediaContainer<T> {
	protected final MediaType mediaType;
	protected final ArrayList<MediaReference<T>> medias;
	protected final ArrayList<Annotation> annotations;
	
	public MediaContainer (MediaType mediaType) {
		this(mediaType, new ArrayList<MediaReference<T>> (), new ArrayList<Annotation>());
	}
	
	public MediaContainer (MediaType mediaType, List<MediaReference<T>> medias, List<Annotation> annotations) {
		this.mediaType = mediaType;
		this.medias = new ArrayList<MediaReference<T>>(medias);
		this.annotations = new ArrayList<Annotation>(annotations);
	}
	
	public MediaContainer (MediaContainer<T> mediaContainer) {
		this(mediaContainer.mediaType, mediaContainer.medias, mediaContainer.annotations);
	}
	
	public final MediaType getMediaType () {
		return this.mediaType;
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
	
	public final boolean[] getValidities () {
		boolean[] valids = new boolean[this.medias.size()];
		
		for (int i = 0; i < valids.length; i++) {
			valids[i] = this.medias.get(i).isValid();
		}
		
		return valids;
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
