package control;

import java.util.HashMap;

import model.Annotation;
import model.BoxAnnotation;
import model.MapClassAnnotation;
import view.ImageViewer;
import view.annotation.ActivePanelContainer;
import view.annotation.AnnotationPanel;
import view.annotation.BoxAnnotationPanel;
import view.annotation.ClassAnnotationPanel;

public class ViewAnnotationLink {
	private ActivePanelContainer activePanelContainer;
	private AnnotationPanel root;
	
	public final RectangleEditor rectEditor;
	public ImageViewer imageViewer;
	
	public ViewAnnotationLink (RectangleEditor rectEditor) {
		this.activePanelContainer = new ActivePanelContainer();
		
		this.rectEditor = rectEditor;
		this.imageViewer = null;
	}
	
	public void setImageViewer (ImageViewer imageViewer) {
		this.imageViewer = imageViewer;
	}
	
	public void setRootAnnotationPanel (AnnotationPanel root) {
		this.root = root;
	}
	
	public void updateActivePanelContainer () {
		if (this.root != null) {
			this.activePanelContainer.reset();
			this.root.fillActivePanelContainer(this.activePanelContainer);
		}
	}
	
	public void updateFromPanels () {
		BoxAnnotationPanel boxPanel = this.activePanelContainer.getBoxAnnotationPanel();
		ClassAnnotationPanel classPanel = this.activePanelContainer.getClassAnnotationPanel();
		
		if (boxPanel != null) {
			BoxAnnotation boxAnnotation = (BoxAnnotation) boxPanel.getAnnotation();
			
			int xmin = boxAnnotation.getStartX();
			int ymin = boxAnnotation.getStartY();
			int xmax = boxAnnotation.getEndX();
			int ymax = boxAnnotation.getEndY();
			
		}
	}
	
	public void updateViewedAnnotations (Annotation annotation) {
		VisualAnnotationCollector collector = new VisualAnnotationCollector(annotation);
		HashMap<BoxAnnotation, MapClassAnnotation> annotations = collector.getBoxAnnotations();
		
		this.rectEditor.clearStoredRectangles();
		
		for (BoxAnnotation box: annotations.keySet()) {
			this.rectEditor.addRectangle(box.getStartX(), box.getStartY(), box.getEndX(), box.getEndY());
		}
		
		this.imageViewer.repaint();
	}
	
	
}
