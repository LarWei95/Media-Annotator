package view.elements;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSeparator;

import model.Annotation;
import view.annotation.types.AnnotationPanel;
import view.annotation.types.MappableAnnotationPanel;

import java.awt.BorderLayout;

public class MappablePanel extends ChangeEmitterPanel {
	protected MappableHeaderPanel header;
	protected AnnotationPanel annotationPanel;
	
	/**
	 * Create the panel.
	 */
	public MappablePanel(String type, MappableAnnotationPanel mappableAnnotationPanel, AnnotationPanel annotationPanel) {
		super(mappableAnnotationPanel);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		this.header = new MappableHeaderPanel(type, this, mappableAnnotationPanel);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(this.header, gbc_lblNewLabel);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		add(separator, gbc_separator);
		
		JPanel fillerPanel = new JPanel();
		GridBagConstraints gbc_fillerPanel = new GridBagConstraints();
		gbc_fillerPanel.fill = GridBagConstraints.BOTH;
		gbc_fillerPanel.gridx = 0;
		gbc_fillerPanel.gridy = 2;
		add(fillerPanel, gbc_fillerPanel);
		fillerPanel.setLayout(new BorderLayout(0, 0));
		
		this.annotationPanel = annotationPanel;
		this.annotationPanel.setSuperChangeEmitter(this);
		
		fillerPanel.add(this.annotationPanel, BorderLayout.CENTER);
		
	}

	public Annotation getAnnotation () {
		return this.annotationPanel.getAnnotation();
	}
	
	public String getAnnotationIdentifier () {
		return this.header.getTextField();
	}
	
	public void setAnnotationIdentifier (String string) {
		this.header.setTextField(string);
	}
	
	@Override
	public void updateOnForwardedChange() {
		// TODO Auto-generated method stub
		
	}
	
	public void setIdentifierEnabled (boolean enabled) {
		this.header.setTextFieldEnabled(enabled);
	}
}
