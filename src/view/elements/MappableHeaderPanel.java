package view.elements;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import view.ChangeEmitter;
import view.ChangeEmitterPanel;
import view.annotation.types.MappableAnnotationPanel;

import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

class DeleteTabListener implements ActionListener {
	private MappableAnnotationPanel panel;
	
	public DeleteTabListener (MappableAnnotationPanel panel) {
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.panel.deleteCurrentTab();
	}
}

class IdentifierListener implements DocumentListener {
	private MappableHeaderPanel panel;
	
	public IdentifierListener (MappableHeaderPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		this.panel.forwardChange();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		this.panel.forwardChange();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		this.panel.forwardChange();
	}
}

class CopyListener implements ActionListener {
	private MappableAnnotationPanel annotationPanel;
	
	public CopyListener (MappableAnnotationPanel annotationPanel) {
		this.annotationPanel = annotationPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

public class MappableHeaderPanel extends ChangeEmitterPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1514524989040596560L;	
	private JTextField nameTextField;
	private IdentifierListener listener;
	
	private JButton moveUpButton;
	private JButton moveDownButton;
	private JButton deleteButton;
	
	private MappableAnnotationPanel annotationPanel;
	private JLabel typeLabel;
	
	/**
	 * Create the panel.
	 */
	public MappableHeaderPanel(String type, ChangeEmitter emitter, MappableAnnotationPanel annotationPanel) {
		super(emitter);
		this.annotationPanel = annotationPanel;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		typeLabel = new JLabel(type);
		typeLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_typeLabel = new GridBagConstraints();
		gbc_typeLabel.gridwidth = 2;
		gbc_typeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_typeLabel.gridx = 0;
		gbc_typeLabel.gridy = 0;
		add(typeLabel, gbc_typeLabel);
		
		JLabel nameLabel = new JLabel("Identifier:");
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.anchor = GridBagConstraints.EAST;
		gbc_nameLabel.gridx = 0;
		gbc_nameLabel.gridy = 1;
		add(nameLabel, gbc_nameLabel);
		
		nameTextField = new JTextField();
		this.listener = new IdentifierListener(this);
		nameTextField.getDocument().addDocumentListener(this.listener);
		
		GridBagConstraints gbc_nameTextField = new GridBagConstraints();
		gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameTextField.gridx = 1;
		gbc_nameTextField.gridy = 1;
		add(nameTextField, gbc_nameTextField);
		nameTextField.setColumns(10);
		
		JPanel tabButtonPanel = new JPanel();
		GridBagConstraints gbc_tabButtonPanel = new GridBagConstraints();
		gbc_tabButtonPanel.gridwidth = 2;
		gbc_tabButtonPanel.fill = GridBagConstraints.BOTH;
		gbc_tabButtonPanel.gridx = 0;
		gbc_tabButtonPanel.gridy = 2;
		add(tabButtonPanel, gbc_tabButtonPanel);
		tabButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		moveUpButton = new JButton("Move up");
		moveUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				annotationPanel.moveCurrentTabUp();
			}
		});
		tabButtonPanel.add(moveUpButton);
		
		moveDownButton = new JButton("Move down");
		moveDownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				annotationPanel.moveCurrentTabDown();
			}
		});
		tabButtonPanel.add(moveDownButton);
		
		deleteButton = new JButton("Delete annotation");
		deleteButton.addActionListener(new DeleteTabListener(this.annotationPanel));
		tabButtonPanel.add(deleteButton);
	}

	public void setTextFieldEnabled (boolean enabled) {
		this.nameTextField.setEnabled(enabled);
		
		if (enabled) {
			if (this.listener == null) {
				this.listener = new IdentifierListener(this);
				this.nameTextField.getDocument().addDocumentListener(listener);
			}
		} else {
			if (this.listener != null) {
				this.nameTextField.getDocument().removeDocumentListener(listener);
				this.listener = null;
			}
		}
	}
	
	public void setTextField (String string) {
		boolean initial = this.listener != null;
		
		this.setTextFieldEnabled(false);
		this.nameTextField.setText(string);
		this.setTextFieldEnabled(initial);
	}
	
	public String getTextField () {
		return this.nameTextField.getText();
	}
	
	@Override
	public void updateOnForwardedChange() {
		// Nichts
	}

}
