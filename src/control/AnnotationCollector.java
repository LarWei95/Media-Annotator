package control;

import model.Annotation;

public abstract class AnnotationCollector {
	public AnnotationCollector (Annotation rootAnnotation) {
		this.collect(rootAnnotation);
	}
	
	protected abstract void collect (Annotation rootAnnotation);
}
