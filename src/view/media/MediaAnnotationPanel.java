package view.media;

import java.util.List;

import javax.swing.JPanel;

import control.selection.MediaReference;
import model.annotation.Annotation;

public abstract class  MediaAnnotationPanel<T> extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3386056201591584996L;
	protected MediaReference<T> media;
	protected List<MediaReference<T>> medias;
	protected boolean multiView;
	
	public MediaAnnotationPanel () {
		this.media = null;
		this.medias = null;
		this.multiView = false;
	}
	
	public abstract Annotation getAnnotation ();
	
	public abstract void setAnnotation (Annotation annotation);
	
	public void setMediaReference (MediaReference<T> media) {
		this.media = media;
		this.multiView = false;
	}
	
	public  void setMediaReferences (List<MediaReference<T>> medias) {
		this.medias = medias;
		this.multiView = true;
	}
	
	public final boolean inMultiView () {
		return this.multiView;
	}
	
	public abstract void clear ();
}
