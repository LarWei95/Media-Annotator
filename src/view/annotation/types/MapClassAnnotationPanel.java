package view.annotation.types;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

import control.ViewAnnotationLink;
import model.Annotation;
import model.ArrayClassAnnotation;
import model.BoxAnnotation;
import model.ClassAnnotation;
import model.MapClassAnnotation;
import view.ChangeEmitter;
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

	@Override
	public void setAnnotation(Annotation annotation) {
		this.clear();
		
		if (annotation != null) {
			MapClassAnnotation anno = (MapClassAnnotation) annotation;
			HashMap<String, Annotation> subAnnos = anno.getClasses();
			String selectedType;
			
			Annotation subAnno;
			int lastIndex;
			
			for (String  key: subAnnos.keySet()) {
				subAnno = subAnnos.get(key);
				
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
				
				this.mappablePanels.get(lastIndex).setAnnotationIdentifier(key);
				this.annotationPanels.get(lastIndex).setAnnotation(subAnno);
			}
		}
		
		this.forwardChange();
	}

}
