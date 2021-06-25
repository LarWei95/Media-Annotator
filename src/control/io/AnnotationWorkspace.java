package control.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import control.selection.PaneledMediaContainer;
import control.selection.MediaReference;
import model.annotation.Annotation;
import view.annotation.media.MediaAnnotationPanel;

public class AnnotationWorkspace<T> {
	private final PaneledMediaContainer<T> mediaContainer;
	private Path savePath;
	
	public AnnotationWorkspace (MediaAnnotationPanel<T> mediaAnnotator, List<MediaReference<T>> medias, List<Annotation> annotations,
			Path defaultSavePath) {
		this.mediaContainer = new PaneledMediaContainer<T>(mediaAnnotator, medias, annotations);
		this.savePath = defaultSavePath;
	}
	
	public PaneledMediaContainer<T> getMediaContainer () {
		return this.mediaContainer;
	}
	
	protected void saveToPath (File file) throws IOException{
		this.mediaContainer.updateCurrentAnnotation();
		
		ArrayList<MediaReference<T>> medias = this.mediaContainer.getMedias();
		ArrayList<Annotation> annotations = this.mediaContainer.getAnnotations();
		
		AnnotationIO.save(medias, annotations, file, true);
		this.savePath = file.toPath();
	}
	
	public void saveAs () throws IOException {
		JFileChooser chooser = new JFileChooser();
		int ret = chooser.showSaveDialog(null);
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			this.saveToPath(file);
		}
	}
	
	public void save () throws IOException {
		if (this.savePath != null) {
			this.saveToPath(this.savePath.toFile());
		} else {
			this.saveAs();
		}
	}
}
