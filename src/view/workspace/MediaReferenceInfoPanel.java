package view.workspace;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;

import control.selection.MediaReference;

import java.awt.Insets;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;

public class MediaReferenceInfoPanel<T> extends JPanel {
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
	
	/**
	 * Create the panel.
	 */
	public MediaReferenceInfoPanel() {
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
		GridBagConstraints gbc_filePathTextField = new GridBagConstraints();
		gbc_filePathTextField.insets = new Insets(0, 0, 5, 5);
		gbc_filePathTextField.fill = GridBagConstraints.BOTH;
		gbc_filePathTextField.gridx = 1;
		gbc_filePathTextField.gridy = 0;
		add(this.filePathTextField, gbc_filePathTextField);
		this.filePathTextField.setColumns(10);
		
		this.filePathOpenButton = new JButton("Select ...");
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
		GridBagConstraints gbc_overwriteChksumButton = new GridBagConstraints();
		gbc_overwriteChksumButton.gridx = 2;
		gbc_overwriteChksumButton.gridy = 2;
		add(this.overwriteChksumButton, gbc_overwriteChksumButton);
	}
	
	protected static String bytesToHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		
		byte current;
		
		for (int i = 0; i < bytes.length; i++) {
			current = bytes[i];
			builder.append(String.format("%02X", current));
		}
		
		return builder.toString();
	}
	
	private void updateCurrentChecksum () throws NoSuchAlgorithmException, IOException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		FileInputStream s = new FileInputStream(this.mediaReference.getPath().toFile());
		md.update(s.readAllBytes());
		
		byte[] bytes = md.digest();
		
		s.close();
		
		String str = bytesToHex(bytes);
		
		this.fileChksumOutLabel.setText(str);
	}
	
	public void setMediaReference (MediaReference<T> mediaReference) {
		this.mediaReference = mediaReference;
		Path path = this.mediaReference.getPath();
		
		if (path != null) {
			this.filePathTextField.setText(this.mediaReference.getPath().toAbsolutePath().toString());
			
			try {
				this.updateCurrentChecksum();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.filePathTextField.setText("");
		}
		
		
		
	}
	
	public MediaReference<T> getMediaReference () {
		return this.mediaReference;
	}
}
