package view.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonException;

import control.clipboard.AnnotationClipboard;
import control.framefactory.FrameFactory;
import control.io.AnnotationIO;
import control.io.AnnotationWorkspace;
import control.selection.ImageReference;
import control.selection.MediaContainer;
import control.selection.MediaReference;
import control.selection.MediaReferenceFactory;
import control.selection.PaneledMediaContainer;
import model.MediaType;
import model.annotation.Annotation;
import view.media.info.ImageInfoPanel;
import view.media.info.MediaInfoPanel;
import view.workspace.WorkspaceAnnotationPanel;

class OpenWorkspaceListener<T> implements ActionListener {

	private ActionContainer<T> actionContainer;
	private MediaReferenceFactory<T> factory;
	private MediaInfoPanel<T> infoPanel;
	
	public OpenWorkspaceListener (ActionContainer<T> actionContainer, MediaReferenceFactory<T> factory, MediaInfoPanel<T> infoPanel) {
		this.actionContainer = actionContainer;
		this.factory = factory;
		this.infoPanel = infoPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.actionContainer.updateCurrentAnnotation();
		MediaContainer<T> mediaContainer = new MediaContainer<T>(this.actionContainer.getMediaContainer());
		
		WorkspaceDialog<T> dialog = new WorkspaceDialog<T>(this.actionContainer.frame, mediaContainer, this.factory, this.infoPanel);
		dialog.setVisible(true);
		
		MediaContainer<T> newMediaContainer = dialog.getWorkspaceMediaContainer();
		this.actionContainer.setMediaContainer(newMediaContainer);
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

class PasteToAllListener extends AnnotationWorkspaceListener {
	private AnnotationClipboard clipboard;
	
	public PasteToAllListener(AnnotationWorkspace<?> workspace, AnnotationClipboard clipboard) {
		super(workspace);
		this.clipboard = clipboard;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Annotation anno = this.clipboard.getAnnotation();
		
		if (anno != null) {
			PaneledMediaContainer<?> mediaContainer = this.workspace.getMediaContainer();
			
			for (int i = 0; i < mediaContainer.getMedias().size(); i++) {
				mediaContainer.setAnnotationAt(anno.copy(), i);
			}
		}
	}
}

class ActionContainer<T> extends MainFrameContainer<T>{
	protected MainFrame frame;
	
	protected SaveAsWorkspaceListener saveAsListener;
	protected SaveWorkspaceListener saveListener;
	
	protected OpenWorkspaceListener<T> openWorkspaceListener;
	protected PasteToAllListener pasteToAllListener;
	
	public ActionContainer(MainFrame frame, WorkspaceAnnotationPanel<T> workspacePanel, MediaReferenceFactory<T> factory, 
			MediaInfoPanel<T> infoPanel) {
		super(workspacePanel, factory, infoPanel);
		
		this.frame = frame;
		
		this.saveAsListener = new SaveAsWorkspaceListener(workspacePanel.getAnnotationWorkspace());
		this.saveListener = new SaveWorkspaceListener(workspacePanel.getAnnotationWorkspace());
		this.openWorkspaceListener = new OpenWorkspaceListener<T>(this, factory, infoPanel);
		this.pasteToAllListener = new PasteToAllListener(workspacePanel.getAnnotationWorkspace(), frame.clipboard);
	}
	
	public ActionContainer (MainFrame frame, MainFrameContainer<T> mainFrameContainer) {
		super(mainFrameContainer.workspacePanel, mainFrameContainer.factory, mainFrameContainer.infoPanel);
		
		this.frame = frame;
		
		this.saveAsListener = new SaveAsWorkspaceListener(workspacePanel.getAnnotationWorkspace());
		this.saveListener = new SaveWorkspaceListener(workspacePanel.getAnnotationWorkspace());
		this.openWorkspaceListener = new OpenWorkspaceListener<T>(this, factory, infoPanel);
	}
	
	protected void setMediaContainer (MediaContainer<T> container) {
		super.setMediaContainer(container);
		
		frame.validate();
	}
}

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2594191483753611719L;
	private ActionContainer<?> actionContainer;
	protected AnnotationClipboard clipboard;
	
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
	private JMenuItem pasteToAllMenuItem;
	
	/**
	 * @wbp.parser.constructor
	 */
	public MainFrame(AnnotationClipboard clipboard) {
		this.clipboard = clipboard;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		this.initializeMenu();
	}
	
	/**
	 * Create the frame.
	 */
	public <T> MainFrame(AnnotationClipboard clipboard, MainFrameContainer<T> mainFrameContainer) {
		this(clipboard);
		this.actionContainer = new ActionContainer<T>(this, mainFrameContainer);
		this.setCurrentElements();
	}
	
	public <T> void setMainFrameContainer (MainFrameContainer<T> mainFrameContainer) {
		this.removeCurrentElements();
		this.actionContainer = new ActionContainer<T>(this, mainFrameContainer);
		this.setCurrentElements();
	}
	
	protected void setCurrentElements () {
		if (this.actionContainer != null) {
			this.contentPane.add(this.actionContainer.workspacePanel);
			
			this.fileSaveAsItem.addActionListener(this.actionContainer.saveAsListener);
			this.fileSaveItem.addActionListener(this.actionContainer.saveListener);
			this.editWorkspaceMenuItem.addActionListener(this.actionContainer.openWorkspaceListener);
			
			this.pasteToAllMenuItem.addActionListener(this.actionContainer.pasteToAllListener);
		}
	}
	
	protected void removeCurrentElements () {
		if (this.actionContainer != null) {
			this.contentPane.remove(this.actionContainer.workspacePanel);
			
			this.fileSaveAsItem.removeActionListener(this.actionContainer.saveAsListener);
			this.fileSaveItem.removeActionListener(this.actionContainer.saveListener);
			this.editWorkspaceMenuItem.removeActionListener(this.actionContainer.openWorkspaceListener);
		}
	}
	
	private void initializeMenu () {
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		
		menuBar.add(fileMenu);
		
		fileNewItem = new JMenuItem("New");
		fileMenu.add(fileNewItem);
		
		fileOpenItem = new JMenuItem("Open");
		fileOpenItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadNewMainFrameContainer();
			}
		});
		fileMenu.add(fileOpenItem);
		
		fileSaveAsItem = new JMenuItem("Save as ...");
		fileSaveItem = new JMenuItem("Save");
		
		fileMenu.add(fileSaveItem);
		fileMenu.add(fileSaveAsItem);
		
		separator = new JSeparator();
		fileMenu.add(separator);
		
		fileQuitItem = new JMenuItem("Quit");
		fileMenu.add(fileQuitItem);
		
		editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
		editWorkspaceMenuItem = new JMenuItem("Workspace ...");
		editMenu.add(editWorkspaceMenuItem);
		
		pasteToAllMenuItem = new JMenuItem("Paste to all");
		editMenu.add(pasteToAllMenuItem);
	}
	
	@SuppressWarnings("unchecked")
	protected void loadNewMainFrameContainer () {
		JFileChooser chooser = new JFileChooser();
		int ret = chooser.showOpenDialog(this);
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				MediaContainer<?> mediaContainer = AnnotationIO.load(file);
				MediaType mediaType = mediaContainer.getMediaType();
				
				switch (mediaType) {
				case IMAGE:
					MainFrameContainer<BufferedImage> newContainer = FrameFactory.getBufferedImageMainFrameContainer(this.clipboard, (MediaContainer<BufferedImage>) mediaContainer, file.toPath());
					this.setMainFrameContainer(newContainer);
					break;
				}
				this.validate();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
