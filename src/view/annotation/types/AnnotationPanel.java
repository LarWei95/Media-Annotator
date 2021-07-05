package view.annotation.types;

import control.ViewAnnotationLink;
import control.clipboard.AnnotationClipboard;
import model.annotation.Annotation;
import view.ChangeEmitter;
import view.ChangeEmitterPanel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CopyListener implements ActionListener {
	private AnnotationPanel panel;
	
	public CopyListener (AnnotationPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.panel.clipboard.setAnnotation(this.panel.getAnnotation());
	}
}

class PasteValuesListener implements ActionListener {
	private AnnotationPanel panel;
	
	public PasteValuesListener (AnnotationPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Annotation anno = this.panel.clipboard.getAnnotation();
		
		if (anno != null) {
			try {
				this.panel.setAnnotation(anno);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}

public abstract class AnnotationPanel extends ChangeEmitterPanel{
	protected final ViewAnnotationLink viewAnnotationLink;
	protected JPanel mainPanel;
	protected final AnnotationClipboard clipboard;
	
	protected CopyListener copyListener;
	protected PasteValuesListener pasteValuesListener;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2932795140531877268L;
	
	/**
	 * Create the panel.
	 */
	public AnnotationPanel(AnnotationClipboard clipboard, ChangeEmitter changeEmitter, ViewAnnotationLink viewAnnotationLink) {
		super(changeEmitter);
		
		this.clipboard = clipboard;
		this.viewAnnotationLink = viewAnnotationLink;
		
		this.copyListener = new CopyListener(this);
		this.pasteValuesListener = new PasteValuesListener(this);
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel clipboardActionPanel = new JPanel();
		add(clipboardActionPanel, BorderLayout.NORTH);
		GridBagLayout gbl_clipboardActionPanel = new GridBagLayout();
		gbl_clipboardActionPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_clipboardActionPanel.rowHeights = new int[]{0, 0};
		gbl_clipboardActionPanel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_clipboardActionPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		clipboardActionPanel.setLayout(gbl_clipboardActionPanel);
		
		JButton copyButton = new JButton("Copy");
		copyButton.addActionListener(this.copyListener);
		GridBagConstraints gbc_copyButton = new GridBagConstraints();
		gbc_copyButton.fill = GridBagConstraints.BOTH;
		gbc_copyButton.insets = new Insets(0, 0, 0, 5);
		gbc_copyButton.gridx = 0;
		gbc_copyButton.gridy = 0;
		clipboardActionPanel.add(copyButton, gbc_copyButton);
		
		JButton pasteButton = new JButton("Paste");
		GridBagConstraints gbc_pasteButton = new GridBagConstraints();
		gbc_pasteButton.fill = GridBagConstraints.BOTH;
		gbc_pasteButton.insets = new Insets(0, 0, 0, 5);
		gbc_pasteButton.gridx = 1;
		gbc_pasteButton.gridy = 0;
		clipboardActionPanel.add(pasteButton, gbc_pasteButton);
		
		JButton pasteValuesButton = new JButton("Paste values");
		pasteValuesButton.addActionListener(this.pasteValuesListener);
		GridBagConstraints gbc_pasteValuesButton = new GridBagConstraints();
		gbc_pasteValuesButton.fill = GridBagConstraints.BOTH;
		gbc_pasteValuesButton.gridx = 2;
		gbc_pasteValuesButton.gridy = 0;
		clipboardActionPanel.add(pasteValuesButton, gbc_pasteValuesButton);
		
		this.mainPanel = new JPanel();
		add(mainPanel, BorderLayout.CENTER);
	}
	
	public abstract void fillActivePanelContainer (ActivePanelContainer activePanelContainer);
	
	public abstract void setAnnotation (Annotation annotation);
	
	public abstract Annotation getAnnotation ();
	
	public abstract void clear();
}
