package view.workspace;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import control.selection.MediaContainer;
import control.selection.MediaReference;
import control.selection.MediaReferenceFactory;
import control.selection.PaneledMediaContainer;
import control.selection.WorkspaceMediaContainer;
import view.ChangeEmitterPanel;
import view.media.info.MediaInfoPanel;
import view.selection.MediaSelectionPanel;
import view.viewer.MediaViewer;

import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.awt.event.ActionEvent;

class AddMediaListener<T> implements ActionListener {
	private JPanel panel;
	private WorkspaceMediaContainer<T> container;
	private MediaSelectionPanel<T> mediaSelectionPanel;
	private MediaReferenceFactory<T> factory;
	
	public AddMediaListener (JPanel panel, WorkspaceMediaContainer<T> container, MediaSelectionPanel<T> mediaSelectionPanel, MediaReferenceFactory<T> factory) {
		this.panel = panel;
		this.container = container;
		this.mediaSelectionPanel = mediaSelectionPanel;
		this.factory = factory;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);
		int ret = chooser.showOpenDialog(null);
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			File[] selectedFiles = chooser.getSelectedFiles();
			
			MediaReference<T> ref;
			
			for (File file: selectedFiles) {
				try {
					ref = factory.generateByPath(file.toPath());
					this.container.addBlankMedia(ref);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		this.mediaSelectionPanel.updateList();
		this.mediaSelectionPanel.setSelectionFromMediaContainer();
		this.panel.validate();
	}
}

class RemoveMediaListener<T> implements ActionListener {
	private JPanel panel;
	private WorkspaceMediaContainer<T> container;
	private MediaSelectionPanel<T> mediaSelectionPanel;
	
	public RemoveMediaListener (JPanel panel, WorkspaceMediaContainer<T> container, MediaSelectionPanel<T> mediaSelectionPanel) {
		this.panel = panel;
		this.container = container;
		this.mediaSelectionPanel = mediaSelectionPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.container.removeCurrentMediaReference();
		this.mediaSelectionPanel.updateList();
		this.mediaSelectionPanel.setSelectionFromMediaContainer();
		this.panel.validate();
	}
}

public class WorkspaceEditorPanel<T> extends ChangeEmitterPanel {
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
		super(null);
		setLayout(new BorderLayout(0, 0));
		
		this.infoPanel = mediaInfoPanel;
		this.infoPanel.setSuperChangeEmitter(this);
		this.mediaContainer = new WorkspaceMediaContainer<T>(mediaContainer, this.infoPanel, factory);
		this.mediaSelectionPanel = new MediaSelectionPanel<T>(this.mediaContainer);
		
		this.addListener = new AddMediaListener<T>(this, this.mediaContainer, this.mediaSelectionPanel, factory);
		this.removeListener = new RemoveMediaListener<T>(this, this.mediaContainer, this.mediaSelectionPanel);
		
		JScrollPane scroll = new JScrollPane();
		this.add(scroll, BorderLayout.WEST);
		scroll.setViewportView(this.mediaSelectionPanel);
		
		this.add(this.infoPanel, BorderLayout.CENTER);
		
		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);
		
		JButton addMediaReferenceButton = new JButton("Add media(s)");
		addMediaReferenceButton.addActionListener(this.addListener);
		toolBar.add(addMediaReferenceButton);
		
		JButton removeMediaReferenceButton = new JButton("Remove media");
		removeMediaReferenceButton.addActionListener(this.removeListener);
		toolBar.add(removeMediaReferenceButton);
	}

	@Override
	public void updateOnForwardedChange() {
		this.mediaSelectionPanel.updateList();
		this.infoPanel.updateView();
	}
	
	public WorkspaceMediaContainer<T> getWorkspaceMediaContainer () {
		return this.mediaContainer;
	}
}
