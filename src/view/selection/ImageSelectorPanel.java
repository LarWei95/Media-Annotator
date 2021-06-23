package view.selection;

import javax.swing.JPanel;

import view.ChangeEmitter;
import view.ChangeEmitterPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

import control.selection.ImageReference;
import control.selection.MediaContainer;
import control.selection.MediaReference;

public class ImageSelectorPanel extends ChangeEmitterPanel {
	
	private MediaContainer<BufferedImage> container;
	private ArrayList<ImageIconPanel> iconPanels;
	private JPanel subPanel;
	
	/**
	 * Create the panel.
	 */
	public ImageSelectorPanel(ChangeEmitter panel, MediaContainer<BufferedImage> container) {
		super(panel);
		this.container = container;
		this.iconPanels = new ArrayList<ImageIconPanel>();
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);
		
		this.subPanel = new JPanel();
		scrollPane.setViewportView(this.subPanel);
		FlowLayout fl_panel_1 = new FlowLayout(FlowLayout.LEFT, 5, 5);
		fl_panel_1.setAlignOnBaseline(true);
		this.subPanel.setLayout(fl_panel_1);
	}

	public void deleteAllIconPanels () {
		for (ImageIconPanel panel: this.iconPanels) {
			this.subPanel.remove(panel);
		}
		
		this.iconPanels.clear();
	}
	
	public void updateIconPanels () {
		this.deleteAllIconPanels();
		ArrayList<MediaReference<BufferedImage>> medias = this.container.getMedias();
		ImageIconPanel newPanel;
		
		int i = 0;
		
		for (MediaReference<BufferedImage> media: medias) {
			newPanel = new ImageIconPanel(this, i);
			this.subPanel.add(newPanel);
			newPanel.setImage(media.getMedia());
			newPanel.setLabel(media.getPath().getFileName().toString());
			i++;
		}
		
	}
	
	@Override
	public void updateOnForwardedChange() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean setActivePanel (int index) {
		System.out.println(index);
		return true;
	}

}
