package control.selection;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.MessageDigest;

import model.Marking;

public abstract class MediaReference<T> {
	private Path path;
	private T media;
	
	private String checksum;
	private Marking marking;
	
	public MediaReference (Path path){
		this.path = path;
		
		this.media = null;
		this.checksum = null;
		this.marking = Marking.NONE;
	}
	
	public MediaReference (Path path, String checksum){
		this.path = path;
		
		this.media = null;
		this.checksum = checksum;
		this.marking = Marking.NONE;
	}
	
	public MediaReference (Path path, String checksum, Marking marking){
		this.path = path;
		
		this.media = null;
		this.checksum = checksum;
		this.marking = marking;
	}
	
	public Marking getMarking () {
		return this.marking;
	}
	
	public void setMarkin (Marking marking) {
		this.marking = marking;
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
	
	public T getMediaLoaded () {
		try {
			return this.loadMedia();
		} catch (Exception e) {
			return null;
		}
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
	
	protected static String bytesToHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		
		byte current;
		
		for (int i = 0; i < bytes.length; i++) {
			current = bytes[i];
			builder.append(String.format("%02X", current));
		}
		
		return builder.toString();
	}
	
	public String getMD5Checksum () {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			FileInputStream s = new FileInputStream(this.path.toFile());
			md.update(s.readAllBytes());
			
			byte[] bytes = md.digest();
			
			s.close();
			
			String str = bytesToHex(bytes);
			
			return str;
		} catch (Exception e) {
			return null;
		}
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
	
	public String getChecksum () {
		return this.checksum;
	}
	
	public void setChecksum (String checksum) {
		this.checksum = checksum;
	}
}
