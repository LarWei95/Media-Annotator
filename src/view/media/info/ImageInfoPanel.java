package view.media.info;

import control.ViewAnnotationLink;
import control.annotation.editor.RectangleEditor;
import control.selection.MediaReference;
import model.annotation.Annotation;
import view.ChangeEmitter;
import view.annotation.types.MapClassAnnotationPanel;
import view.annotation.types.MappableAnnotationPanel;
import view.viewer.image.BasicImageViewer;
import view.viewer.image.ImageViewer;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

public class ImageInfoPanel extends MediaInfoPanel<BufferedImage> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8608652095399255409L;
	private BasicImageViewer imagePanel;
	
	/**
	 * Create the panel.
	 */
	public ImageInfoPanel() {
		super(null);
		
		this.setLayout(new BorderLayout(0, 0));
		
		this.imagePanel = new BasicImageViewer();
		this.add(this.imagePanel, BorderLayout.CENTER);
		
		this.add(this.refInfoPanel, BorderLayout.EAST);
	}

	@Override
	public void clear() {
	}

	@Override
	public MediaReference<BufferedImage> getMediaReference() {
		return this.refInfoPanel.getMediaReference();
	}

	@Override
	public void setMediaReference(MediaReference<BufferedImage> media) {
		this.imagePanel.setMedia(media.getMediaLoaded());
		this.refInfoPanel.setMediaReference(media);
	}

	@Override
	public void updateOnForwardedChange() {
		
	}

	@Override
	public void updateView() {
		MediaReference<BufferedImage> image = this.refInfoPanel.getMediaReference();
		
		if (image != null) {
			this.imagePanel.setMedia(image.getMedia());
		}
	}

}
