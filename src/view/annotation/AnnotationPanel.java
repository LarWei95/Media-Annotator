package view.annotation;

import javax.swing.JPanel;

import control.IAnnotationSource;
import model.Annotation;
import view.elements.ChangeEmitter;
import view.elements.ChangeEmitterPanel;

public abstract class AnnotationPanel extends ChangeEmitterPanel implements IAnnotationSource{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2932795140531877268L;
	
	/**
	 * Create the panel.
	 */
	public AnnotationPanel(ChangeEmitter changeEmitter) {
		super(changeEmitter);
	}
}
