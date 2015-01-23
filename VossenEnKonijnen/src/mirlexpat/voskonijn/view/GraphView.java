package mirlexpat.voskonijn.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import mirlexpat.voskonijn.logic.Field;

public abstract class GraphView extends AbstractView {

	private Field field;
	Dimension size;
	private Graphics g;
	private Image fieldImage;

	/**
	 * Create a new FieldView component.
	 */
	public GraphView(Field field)
	{
		this.field = field;
		size = new Dimension(0, 0);
	}

	/**
	 * Tell the GUI manager how big we would like to be.
	 */
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(192,127);
	}

	/**
	 * Prepare for a new round of painting. Since the component
	 * may be resized, compute the scaling factor again.
	 */
	public void preparePaint()
	{
		if(! size.equals(getSize())) {  // if the size has changed...
			size = getSize();
			fieldImage = this.createImage(size.width, size.height);
			g = fieldImage.getGraphics();
		}
	}

	/**
	 * The field view component needs to be redisplayed. Copy the
	 * internal image to screen.
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		if(fieldImage != null) {
			Dimension currentSize = getSize();
			if(size.equals(currentSize)) {
				g.drawImage(fieldImage, 0, 0, null);
			}
			else {
				// Rescale the previous image.
				g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
			}
		}
	}

	@Override
	public void update() {
		preparePaint();
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		render(g,field);
		repaint();
	}

	protected abstract void render(Graphics g, Field field);
}