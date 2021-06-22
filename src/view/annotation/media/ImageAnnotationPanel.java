package view.annotation.media;

import javax.swing.JPanel;
import control.RectangleEditor;
import control.ViewAnnotationLink;
import model.Annotation;
import view.ImageViewer;
import view.annotation.types.MapClassAnnotationPanel;
import view.annotation.types.MappableAnnotationPanel;
import view.elements.ChangeEmitter;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

public class ImageAnnotationPanel extends JPanel {

	private MapClassAnnotationPanel panel;
	private RectangleEditor rectEditor;
	private ImageViewer imagePanel;
	
	/**
	 * Create the panel.
	 */
	public ImageAnnotationPanel() {
		this.setLayout(new BorderLayout(0, 0));
		
		this.rectEditor = new RectangleEditor();
		ViewAnnotationLink link = new ViewAnnotationLink (this.rectEditor);
		
		this.imagePanel = new ImageViewer(link);
		link.setImageViewer(this.imagePanel);
		this.add(this.imagePanel, BorderLayout.CENTER);
			
		this.panel = new MapClassAnnotationPanel(new ChangeEmitter() {
			@Override
			public void updateOnForwardedChange() {
				Annotation anno = panel.getAnnotation();
				link.updateViewedAnnotations(anno);
				
				System.out.println(anno.getJsonable().toJson());
			}

			@Override
			public ChangeEmitter getSuperChangeEmitter() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setSuperChangeEmitter(ChangeEmitter changeEmitter) {
				// TODO Auto-generated method stub
				
			}
			
		}, link, MappableAnnotationPanel.TYPE_ALL);
		link.setRootAnnotationPanel(this.panel);
		this.add(this.panel, BorderLayout.EAST);
	}

	public void setImage (BufferedImage image) {
		this.imagePanel.setImage(image);
	}
}