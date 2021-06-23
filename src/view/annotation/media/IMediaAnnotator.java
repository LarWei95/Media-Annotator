package view.annotation.media;

import model.Annotation;

public interface IMediaAnnotator<T> {
	public Annotation getAnnotation ();
	
	public void setAnnotation (Annotation annotation);
	
	public void setMedia (T media);
	
	public void clear ();
}
