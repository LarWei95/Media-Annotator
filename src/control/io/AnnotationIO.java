package control.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.Jsoner;

import control.clipboard.AnnotationClipboard;
import control.selection.MediaContainer;
import control.selection.MediaReference;
import control.selection.MediaReferenceFactory;
import model.Marking;
import model.MediaType;
import model.annotation.Annotation;
import model.annotation.ArrayClassAnnotation;
import model.annotation.BoxAnnotation;
import model.annotation.ClassAnnotation;
import model.annotation.MapClassAnnotation;

public class AnnotationIO {
	public static final String KEY_METADATA = "metadata";
	public static final String KEY_ANNOTATIONS = "annotations";
	
	public static final String KEY_METADATA_MEDIATYPE = "mediatype";
	public static final String KEY_METADATA_ORDER = "order";
	public static final String KEY_METADATA_MD5 = "md5";
	public static final String KEY_METADATA_MARKINGS = "markings";
	
	public static <T> void save (AnnotationWorkspace<T> workspace, File outPath, boolean useAbsolutePaths) throws IOException {
		List<MediaReference<T>> medias = workspace.getMediaContainer().getMedias();
		List<Annotation> annotations = workspace.getMediaContainer().getAnnotations();
		MediaType mediaType = workspace.getMediaContainer().getMediaType();
		
		if (medias.size() == annotations.size()) {
			JsonObject finalAnnotations = AnnotationIO.collectAnnotations(medias, annotations, useAbsolutePaths);
			JsonObject metadata = AnnotationIO.collectMetadata(medias, mediaType);
			
			JsonObject finalObj = new JsonObject();
			finalObj.put(AnnotationIO.KEY_METADATA, metadata);
			finalObj.put(AnnotationIO.KEY_ANNOTATIONS, finalAnnotations);
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outPath));
			finalObj.toJson(writer);
			writer.close();
		} else {
			String errMsg = "The lengths of the media and annotation list are different.\nMedias: "+medias.size()+"\nAnnotations: "+annotations.size();
			throw new IllegalArgumentException(errMsg);
		}
	}
	
	private static <T> JsonObject collectMetadata (List<MediaReference<T>> medias, MediaType mediaType) {
		JsonObject metadata = new JsonObject();
		
		metadata.put(AnnotationIO.KEY_METADATA_MEDIATYPE, mediaType.getLabel());
		
		JsonObject md5s = new JsonObject();
		JsonArray order = new JsonArray();
		JsonArray markings = new JsonArray();
		
		String absPath;
		
		for (MediaReference<T> media: medias) {
			absPath = media.getPath().toAbsolutePath().toString();
			
			md5s.put(absPath, media.getChecksum());
			order.add(absPath);
			markings.add(media.getMarking().toString());
		}
		
		metadata.put(AnnotationIO.KEY_METADATA_MD5, md5s);
		metadata.put(AnnotationIO.KEY_METADATA_ORDER, order);
		metadata.put(AnnotationIO.KEY_METADATA_MARKINGS, markings);
		
		return metadata;
	}
	
	private static <T> JsonObject collectAnnotations (List<MediaReference<T>> medias, List<Annotation> annotations, boolean useAbsolutePaths) {
		JsonObject allAnnotations = new JsonObject();
		
		Path currentPath;
		String finalCurrentPath;
		Annotation currentAnnotation;
		Jsonable currentAnnotationJson;
		
		for (int i = 0; i < medias.size(); i++) {
			currentPath = medias.get(i).getPath();
			currentAnnotation = annotations.get(i);
			
			if (currentAnnotation == null) {
				currentAnnotationJson = new JsonObject();
			} else {
				currentAnnotationJson= currentAnnotation.getJsonable();
			}
			
			if (useAbsolutePaths) {
				finalCurrentPath = currentPath.toAbsolutePath().toString();
			} else {
				finalCurrentPath = currentPath.getFileName().toString();
			}
			
			allAnnotations.put(finalCurrentPath, currentAnnotationJson);
		}
		
		return allAnnotations;
	}
	
	protected static Annotation tryParseJsonObject (JsonObject jsonObject) {
		if (ClassAnnotation.hasRequirements(jsonObject)) {
			return new ClassAnnotation(jsonObject.get(ClassAnnotation.KEY_CLASS), false);
		} else if (BoxAnnotation.hasRequirements(jsonObject)) {
			MapClassAnnotation mapAnnos = parseMapClassAnnotation((JsonObject) jsonObject.get(BoxAnnotation.KEY_ANNOTATION));
			
			BigDecimal xmin = (BigDecimal) jsonObject.get(BoxAnnotation.KEY_XMIN);
			BigDecimal ymin = (BigDecimal) jsonObject.get(BoxAnnotation.KEY_YMIN);
			BigDecimal xmax = (BigDecimal) jsonObject.get(BoxAnnotation.KEY_XMAX);
			BigDecimal ymax = (BigDecimal) jsonObject.get(BoxAnnotation.KEY_YMAX);
			
			return new BoxAnnotation(
						xmin.intValue(),
						ymin.intValue(),
						xmax.intValue(),
						ymax.intValue(),
						mapAnnos
					);			
		} else {
			return parseMapClassAnnotation(jsonObject);
		}
	}
	
	protected static Annotation tryParseJsonable (Jsonable jsonable) {
		if (jsonable instanceof JsonArray) {
			return AnnotationIO.parseArrayClassAnnotation((JsonArray) jsonable);
		} else if (jsonable instanceof JsonObject) {
			return AnnotationIO.tryParseJsonObject((JsonObject) jsonable);
		} else {
			throw new IllegalArgumentException("Could not be parsed.");
		}
	}
	
	protected static MapClassAnnotation parseMapClassAnnotation (JsonObject obj) {
		HashMap<String, Annotation> classes = new HashMap<String, Annotation>(obj.size());
		
		Jsonable current;
		
		for (String key: obj.keySet()) {
			current = (Jsonable) obj.get(key);
			classes.put(key, AnnotationIO.tryParseJsonable(current));
		}
		
		return new MapClassAnnotation(classes);
	}
	
	protected static ArrayClassAnnotation parseArrayClassAnnotation (JsonArray jsonArray) {
		ArrayList<Annotation> classes = new ArrayList<Annotation>(jsonArray.size());
		
		for (Object obj: jsonArray) {
			classes.add(AnnotationIO.tryParseJsonable((Jsonable) obj));
		}
		
		return new ArrayClassAnnotation(classes);
	}
	
	protected static HashMap<Path, Annotation> parseAnnotationDict (JsonObject annotationDict) {
		HashMap<Path, Annotation> annotations = new HashMap<Path, Annotation>(annotationDict.size());
		
		Path currentPath;
		JsonObject subObject;
		
		for (String key: annotationDict.keySet()) {
			currentPath = Path.of(key);
			subObject = (JsonObject) annotationDict.get(key);
			
			annotations.put(currentPath, AnnotationIO.parseMapClassAnnotation(subObject));
		}
		
		return annotations;
	}
	
	public static HashMap<Path, String> getHashes (JsonObject metadata) {
		metadata = (JsonObject)metadata.get(AnnotationIO.KEY_METADATA_MD5);
		
		HashMap<Path, String> hashes = new HashMap<Path, String>(metadata.size());
		
		Path currentPath;
		String hash;
		
		for (String pathString: metadata.keySet()) {
			currentPath = Path.of(pathString);
			hash = (String)metadata.get(pathString);
			
			hashes.put(currentPath, hash);
		}
		
		return hashes;
	}
	
	public static ArrayList<Path> getOrder (JsonObject metadata) {
		JsonArray order = (JsonArray) metadata.get(AnnotationIO.KEY_METADATA_ORDER);
		ArrayList<Path> paths = new ArrayList<Path>(order.size());
		
		for (Object obj: order) {
			paths.add(Path.of((String) obj));
		}
		
		return paths;
	}
	
	public static ArrayList<Marking> getMarkings (JsonObject metadata, int pathCount) {
		JsonArray markings = (JsonArray) metadata.getOrDefault(AnnotationIO.KEY_METADATA_MARKINGS, null);
		ArrayList<Marking> markingsArray;
		
		if (markings == null) {
			markingsArray = new ArrayList<Marking>(pathCount);
			
			for (int i = 0; i < pathCount; i++) {
				markingsArray.add(Marking.NONE);
			}
		} else {
			if (pathCount != markings.size()) {
				throw new IllegalArgumentException("Unequal array length: "+pathCount+" "+markings.size());
			}
			
			markingsArray = new ArrayList<Marking>(pathCount);
			
			for (int i = 0; i < pathCount; i++) {
				markingsArray.add(Marking.ofString((String) markings.get(i)));
			}
		}
		
		return markingsArray;
	}
	
	public static MediaContainer<?> load (File inPath) throws IOException, JsonException {
		BufferedReader reader = new BufferedReader(new FileReader(inPath));
		
		JsonObject object = (JsonObject) Jsoner.deserialize(reader);
		JsonObject metadata = (JsonObject) object.get(AnnotationIO.KEY_METADATA);
		String mediaType = (String)metadata.get(AnnotationIO.KEY_METADATA_MEDIATYPE);
		JsonObject annotationDict = (JsonObject) object.get(AnnotationIO.KEY_ANNOTATIONS);
		
		HashMap<Path, String> hashes = AnnotationIO.getHashes(metadata);
		HashMap<Path, Annotation> annotations = parseAnnotationDict(annotationDict);
		ArrayList<Path> order = getOrder(metadata);
		ArrayList<Marking> markings = getMarkings(metadata, order.size());
		
		switch (mediaType) {
		case MediaType.IMAGE_STRING:
			return MediaReferenceFactory.getImageInstance().ofCollections(hashes, annotations, order, markings);
			default:
				String errMsg = "The media type could not be used: "+mediaType;
				throw new IllegalArgumentException(errMsg);
		}
	}
	
	public static void saveClipboard (AnnotationClipboard clipboard, File file) throws IOException {
		JsonObject main = new JsonObject();
		main.put(AnnotationClipboard.CURRENT_KEY, clipboard.getAnnotation().getJsonable());
		
		HashMap<String, Annotation> repository = clipboard.getRepository();
		JsonObject repo = new JsonObject();
		
		for (String key: repository.keySet()) {
			repo.put(key, repository.get(key).getJsonable());
		}
		
		main.put(AnnotationClipboard.REPOSITORY_KEY, repo);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		main.toJson(writer);
		writer.close();
	}
	
	public static AnnotationClipboard loadClipboard (File inPath) throws IOException, JsonException {
		BufferedReader reader = new BufferedReader(new FileReader(inPath));
		JsonObject object = (JsonObject) Jsoner.deserialize(reader);
		
		JsonObject current = (JsonObject) object.get(AnnotationClipboard.CURRENT_KEY);
		Annotation currentAnnotation = AnnotationIO.tryParseJsonObject(current);
		
		JsonObject repo = (JsonObject) object.get(AnnotationClipboard.REPOSITORY_KEY);
		
		HashMap<String, Annotation> repoAnnotations = new HashMap<String, Annotation>(repo.size());
		
		for (String key: repo.keySet()) {
			repoAnnotations.put(key, AnnotationIO.tryParseJsonObject((JsonObject)repo.get(key)));
		}
		
		return new AnnotationClipboard(currentAnnotation, repoAnnotations);
	}
}
