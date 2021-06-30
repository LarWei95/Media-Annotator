package control.annotation.editor;
import java.awt.Rectangle;
import java.util.ArrayList;
public class RectangleEditor {
	private  ArrayList<Rectangle> rectangles;
	
	private int[] start;
	private Rectangle rectangle;
	
	public RectangleEditor () {
		this.rectangles = new ArrayList<Rectangle>();
		
		this.start = null;
		
		this.rectangle = null;
	}
	
	public void clearStoredRectangles () {
		this.rectangles.clear();
	}
	
	public void clearCurrentRectangle () {
		this.rectangle = null;
	}
	
	public void addRectangle (int xmin, int ymin, int xmax, int ymax) {
		int temp;
		
		if (xmin > xmax) {
			temp = xmin;
			xmin = xmax;
			
			xmax = temp;
		}
		
		if (ymin > ymax) {
			temp = ymin;
			ymin = ymax;
			
			ymax = temp;
		}
		
		xmax = xmax - xmin;
		ymax = ymax - ymin;
		
		Rectangle rectangle = new Rectangle(xmin, ymin, xmax, ymax);
		this.rectangles.add(rectangle);
	}
	
	public void updateStart (int x, int y, double scale) {
		x = (int) Math.round(x / scale);
		y = (int) Math.round(y / scale);
		
		this.start = new int[] {x, y};
	}	
	
	public void updateEnd (int x, int y, double scale) {
		x = (int) Math.round(x / scale);
		y = (int) Math.round(y / scale);
		
		this.updateRectangle(x, y);
	}
	
	private void updateRectangle (int endX, int endY) {
		int finalStartX;
		int finalEndX;
		int finalStartY;
		int finalEndY;
		
		if (endX > this.start[0]) {
			finalStartX = this.start[0];
			finalEndX = endX;
		} else {
			finalStartX = endX;
			finalEndX = this.start[0];
		}
		
		if (endY > this.start[1]) {
			finalStartY = this.start[1];
			finalEndY = endY;
		} else {
			finalStartY = endY;
			finalEndY = this.start[1];
		}
		
		int width = finalEndX - finalStartX;
		int height = finalEndY - finalStartY;
		
		this.rectangle = new Rectangle(finalStartX, finalStartY, width, height);
	}
	
	public Rectangle getRectangle () {
		return this.rectangle;
	}
	
	public ArrayList<Rectangle> getRectangles () {
		return this.rectangles;
	}
	
	public void addCurrentRectangle () {
		if (this.rectangle != null) {
			this.rectangles.add(this.rectangle);
		}
		
		this.resetCurrent();
	}
	
	public void resetCurrent () {
		this.start = null;
		this.rectangle = null;
	}
}
