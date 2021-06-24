package control.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import control.selection.MediaReference;
import model.Annotation;

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
}
