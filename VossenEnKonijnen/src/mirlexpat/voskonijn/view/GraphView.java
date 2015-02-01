package mirlexpat.voskonijn.view;

import java.awt.Color;
/**
 * Class with containing methods for all graphs.
 * 
 * @author Mirko Rog
 * @version 1.0
 */
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Map;

import javax.swing.JPanel;

import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Simulator;

public abstract class GraphView extends AbstractView {

	private Simulator sim;
	Dimension size;
	private Graphics g;
	private Image fieldImage;

	/**
	 * Create a new FieldView component.
	 */
	public GraphView(Simulator sim, Map<Class, Color> colors)
	{
		super(colors);
		this.sim = sim;
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
	/**
	 * Paints the color used in the backgrounds of the graphs.
	 */
	@Override
	public void update() {
		preparePaint();
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		render(g,sim.getField());
		repaint();
	}

	protected abstract void render(Graphics g, Field field);
	/**
	 * Draws a line to show the scale used in various graphs.
	 */
	protected void scaleLine() {
		int amount = 1;
		int h = getHeight();
		g.setColor(Color.DARK_GRAY);
		for(int j=0; j<h; j+=32){
			g.drawString(""+amount, 4, h-j-4);
			g.drawLine(0, h-j, getWidth(), h-j);
			amount *=16;
		}
	}
}
