package control.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import control.selection.PaneledMediaContainer;
import control.selection.MediaContainer;
import control.selection.MediaReference;
import model.MediaType;
import model.annotation.Annotation;
import view.media.MediaAnnotationPanel;

public class AnnotationWorkspace<T> {
	private PaneledMediaContainer<T> mediaContainer;
	private Path savePath;
	
	public AnnotationWorkspace (MediaType mediaType, MediaAnnotationPanel<T> mediaAnnotator, List<MediaReference<T>> medias, List<Annotation> annotations,
			Path defaultSavePath) {
		this.mediaContainer = new PaneledMediaContainer<T>(mediaType, mediaAnnotator, medias, annotations);
		this.savePath = defaultSavePath;
	}
	
	public PaneledMediaContainer<T> getMediaContainer () {
		return this.mediaContainer;
	}
	
	public void setMediaContainer (MediaContainer<T> mediaContainer) {
		this.mediaContainer = new PaneledMediaContainer<T>(this.mediaContainer.getAnnotator(), mediaContainer);
	}
	
	protected void saveToPath (File file) throws IOException{
		this.mediaContainer.updateCurrentAnnotation();
		
		AnnotationIO.save(this, file, true);
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
	
	public void updateCurrentAnnotation () {
		this.mediaContainer.updateCurrentAnnotation();
	}
}
