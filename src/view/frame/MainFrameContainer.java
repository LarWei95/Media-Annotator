package view.frame;

import javax.swing.JFrame;

import control.selection.MediaContainer;
import control.selection.MediaReferenceFactory;
import control.selection.PaneledMediaContainer;
import view.media.info.MediaInfoPanel;
import view.workspace.WorkspaceAnnotationPanel;

public class MainFrameContainer<T> {
	protected WorkspaceAnnotationPanel<T> workspacePanel;
	protected MediaReferenceFactory<T> factory;
	protected MediaInfoPanel<T> infoPanel;
	
	public MainFrameContainer(WorkspaceAnnotationPanel<T> workspacePanel, MediaReferenceFactory<T> factory, 
			MediaInfoPanel<T> infoPanel) {
		this.workspacePanel = workspacePanel;
		this.factory = factory;
		this.infoPanel = infoPanel;
	}
	
	protected PaneledMediaContainer<T> getMediaContainer () {
		return this.workspacePanel.getMediaContainer();
	}
	
	protected void setMediaContainer (MediaContainer<T> container) {
		MediaContainer<T> old = this.workspacePanel.getMediaContainer();
		
		for (int i = 0; i < old.getMedias().size(); i++) {
			System.out.println("Old: "+old.getMedias().get(i)+" "+old.getAnnotations().get(i));
		}		
		
		this.workspacePanel.setMediaContainer(container);
		
		old = this.workspacePanel.getMediaContainer();
		
		for (int i = 0; i < old.getMedias().size(); i++) {
			System.out.println("New: "+old.getMedias().get(i)+" "+old.getAnnotations().get(i));
		}
	}
	
	protected void updateCurrentAnnotation () {
		this.workspacePanel.getAnnotationWorkspace().updateCurrentAnnotation();
	}
}
