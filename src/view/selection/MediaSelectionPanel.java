package view.selection;

import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import control.selection.PaneledMediaContainer;
import control.selection.SelectionMediaContainer;
import control.selection.MediaReference;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.nio.file.Path;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

class MediaSelectionListener implements ListSelectionListener {
	private MediaSelectionPanel<?> panel;
	public boolean active;
	
	public MediaSelectionListener (MediaSelectionPanel<?> panel) {
		this.panel = panel;
		this.active = true;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false && this.active) {
			this.panel.updateSelection();
		}
	}
}

public class MediaSelectionPanel<T> extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5703838836379937753L;
	private SelectionMediaContainer<T> mediaContainer;
	private JList<String> mediaList ;
	private MediaSelectionListener listener;
	
	private MarkingCellRenderer cellRenderer;
	
	/**
	 * Create the panel.
	 */
	public MediaSelectionPanel(SelectionMediaContainer<T> mediaContainer) {
		this.mediaContainer = mediaContainer;
		
		setLayout(new BorderLayout(0, 0));
		
		this.mediaList = new JList<String>();
		this.listener = new MediaSelectionListener(this);
		
		this.mediaList.addListSelectionListener(this.listener);
		this.mediaList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		add(this.mediaList, BorderLayout.CENTER);
		
		this.cellRenderer = new MarkingCellRenderer(this.mediaContainer);
		
		this.updateList();
	}
	
	public void setMediaContainer (SelectionMediaContainer<T> mediaContainer) {
		this.listener.active = false;
		this.mediaContainer = mediaContainer;
		this.updateList();
		this.listener.active = true;
	}
	
	public void updateList () {
		DefaultListModel<String> newListModel = new DefaultListModel<String>();
		
		Path path;
		
		for (MediaReference<T> media: this.mediaContainer.getMedias()) {
			path = media.getPath();
			
			if (path != null) {
				if (path.toString().trim() != "") {
					newListModel.addElement(path.getFileName().toString());
				} else {
					newListModel.addElement("<Empty>");
				}
			} else {
				newListModel.addElement("<Empty>");
			}
		}
		this.listener.active = false;
		this.mediaList.setCellRenderer(this.cellRenderer);
		this.mediaList.setModel(newListModel);
		this.mediaList.setSelectedIndex(this.mediaContainer.getMediaIndex());
		this.listener.active = true;
	}
	
	public void setSelectionFromMediaContainer () {
		int index = this.mediaContainer.getMediaIndex();
		
		this.mediaList.setSelectedIndex(index);		
	}
	
	public void updateSelection () {
		int[] indices = this.mediaList.getSelectedIndices();
		
		if (indices.length == 1) {
			this.mediaContainer.setSelectedMedia(indices[0]);
		} else {
			this.mediaContainer.setSelectedMedias(indices);
		}
		
	}
}
