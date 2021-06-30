package model.annotation;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

public class BoxAnnotation extends Annotation {
	public static final String KEY_ANNOTATION = "annotation";
	public static final String KEY_XMIN = "xmin";
	public static final String KEY_YMIN = "ymin";
	public static final String KEY_XMAX = "xmax";
	public static final String KEY_YMAX = "ymax";
	public static final List<String> ALL_KEYS = Arrays.asList(KEY_ANNOTATION,
			KEY_XMIN, KEY_YMIN, KEY_XMAX, KEY_YMAX);
	
	
	private int startX;
	private int startY;
	
	private int endX;
	private int endY;
	
	private MapClassAnnotation annotation;
	
	public BoxAnnotation (int startX, int startY, int endX, int endY, MapClassAnnotation annotation) {
		this.startX = startX;
		this.startY = startY;
		
		this.endX = endX;
		this.endY = endY;
		
		this.annotation = annotation;
	}
	
	public MapClassAnnotation getMapClassAnnotation () {
		return this.annotation;
	}
	
	@Override
	public String getKeyString() {
		return "box";
	}
	
	public int getStartX () {
		return this.startX;
	}
	
	public int getStartY () {
		return this.startY;
	}
	
	public int getEndX () {
		return this.endX;
	}
	
	public int getEndY () {
		return this.endY;
	}

	@Override
	public String getValueString() {
		StringBuilder builder = new StringBuilder("{");
		
		builder.append("\""+KEY_XMIN+"\":");
		builder.append(this.startX);
		
		builder.append(",\""+KEY_YMIN+"\":");
		builder.append(this.startY);
		
		builder.append(",\""+KEY_XMAX+"\":");
		builder.append(this.endX);
		
		builder.append(",\""+KEY_YMAX+"\":");
		builder.append(this.endY);
		
		builder.append(",\"");
		builder.append(this.annotation.getKeyString());
		builder.append("\":");
		builder.append(this.annotation.getValueString());
		
		builder.append("}");
		return builder.toString();
	}
	
	@Override
	public Jsonable getJsonable() {
		JsonObject obj = new JsonObject();
		
		obj.put(KEY_XMIN, this.startX);
		obj.put(KEY_YMIN, this.startY);
		obj.put(KEY_XMAX, this.endX);
		obj.put(KEY_YMAX, this.endY);
		
		obj.put(KEY_ANNOTATION, this.annotation.getJsonable());
		
		return obj;
	}
	
	public static boolean hasRequirements (JsonObject jsonObject) {
		Set<String> keys = jsonObject.keySet();
		
		if (keys.containsAll(ALL_KEYS)) {
			boolean xminNum = jsonObject.get(KEY_XMIN) instanceof Number;
			boolean yminNum = jsonObject.get(KEY_YMIN) instanceof Number;
			boolean xmaxNum = jsonObject.get(KEY_XMAX) instanceof Number;
			boolean ymaxNum = jsonObject.get(KEY_YMAX) instanceof Number;
			boolean annotationObject  = jsonObject.get(KEY_ANNOTATION) instanceof JsonObject;
			
			return xminNum && yminNum && xmaxNum && ymaxNum && annotationObject;
		} else {
			return false;
		}
	}	
}
