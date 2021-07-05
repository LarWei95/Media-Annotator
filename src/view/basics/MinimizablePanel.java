package view.basics;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JToggleButton;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ItemEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

class SelectionListener implements ItemListener {
	private MinimizablePanel panel;
	
	public SelectionListener (MinimizablePanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			this.panel.setCurrentPanel();
		}
	}
	
}

public class MinimizablePanel extends JPanel {
	public static final String NONE_STRING = "---None---";
	/**
	 * 
	 */
	private static final long serialVersionUID = -5906559263488024300L;
	
	protected HashMap<String, JPanel> panels;
	protected JComboBox<String> panelComboBox;
	protected JPanel currentPanel;
	
	public MinimizablePanel() {
		this(new HashMap<String, JPanel>());
	}
	
	/**
	 * Create the panel.
	 */
	public MinimizablePanel(Map<String, JPanel> panels) {
		this.panels = new HashMap<String, JPanel>(panels);
		this.currentPanel = null;
		
		this.setLayout(new BorderLayout(0, 0));
		
		this.panelComboBox = new JComboBox<String>();
		this.panelComboBox.addItemListener(new SelectionListener(this));
		this.add(this.panelComboBox, BorderLayout.NORTH);
		
		this.setComboBoxModel();
	}

	private void setComboBoxModel () {
		String[] items = new String[this.panels.size() + 1];
		items[0] = MinimizablePanel.NONE_STRING;
		
		int i = 1;
		
		for (String key: this.panels.keySet()) {
			items[i] = key;
			i++;
		}
		
		this.panelComboBox.setModel(new DefaultComboBoxModel<String>(items));
		this.panelComboBox.setSelectedIndex(0);
	}
	
	protected void setCurrentPanel () {
		String selected = (String)this.panelComboBox.getSelectedItem();
		
		if (selected != null) {
			if (selected != MinimizablePanel.NONE_STRING) {
				JPanel newPanel = this.panels.get(selected);
				
				if (this.currentPanel != null) {
					this.remove(this.currentPanel);
				}
				
				this.add(newPanel, BorderLayout.CENTER);
				this.currentPanel = newPanel;
			} else {
				if (this.currentPanel != null) {
					this.remove(this.currentPanel);
				}
				
				this.currentPanel = null;
			}
			
			this.getRootPane().validate();
		}
	}
}
