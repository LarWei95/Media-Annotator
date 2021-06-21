package view;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

class BasicImageViewerComponent extends ComponentAdapter {
	private final BasicImageViewer imageViewer;
	
	public BasicImageViewerComponent (BasicImageViewer imageViewer) {
		this.imageViewer = imageViewer;
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		this.imageViewer.updateViewScale();
	}
}

public class BasicImageViewer extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5865244000476502792L;
	private BufferedImage image;
	private BufferedImage resizedImage;
	private double scale;
	
	private BasicImageViewerComponent component;
	
	/**
	 * Create the panel.
	 */
	public BasicImageViewer() {
		this.component = new BasicImageViewerComponent(this);
		this.addComponentListener(this.component);
	}
	
	public double getScale () {
		return this.scale;
	}
	
	public void setImage (BufferedImage image) {
		this.image = image;
		this.updateViewScale();
		this.repaint();
	}
	
	protected void updateViewScale () {
		double displayWidth = this.getWidth();
		double displayHeight = this.getHeight();
		
		double originalWidth = this.image.getWidth();
		double originalHeight = this.image.getHeight();
		
		double widthScale = displayWidth / originalWidth;
		double heightScale = displayHeight / originalHeight;
		
		this.scale = (widthScale < heightScale) ? widthScale : heightScale;
		this.updateViewImage();		
	}
	
	private void updateViewImage () {
		int newWidth = (int) Math.floor(this.image.getWidth() * this.scale);
		int newHeight = (int) Math.floor(this.image.getHeight() * this.scale);
		
		Image rescaledImage = this.image.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
		this.resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		this.resizedImage.getGraphics().drawImage(rescaledImage, 0, 0,  this);
		this.repaint();
	}
	
	@Override
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.resizedImage, 0, 0, this);
	}
}
