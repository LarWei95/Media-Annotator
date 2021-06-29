package view.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import control.io.AnnotationWorkspace;
import control.selection.ImageReference;
import control.selection.MediaContainer;
import control.selection.MediaReference;
import control.selection.MediaReferenceFactory;
import control.selection.PaneledMediaContainer;
import model.annotation.Annotation;
import view.media.info.ImageInfoPanel;
import view.media.info.MediaInfoPanel;
import view.workspace.WorkspaceAnnotationPanel;

class OpenWorkspaceListener<T> implements ActionListener {

	private MainFrame<T> frame;
	private MediaReferenceFactory<T> factory;
	private MediaInfoPanel<T> infoPanel;
	
	public OpenWorkspaceListener (MainFrame<T> frame, MediaReferenceFactory<T> factory, MediaInfoPanel<T> infoPanel) {
		this.frame = frame;
		this.factory = factory;
		this.infoPanel = infoPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.frame.updateCurrentAnnotation();
		MediaContainer<T> mediaContainer = new MediaContainer<T>(this.frame.getMediaContainer());
		
		WorkspaceDialog<T> dialog = new WorkspaceDialog<T>(this.frame, mediaContainer, this.factory, this.infoPanel);
		dialog.setVisible(true);
		
		MediaContainer<T> newMediaContainer = dialog.getWorkspaceMediaContainer();
		this.frame.setMediaContainer(newMediaContainer);
	}
}

abstract class AnnotationWorkspaceListener implements ActionListener {
	protected AnnotationWorkspace<?> workspace;
	
	public AnnotationWorkspaceListener (AnnotationWorkspace<?> workspace) {
		this.workspace = workspace;
	}
	
}

class SaveAsWorkspaceListener extends AnnotationWorkspaceListener {
	public SaveAsWorkspaceListener (AnnotationWorkspace<?> workspace) {
		super(workspace);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			this.workspace.saveAs();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

class SaveWorkspaceListener extends AnnotationWorkspaceListener {
	public SaveWorkspaceListener (AnnotationWorkspace<?> workspace) {
		super(workspace);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			this.workspace.saveAs();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

public class MainFrame<T> extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2594191483753611719L;
	private WorkspaceAnnotationPanel<T> workspacePanel;
	
	private JPanel contentPane;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem fileNewItem;
	private JMenuItem fileOpenItem;
	private JMenuItem fileSaveItem;
	private JMenuItem fileSaveAsItem;
	private JSeparator separator;
	private JMenuItem fileQuitItem;
	private JMenu editMenu;
	private JMenuItem editWorkspaceMenuItem;
	
	private SaveAsWorkspaceListener saveAsListener;
	private SaveWorkspaceListener saveListener;
	
	/**
	 * Create the frame.
	 */
	public MainFrame(WorkspaceAnnotationPanel<T> workspacePanel, MediaReferenceFactory<T> factory, 
			MediaInfoPanel<T> infoPanel) {
		this.workspacePanel = workspacePanel;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		this.contentPane.add(workspacePanel);
		
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		fileNewItem = new JMenuItem("New");
		fileMenu.add(fileNewItem);
		
		fileOpenItem = new JMenuItem("Open");
		fileOpenItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		fileMenu.add(fileOpenItem);
		
		fileSaveAsItem = new JMenuItem("Save as ...");
		this.saveAsListener = new SaveAsWorkspaceListener(workspacePanel.getAnnotationWorkspace());
		fileSaveAsItem.addActionListener(this.saveAsListener);
		
		fileSaveItem = new JMenuItem("Save");
		this.saveListener = new SaveWorkspaceListener(workspacePanel.getAnnotationWorkspace());
		fileSaveItem.addActionListener(this.saveListener);
		fileMenu.add(fileSaveItem);
		fileMenu.add(fileSaveAsItem);
		
		separator = new JSeparator();
		fileMenu.add(separator);
		
		fileQuitItem = new JMenuItem("Quit");
		fileMenu.add(fileQuitItem);
		
		editMenu = new JMenu("New menu");
		menuBar.add(editMenu);
		
		editWorkspaceMenuItem = new JMenuItem("Workspace ...");
		editWorkspaceMenuItem.addActionListener(new OpenWorkspaceListener<T>(this, factory, infoPanel));
		editMenu.add(editWorkspaceMenuItem);
	}
	
	protected PaneledMediaContainer<T> getMediaContainer () {
		return this.workspacePanel.getMediaContainer();
	}
	
	protected void setMediaContainer (MediaContainer<T> container) {
		MediaContainer<T> old = this.workspacePanel.getMediaContainer();
		
		for (int i = 0; i < old.getMedias().size(); i++) {
			System.out.println("Old: "+old.getMedias().get(i)+" "+old.getAnnotations().get(i));
		}		
		
		this.workspacePanel.setMediaContainer(container);
		
		old = this.workspacePanel.getMediaContainer();
		
		for (int i = 0; i < old.getMedias().size(); i++) {
			System.out.println("New: "+old.getMedias().get(i)+" "+old.getAnnotations().get(i));
		}
		
		this.validate();
	}
	
	protected void updateCurrentAnnotation () {
		this.workspacePanel.getAnnotationWorkspace().updateCurrentAnnotation();
	}
}
