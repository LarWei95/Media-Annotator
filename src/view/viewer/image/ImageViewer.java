package view.viewer.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import control.ViewAnnotationLink;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class ImageViewerMouseMotion extends MouseMotionAdapter {
	private final ImageViewer imageViewer;
	protected boolean active;
	
	public ImageViewerMouseMotion (ImageViewer imageViewer) {
		this.imageViewer = imageViewer;
		this.active = true;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (this.active) {
			// Update bei jeder gedrueckten Bewegung
			this.imageViewer.link.rectEditor.updateEnd(e.getX(), e.getY(), this.imageViewer.getScale());
			this.imageViewer.setCurrentRectangleToPanel();
			this.imageViewer.repaint();
		}
	}
}

class ImageViewerMouse extends MouseAdapter {
	private final ImageViewer imageViewer;
	protected boolean active;
	
	public ImageViewerMouse (ImageViewer imageViewer) {
		this.imageViewer = imageViewer;
		this.active = true;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (this.active) {
			this.imageViewer.link.rectEditor.updateStart(e.getX(), e.getY(), this.imageViewer.getScale());
			this.imageViewer.link.rectEditor.updateEnd(e.getX(), e.getY(), this.imageViewer.getScale());
			this.imageViewer.setCurrentRectangleToPanel();
			this.imageViewer.repaint();
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if (this.active) {
			this.imageViewer.link.rectEditor.updateEnd(e.getX(), e.getY(), this.imageViewer.getScale());
			this.imageViewer.setCurrentRectangleToPanel();
			this.imageViewer.repaint();
		}
	}
}

class ImageViewerComponent extends ComponentAdapter {
	private final ImageViewer imageViewer;
	
	public ImageViewerComponent (ImageViewer imageViewer) {
		this.imageViewer = imageViewer;
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		this.imageViewer.updateViewScale();
	}
}

public class ImageViewer extends BasicImageViewer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4255320709032356584L;

	protected ViewAnnotationLink link;
	
	private ImageViewerMouseMotion mouseMotion;
	private ImageViewerMouse mouse;
	
	/**
	 * Create the panel.
	 */
	public ImageViewer(ViewAnnotationLink link) {
		super();
		this.link = link;
		this.mouseMotion = new ImageViewerMouseMotion(this);
		this.mouse = new ImageViewerMouse(this);
		this.addMouseMotionListener(this.mouseMotion);
		this.addMouseListener(this.mouse);
	}
	
	public void setActive (boolean active) {
		this.mouseMotion.active = active;
		this.mouse.active = active;
	}
	
	@Override
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		
		Rectangle currentRect = this.link.rectEditor.getRectangle();
		
		Color existing = new Color(255, 255, 0);
		Color newColor = new Color(255, 0, 0);
		
		for (Rectangle rect: this.link.rectEditor.getRectangles()) {
			this.drawRectangle(g, rect, existing);
		}
		
		if (currentRect != null) {
			this.drawRectangle(g, currentRect, newColor);
		}
	}
	
	private void drawRectangle (Graphics g, Rectangle rect, Color color) {
		g.setColor(color);
		
		double scale = this.getScale();
		
		int x = (int) Math.round(rect.x * scale);
		int y = (int) Math.round(rect.y * scale);
		
		int width = (int) Math.round(rect.width * scale);
		int height = (int) Math.round(rect.height * scale);
		
		g.drawRect(x, y, width, height);
		
		x = x - 1;
		y = y - 1;
		
		g.drawRect(x, y, width, height);
		
		x = x + 2;
		y = y + 2;
		
		g.drawRect(x, y, width, height);		
	}
	
	protected void setCurrentRectangleToPanel () {
		this.link.setCurrentBox();
	}
	
}
