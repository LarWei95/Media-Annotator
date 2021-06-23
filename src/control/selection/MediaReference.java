package control.selection;

import java.io.IOException;
import java.nio.file.Path;

public abstract class MediaReference<T> {
	private Path path;
	private T media;
	
	public MediaReference (Path path, T media) {
		this.path = path;
		this.media = media;
	}
	
	public Path getPath () {
		return this.path;
	}
	
	public T getMedia () {
		return this.media;
	}
	
	public abstract T loadMedia () throws IOException;
	
	public final void load () throws IOException{
		this.media = this.loadMedia();
	}
	
	public void unload () {
		this.media = null;
	}
	
	public boolean isLoaded () {
		return this.media != null;
	}
	
	public String getBaseName () {
		return this.path.getFileName().toString();
	}
}
