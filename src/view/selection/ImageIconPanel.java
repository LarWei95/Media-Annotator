package view.selection;

import javax.swing.JPanel;

import view.ChangeEmitterPanel;
import view.image.BasicImageViewer;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class SelectActionListener extends MouseAdapter {
	private ImageIconPanel panel;
	
	public SelectActionListener (ImageIconPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		this.panel.setActive();
	}
}

public class ImageIconPanel extends ChangeEmitterPanel {
	private ImageSelectorPanel superPanel;
	private final int index;
	private JLabel pathLabel;
	private BasicImageViewer imageViewer;
	private SelectActionListener listener;
	
	/**
	 * Create the panel.
	 */
	public ImageIconPanel(ImageSelectorPanel superPanel, int index) {
		super(superPanel);
		this.index = index;
		this.superPanel = superPanel;
		this.listener = new SelectActionListener(this);
		
		this.imageViewer = new BasicImageViewer();
		this.imageViewer.setForcedDimensions(50, 50);
		setLayout(new BorderLayout(0, 0));
		add(this.imageViewer, BorderLayout.CENTER);
		
		this.pathLabel = new JLabel("New label");
		pathLabel.addMouseListener(this.listener);
		this.imageViewer.addMouseListener(this.listener);
		pathLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(this.pathLabel, BorderLayout.SOUTH);
	}

	public void setLabel (String label) {
		this.pathLabel.setText(label);
	}
	
	public void setImage (BufferedImage image) {
		this.imageViewer.setImage(image);
	}

	public void setActive () {
		boolean active = this.superPanel.setActivePanel(this.index);
		
	}
	
	@Override
	public void updateOnForwardedChange() {
		// TODO Auto-generated method stub
		
	}
}
