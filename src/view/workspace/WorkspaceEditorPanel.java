package view.workspace;

import javax.swing.JPanel;

import control.selection.MediaContainer;
import control.selection.MediaReferenceFactory;
import control.selection.PaneledMediaContainer;
import control.selection.WorkspaceMediaContainer;
import view.media.info.MediaInfoPanel;
import view.selection.MediaSelectionPanel;
import view.viewer.MediaViewer;

import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class AddMediaListener<T> implements ActionListener {
	private WorkspaceMediaContainer<T> container;
	private MediaSelectionPanel<T> mediaSelectionPanel;
	
	public AddMediaListener (WorkspaceMediaContainer<T> container, MediaSelectionPanel<T> mediaSelectionPanel) {
		this.container = container;
		this.mediaSelectionPanel = mediaSelectionPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.container.addEmptyMediaReference();
		this.mediaSelectionPanel.updateList();
		this.mediaSelectionPanel.setSelectionFromMediaContainer();
	}
}

class RemoveMediaListener<T> implements ActionListener {
	private WorkspaceMediaContainer<T> container;
	private MediaSelectionPanel<T> mediaSelectionPanel;
	
	public RemoveMediaListener (WorkspaceMediaContainer<T> container, MediaSelectionPanel<T> mediaSelectionPanel) {
		this.container = container;
		this.mediaSelectionPanel = mediaSelectionPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.container.removeCurrentMediaReference();
		this.mediaSelectionPanel.updateList();
		this.mediaSelectionPanel.setSelectionFromMediaContainer();
	}
}

public class WorkspaceEditorPanel<T> extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7392076053194998188L;
	private WorkspaceMediaContainer<T> mediaContainer;
	private MediaSelectionPanel<T> mediaSelectionPanel;
	private MediaInfoPanel<T> infoPanel;
	
	private AddMediaListener<T> addListener;
	private RemoveMediaListener<T> removeListener;
	
	/**
	 * Create the panel.
	 */
	public WorkspaceEditorPanel(MediaContainer<T> mediaContainer, MediaInfoPanel<T> mediaInfoPanel,
			MediaReferenceFactory<T> factory) {
		setLayout(new BorderLayout(0, 0));
		
		this.infoPanel = mediaInfoPanel;
		this.mediaContainer = new WorkspaceMediaContainer<T>(mediaContainer, this.infoPanel, factory);
		this.mediaSelectionPanel = new MediaSelectionPanel<T>(this.mediaContainer);
		
		this.addListener = new AddMediaListener<T>(this.mediaContainer, this.mediaSelectionPanel);
		this.removeListener = new RemoveMediaListener<T>(this.mediaContainer, this.mediaSelectionPanel);
		
		this.add(this.mediaSelectionPanel, BorderLayout.WEST);
		this.add(this.infoPanel, BorderLayout.CENTER);
		
		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);
		
		JButton addMediaReferenceButton = new JButton("Add media");
		addMediaReferenceButton.addActionListener(this.addListener);
		toolBar.add(addMediaReferenceButton);
		
		JButton removeMediaReferenceButton = new JButton("Remove media");
		removeMediaReferenceButton.addActionListener(this.removeListener);
		toolBar.add(removeMediaReferenceButton);
	}
}
