package control.selection;

import java.util.ArrayList;
import java.util.List;

import model.MediaType;
import model.annotation.Annotation;
import view.media.MediaAnnotationPanel;

public class PaneledMediaContainer<T> extends SelectionMediaContainer<T>{
	
	
	private final MediaAnnotationPanel<T> annotator;

	public PaneledMediaContainer (MediaType mediaType, MediaAnnotationPanel<T> annotator) {
		this(mediaType, annotator, new ArrayList<MediaReference<T>> (), new ArrayList<Annotation>());
	}
	
	public PaneledMediaContainer (MediaType mediaType, MediaAnnotationPanel<T> annotator, List<MediaReference<T>> medias, List<Annotation> annotations) {
		super(mediaType, medias, annotations);
		
		this.annotator = annotator;
	}
	
	public PaneledMediaContainer(MediaAnnotationPanel<T> annotator, MediaContainer<T> mediaContainer) {
		super(mediaContainer);
		
		this.annotator = annotator;
	}
	
	public MediaAnnotationPanel<T> getAnnotator () {
		return this.annotator;
	}
	
	public void setContainer (ArrayList<MediaReference<T>> medias, ArrayList<Annotation> annotations) {
		super.setContainer(medias, annotations);
		
		if (this.mediaIndex < this.annotations.size()) {
			this.setSelectedMedia(this.mediaIndex);
		} else {
			this.setSelectedMedia(0);
		}
	}
	
	public void updateCurrentAnnotation () {
		if (this.mediaIndex != -1) {
			this.annotations.set(this.mediaIndex, this.annotator.getAnnotation());
		}
	}
	
	@Override
	public void setSelectedMedia (int index) {
		if (this.annotator.inMultiView() == false) {
			this.updateCurrentAnnotation();
		}
		
		this.annotator.clear();
		
		if (index != -1) {
			this.annotator.setAnnotation(this.annotations.get(index));
			this.annotator.setMediaReference(this.medias.get(index));
			super.setSelectedMedia(index);
		}
	}
	
	@Override
	public void setSelectedMedias (int[] indices) {
		if (this.annotator.inMultiView() == false) {
			this.updateCurrentAnnotation();
		}
		
		this.annotator.clear();
		
		ArrayList<MediaReference<T>> medias = new ArrayList<MediaReference<T>>(indices.length);
		
		for (int ind: indices) {
			medias.add(this.medias.get(ind));
		}
		
		this.annotator.setMediaReferences(medias);
		super.setSelectedMedias(indices);
	}
}
