package view.media;

import javax.swing.JPanel;

import model.Marking;
import view.ChangeEmitter;
import view.ChangeEmitterPanel;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.util.List;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

class MarkingChangeListener implements ItemListener {

	private MarkingPanel markingPanel;
	public boolean active;
	
	public MarkingChangeListener (MarkingPanel panel) {
		this.markingPanel = panel;
		this.active = true;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (this.active) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				markingPanel.forwardChange();
			}
		}
	}
	
}

public class MarkingPanel extends ChangeEmitterPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2536466305719455007L;
	private JComboBox<Marking> comboBox;
	private MarkingChangeListener listener;
	/**
	 * Create the panel.
	 */
	public MarkingPanel(ChangeEmitter emitter) {
		super(emitter);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Marking");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		this.comboBox = new JComboBox<Marking>();
		this.listener = new MarkingChangeListener(this);
		comboBox.addItemListener(this.listener);
		Marking[] markingOptions = new Marking[] {
				Marking.NONE,
				Marking.UNFINISHED,
				Marking.DONE
		};
		
		comboBox.setModel(new DefaultComboBoxModel<Marking>(markingOptions));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 1;
		add(comboBox, gbc_comboBox);

	}

	public Marking getMarking () {
		return (Marking)this.comboBox.getSelectedItem();
	}
	
	public void setMarking (Marking marking) {
		this.listener.active = false;
		this.comboBox.setSelectedItem(marking);
		this.listener.active = true;
	}
	
	@Override
	public void updateOnForwardedChange() {
		// Nothing
	}

}
