package view.selection;

import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import control.selection.MediaContainer;
import control.selection.MediaReference;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

class MediaSelectionListener implements ListSelectionListener {
	private MediaSelectionPanel<?> panel;
	
	public MediaSelectionListener (MediaSelectionPanel<?> panel) {
		this.panel = panel;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			this.panel.updateSelection();
		}
	}
	
}

public class MediaSelectionPanel<T> extends JPanel {
	private MediaContainer<T> mediaContainer;
	private JList<String> mediaList ;
	private MediaSelectionListener listener;
	
	/**
	 * Create the panel.
	 */
	public MediaSelectionPanel(MediaContainer<T> mediaContainer) {
		this.mediaContainer = mediaContainer;
		
		setLayout(new BorderLayout(0, 0));
		
		this.mediaList = new JList<String>();
		this.listener = new MediaSelectionListener(this);
		
		this.mediaList.addListSelectionListener(this.listener);
		this.mediaList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(this.mediaList, BorderLayout.CENTER);
		this.updateList();
	}
	
	public void updateList () {
		DefaultListModel<String> newListModel = new DefaultListModel<String>();
		
		for (MediaReference<T> media: this.mediaContainer.getMedias()) {
			newListModel.addElement(media.getBaseName());
		}
		
		this.mediaList.setModel(newListModel);
	}
	
	public void updateSelection () {
		int index = this.mediaList.getSelectedIndex();
		System.out.println(index);
	}
}
