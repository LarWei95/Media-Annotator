package control.framefactory;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import control.io.AnnotationWorkspace;
import control.selection.MediaContainer;
import control.selection.MediaReference;
import control.selection.MediaReferenceFactory;
import model.MediaType;
import model.annotation.Annotation;
import view.frame.MainFrame;
import view.frame.MainFrameContainer;
import view.media.ImageAnnotationPanel;
import view.media.info.ImageInfoPanel;
import view.media.info.MediaInfoPanel;
import view.workspace.WorkspaceAnnotationPanel;

public class FrameFactory {
	public static final MainFrame getBufferedImageMainFrame () {
		return new ImageFrameFactory().getFrame();
	}
	
	public static final MainFrame getBufferedImageMainFrame (List<MediaReference<BufferedImage>> references, List<Annotation> annotations) {
		return new ImageFrameFactory().getFrame(references, annotations);
	}
	
	public static final MainFrame getBufferedImageMainFrame (List<MediaReference<BufferedImage>> references, List<Annotation> annotations, Path savePath) {
		return new ImageFrameFactory().getFrame(references, annotations, savePath);
	}
	
	public static final MainFrame getBufferedImageMainFrame (Path savePath) {
		return new ImageFrameFactory().getFrame(savePath);
	}
	
	public static final MainFrameContainer<BufferedImage> getBufferedImageMainFrameContainer () {
		return new ImageFrameFactory().getMainFrameContainer();
	}
	
	public static final MainFrameContainer<BufferedImage> getBufferedImageMainFrameContainer (List<MediaReference<BufferedImage>> references, List<Annotation> annotations) {
		return new ImageFrameFactory().getMainFrameContainer(references, annotations);
	}
	
	public static final MainFrameContainer<BufferedImage> getBufferedImageMainFrameContainer (List<MediaReference<BufferedImage>> references, List<Annotation> annotations, Path savePath) {
		return new ImageFrameFactory().getMainFrameContainer(references, annotations, savePath);
	}
	
	public static final MainFrameContainer<BufferedImage> getBufferedImageMainFrameContainer (Path savePath) {
		return new ImageFrameFactory().getMainFrameContainer(savePath);
	}
	
	public static final MainFrameContainer<BufferedImage> getBufferedImageMainFrameContainer (MediaContainer<BufferedImage> mediaContainer) {
		return new ImageFrameFactory().getMainFrameContainer(mediaContainer);
	}
	
	public static final MainFrameContainer<BufferedImage> getBufferedImageMainFrameContainer (MediaContainer<BufferedImage> mediaContainer, Path savePath) {
		return new ImageFrameFactory().getMainFrameContainer(mediaContainer, savePath);
	}
}

abstract class AFrameFactory<T> {
	protected abstract WorkspaceAnnotationPanel<T> getWorkspace ();
	
	protected abstract WorkspaceAnnotationPanel<T> getWorkspace (List<MediaReference<T>> references, List<Annotation> annotations);
	
	protected final WorkspaceAnnotationPanel<T> getWorkspace(MediaContainer<T> mediaContainer) {
		return this.getWorkspace(mediaContainer.getMedias(), mediaContainer.getAnnotations());
	}
	
	protected final WorkspaceAnnotationPanel<T> getWorkspace(MediaContainer<T> mediaContainer, Path savePath) {
		return this.getWorkspace(mediaContainer.getMedias(), mediaContainer.getAnnotations(), savePath);
	}
	
	protected abstract WorkspaceAnnotationPanel<T> getWorkspace (List<MediaReference<T>> references, List<Annotation> annotations, Path savePath);
	
	protected abstract WorkspaceAnnotationPanel<T> getWorkspace (Path savePath);
	
	protected abstract MediaInfoPanel<T> getMediaInfoPanel ();
	
	protected abstract MediaReferenceFactory<T> getFactory ();
	
	public final MainFrameContainer<T> getMainFrameContainer () {
		WorkspaceAnnotationPanel<T> workspacePanel = this.getWorkspace();
		MediaReferenceFactory<T> factory = this.getFactory();
		MediaInfoPanel<T> infoPanel = this.getMediaInfoPanel();
		return new MainFrameContainer<T>(workspacePanel, factory, infoPanel);
	}
	
