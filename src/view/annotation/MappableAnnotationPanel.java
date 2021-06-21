package view.annotation;

import javax.swing.JPanel;

import view.elements.ChangeEmitter;
import view.elements.ChangeEmitterPanel;
import view.elements.MappablePanel;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JTabbedPane;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

class AnnotationAddAction implements ActionListener {
	private MappableAnnotationPanel annotationPanel;
	
	public AnnotationAddAction (MappableAnnotationPanel panel) {
		this.annotationPanel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.annotationPanel.addNewAnnotation();
	}
}

public abstract class MappableAnnotationPanel extends AnnotationPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7260127516676080872L;
	
	public static final String TYPE_CLASS_ANNO = "Class annotation";
	public static final String TYPE_MAP_ANNO = "Annotation map";
	public static final String TYPE_ARRAY_ANNO = "Annotation array";
	public static final String TYPE_BOX_ANNO = "Box annotation";
	
	public static final String[] TYPE_ALL = new String[] {
		TYPE_CLASS_ANNO,	
		TYPE_MAP_ANNO,
		TYPE_ARRAY_ANNO,
		TYPE_BOX_ANNO
	};
	public static final String[] TYPE_BASECLASS = new String[] {
			TYPE_CLASS_ANNO,
			TYPE_BOX_ANNO
		};
	
	private JComboBox<String> annotationTypeBox;
	
	protected ArrayList<MappablePanel> mappablePanels;
	protected ArrayList<AnnotationPanel> annotationPanels;
	protected JTabbedPane annotationTabs;
	protected String[] options;
	/**
	 * Create the panel.
	 */
	protected MappableAnnotationPanel(ChangeEmitter emitter, String[] options) {
		super(emitter);
		this.options = options;
		this.mappablePanels = new ArrayList<MappablePanel>();
		this.annotationPanels = new ArrayList<AnnotationPanel>();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		annotationTypeBox = new JComboBox<String>();
		annotationTypeBox.setModel(new DefaultComboBoxModel<String>(this.options));
		GridBagConstraints gbc_annotationTypeBox = new GridBagConstraints();
		gbc_annotationTypeBox.insets = new Insets(0, 0, 5, 0);
		gbc_annotationTypeBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_annotationTypeBox.gridx = 0;
		gbc_annotationTypeBox.gridy = 0;
		add(annotationTypeBox, gbc_annotationTypeBox);
		
		JButton addAnnotationButton = new JButton("Add annotation");
		addAnnotationButton.addActionListener(new AnnotationAddAction(this));
		GridBagConstraints gbc_addAnnotationButton = new GridBagConstraints();
		gbc_addAnnotationButton.insets = new Insets(0, 0, 5, 0);
		gbc_addAnnotationButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_addAnnotationButton.gridx = 0;
		gbc_addAnnotationButton.gridy = 1;
		add(addAnnotationButton, gbc_addAnnotationButton);
		
		annotationTabs = new JTabbedPane(JTabbedPane.RIGHT);
		GridBagConstraints gbc_annotationTabs = new GridBagConstraints();
		gbc_annotationTabs.fill = GridBagConstraints.BOTH;
		gbc_annotationTabs.gridx = 0;
		gbc_annotationTabs.gridy = 2;
		add(annotationTabs, gbc_annotationTabs);
		
		// JPanel panel = new JPanel();
		// annotationTabs.addTab("New tab", null, panel, null);
	}

	protected void addNewAnnotation () {
		String selected = (String) this.annotationTypeBox.getSelectedItem();
		
		AnnotationPanel newPanel;
		
		switch (selected) {
		case MappableAnnotationPanel.TYPE_CLASS_ANNO:
			newPanel = new ClassAnnotationPanel(null);
			break;
		case MappableAnnotationPanel.TYPE_MAP_ANNO:
			newPanel = new MapClassAnnotationPanel(null, this.options);
			break;
		case MappableAnnotationPanel.TYPE_ARRAY_ANNO:
			newPanel = new ArrayClassAnnotationPanel(null, this.options);
			break;
		case MappableAnnotationPanel.TYPE_BOX_ANNO:
			newPanel = new BoxAnnotationPanel(null);
			break;
		default:
			newPanel = null;
		}
		
		if (newPanel != null) {
			MappablePanel superPanel = new MappablePanel(selected, this, newPanel);
			this.mappablePanels.add(superPanel);
			this.annotationPanels.add(newPanel);
			
			String tabName = this.getDefaultTabName();
			boolean changeable = this.isIdentifierChangeable();
			
			this.annotationTabs.addTab(tabName, null, superPanel, null);
			superPanel.setIdentifierEnabled(changeable);
			superPanel.setAnnotationIdentifier(tabName);
			this.forwardChange();
		}
	}
	
	protected abstract String getDefaultTabName ();
	
	protected abstract boolean isIdentifierChangeable ();
	
	public final void deleteCurrentTab () {
		int selected = this.annotationTabs.getSelectedIndex();
		this.annotationTabs.remove(selected);
		this.mappablePanels.remove(selected);
		this.annotationPanels.remove(selected);
		this.updateTabNames();
		this.forwardChange();
	}
	
	public final void moveCurrentTabUp () {
		int i = this.annotationTabs.getSelectedIndex();
		int newIndex = i - 1;
		
		if (newIndex >= 0) {
			MappablePanel affectedMappablePanel = this.mappablePanels.get(i);
			AnnotationPanel affectedAnnotationPanel = this.annotationPanels.get(i);
			String originalTabName = this.annotationTabs.getTitleAt(i);
			
			this.mappablePanels.remove(i);
			this.mappablePanels.add(newIndex, affectedMappablePanel);
			
			this.annotationPanels.remove(i);
			this.annotationPanels.add(newIndex, affectedAnnotationPanel);
			
			this.annotationTabs.remove(i);
			this.annotationTabs.insertTab(originalTabName, null, affectedMappablePanel, null, newIndex);
			
			i = newIndex;
			
			this.forwardChange();
		}
		
		this.annotationTabs.setSelectedIndex(i);
	}
	
	public final void moveCurrentTabDown () {
		int i = this.annotationTabs.getSelectedIndex();
		int newIndex = i + 1;
		
		if (newIndex < this.annotationTabs.getTabCount()) {
			MappablePanel affectedMappablePanel = this.mappablePanels.get(i);
			AnnotationPanel affectedAnnotationPanel = this.annotationPanels.get(i);
			String originalTabName = this.annotationTabs.getTitleAt(i);
			
			this.mappablePanels.remove(i);
			this.mappablePanels.add(newIndex, affectedMappablePanel);
			
			this.annotationPanels.remove(i);
			this.annotationPanels.add(newIndex, affectedAnnotationPanel);
			
			this.annotationTabs.remove(i);
			this.annotationTabs.insertTab(originalTabName, null, affectedMappablePanel, null, newIndex);
			
			i = newIndex;
			
			this.forwardChange();
		}
		
		this.annotationTabs.setSelectedIndex(i);
	}
	
	protected void updateTabNames () {
		int count = this.mappablePanels.size();
		
		MappablePanel panel;
		String identifier;
		
		for (int i = 0; i < count; i++) {
			panel = this.mappablePanels.get(i);
			identifier = panel.getAnnotationIdentifier();
			this.annotationTabs.setTitleAt(i, identifier);
		}
	}
	
	@Override
	public void updateOnForwardedChange() {
		this.updateTabNames();		
	}
}