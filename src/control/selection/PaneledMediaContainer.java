package control.selection;

import java.util.ArrayList;
import java.util.List;
import model.annotation.Annotation;
import view.annotation.media.MediaAnnotationPanel;

public class PaneledMediaContainer<T> extends MediaContainer<T>{
	
	
	private final MediaAnnotationPanel<T> annotator;
	private int mediaIndex;

	public PaneledMediaContainer (MediaAnnotationPanel<T> annotator) {
		this(annotator, new ArrayList<MediaReference<T>> (), new ArrayList<Annotation>());
	}
	
	public PaneledMediaContainer (MediaAnnotationPanel<T> annotator, List<MediaReference<T>> medias, List<Annotation> annotations) {
		super(medias, annotations);
		
		this.annotator = annotator;
		this.mediaIndex = -1;
	}
	
	public PaneledMediaContainer(MediaAnnotationPanel<T> annotator, MediaContainer<T> mediaContainer) {
		super(mediaContainer);
		
		this.annotator = annotator;
		this.mediaIndex = -1;
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
