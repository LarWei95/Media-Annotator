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
	
	public void setPath (String path) {
		try {
			this.path = Path.of(path);
			
			if (this.isValid()) {
				this.load();
			}
		} catch (Exception e) {
			this.path = null;
		}
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
		if (this.path != null) {
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
		} else {
			return false;
		}
	}
}
