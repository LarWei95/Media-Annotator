package control;

import java.util.HashMap;

import model.Annotation;
import model.ArrayClassAnnotation;
import model.BoxAnnotation;
import model.MapClassAnnotation;

public class VisualAnnotationCollector extends AnnotationCollector {

	private HashMap<BoxAnnotation, MapClassAnnotation> boxAnnotations;
	
	public VisualAnnotationCollector(Annotation rootAnnotation) {
		super(rootAnnotation);
	}

	public HashMap<BoxAnnotation, MapClassAnnotation> getBoxAnnotations () {
		return this.boxAnnotations;
	}
	
	@Override
	protected void collect(Annotation rootAnnotation) {
		HashMap<BoxAnnotation, MapClassAnnotation> annotations = new HashMap<BoxAnnotation, MapClassAnnotation>();
		VisualAnnotationCollector.collectBoxAnnotations(rootAnnotation, annotations);
		this.boxAnnotations = annotations;
	}

	private static void collectBoxAnnotations (Annotation annotation, HashMap<BoxAnnotation, MapClassAnnotation> annotations) {
		if (annotation instanceof BoxAnnotation) {
			BoxAnnotation anno = (BoxAnnotation) annotation;
			annotations.put(anno, anno.getMapClassAnnotation());
		} else if (annotation instanceof ArrayClassAnnotation) {
			ArrayClassAnnotation anno = (ArrayClassAnnotation) annotation;
			
			for (Annotation a: anno.getClasses()) {
				VisualAnnotationCollector.collectBoxAnnotations(a, annotations);
			}
		} else if (annotation instanceof MapClassAnnotation) {
			MapClassAnnotation anno = (MapClassAnnotation) annotation;
			HashMap<String, Annotation> subannos = anno.getClasses();
			Annotation subanno;
			
			for (String key: subannos.keySet()) {
				subanno = subannos.get(key);
				VisualAnnotationCollector.collectBoxAnnotations(subanno, annotations);
			}
		}
	}
}
