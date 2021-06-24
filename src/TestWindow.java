import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import control.io.AnnotationIO;
import control.io.AnnotationWorkspace;
import control.selection.ImageReference;
import control.selection.MediaContainer;
import control.selection.MediaReference;
import model.annotation.Annotation;
import view.annotation.media.ImageAnnotationPanel;
import view.frame.MainFrame;
import view.selection.ImageSelectorPanel;
import view.selection.MediaSelectionPanel;
import view.workspace.WorkspacePanel;

import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestWindow {

	private MainFrame frame;
	private ImageAnnotationPanel imageAnnotationPanel;
	private AnnotationWorkspace<BufferedImage> annotationWorkspace;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestWindow window = new TestWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.imageAnnotationPanel = new ImageAnnotationPanel();
		
		Path[] paths = new Path[] {
				Path.of("G:\\Eclipse-Projekte\\GTA_Casino\\Screencaps\\2021-06-17---14-30-10-367495\\2021-06-17---14-30-13-477013.png"),
				Path.of("G:\\Eclipse-Projekte\\GTA_Casino\\Screencaps\\2021-06-17---14-40-29-041192\\2021-06-17---14-42-38-533731.png")
		};
		
		ArrayList<MediaReference<BufferedImage>> images = new ArrayList<MediaReference<BufferedImage>>();
		ArrayList<Annotation> annotations = new ArrayList<Annotation>();
		
		for (Path path: paths) {
			System.out.println(path);
			try {
				images.add(new ImageReference(path, ImageIO.read(path.toFile())));
				annotations.add(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		this.annotationWorkspace = new AnnotationWorkspace<BufferedImage>(this.imageAnnotationPanel, images, annotations, null);
		
		WorkspacePanel<BufferedImage> wsPanel = new WorkspacePanel<BufferedImage>(this.annotationWorkspace, this.imageAnnotationPanel);
		this.frame = new MainFrame(wsPanel, this.annotationWorkspace);
	}

}
