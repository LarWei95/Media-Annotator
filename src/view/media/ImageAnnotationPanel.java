package view.media;

import control.ViewAnnotationLink;
import control.annotation.editor.RectangleEditor;
import model.annotation.Annotation;
import view.ChangeEmitter;
import view.annotation.types.MapClassAnnotationPanel;
import view.annotation.types.MappableAnnotationPanel;
import view.viewer.image.ImageViewer;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

public class ImageAnnotationPanel extends MediaAnnotationPanel<BufferedImage> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8608652095399255409L;
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

	@Override
	public Annotation getAnnotation() {
		return this.panel.getAnnotation();
	}

	@Override
	public void setAnnotation(Annotation annotation) {
		this.panel.setAnnotation(annotation);
	}

	@Override
	public void setMedia(BufferedImage media) {
		this.imagePanel.setMedia(media);
	}

	@Override
	public void clear() {
		this.panel.clear();
	}
}
