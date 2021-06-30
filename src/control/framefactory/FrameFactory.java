package control.framefactory;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import control.clipboard.AnnotationClipboard;
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
	public static final MainFrame getBufferedImageMainFrame (AnnotationClipboard clipboard) {
		return new ImageFrameFactory().getFrame(clipboard);
	}
	
	public static final MainFrame getBufferedImageMainFrame (AnnotationClipboard clipboard, List<MediaReference<BufferedImage>> references, List<Annotation> annotations) {
		return new ImageFrameFactory().getFrame(clipboard, references, annotations);
	}
	
	public static final MainFrame getBufferedImageMainFrame (AnnotationClipboard clipboard, List<MediaReference<BufferedImage>> references, List<Annotation> annotations, Path savePath) {
		return new ImageFrameFactory().getFrame(clipboard, references, annotations, savePath);
	}
	
	public static final MainFrame getBufferedImageMainFrame (AnnotationClipboard clipboard, Path savePath) {
		return new ImageFrameFactory().getFrame(clipboard, savePath);
	}
	
	public static final MainFrameContainer<BufferedImage> getBufferedImageMainFrameContainer (AnnotationClipboard clipboard) {
		return new ImageFrameFactory().getMainFrameContainer(clipboard);
	}
	
	public static final MainFrameContainer<BufferedImage> getBufferedImageMainFrameContainer (AnnotationClipboard clipboard, List<MediaReference<BufferedImage>> references, List<Annotation> annotations) {
		return new ImageFrameFactory().getMainFrameContainer(clipboard, references, annotations);
	}
	
	public static final MainFrameContainer<BufferedImage> getBufferedImageMainFrameContainer (AnnotationClipboard clipboard, List<MediaReference<BufferedImage>> references, List<Annotation> annotations, Path savePath) {
		return new ImageFrameFactory().getMainFrameContainer(clipboard, references, annotations, savePath);
	}
	
	public static final MainFrameContainer<BufferedImage> getBufferedImageMainFrameContainer (AnnotationClipboard clipboard, Path savePath) {
		return new ImageFrameFactory().getMainFrameContainer(clipboard, savePath);
	}
	
	public static final MainFrameContainer<BufferedImage> getBufferedImageMainFrameContainer (AnnotationClipboard clipboard, MediaContainer<BufferedImage> mediaContainer) {
		return new ImageFrameFactory().getMainFrameContainer(clipboard, mediaContainer);
	}
	
	public static final MainFrameContainer<BufferedImage> getBufferedImageMainFrameContainer (AnnotationClipboard clipboard, MediaContainer<BufferedImage> mediaContainer, Path savePath) {
		return new ImageFrameFactory().getMainFrameContainer(clipboard, mediaContainer, savePath);
	}
}

abstract class AFrameFactory<T> {
	protected abstract WorkspaceAnnotationPanel<T> getWorkspace (AnnotationClipboard clipboard);
	
	protected abstract WorkspaceAnnotationPanel<T> getWorkspace (AnnotationClipboard clipboard, List<MediaReference<T>> references, List<Annotation> annotations);
	
	protected final WorkspaceAnnotationPanel<T> getWorkspace(AnnotationClipboard clipboard, MediaContainer<T> mediaContainer) {
		return this.getWorkspace(clipboard, mediaContainer.getMedias(), mediaContainer.getAnnotations());
	}
	
	protected final WorkspaceAnnotationPanel<T> getWorkspace(AnnotationClipboard clipboard, MediaContainer<T> mediaContainer, Path savePath) {
		return this.getWorkspace(clipboard, mediaContainer.getMedias(), mediaContainer.getAnnotations(), savePath);
	}
	
	protected abstract WorkspaceAnnotationPanel<T> getWorkspace (AnnotationClipboard clipboard, List<MediaReference<T>> references, List<Annotation> annotations, Path savePath);
	
	protected abstract WorkspaceAnnotationPanel<T> getWorkspace (AnnotationClipboard clipboard, Path savePath);
	
	protected abstract MediaInfoPanel<T> getMediaInfoPanel ();
	
	protected abstract MediaReferenceFactory<T> getFactory ();
	
	public final MainFrameContainer<T> getMainFrameContainer (AnnotationClipboard clipboard) {
		WorkspaceAnnotationPanel<T> workspacePanel = this.getWorkspace(clipboard);
		MediaReferenceFactory<T> factory = this.getFactory();
		MediaInfoPanel<T> infoPanel = this.getMediaInfoPanel();
		return new MainFrameContainer<T>(workspacePanel, factory, infoPanel);
	}
	
