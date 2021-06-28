package view.frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.selection.MediaContainer;
import control.selection.MediaReferenceFactory;
import control.selection.WorkspaceMediaContainer;
import view.media.info.MediaInfoPanel;
import view.workspace.WorkspaceEditorPanel;

public class WorkspaceDialog<T> extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9095641736309652039L;
	private final WorkspaceEditorPanel<T> panel;
	
	/**
	 * Create the dialog.
	 */
	public WorkspaceDialog(JFrame frame, MediaContainer<T> mediaContainer, MediaReferenceFactory<T> factory, MediaInfoPanel<T> infoPanel) {
		super(frame, true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		
		this.panel = new WorkspaceEditorPanel<T>(mediaContainer, infoPanel, factory);
		this.getContentPane().add(this.panel, BorderLayout.CENTER);		
	}

	public WorkspaceMediaContainer<T> getWorkspaceMediaContainer () {
		return this.panel.getWorkspaceMediaContainer();
	}
}
