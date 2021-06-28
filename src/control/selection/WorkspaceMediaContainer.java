package control.selection;

import java.util.ArrayList;
import java.util.List;

import model.annotation.Annotation;
import view.media.info.MediaInfoPanel;

public class WorkspaceMediaContainer<T> extends SelectionMediaContainer<T> {
	
	private final MediaInfoPanel<T> mediaInfoPanel;
	private final MediaReferenceFactory<T> mediaReferenceFactory;
	
	public WorkspaceMediaContainer (MediaInfoPanel<T> mediaInfoPanel, MediaReferenceFactory<T> mediaReferenceFactory) {
		this(new ArrayList<MediaReference<T>> (), new ArrayList<Annotation>(), mediaInfoPanel, mediaReferenceFactory);
	}
	
	public WorkspaceMediaContainer (List<MediaReference<T>> medias, List<Annotation> annotations, MediaInfoPanel<T> mediaInfoPanel,
			MediaReferenceFactory<T> mediaReferenceFactory) {
		super(medias, annotations);
		
		this.mediaInfoPanel = mediaInfoPanel;
		this.mediaReferenceFactory = mediaReferenceFactory;
	}
	
	public WorkspaceMediaContainer (MediaContainer<T> mediaContainer, MediaInfoPanel<T> mediaInfoPanel,
			MediaReferenceFactory<T> mediaReferenceFactory) {
		this(mediaContainer.medias, mediaContainer.annotations, mediaInfoPanel, mediaReferenceFactory);
	}
	
	public void updateCurrentMediaReference () {
		MediaReference<T> t = this.mediaInfoPanel.getMediaReference();
		
		if (t != null) {
			if (this.mediaIndex != -1) {
				this.medias.set(this.mediaIndex, t);
			}	
		}
	}
	
	public void setSelectedMedia (int index, boolean updateCurrent) {
		if (updateCurrent) {
			this.updateCurrentMediaReference();
		}
		
		this.mediaInfoPanel.clear();
		
		super.setSelectedMedia(index);
		
		if (this.mediaIndex != -1) {
			this.mediaInfoPanel.setMediaReference(this.medias.get(this.mediaIndex));
		}
	}
	
	public void setSelectedMedia (int index) {
		this.setSelectedMedia(index, true);
	}
	
	public void addEmptyMediaReference () {
		MediaReference<T> newRef = this.mediaReferenceFactory.generateEmpty();
		this.addBlankMedia(newRef);
		
		int newMediaIndex = this.medias.size() - 1;
		
		this.setSelectedMedia(newMediaIndex);	
	}
	
	public void removeCurrentMediaReference () {
		this.medias.remove(this.mediaIndex);
		this.annotations.remove(this.mediaIndex);
		
		int newMediaIndex = this.medias.size() - 1;
		
		System.out.println(this.medias.size()+" "+newMediaIndex);
		
		this.setSelectedMedia(newMediaIndex, false);	
	}
}
