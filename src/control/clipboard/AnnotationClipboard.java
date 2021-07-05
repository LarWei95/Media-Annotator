package control.clipboard;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Set;

import com.github.cliftonlabs.json_simple.JsonObject;

import model.annotation.Annotation;

public class AnnotationClipboard {
	public static final String CURRENT_KEY = "current";
	public static final String REPOSITORY_KEY = "repository";
	
	private Annotation annotation;
	private HashMap<String, Annotation> annotationRepository;
	
	public AnnotationClipboard () {
		this.clear();
	}
	
	public AnnotationClipboard (Annotation annotation, HashMap<String, Annotation> repo) {
		this.annotation = annotation;
		this.annotationRepository = repo;
	}
	
	public void clear () {
		this.annotation = null;
		this.annotationRepository = new HashMap<String, Annotation>();
	}
	
	
	
	public void setClipboard (AnnotationClipboard other) {
		this.annotation = other.annotation.copy();
		this.annotationRepository = new HashMap<String, Annotation>(other.annotationRepository.size());
		
		for (String key: other.annotationRepository.keySet()) {
			this.annotationRepository.put(key, other.annotationRepository.get(key));
		}
	}
	
	public void save (Path path) throws IOException {
		
	}
	
	public JsonObject getJsonable () {
		JsonObject dict = new JsonObject();
		dict.put(AnnotationClipboard.CURRENT_KEY, this.annotation.getJsonable());
		
		JsonObject repo = new JsonObject();
		
		for (String key: this.annotationRepository.keySet()) {
			repo.put(key, this.annotationRepository.get(key).getJsonable());
		}
		
		dict.put(AnnotationClipboard.REPOSITORY_KEY, repo);
		return dict;
	}
	
	public Annotation getAnnotation () {
		return this.annotation;
	}
	
	public void setAnnotation (Annotation annotation) {
		this.annotation = annotation.copy();
	}
	
	public void addToRepository (String key, Annotation annotation) {
		this.annotationRepository.put(key, annotation);
	}
	
	public void removeFromRepository (String key) {
		this.annotationRepository.remove(key);
	}
	
	public void setFromRepository (String key) {
		Annotation newAnnotation = this.annotationRepository.getOrDefault(key, null);
		
		if (newAnnotation != null) {
			this.annotation = newAnnotation;
		}
	}
	
	public void addCurrentToRepository (String key) {
		if (this.annotation != null) {
			this.addToRepository(key, this.annotation);
		}
	}
	
	public Set<String> getRepositoryKeys () {
		return this.annotationRepository.keySet();
	}
	
	public HashMap<String, Annotation> getRepository () {
		return this.annotationRepository;
	}
}
