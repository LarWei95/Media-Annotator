package view.viewer;

import javax.swing.JPanel;

public abstract class MediaViewer<T> extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5595939387756232818L;
	protected T media;
	
	public final void setMedia (T media) {
		this.media = media;
		this.updateAfterMediaSet();
	}
	
	protected abstract void updateAfterMediaSet ();
	
	public abstract void updateView();
}
