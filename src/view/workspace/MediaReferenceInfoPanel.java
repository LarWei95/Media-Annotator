package view.workspace;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import control.selection.MediaReference;
import view.ChangeEmitter;
import view.ChangeEmitterPanel;

import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class PathOpenListener implements ActionListener {
	private MediaReferenceInfoPanel<?> infoPanel;
	
	public PathOpenListener (MediaReferenceInfoPanel<?> infoPanel) {
		this.infoPanel = infoPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.infoPanel.setPathByFileChooser();
	}
	
}

class PathListener implements DocumentListener {
	private MediaReferenceInfoPanel<?> infoPanel;
	public boolean active;
	
	public PathListener (MediaReferenceInfoPanel<?> infoPanel) {
		this.infoPanel = infoPanel;
		this.active = true;
	}
	
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		if (this.active) {
			this.infoPanel.setPathByInputField();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if (this.active) {
			this.infoPanel.setPathByInputField();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		if (this.active) {
			this.infoPanel.setPathByInputField();
		}
	}
}

class OverwriteChecksumListener implements ActionListener {

	private MediaReferenceInfoPanel<?> infoPanel;
	
	public OverwriteChecksumListener (MediaReferenceInfoPanel<?> infoPanel) {
		this.infoPanel = infoPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.infoPanel.overwriteChecksum();
	}
	
}

public class MediaReferenceInfoPanel<T> extends ChangeEmitterPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6960195772905680468L;
	private MediaReference<T> mediaReference;
	
	private JTextField filePathTextField;
	private JButton filePathOpenButton;
	private JLabel fileChksumOutLabel;
	private JLabel savedChksumOutLabel;
	private JButton overwriteChksumButton;
	
	private PathOpenListener openListener;
	private PathListener pathListener;
	private OverwriteChecksumListener chksumListener;
	/**
	 * Create the panel.
	 */
	public MediaReferenceInfoPanel(ChangeEmitter changeEmitter) {
		super(changeEmitter);
		
		this.mediaReference = null;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel filePathLabel = new JLabel("File path:");
		GridBagConstraints gbc_filePathLabel = new GridBagConstraints();
		gbc_filePathLabel.fill = GridBagConstraints.BOTH;
		gbc_filePathLabel.insets = new Insets(0, 0, 5, 5);
		gbc_filePathLabel.gridx = 0;
		gbc_filePathLabel.gridy = 0;
		add(filePathLabel, gbc_filePathLabel);
		
		this.filePathTextField = new JTextField();
		this.pathListener = new PathListener(this);
		this.filePathTextField.getDocument().addDocumentListener(this.pathListener);
		GridBagConstraints gbc_filePathTextField = new GridBagConstraints();
		gbc_filePathTextField.insets = new Insets(0, 0, 5, 5);
		gbc_filePathTextField.fill = GridBagConstraints.BOTH;
		gbc_filePathTextField.gridx = 1;
		gbc_filePathTextField.gridy = 0;
		add(this.filePathTextField, gbc_filePathTextField);
		this.filePathTextField.setColumns(10);
		
		this.filePathOpenButton = new JButton("Select ...");
		this.openListener = new PathOpenListener(this);
		filePathOpenButton.addActionListener(this.openListener);
		GridBagConstraints gbc_filePathOpenButton = new GridBagConstraints();
		gbc_filePathOpenButton.insets = new Insets(0, 0, 5, 0);
		gbc_filePathOpenButton.fill = GridBagConstraints.BOTH;
		gbc_filePathOpenButton.gridx = 2;
		gbc_filePathOpenButton.gridy = 0;
		add(this.filePathOpenButton, gbc_filePathOpenButton);
		
		JLabel fileChksumLabel = new JLabel("MD5 checksum:");
		GridBagConstraints gbc_fileChksumLabel = new GridBagConstraints();
		gbc_fileChksumLabel.fill = GridBagConstraints.BOTH;
		gbc_fileChksumLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fileChksumLabel.gridx = 0;
		gbc_fileChksumLabel.gridy = 1;
		add(fileChksumLabel, gbc_fileChksumLabel);
		
		this.fileChksumOutLabel = new JLabel("...");
		GridBagConstraints gbc_fileChksumOutLabel = new GridBagConstraints();
		gbc_fileChksumOutLabel.fill = GridBagConstraints.BOTH;
		gbc_fileChksumOutLabel.gridwidth = 2;
		gbc_fileChksumOutLabel.insets = new Insets(0, 0, 5, 0);
		gbc_fileChksumOutLabel.gridx = 1;
		gbc_fileChksumOutLabel.gridy = 1;
		add(this.fileChksumOutLabel, gbc_fileChksumOutLabel);
		
		JLabel savedChksumLabel = new JLabel("Saved MD5:");
		GridBagConstraints gbc_savedChksumLabel = new GridBagConstraints();
		gbc_savedChksumLabel.fill = GridBagConstraints.BOTH;
		gbc_savedChksumLabel.insets = new Insets(0, 0, 0, 5);
		gbc_savedChksumLabel.gridx = 0;
		gbc_savedChksumLabel.gridy = 2;
		add(savedChksumLabel, gbc_savedChksumLabel);
		
		this.savedChksumOutLabel = new JLabel("...");
		GridBagConstraints gbc_savedChksumOutLabel = new GridBagConstraints();
		gbc_savedChksumOutLabel.fill = GridBagConstraints.BOTH;
		gbc_savedChksumOutLabel.insets = new Insets(0, 0, 0, 5);
		gbc_savedChksumOutLabel.gridx = 1;
		gbc_savedChksumOutLabel.gridy = 2;
		add(this.savedChksumOutLabel, gbc_savedChksumOutLabel);
		
		this.overwriteChksumButton = new JButton("Overwrite");
		this.chksumListener = new OverwriteChecksumListener(this);
		overwriteChksumButton.addActionListener(this.chksumListener);
		GridBagConstraints gbc_overwriteChksumButton = new GridBagConstraints();
		gbc_overwriteChksumButton.gridx = 2;
		gbc_overwriteChksumButton.gridy = 2;
		add(this.overwriteChksumButton, gbc_overwriteChksumButton);
	}
	
	
	
