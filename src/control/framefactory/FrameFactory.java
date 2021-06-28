package control.framefactory;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import control.io.AnnotationWorkspace;
import control.selection.MediaReference;
import control.selection.MediaReferenceFactory;
import model.annotation.Annotation;
import view.frame.MainFrame;
import view.media.ImageAnnotationPanel;
import view.media.info.ImageInfoPanel;
import view.media.info.MediaInfoPanel;
import view.workspace.WorkspaceAnnotationPanel;

public class FrameFactory {
	public static final MainFrame<BufferedImage> getBufferedImageMainFrame () {
		return new ImageFrameFactory().getFrame();
	}
}

abstract class AFrameFactory<T> {
	protected abstract WorkspaceAnnotationPanel<T> getWorkspace ();
	
	protected abstract WorkspaceAnnotationPanel<T> getWorkspace (List<MediaReference<T>> references, List<Annotation> annotations);
	
	protected abstract WorkspaceAnnotationPanel<T> getWorkspace (List<MediaReference<T>> references, List<Annotation> annotations, Path savePath);
	
	protected abstract WorkspaceAnnotationPanel<T> getWorkspace (Path savePath);
	
	protected abstract MediaInfoPanel<T> getMediaInfoPanel ();
	
	protected abstract MediaReferenceFactory<T> getFactory ();
	
	public final MainFrame<T> getFrame () {
		MediaInfoPanel<T> infoPanel = this.getMediaInfoPanel();
		MediaReferenceFactory<T> factory = this.getFactory();
		
		WorkspaceAnnotationPanel<T> wsPanel = this.getWorkspace();
		return new MainFrame<T>(wsPanel, factory, infoPanel);		
	}
	
	public final MainFrame<T> getFrame (List<MediaReference<T>> references, List<Annotation> annotations) {
		MediaInfoPanel<T> infoPanel = this.getMediaInfoPanel();
		MediaReferenceFactory<T> factory = this.getFactory();
		
		WorkspaceAnnotationPanel<T> wsPanel = this.getWorkspace(references, annotations);
		return new MainFrame<T>(wsPanel, factory, infoPanel);		
	}
	
	public final MainFrame<T> getFrame (List<MediaReference<T>> references, List<Annotation> annotations, Path savePath) {
		MediaInfoPanel<T> infoPanel = this.getMediaInfoPanel();
		MediaReferenceFactory<T> factory = this.getFactory();
		
		WorkspaceAnnotationPanel<T> wsPanel = this.getWorkspace(references, annotations, savePath);
		return new MainFrame<T>(wsPanel, factory, infoPanel);		
	}
	
	public final MainFrame<T> getFrame (Path savePath) {
		MediaInfoPanel<T> infoPanel = this.getMediaInfoPanel();
		MediaReferenceFactory<T> factory = this.getFactory();
		
		WorkspaceAnnotationPanel<T> wsPanel = this.getWorkspace(savePath);
		return new MainFrame<T>(wsPanel, factory, infoPanel);		
	}
}

class ImageFrameFactory extends AFrameFactory<BufferedImage> {

	@Override
	protected WorkspaceAnnotationPanel<BufferedImage> getWorkspace() {
		ImageAnnotationPanel annotationPanel = new ImageAnnotationPanel();
		AnnotationWorkspace<BufferedImage> workspace = new AnnotationWorkspace<BufferedImage>(
				annotationPanel,
				new ArrayList<MediaReference<BufferedImage>>(),
				new ArrayList<Annotation>(),
				null
			);
		
		WorkspaceAnnotationPanel<BufferedImage> wsPanel = new WorkspaceAnnotationPanel<BufferedImage>(workspace, annotationPanel);
		return wsPanel;
	}

	@Override
	protected WorkspaceAnnotationPanel<BufferedImage> getWorkspace(List<MediaReference<BufferedImage>> references,
			List<Annotation> annotations) {
		ImageAnnotationPanel annotationPanel = new ImageAnnotationPanel();
		AnnotationWorkspace<BufferedImage> workspace = new AnnotationWorkspace<BufferedImage>(
				annotationPanel,
				references,
				annotations,
				null
			);
		
		WorkspaceAnnotationPanel<BufferedImage> wsPanel = new WorkspaceAnnotationPanel<BufferedImage>(workspace, annotationPanel);
		return wsPanel;
	}

	@Override
	protected MediaInfoPanel<BufferedImage> getMediaInfoPanel() {
		return new ImageInfoPanel();
	}

	@Override
	protected MediaReferenceFactory<BufferedImage> getFactory() {
		return MediaReferenceFactory.getImageInstance();
	}

	@Override
	protected WorkspaceAnnotationPanel<BufferedImage> getWorkspace(List<MediaReference<BufferedImage>> references,
			List<Annotation> annotations, Path savePath) {
		ImageAnnotationPanel annotationPanel = new ImageAnnotationPanel();
		AnnotationWorkspace<BufferedImage> workspace = new AnnotationWorkspace<BufferedImage>(
				annotationPanel,
				references,
				annotations,
				savePath
			);
		
		WorkspaceAnnotationPanel<BufferedImage> wsPanel = new WorkspaceAnnotationPanel<BufferedImage>(workspace, annotationPanel);
		return wsPanel;
	}

	@Override
	protected WorkspaceAnnotationPanel<BufferedImage> getWorkspace(Path savePath) {
		ImageAnnotationPanel annotationPanel = new ImageAnnotationPanel();
		AnnotationWorkspace<BufferedImage> workspace = new AnnotationWorkspace<BufferedImage>(
				annotationPanel,
				new ArrayList<MediaReference<BufferedImage>>(),
				new ArrayList<Annotation>(),
				savePath
			);
		
		WorkspaceAnnotationPanel<BufferedImage> wsPanel = new WorkspaceAnnotationPanel<BufferedImage>(workspace, annotationPanel);
		return wsPanel;
	}
	
}
