package view.media.info;

import javax.swing.JPanel;

import control.selection.MediaReference;
import view.ChangeEmitter;
import view.ChangeEmitterPanel;
import view.workspace.MediaReferenceInfoPanel;

public abstract class MediaInfoPanel<T> extends ChangeEmitterPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3386056201591584996L;
	
	protected MediaReferenceInfoPanel<T> refInfoPanel;
	
	public MediaInfoPanel (ChangeEmitter changeEmitter) {
		super(changeEmitter);
		this.refInfoPanel = new MediaReferenceInfoPanel<T>(this);
	}

	public abstract MediaReference<T> getMediaReference();

	public abstract void setMediaReference(MediaReference<T> media);

	public abstract void clear();
	
	public abstract void updateView ();
}
