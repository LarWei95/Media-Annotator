package view.clipboard;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import control.clipboard.AnnotationClipboard;
import control.framefactory.FrameFactory;
import control.io.AnnotationIO;
import control.selection.MediaContainer;
import model.MediaType;
import model.annotation.Annotation;
import view.frame.MainFrameContainer;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSeparator;

class AddAnnotationListener implements ActionListener {
	private ClipboardPanel panel;
	
	public AddAnnotationListener (ClipboardPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.panel.addAnnotation();
	}
}

class RemoveAnnotationListener implements ActionListener {
	private ClipboardPanel panel;
	
	public RemoveAnnotationListener (ClipboardPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.panel.removeAnnotation();
	}
}

class CopyAnnotationListener implements ActionListener {
	private ClipboardPanel panel;
	
	public CopyAnnotationListener (ClipboardPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.panel.copyAnnotation();
	}
}

class NewListener implements ActionListener {
	private ClipboardPanel panel;

	public NewListener (ClipboardPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.panel.newClipboard();
	}
}

class OpenListener implements ActionListener {
	private ClipboardPanel panel;

	public OpenListener (ClipboardPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.panel.loadClipboard();
	}
}

class SaveListener implements ActionListener {
	private ClipboardPanel panel;

	public SaveListener (ClipboardPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.panel.saveClipboard();
	}
}

public class ClipboardPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1421347817928734313L;
	private AnnotationClipboard clipboard;
	
	private JTextField nameTextField;
	private JButton addButton;
	private JButton removeButton;
	private JButton copyButton;
	private JList<String> selectionList;
	
	private AddAnnotationListener addListener;
	private RemoveAnnotationListener removeListener;
	private CopyAnnotationListener copyListener;
	
	private NewListener newListener;
	private OpenListener openListener;
	private SaveListener saveListener;
	
	private JSeparator separator;
	private JButton saveButton;
	private JButton openButton;
	private JButton newButton;
	
	/**
	 * Create the panel.
	 */
	public ClipboardPanel(AnnotationClipboard clipboard) {
		this.clipboard = clipboard;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		this.setLayout(gridBagLayout);
		
		JLabel panelLabel = new JLabel("Annotation clipboard repository");
		panelLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_panelLabel = new GridBagConstraints();
		gbc_panelLabel.insets = new Insets(0, 0, 5, 0);
		gbc_panelLabel.gridx = 0;
		gbc_panelLabel.gridy = 0;
		this.add(panelLabel, gbc_panelLabel);
		
		this.addButton = new JButton("Add");
		this.addListener = new AddAnnotationListener(this);
		addButton.addActionListener(this.addListener);
		
		this.newButton = new JButton("New");
		this.newListener = new NewListener(this);
		this.newButton.addActionListener(this.newListener);
		GridBagConstraints gbc_newButton = new GridBagConstraints();
		gbc_newButton.fill = GridBagConstraints.BOTH;
		gbc_newButton.insets = new Insets(0, 0, 5, 0);
		gbc_newButton.gridx = 0;
		gbc_newButton.gridy = 1;
		this.add(this.newButton, gbc_newButton);
		
		this.openButton = new JButton("Open");
		this.openListener = new OpenListener(this);
		this.openButton.addActionListener(this.openListener);
		GridBagConstraints gbc_openButton = new GridBagConstraints();
		gbc_openButton.fill = GridBagConstraints.BOTH;
		gbc_openButton.insets = new Insets(0, 0, 5, 0);
		gbc_openButton.gridx = 0;
		gbc_openButton.gridy = 2;
		add(openButton, gbc_openButton);
		
		this.saveButton = new JButton("Save");
		this.saveListener = new SaveListener(this);
		this.saveButton.addActionListener(this.saveListener);
		GridBagConstraints gbc_saveButton = new GridBagConstraints();
		gbc_saveButton.fill = GridBagConstraints.BOTH;
		gbc_saveButton.insets = new Insets(0, 0, 5, 0);
		gbc_saveButton.gridx = 0;
		gbc_saveButton.gridy = 3;
		add(saveButton, gbc_saveButton);
		
		separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 4;
		add(separator, gbc_separator);
		GridBagConstraints gbc_addButton = new GridBagConstraints();
		gbc_addButton.insets = new Insets(0, 0, 5, 0);
		gbc_addButton.fill = GridBagConstraints.BOTH;
		gbc_addButton.gridx = 0;
		gbc_addButton.gridy = 5;
		this.add(this.addButton, gbc_addButton);
		
		this.removeButton = new JButton("Remove");
		this.removeListener = new RemoveAnnotationListener(this);
		removeButton.addActionListener(this.removeListener);
		GridBagConstraints gbc_removeButton = new GridBagConstraints();
		gbc_removeButton.insets = new Insets(0, 0, 5, 0);
		gbc_removeButton.fill = GridBagConstraints.BOTH;
		gbc_removeButton.gridx = 0;
		gbc_removeButton.gridy = 6;
		this.add(this.removeButton, gbc_removeButton);
		
		this.copyButton = new JButton("Copy");
		this.copyListener = new CopyAnnotationListener(this);
		copyButton.addActionListener(this.copyListener);
		GridBagConstraints gbc_copyButton = new GridBagConstraints();
		gbc_copyButton.insets = new Insets(0, 0, 5, 0);
		gbc_copyButton.fill = GridBagConstraints.BOTH;
		gbc_copyButton.gridx = 0;
		gbc_copyButton.gridy = 7;
		this.add(this.copyButton, gbc_copyButton);
		
		this.nameTextField = new JTextField();
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.gridx = 0;
		gbc_nameTextField.gridy = 8;
		this.add(this.nameTextField, gbc_nameTextField);
		this.nameTextField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 9;
		this.add(scrollPane, gbc_scrollPane);
		
		this.selectionList = new JList<String>();
		this.selectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scrollPane.setViewportView(this.selectionList);
	}
	
	protected void addAnnotation () {
		String name = this.nameTextField.getText();
		this.clipboard.addCurrentToRepository(name);
		this.updateList();
	}
	
	protected void removeAnnotation () {
		String selected = this.selectionList.getSelectedValue();
		
		if (selected != null) {
			this.clipboard.removeFromRepository(selected);
			this.updateList();
		}
	}

	protected void copyAnnotation () {
		String selected = this.selectionList.getSelectedValue();
		
		if (selected != null) {
			this.clipboard.setFromRepository(selected);
		}
	}
	
	private void updateList () {
		Set<String> keys = this.clipboard.getRepositoryKeys();
		DefaultListModel<String> list = new DefaultListModel<String>();
		
		for (String key: keys) {
			list.addElement(key);
		}
		
		this.selectionList.setModel(list);
	}
	
	protected void newClipboard () {
		this.clipboard.clear();
		this.updateList();
		this.getRootPane().validate();
	}
	
	protected void loadClipboard () {
		JFileChooser chooser = new JFileChooser();
		int ret = chooser.showOpenDialog(this);
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			
			try {
				AnnotationClipboard newClipboard = AnnotationIO.loadClipboard(file);
				this.clipboard.setClipboard(newClipboard);
				this.updateList();
				this.getRootPane().validate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void saveClipboard () {
		JFileChooser chooser = new JFileChooser();
		int ret = chooser.showSaveDialog(this);
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			
			try {
				AnnotationIO.saveClipboard(this.clipboard, file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
