package model;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.Jsoner;

public class BoxAnnotation extends Annotation {
	
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
		
		builder.append("\"xmin\":");
		builder.append(this.startX);
		
		builder.append(",\"ymin\":");
		builder.append(this.startY);
		
		builder.append(",\"xmax\":");
		builder.append(this.endX);
		
		builder.append(",\"ymax\":");
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
		
		obj.put("xmin", this.startX);
		obj.put("ymin", this.startY);
		obj.put("xmax", this.endX);
		obj.put("ymax", this.endY);
		
		obj.put("annotation", this.annotation.getJsonable());
		
		return obj;
	}
}