	private void updateCurrentChecksum () throws NoSuchAlgorithmException, IOException {
		String str = this.mediaReference.getMD5Checksum();	
		this.fileChksumOutLabel.setText(str);
	}
	
	public void setMediaReference (MediaReference<T> mediaReference) {
		this.mediaReference = mediaReference;
		
		this.pathListener.active = false;
		
		if (this.mediaReference.isValid()) {
			this.filePathTextField.setText(this.mediaReference.getPath().toAbsolutePath().toString());
			
			try {
				this.updateCurrentChecksum();
			} catch (Exception e) {
				e.printStackTrace();
				this.fileChksumOutLabel.setText("<Invalid>");
			}
		} else {
			this.fileChksumOutLabel.setText("<Invalid>");
		}
		
		if (this.mediaReference.getPath() != null) {
			this.filePathTextField.setText(this.mediaReference.getPath().toAbsolutePath().toString());
		} else {
			this.filePathTextField.setText("<Invalid>");
		}
		
		this.pathListener.active = true;
		
		this.setSavedChecksumOutLabel(this.mediaReference.getChecksum());
	}
	
	private void setSavedChecksumOutLabel (String checksum) {
		if (checksum != null) {
			this.savedChksumOutLabel.setText(checksum);
		} else {
			this.savedChksumOutLabel.setText("None set");
		}
	}
	
	public MediaReference<T> getMediaReference () {
		return this.mediaReference;
	}

	@Override
	public void updateOnForwardedChange() {
		String pathInput = this.filePathTextField.getText();
		
		if (this.mediaReference != null) {
			this.mediaReference.setPath(pathInput);
		}
	}
	
	protected void setPathByInputField () {
		this.forwardChange();
		
		try {
			this.updateCurrentChecksum();
		} catch (Exception e) {
		}
	}
	
	protected void setPathByFileChooser () {
		JFileChooser chooser = new JFileChooser();
		int ret = chooser.showOpenDialog(null);
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			Path path = chooser.getSelectedFile().toPath();
			
			this.filePathTextField.setText(path.toAbsolutePath().toString());
			this.forwardChange();
			
			try {
				this.updateCurrentChecksum();
			} catch (Exception e) {
			}
		}
	}
	
	protected void overwriteChecksum () {
		if (this.mediaReference != null) {
			String chksum = this.mediaReference.getMD5Checksum();
			this.mediaReference.setChecksum(chksum);
			this.setSavedChecksumOutLabel(chksum);
			this.forwardChange();
		}
	}
}