	public final MainFrameContainer<T> getMainFrameContainer (AnnotationClipboard clipboard,List<MediaReference<T>> references, List<Annotation> annotations) {
		WorkspaceAnnotationPanel<T> workspacePanel = this.getWorkspace(clipboard, references, annotations);
		MediaReferenceFactory<T> factory = this.getFactory();
		MediaInfoPanel<T> infoPanel = this.getMediaInfoPanel();
		return new MainFrameContainer<T>(workspacePanel, factory, infoPanel);
	}
	
	public final MainFrameContainer<T> getMainFrameContainer (AnnotationClipboard clipboard,List<MediaReference<T>> references, List<Annotation> annotations, Path savePath) {
		WorkspaceAnnotationPanel<T> workspacePanel = this.getWorkspace(clipboard, references, annotations, savePath);
		MediaReferenceFactory<T> factory = this.getFactory();
		MediaInfoPanel<T> infoPanel = this.getMediaInfoPanel();
		return new MainFrameContainer<T>(workspacePanel, factory, infoPanel);
	}
	
	public final MainFrameContainer<T> getMainFrameContainer (AnnotationClipboard clipboard, Path savePath) {
		WorkspaceAnnotationPanel<T> workspacePanel = this.getWorkspace(clipboard, savePath);
		MediaReferenceFactory<T> factory = this.getFactory();
		MediaInfoPanel<T> infoPanel = this.getMediaInfoPanel();
		return new MainFrameContainer<T>(workspacePanel, factory, infoPanel);
	}
	
	public final MainFrameContainer<T> getMainFrameContainer (AnnotationClipboard clipboard, MediaContainer<T> mediaContainer) {
		WorkspaceAnnotationPanel<T> workspacePanel = this.getWorkspace(clipboard, mediaContainer);
		MediaReferenceFactory<T> factory = this.getFactory();
		MediaInfoPanel<T> infoPanel = this.getMediaInfoPanel();
		return new MainFrameContainer<T>(workspacePanel, factory, infoPanel);
	}
	
	public final MainFrameContainer<T> getMainFrameContainer (AnnotationClipboard clipboard, MediaContainer<T> mediaContainer, Path savePath) {
		WorkspaceAnnotationPanel<T> workspacePanel = this.getWorkspace(clipboard, mediaContainer, savePath);
		MediaReferenceFactory<T> factory = this.getFactory();
		MediaInfoPanel<T> infoPanel = this.getMediaInfoPanel();
		return new MainFrameContainer<T>(workspacePanel, factory, infoPanel);
	}
	
	public final MainFrame getFrame (AnnotationClipboard clipboard) {
		return new MainFrame(clipboard, this.getMainFrameContainer(clipboard));
	}
	
	public final MainFrame getFrame (AnnotationClipboard clipboard, List<MediaReference<T>> references, List<Annotation> annotations) {
		return new MainFrame(clipboard, this.getMainFrameContainer(clipboard, references, annotations));
	}
	
	public final MainFrame getFrame (AnnotationClipboard clipboard, List<MediaReference<T>> references, List<Annotation> annotations, Path savePath) {
		return new MainFrame(clipboard, this.getMainFrameContainer(clipboard, references, annotations, savePath));
	}
	
	public final MainFrame getFrame (AnnotationClipboard clipboard, Path savePath) {
		return new MainFrame(clipboard, this.getMainFrameContainer(clipboard, savePath));
	}
	
	public final MainFrame getFrame (AnnotationClipboard clipboard, MediaContainer<T> mediaContainer) {
		return new MainFrame(clipboard, this.getMainFrameContainer(clipboard, mediaContainer));
	}
	
	public final MainFrame getFrame (AnnotationClipboard clipboard, MediaContainer<T> mediaContainer, Path savePath) {
		return new MainFrame(clipboard, this.getMainFrameContainer(clipboard, mediaContainer, savePath));
	}
}

class ImageFrameFactory extends AFrameFactory<BufferedImage> {

	@Override
	protected WorkspaceAnnotationPanel<BufferedImage> getWorkspace(AnnotationClipboard clipboard) {
		ImageAnnotationPanel annotationPanel = new ImageAnnotationPanel(clipboard);
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
	protected WorkspaceAnnotationPanel<BufferedImage> getWorkspace(AnnotationClipboard clipboard, List<MediaReference<BufferedImage>> references,
			List<Annotation> annotations) {
		ImageAnnotationPanel annotationPanel = new ImageAnnotationPanel(clipboard);
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
	protected WorkspaceAnnotationPanel<BufferedImage> getWorkspace(AnnotationClipboard clipboard, List<MediaReference<BufferedImage>> references,
			List<Annotation> annotations, Path savePath) {
		ImageAnnotationPanel annotationPanel = new ImageAnnotationPanel(clipboard);
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
	protected WorkspaceAnnotationPanel<BufferedImage> getWorkspace(AnnotationClipboard clipboard, Path savePath) {
		ImageAnnotationPanel annotationPanel = new ImageAnnotationPanel(clipboard);
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
