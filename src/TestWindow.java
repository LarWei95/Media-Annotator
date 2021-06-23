import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import control.selection.ImageReference;
import control.selection.MediaContainer;
import control.selection.MediaReference;
import view.annotation.media.ImageAnnotationPanel;
import view.selection.ImageSelectorPanel;
import view.selection.MediaSelectionPanel;

import java.awt.BorderLayout;

public class TestWindow {

	private JFrame frame;
	private ImageAnnotationPanel imageAnnotationPanel;
	private MediaContainer<BufferedImage> imageContainer;
	private MediaSelectionPanel<BufferedImage> mediaSelectionPanel;

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
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 450, 300);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		this.imageAnnotationPanel = new ImageAnnotationPanel();
		
		frame.getContentPane().add(this.imageAnnotationPanel,BorderLayout.CENTER);
		
		this.imageContainer = new MediaContainer<BufferedImage>(this.imageAnnotationPanel);
		
		Path[] paths = new Path[] {
				Path.of("G:\\Eclipse-Projekte\\GTA_Casino\\Screencaps\\2021-06-17---14-30-10-367495\\2021-06-17---14-30-13-477013.png"),
				Path.of("G:\\Eclipse-Projekte\\GTA_Casino\\Screencaps\\2021-06-17---14-40-29-041192\\2021-06-17---14-42-38-533731.png")
		};
		
		for (Path path: paths) {
			System.out.println(path);
			try {
				this.imageContainer.addBlankMedia(new ImageReference(path, ImageIO.read(path.toFile())));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		this.mediaSelectionPanel = new MediaSelectionPanel<BufferedImage>(this.imageContainer);
		frame.getContentPane().add(this.mediaSelectionPanel, BorderLayout.WEST);
	}

}
