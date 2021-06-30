package control.clipboard;

import model.annotation.Annotation;

public class AnnotationClipboard {
	private Annotation annotation;
	
	public AnnotationClipboard () {
		this.annotation = null;
	}
	
	public Annotation getAnnotation () {
		return this.annotation;
	}
	
	public void setAnnotation (Annotation annotation) {
		this.annotation = annotation.copy();
	}
}
