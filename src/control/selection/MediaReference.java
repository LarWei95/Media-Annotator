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
	
	public MediaReference (Path path, boolean load) throws IOException{
		this.path = path;
		
		if (load) {
			this.load();
		}
	}
	
	public MediaReference() {
		this.path = null;
		this.media = null;
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
	
	public boolean isValid () {
		if (this.path.toFile().isFile()) {
			try {
				this.loadMedia();
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}
}
