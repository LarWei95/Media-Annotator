package view.annotation.types;

import java.util.ArrayList;

import javax.swing.JPanel;

import control.ViewAnnotationLink;
import model.annotation.Annotation;
import model.annotation.ArrayClassAnnotation;
import model.annotation.BoxAnnotation;
import model.annotation.ClassAnnotation;
import model.annotation.MapClassAnnotation;
import view.ChangeEmitter;
import view.elements.MappablePanel;

public class ArrayClassAnnotationPanel extends MappableAnnotationPanel {

	public ArrayClassAnnotationPanel(ChangeEmitter emitter, ViewAnnotationLink viewAnnotationLink, String[] options) {
		super(emitter, viewAnnotationLink, options);
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

	@Override
	public void setAnnotation(Annotation annotation) {
		this.clear();
		
		if (annotation != null) {
			ArrayClassAnnotation anno = (ArrayClassAnnotation) annotation;
			ArrayList<Annotation> subAnnos = anno.getClasses();
			String selectedType;
			
			int i = 1;
			int lastIndex;
			
			for (Annotation subAnno: subAnnos) {
				if (subAnno instanceof ArrayClassAnnotation) {
					selectedType = MappableAnnotationPanel.TYPE_ARRAY_ANNO;	
				} else if (subAnno instanceof MapClassAnnotation) {
					selectedType = MappableAnnotationPanel.TYPE_MAP_ANNO;	
				} else if (subAnno instanceof ClassAnnotation) {
					selectedType = MappableAnnotationPanel.TYPE_CLASS_ANNO;	
				} else if (subAnno instanceof BoxAnnotation) {
					selectedType = MappableAnnotationPanel.TYPE_BOX_ANNO;	
				} else {
					selectedType = null;
				}
				
				this.addNewAnnotation(selectedType, false);
				lastIndex = this.mappablePanels.size() - 1;
				
				this.mappablePanels.get(lastIndex).setAnnotationIdentifier(String.valueOf(i));
				this.annotationPanels.get(lastIndex).setAnnotation(subAnno);
				
				i++;
			}
		}
		
		this.forwardChange();
	}
}
