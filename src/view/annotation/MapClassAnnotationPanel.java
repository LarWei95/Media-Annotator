package view.annotation;

import java.util.HashMap;

import javax.swing.JPanel;

import control.ViewAnnotationLink;
import model.Annotation;
import model.MapClassAnnotation;
import view.elements.ChangeEmitter;
import view.elements.MappablePanel;

public class MapClassAnnotationPanel extends MappableAnnotationPanel {

	/**
	 * Create the panel.
	 */
	public MapClassAnnotationPanel(ChangeEmitter emitter, ViewAnnotationLink viewAnnotationLink, String[] options) {
		super(emitter, viewAnnotationLink, options);
	}

	@Override
	public void updateOnForwardedChange() {
		super.updateOnForwardedChange();
	}

	@Override
	public Annotation getAnnotation() {
		HashMap<String, Annotation> annotationMap = new HashMap<String, Annotation>();
		
		String key;
		Annotation annotation;
		
		for (MappablePanel mappablePanel: this.mappablePanels) {
			key = mappablePanel.getAnnotationIdentifier();
			annotation = mappablePanel.getAnnotation();
			
			if (annotation != null) {
				annotationMap.put(key, annotation);
			}
		}
		
		MapClassAnnotation finalAnnotation = new MapClassAnnotation(annotationMap);
		return finalAnnotation;
	}

	@Override
	protected String getDefaultTabName() {
		int count = this.mappablePanels.size();
		return "New "+count;
	}

	@Override
	protected boolean isIdentifierChangeable() {
		return true;
	}

}