	public final MainFrameContainer<T> getMainFrameContainer (List<MediaReference<T>> references, List<Annotation> annotations) {
		WorkspaceAnnotationPanel<T> workspacePanel = this.getWorkspace(references, annotations);
		MediaReferenceFactory<T> factory = this.getFactory();
		MediaInfoPanel<T> infoPanel = this.getMediaInfoPanel();
		return new MainFrameContainer<T>(workspacePanel, factory, infoPanel);
	}
	
	public final MainFrameContainer<T> getMainFrameContainer (List<MediaReference<T>> references, List<Annotation> annotations, Path savePath) {
		WorkspaceAnnotationPanel<T> workspacePanel = this.getWorkspace(references, annotations, savePath);
		MediaReferenceFactory<T> factory = this.getFactory();
		MediaInfoPanel<T> infoPanel = this.getMediaInfoPanel();
		return new MainFrameContainer<T>(workspacePanel, factory, infoPanel);
	}
	
	public final MainFrameContainer<T> getMainFrameContainer (Path savePath) {
		WorkspaceAnnotationPanel<T> workspacePanel = this.getWorkspace(savePath);
		MediaReferenceFactory<T> factory = this.getFactory();
		MediaInfoPanel<T> infoPanel = this.getMediaInfoPanel();
		return new MainFrameContainer<T>(workspacePanel, factory, infoPanel);
	}
	
	public final MainFrameContainer<T> getMainFrameContainer (MediaContainer<T> mediaContainer) {
		WorkspaceAnnotationPanel<T> workspacePanel = this.getWorkspace(mediaContainer);
		MediaReferenceFactory<T> factory = this.getFactory();
		MediaInfoPanel<T> infoPanel = this.getMediaInfoPanel();
		return new MainFrameContainer<T>(workspacePanel, factory, infoPanel);
	}
	
	public final MainFrameContainer<T> getMainFrameContainer (MediaContainer<T> mediaContainer, Path savePath) {
		WorkspaceAnnotationPanel<T> workspacePanel = this.getWorkspace(mediaContainer, savePath);
		MediaReferenceFactory<T> factory = this.getFactory();
		MediaInfoPanel<T> infoPanel = this.getMediaInfoPanel();
		return new MainFrameContainer<T>(workspacePanel, factory, infoPanel);
	}
	
	public final MainFrame getFrame () {
		return new MainFrame(this.getMainFrameContainer());
	}
	
	public final MainFrame getFrame (List<MediaReference<T>> references, List<Annotation> annotations) {
		return new MainFrame(this.getMainFrameContainer(references, annotations));
	}
	
	public final MainFrame getFrame (List<MediaReference<T>> references, List<Annotation> annotations, Path savePath) {
		return new MainFrame(this.getMainFrameContainer(references, annotations, savePath));
	}
	
	public final MainFrame getFrame (Path savePath) {
		return new MainFrame(this.getMainFrameContainer(savePath));
	}
	
	public final MainFrame getFrame (MediaContainer<T> mediaContainer) {
		return new MainFrame(this.getMainFrameContainer(mediaContainer));
	}
	
	public final MainFrame getFrame (MediaContainer<T> mediaContainer, Path savePath) {
		return new MainFrame(this.getMainFrameContainer(mediaContainer, savePath));
	}
}

class ImageFrameFactory extends AFrameFactory<BufferedImage> {

	@Override
	protected WorkspaceAnnotationPanel<BufferedImage> getWorkspace() {
		ImageAnnotationPanel annotationPanel = new ImageAnnotationPanel();
		AnnotationWorkspace<BufferedImage> workspace = new AnnotationWorkspace<BufferedImage>(
				MediaType.IMAGE,
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
				MediaType.IMAGE,
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
				MediaType.IMAGE,
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
				MediaType.IMAGE,
				annotationPanel,
				new ArrayList<MediaReference<BufferedImage>>(),
				new ArrayList<Annotation>(),
				savePath
			);
		
		WorkspaceAnnotationPanel<BufferedImage> wsPanel = new WorkspaceAnnotationPanel<BufferedImage>(workspace, annotationPanel);
		return wsPanel;
	}
	
}
