import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import view.ImageViewer;
import view.annotation.MapClassAnnotationPanel;
import view.annotation.MappableAnnotationPanel;
import view.elements.ChangeEmitter;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import model.Annotation;
import com.github.cliftonlabs.json_simple.Jsonable;

import control.RectangleEditor;
import control.ViewAnnotationLink;

public class TestWindow {

	private JFrame frame;	
	private MapClassAnnotationPanel panel;
	private RectangleEditor rectEditor;

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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.rectEditor = new RectangleEditor();
		ViewAnnotationLink link = new ViewAnnotationLink (this.rectEditor);
		
		try {
			
			BufferedImage image = ImageIO.read(new File("G:\\Eclipse-Projekte\\GTA_Casino\\Screencaps\\2021-06-17---14-30-10-367495\\2021-06-17---14-30-13-477013.png"));
			
			ImageViewer imagePanel = new ImageViewer(link);
			link.setImageViewer(imagePanel);
			frame.getContentPane().add(imagePanel, BorderLayout.CENTER);
			imagePanel.setImage(image);
			
		} catch (Exception e) {
			
		}
		panel = new MapClassAnnotationPanel(new ChangeEmitter() {
			@Override
			public void updateOnForwardedChange() {
				Annotation anno = panel.getAnnotation();
				link.updateViewedAnnotations(anno);
				
				// System.out.println(jsonable.toJson());
			}

			@Override
			public ChangeEmitter getSuperChangeEmitter() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void setSuperChangeEmitter(ChangeEmitter changeEmitter) {
				// TODO Auto-generated method stub
				
			}
			
		}, link, MappableAnnotationPanel.TYPE_ALL);
		link.setRootAnnotationPanel(this.panel);
		
		
		frame.getContentPane().add(panel, BorderLayout.EAST);
		
		
		
	}

}
