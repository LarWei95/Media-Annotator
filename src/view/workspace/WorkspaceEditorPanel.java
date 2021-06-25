package view.workspace;

import javax.swing.JPanel;

import control.selection.PaneledMediaContainer;
import view.selection.MediaSelectionPanel;
import view.viewer.MediaViewer;

import java.awt.BorderLayout;

public abstract class WorkspaceEditorPanel<T> extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7392076053194998188L;
	private PaneledMediaContainer<T> mediaContainer;
	private MediaSelectionPanel<T> mediaSelectionPanel;
	private MediaViewer<T> mediaViewer;
	/**
	 * Create the panel.
	 */
	public WorkspaceEditorPanel(PaneledMediaContainer<T> mediaContainer) {
		setLayout(new BorderLayout(0, 0));
		
		this.mediaContainer = mediaContainer;
		this.mediaSelectionPanel = this.getMediaSelectionPanel(this.mediaContainer);
		this.mediaViewer = this.getMediaViewer();
		
		this.add(this.mediaSelectionPanel, BorderLayout.WEST);
		this.add(this.mediaViewer, BorderLayout.CENTER);
	}

	protected abstract MediaSelectionPanel<T> getMediaSelectionPanel (PaneledMediaContainer<T> mediaContainer);
	
	protected abstract MediaViewer<T> getMediaViewer ();
}
