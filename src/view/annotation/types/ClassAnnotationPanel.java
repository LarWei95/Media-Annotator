package view.annotation.types;
import model.Annotation;
import model.ClassAnnotation;
import view.ChangeEmitter;
import view.elements.VariableInput;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JCheckBox;

import control.ViewAnnotationLink;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class ClassAnnotationPanel extends AnnotationPanel {

	private VariableInput variableInput;
	private JCheckBox forceQuotesCheckbox;
	
	private ClassAnnotation classAnnotation;
	
	/**
	 * Create the panel.
	 */
	public ClassAnnotationPanel(ChangeEmitter changeEmitter, ViewAnnotationLink viewAnnotationLink) {
		super(changeEmitter, viewAnnotationLink);
		this.variableInput = new VariableInput("Class name:", this);
		this.classAnnotation = new ClassAnnotation("", false);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(this.variableInput, gbc_lblNewLabel);
		
		forceQuotesCheckbox = new JCheckBox("Force quotes");
		forceQuotesCheckbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				forwardChange();
			}
		});
		GridBagConstraints gbc_forceQuotesCheckbox = new GridBagConstraints();
		gbc_forceQuotesCheckbox.fill = GridBagConstraints.HORIZONTAL;
		gbc_forceQuotesCheckbox.gridx = 0;
		gbc_forceQuotesCheckbox.gridy = 1;
		add(forceQuotesCheckbox, gbc_forceQuotesCheckbox);
	}

	@Override
	public void updateOnForwardedChange() {
		this.classAnnotation = new ClassAnnotation(this.variableInput.getInputString(), this.forceQuotesCheckbox.isSelected());
	}

	@Override
	public Annotation getAnnotation() {
		return this.classAnnotation;
	}

	@Override
	public void fillActivePanelContainer(ActivePanelContainer activePanelContainer) {
		activePanelContainer.setClassAnnotationPanel(this);
	}

	@Override
	public void setAnnotation(Annotation annotation) {
		ClassAnnotation anno = (ClassAnnotation) annotation;
		this.classAnnotation = anno;
		this.variableInput.setInputString(anno.getClassAnnotation());
	}

}
