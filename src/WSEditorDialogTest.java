import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import control.selection.ImageReference;
import control.selection.MediaContainer;
import control.selection.MediaReference;
import control.selection.MediaReferenceFactory;
import model.annotation.Annotation;
import view.ChangeEmitter;
import view.media.info.ImageInfoPanel;
import view.workspace.WorkspaceEditorPanel;

public class WSEditorDialogTest {	
	public static void main(String[] args) {
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
		
		MediaContainer<BufferedImage> mediaContainer = new MediaContainer<BufferedImage>(images, annotations);
		MediaReferenceFactory<BufferedImage> factory = MediaReferenceFactory.getImageInstance();
		ImageInfoPanel imageInfoPanel = new ImageInfoPanel();
		WorkspaceEditorPanel<BufferedImage> panel = new WorkspaceEditorPanel<BufferedImage>(mediaContainer, imageInfoPanel, factory);
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}

}
