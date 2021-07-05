import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import control.clipboard.AnnotationClipboard;
import control.framefactory.FrameFactory;
import control.io.AnnotationWorkspace;
import control.selection.ImageReference;
import control.selection.MediaReference;
import control.selection.MediaReferenceFactory;
import model.annotation.Annotation;
import view.frame.MainFrame;
import view.media.ImageAnnotationPanel;
import view.media.info.ImageInfoPanel;
import view.workspace.WorkspaceAnnotationPanel;

public class TestWindow {

	private MainFrame frame;

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
		this.frame = FrameFactory.getBufferedImageMainFrame(new AnnotationClipboard());
	}

}
