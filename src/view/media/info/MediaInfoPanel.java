package view.media.info;

import javax.swing.JPanel;

import control.selection.MediaReference;
import view.workspace.MediaReferenceInfoPanel;

public abstract class MediaInfoPanel<T> extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3386056201591584996L;
	
	protected MediaReferenceInfoPanel<T> refInfoPanel;
	
	public MediaInfoPanel () {
		this.refInfoPanel = new MediaReferenceInfoPanel<T>();
	}

	public abstract MediaReference<T> getMediaReference();

	public abstract void setMediaReference(MediaReference<T> media);

	public abstract void clear();
}
