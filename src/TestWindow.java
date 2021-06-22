import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import view.annotation.media.ImageAnnotationPanel;

public class TestWindow {

	private JFrame frame;

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
		ImageAnnotationPanel panel = new ImageAnnotationPanel();
		
		frame.getContentPane().add(panel);
		try {
			
			BufferedImage image = ImageIO.read(new File("G:\\Eclipse-Projekte\\GTA_Casino\\Screencaps\\2021-06-17---14-30-10-367495\\2021-06-17---14-30-13-477013.png"));
			panel.setImage(image);
		} catch (Exception e) {
			
		}
		
	}

}
