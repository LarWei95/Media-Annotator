package control;

import java.awt.Rectangle;
import java.util.HashMap;

import control.annotation.collector.VisualAnnotationCollector;
import control.annotation.editor.RectangleEditor;
import model.annotation.Annotation;
import model.annotation.BoxAnnotation;
import model.annotation.MapClassAnnotation;
import view.annotation.types.ActivePanelContainer;
import view.annotation.types.AnnotationPanel;
import view.annotation.types.BoxAnnotationPanel;
import view.annotation.types.ClassAnnotationPanel;
import view.image.ImageViewer;

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
	
	public void setCurrentBox () {
		BoxAnnotationPanel boxPanel= this.activePanelContainer.getBoxAnnotationPanel();
		
		if (boxPanel != null) {
			Rectangle rect = this.rectEditor.getRectangle();
			
			int xmin = rect.x;
			int ymin = rect.y;
			int xmax = xmin + rect.width;
			int ymax = ymin + rect.height;
			
			boxPanel.setCoordinates(xmin, ymin, xmax, ymax);
		}
		
		this.rectEditor.clearCurrentRectangle();
	}
}
