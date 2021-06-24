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

import control.selection.MediaReference;
import model.annotation.Annotation;
import model.annotation.ArrayClassAnnotation;
import model.annotation.BoxAnnotation;
import model.annotation.ClassAnnotation;
import model.annotation.MapClassAnnotation;

public class AnnotationIO {
	public static <T> void save (List<MediaReference<T>> medias, List<Annotation> annotations, File outPath, boolean useAbsolutePaths) throws IOException {
		JsonObject allAnnotations = new JsonObject();
		
		if (medias.size() == annotations.size()) {
			Path currentPath;
			String finalCurrentPath;
			Jsonable currentAnnotation;
			
			for (int i = 0; i < medias.size(); i++) {
				currentPath = medias.get(i).getPath();
				currentAnnotation = annotations.get(i).getJsonable();
				
				if (useAbsolutePaths) {
					finalCurrentPath = currentPath.toAbsolutePath().toString();
				} else {
					finalCurrentPath = currentPath.getFileName().toString();
				}
				
				allAnnotations.put(finalCurrentPath, currentAnnotation);
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outPath));
			allAnnotations.toJson(writer);
			writer.close();
		} else {
			String errMsg = "The lengths of the media and annotation list are different.\nMedias: "+medias.size()+"\nAnnotations: "+annotations.size();
			throw new IllegalArgumentException(errMsg);
		}
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
	
	public static HashMap<Path, Annotation> load (File inPath) throws IOException, JsonException {
		BufferedReader reader = new BufferedReader(new FileReader(inPath));
		
		JsonObject object = (JsonObject) Jsoner.deserialize(reader);
		HashMap<Path, Annotation> annotations = new HashMap<Path, Annotation>(object.size());
		
		Path currentPath;
		JsonObject subObject;
		
		for (String key: object.keySet()) {
			currentPath = Path.of(key);
			subObject = (JsonObject) object.get(key);
			
			annotations.put(currentPath, AnnotationIO.parseMapClassAnnotation(subObject));
		}
		
		return annotations;
	}
}
