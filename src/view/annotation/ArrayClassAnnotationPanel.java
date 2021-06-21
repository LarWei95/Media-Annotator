package view.annotation;

import java.util.ArrayList;

import javax.swing.JPanel;

import model.Annotation;
import model.ArrayClassAnnotation;
import view.elements.ChangeEmitter;
import view.elements.MappablePanel;

public class ArrayClassAnnotationPanel extends MappableAnnotationPanel {

	public ArrayClassAnnotationPanel(ChangeEmitter emitter, String[] options) {
		super(emitter, options);
	}

	@Override
	protected String getDefaultTabName() {
		return String.valueOf(this.annotationPanels.size());
	}

	@Override
	protected boolean isIdentifierChangeable() {
		return false;
	}
	
	@Override
	public void updateOnForwardedChange() {
		super.updateOnForwardedChange();
	}

	@Override
	public Annotation getAnnotation() {
		ArrayList<Annotation> annotations = new ArrayList<Annotation>(this.annotationPanels.size());
		
		Annotation current;
		
		for (int i = 0; i < this.annotationPanels.size(); i++) {
			current = this.mappablePanels.get(i).getAnnotation();
			
			if (current != null) {
				annotations.add(current);
			}
		}
		
		return new ArrayClassAnnotation(annotations);
	}

	/**
	 * Create the panel.
	 */
	

	
	@Override
	protected void updateTabNames () {
		int count = this.mappablePanels.size();
		
		MappablePanel panel;
		String identifier;
		
		for (int i = 0; i < count; i++) {
			panel = this.mappablePanels.get(i);
			identifier = String.valueOf(i+1);
			
			panel.setAnnotationIdentifier(identifier);
			this.annotationTabs.setTitleAt(i, identifier);
			
		}
	}
}
