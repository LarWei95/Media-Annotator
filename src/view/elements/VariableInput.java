package view.elements;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Insets;

public class VariableInput extends ChangeEmitterPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8056671622428951949L;
	private JLabel inputLabel;
	private JTextField textInputField;
	
	private ValueType valueType;
	
	/**
	 * Create the panel.
	 */
	public VariableInput(String labelText, ChangeEmitter superChange) {
		super(superChange);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		inputLabel = new JLabel(labelText);
		GridBagConstraints gbc_inputLabel = new GridBagConstraints();
		gbc_inputLabel.insets = new Insets(0, 0, 0, 5);
		gbc_inputLabel.anchor = GridBagConstraints.EAST;
		gbc_inputLabel.gridx = 0;
		gbc_inputLabel.gridy = 0;
		add(inputLabel, gbc_inputLabel);
		
		textInputField = new JTextField();
		textInputField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				forwardChange();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				forwardChange();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				forwardChange();
			}
			
		});
		
		GridBagConstraints gbc_textInputField = new GridBagConstraints();
		gbc_textInputField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textInputField.gridx = 1;
		gbc_textInputField.gridy = 0;
		add(textInputField, gbc_textInputField);
		textInputField.setColumns(10);
	}
	
	public final String getInputString () {
		return this.textInputField.getText();
	}

	@Override
	public void updateOnForwardedChange() {
		// Nichts
	}
	
	
	

}
