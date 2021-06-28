package view.workspace;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import control.io.AnnotationWorkspace;
import control.selection.MediaContainer;
import control.selection.PaneledMediaContainer;
import view.media.MediaAnnotationPanel;
import view.selection.MediaSelectionPanel;

public class WorkspaceAnnotationPanel<T> extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3155045839334449064L;
	protected final AnnotationWorkspace<T> annotationWorkspace;
	protected final MediaAnnotationPanel<T> mediaAnnotationPanel;
	protected final MediaSelectionPanel<T> mediaSelectionPanel;
	
	/**
	 * Create the panel.
	 */
	public WorkspaceAnnotationPanel(AnnotationWorkspace<T> annotationWorkspace, MediaAnnotationPanel<T> mediaAnnotationPanel) {
		this.annotationWorkspace = annotationWorkspace;
		
		this.setLayout(new BorderLayout(0, 0));
		
		this.mediaAnnotationPanel = mediaAnnotationPanel;
		this.add(this.mediaAnnotationPanel, BorderLayout.CENTER);
		
		this.mediaSelectionPanel = new MediaSelectionPanel<T>(this.annotationWorkspace.getMediaContainer());
		this.add(this.mediaSelectionPanel, BorderLayout.WEST);
	}
	
	public  AnnotationWorkspace<T> getAnnotationWorkspace () {
		return this.annotationWorkspace;
	}
	
	public void setMediaContainer (MediaContainer<T> mediaContainer) {
		this.annotationWorkspace.setMediaContainer(mediaContainer);
		this.mediaSelectionPanel.setMediaContainer(this.annotationWorkspace.getMediaContainer());
	}
	
	public PaneledMediaContainer<T> getMediaContainer () {
		return this.annotationWorkspace.getMediaContainer();
	}
}
