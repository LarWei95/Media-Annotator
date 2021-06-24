package view.annotation.types;

import control.ViewAnnotationLink;
import model.annotation.Annotation;
import view.ChangeEmitter;
import view.ChangeEmitterPanel;

public abstract class AnnotationPanel extends ChangeEmitterPanel{
	protected final ViewAnnotationLink viewAnnotationLink;
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 2932795140531877268L;
	
	/**
	 * Create the panel.
	 */
	public AnnotationPanel(ChangeEmitter changeEmitter, ViewAnnotationLink viewAnnotationLink) {
		super(changeEmitter);
		this.viewAnnotationLink = viewAnnotationLink;
	}
	
	public abstract void fillActivePanelContainer (ActivePanelContainer activePanelContainer);
	
	public abstract void setAnnotation (Annotation annotation);
	
	public abstract Annotation getAnnotation ();
	
	public abstract void clear();
}
