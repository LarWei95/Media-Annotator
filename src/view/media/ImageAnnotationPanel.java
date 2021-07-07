package view.media;

import control.ViewAnnotationLink;
import control.annotation.editor.RectangleEditor;
import control.clipboard.AnnotationClipboard;
import control.selection.MediaReference;
import model.Marking;
import model.annotation.Annotation;
import view.ChangeEmitter;
import view.annotation.types.MapClassAnnotationPanel;
import view.annotation.types.MappableAnnotationPanel;
import view.viewer.image.ImageViewer;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.util.List;

public class ImageAnnotationPanel extends MediaAnnotationPanel<BufferedImage> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8608652095399255409L;
	private MapClassAnnotationPanel panel;
	private RectangleEditor rectEditor;
	private ImageViewer imagePanel;
	private MarkingPanel markingPanel;
	
	/**
	 * Create the panel.
	 */
	public ImageAnnotationPanel(AnnotationClipboard annotationClipboard) {
		this.setLayout(new BorderLayout(0, 0));
		
		this.rectEditor = new RectangleEditor();
		ViewAnnotationLink link = new ViewAnnotationLink (this.rectEditor);
		
		this.imagePanel = new ImageViewer(link);
		link.setImageViewer(this.imagePanel);
		this.add(this.imagePanel, BorderLayout.CENTER);
			
		this.panel = new MapClassAnnotationPanel(annotationClipboard, new ChangeEmitter() {
			@Override
			public void updateOnForwardedChange() {
				Annotation anno = panel.getAnnotation();
				link.updateViewedAnnotations(anno);
			}

			@Override
			public ChangeEmitter getSuperChangeEmitter() {
				return null;
			}

			@Override
			public void setSuperChangeEmitter(ChangeEmitter changeEmitter) {
				// Nothing
			}
			
		}, link, MappableAnnotationPanel.TYPE_ALL);
		
		this.markingPanel = new MarkingPanel(new ChangeEmitter () {
			@Override
			public void updateOnForwardedChange() {
				Marking selected = markingPanel.getMarking();
				setMarking(selected);
			}

			@Override
			public ChangeEmitter getSuperChangeEmitter() {
				return null;
			}

			@Override
			public void setSuperChangeEmitter(ChangeEmitter changeEmitter) {
				// Nothing
			}
		});
		this.add(this.markingPanel, BorderLayout.NORTH);
		
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

	protected void setMarking (Marking marking) {
		if (!this.multiView) {
			if (this.media != null) {
				this.media.setMarking(marking);
			}
		} else {
			for (MediaReference<BufferedImage> media: this.medias) {
				media.setMarking(marking);
			}
		}
	}
	
	@Override
	public void setMediaReference(MediaReference<BufferedImage> media) {
		super.setMediaReference(media);
		this.imagePanel.setMedia(media.getMediaLoaded());
		this.markingPanel.setMarking(media.getMarking());
	}
	
	@Override
	public void setMediaReferences (List<MediaReference<BufferedImage>> medias) {
		super.setMediaReferences(medias);
		this.markingPanel.setMarking(Marking.NONE);
	}

	@Override
	public void clear() {
		this.panel.clear();
	}
}
