package view.media;

import javax.swing.JPanel;

import control.selection.MediaReference;
import model.annotation.Annotation;

public abstract class  MediaAnnotationPanel<T> extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3386056201591584996L;

	public abstract Annotation getAnnotation ();
	
	public abstract void setAnnotation (Annotation annotation);
	
	public abstract void setMediaReference (MediaReference<T> media);
	
	public abstract void clear ();
}
